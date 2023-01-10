package com.joung45387.EveryAuction.Controller.Item;

import com.joung45387.EveryAuction.Domain.Model.Comment;
import com.joung45387.EveryAuction.Domain.Model.Item;
import com.joung45387.EveryAuction.Repository.CommentRepository.CommentRepository;
import com.joung45387.EveryAuction.Repository.ItemRepository.ItemRepository;
import com.joung45387.EveryAuction.Security.Auth.PrincipalDetails;
import com.joung45387.EveryAuction.Service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class CommentController {
    private final CommentRepository commentRepository;
    private final ItemRepository itemRepository;
    private final ItemService itemService;

    @PostMapping("/comment/item/{id}")
    public String commentUpload(@PathVariable("id") Long id,
                                @AuthenticationPrincipal PrincipalDetails principalDetails,
                                String content,
                                Model model){
        Item item = itemRepository.findByItemId(id);
        if(content.equals("")){
            model = itemService.setItemModel(model, item, principalDetails.getUser());
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
}
