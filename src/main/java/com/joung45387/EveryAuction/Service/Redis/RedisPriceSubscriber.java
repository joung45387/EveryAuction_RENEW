package com.joung45387.EveryAuction.Service.Redis;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.joung45387.EveryAuction.Domain.DTO.ChatDTO;
import com.joung45387.EveryAuction.Domain.DTO.SimpleMessageDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
public class RedisPriceSubscriber implements MessageListener {
    private final ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
    private final SimpMessageSendingOperations simpMessageSendingOperations;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        GenericJackson2JsonRedisSerializer genericJackson2JsonRedisSerializer = new GenericJackson2JsonRedisSerializer();
        SimpleMessageDTO simpleMessageDTO = (SimpleMessageDTO)genericJackson2JsonRedisSerializer.deserialize(message.getBody());
        simpMessageSendingOperations.convertAndSend("/sub/itemPrice/"+simpleMessageDTO.getId(), simpleMessageDTO.getText());
    }
}
