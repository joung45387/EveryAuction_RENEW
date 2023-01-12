package com.joung45387.EveryAuction.Repository.ChatRepository;

import com.joung45387.EveryAuction.Domain.DTO.ChatDTO;
import com.joung45387.EveryAuction.Domain.Model.Chat;
import com.joung45387.EveryAuction.Domain.Model.Item;

import java.util.List;

public interface ChatRepository {
    List<Chat> findByItem(Long itemId);


    List<Chat> findByItemOneQuery(Long itemId);

    Chat saveChat(ChatDTO chatDTO, Long itemId);
}
