package com.joung45387.EveryAuction.Service;

import com.joung45387.EveryAuction.Domain.Model.Comment;
import com.joung45387.EveryAuction.Domain.Model.Item;
import com.joung45387.EveryAuction.Domain.Model.User;
import com.joung45387.EveryAuction.Repository.CommentRepository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final CommentRepository commentRepository;
    public Model setItemModel(Model model, Item item, User user){
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
