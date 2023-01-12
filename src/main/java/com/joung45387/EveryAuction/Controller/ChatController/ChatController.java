package com.joung45387.EveryAuction.Controller.ChatController;

import com.joung45387.EveryAuction.Domain.DTO.ChatDTO;
import com.joung45387.EveryAuction.Domain.Model.Chat;
import com.joung45387.EveryAuction.Repository.ChatRepository.ChatRepository;
import com.joung45387.EveryAuction.Security.Auth.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ChatController {
    private final ChatRepository chatRepository;
    private final SimpMessageSendingOperations simpMessageSendingOperations;
    @GetMapping("/chat/{productId}")
    public String SearchbidComplete(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                    Model model,
                                    @PathVariable("productId") Long id){

        List<Chat> chats = chatRepository.findByItemOneQuery(id);
        model.addAttribute("allChat", chats);
        model.addAttribute("productId", id);
        model.addAttribute("userName", principalDetails.getUser().getName());
        return "Chat";
    }

    @MessageMapping("/sendChat/{id}")
    public void chat(ChatDTO chatDTO, @DestinationVariable("id") Long id){
        Chat chat = chatRepository.saveChat(chatDTO, id);
        ChatDTO socketDTO = new ChatDTO();
        socketDTO.setSender(chat.getSender().getName());
        socketDTO.setText(chat.getText());
        socketDTO.setTime(chat.getChatTime());
        simpMessageSendingOperations.convertAndSend("/sub/itemChat/"+id, socketDTO);
    }


}
