package com.joung45387.EveryAuction.Configuration.Interceptor;

import com.joung45387.EveryAuction.Domain.Model.Item;
import com.joung45387.EveryAuction.Domain.Model.User;
import com.joung45387.EveryAuction.Repository.ItemRepository.ItemRepository;
import com.joung45387.EveryAuction.Repository.UserRepository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

import java.security.Principal;


@Component
@RequiredArgsConstructor
public class TopicSubscriptionInterceptor implements ChannelInterceptor {
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor headerAccessor= StompHeaderAccessor.wrap(message);
        if (StompCommand.SUBSCRIBE.equals(headerAccessor.getCommand()) && headerAccessor.getDestination().split("/")[1].equals("topic")) {
            Principal userPrincipal = headerAccessor.getUser();
            if (!validateSubscription(userPrincipal, headerAccessor.getDestination())) {
                throw new IllegalArgumentException("No permission for this topic");
            }
        }
        return message;
    }
    //subscribe 인증 로직
    private boolean validateSubscription(Principal principal, String topicDestination) {
        if (principal == null) {
            // Unauthenticated user
            return false;
        }
        String name = principal.getName();
        String itemId = topicDestination.split("/")[2];
        Item item = itemRepository.findSellerAndBuyerByItemId(Long.parseLong(itemId));
        if(name.equals(item.getSeller().getUsername()) || name.equals(item.getBuyer().getUsername())){
            return true;
        }
        else {
            return false;
        }
    }
}
