package com.joung45387.EveryAuction.Controller.ChatController;

import com.joung45387.EveryAuction.Domain.DTO.ChatDTO;
import com.joung45387.EveryAuction.Domain.Model.Chat;
import com.joung45387.EveryAuction.Domain.Model.Item;
import com.joung45387.EveryAuction.Repository.ChatRepository.ChatRepository;
import com.joung45387.EveryAuction.Repository.ItemRepository.ItemRepository;
import com.joung45387.EveryAuction.Security.Auth.PrincipalDetails;
import com.joung45387.EveryAuction.Service.Redis.RedisPubService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ChatController {
    private final ChatRepository chatRepository;
    private final ItemRepository itemRepository;
    private final SimpMessageSendingOperations simpMessageSendingOperations;
    private final RedisPubService redisPubService;
    @GetMapping("/chat/{productId}")
    public String SearchbidComplete(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                    Model model,
                                    @PathVariable("productId") Long id){
        Item item = itemRepository.findSellerAndBuyerByItemId(id);
        if(item.getBuyer() == null || (!item.getSeller().getUsername().equals(principalDetails.getUsername()) && !item.getBuyer().getUsername().equals(principalDetails.getUsername()))){
            return "PermissionDenied";
        }
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
        socketDTO.setItemId(id);
        socketDTO.setText(chat.getText());
        socketDTO.setTime(chat.getChatTime());
        //redisPubService.sendPrice(socketDTO);
        simpMessageSendingOperations.convertAndSend("/topic/"+id, socketDTO);
    }


}
