package com.joung45387.EveryAuction.Controller.Item;

import com.joung45387.EveryAuction.Domain.Model.Comment;
import com.joung45387.EveryAuction.Domain.Model.Item;
import com.joung45387.EveryAuction.Domain.Model.User;
import com.joung45387.EveryAuction.Repository.BidRecordRepository.BidRecordRepository;
import com.joung45387.EveryAuction.Repository.CommentRepository.CommentRepository;
import com.joung45387.EveryAuction.Repository.ItemRepository.ItemRepository;
import com.joung45387.EveryAuction.Security.Auth.PrincipalDetails;
import com.joung45387.EveryAuction.Service.ItemService;
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
    private final ItemService itemService;
    private final BidRecordRepository bidRecordRepository;
    private final CommentRepository commentRepository;

    @GetMapping("/item/{id}")
    public String saleItemUpload(@PathVariable("id") Long id,
                                 @AuthenticationPrincipal PrincipalDetails principalDetails,
                                 Model model){
        Item item = itemRepository.findByItemId(id);
        boolean login = (boolean) model.getAttribute("login");
        model = login? itemService.setItemModel(model, item, principalDetails.getUser()):itemService.setItemModel(model, item,null);
        return "ItemView";
    }

    @PostMapping("/bid/item/{id}")
    public String bidItem(@PathVariable("id") Long id,
                                 @AuthenticationPrincipal PrincipalDetails principalDetails,
                                 String cost,
                                 Model model){
        Item item = itemRepository.findByItemId(id);
        model = itemService.setItemModel(model, item, principalDetails.getUser());
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
}
