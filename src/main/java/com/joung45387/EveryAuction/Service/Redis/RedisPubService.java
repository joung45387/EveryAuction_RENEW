package com.joung45387.EveryAuction.Service.Redis;

import com.joung45387.EveryAuction.Domain.DTO.ChatDTO;
import com.joung45387.EveryAuction.Domain.DTO.SimpleMessageDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedisPubService {
    private final RedisTemplate<String, Object> redisTemplate;

    public void sendPrice(ChatDTO chatDTO) {
        redisTemplate.convertAndSend("chat", chatDTO);
    }
    public void sendPrice(SimpleMessageDTO simpleMessageDTO) {
        redisTemplate.convertAndSend("price", simpleMessageDTO);
    }
}
