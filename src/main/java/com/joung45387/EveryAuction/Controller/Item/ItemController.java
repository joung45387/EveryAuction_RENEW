package com.joung45387.EveryAuction.Controller.Item;

import com.joung45387.EveryAuction.Domain.Model.Comment;
import com.joung45387.EveryAuction.Domain.Model.Item;
import com.joung45387.EveryAuction.Domain.Model.User;
import com.joung45387.EveryAuction.Repository.BidRecordRepository.BidRecordRepository;
import com.joung45387.EveryAuction.Repository.CommentRepository.CommentRepository;
import com.joung45387.EveryAuction.Repository.ItemRepository.ItemRepository;
import com.joung45387.EveryAuction.Security.Auth.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class ItemController {
    private final ItemRepository itemRepository;
    private final BidRecordRepository bidRecordRepository;
    private final CommentRepository commentRepository;
    @GetMapping("/item/{id}")
    public String saleItemUpload(@PathVariable("id") Long id,
                                 @AuthenticationPrincipal PrincipalDetails principalDetails,
                                 Model model){
        Item item = itemRepository.findByItemId(id);
        boolean login = (boolean) model.getAttribute("login");
        model = login?initModel(model, item, principalDetails.getUser()):initModel(model, item,null);
        return "ItemView";
    }

    @PostMapping("/bid/item/{id}")
    public String bidItem(@PathVariable("id") Long id,
                                 @AuthenticationPrincipal PrincipalDetails principalDetails,
                                 String cost,
                                 Model model){
        Item item = itemRepository.findByItemId(id);
        model = initModel(model, item, principalDetails.getUser());
        if(item.getEndTime().isBefore(LocalDateTime.now(ZoneId.of("Asia/Seoul")))){
            model.addAttribute("errorInfo", "경매가 끝나 입찰이 불가능 합니다.");
            return "ItemView";
        }
        if(item.getSeller() == principalDetails.getUser()){
            model.addAttribute("errorInfo", "판매자는 입찰에 참여할 수 없습니다.");
            return "ItemView";
        }
        if(item.getCurrentPrice()>=Integer.parseInt(cost)){
            model.addAttribute("errorInfo", "현재가 보다 낮은 입찰은 할수 없습니다.");
            return "ItemView";
        }
        itemRepository.updateItemPrice(item, Integer.parseInt(cost), principalDetails.getUser());
        bidRecordRepository.saveBidRecord(principalDetails.getUser(), item, Integer.parseInt(cost));
        return "redirect:/item/"+id;
    }

    @PostMapping("/comment/item/{id}")
    public String commentUpload(@PathVariable("id") Long id,
                                @AuthenticationPrincipal PrincipalDetails principalDetails,
                                String content,
                                Model model){
        Item item = itemRepository.findByItemId(id);
        if(content.equals("")){
            model = initModel(model, item, principalDetails.getUser());
            model.addAttribute("noComment", "공백은 입력할수 없습니다.");
            return "ItemView";
        }
        commentRepository.saveComment(item, principalDetails.getUser(), content);
        return "redirect:/item/"+id;
    }

    @PostMapping("/delete/comment/{commentId}/item/{itemId}")
    public String deleteComment(@PathVariable("commentId") Long commentId,
                                @PathVariable("itemId") Long itemId,
                                @AuthenticationPrincipal PrincipalDetails principalDetails,
                                Model model){
        Comment comment = commentRepository.findById(commentId);
        if(comment.getUser().getUsername().equals(principalDetails.getUsername())){
            commentRepository.deleteComment(comment);
        }
        return "redirect:/item/"+itemId;
    }

    @PostMapping("/edit/comment/{commentId}/item/{itemId}")
    public String editComment(@PathVariable("commentId") Long commentId,
                                @PathVariable("itemId") Long itemId,
                                @AuthenticationPrincipal PrincipalDetails principalDetails,
                                String content,
                                Model model){
        Comment comment = commentRepository.findById(commentId);
        if(comment.getUser().getUsername().equals(principalDetails.getUsername())){
            commentRepository.editComment(comment, content);
        }
        return "redirect:/item/"+itemId;
    }

    public Model initModel(Model model, Item item, User user){

        byte[] encoded = Base64.encodeBase64((byte[]) item.getItemPhoto());
        String encodedString= new String(encoded);

        List<Comment> comments = commentRepository.findByItem(item);
        List<Boolean> collect = comments.stream().map(c -> c.getUser().getUsername().equals(user.getUsername())).collect(Collectors.toList());
        model.addAttribute("item", item);
        model.addAttribute("photo", encodedString);
        model.addAttribute("possible", item.getEndTime().isAfter(LocalDateTime.now(ZoneId.of("Asia/Seoul"))));
        model.addAttribute("replies", comments);
        model.addAttribute("mine", collect);
        return model;
    }


}
