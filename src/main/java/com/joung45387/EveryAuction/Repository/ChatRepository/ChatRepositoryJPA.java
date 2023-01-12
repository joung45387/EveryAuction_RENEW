package com.joung45387.EveryAuction.Repository.ChatRepository;

import com.joung45387.EveryAuction.Domain.DTO.ChatDTO;
import com.joung45387.EveryAuction.Domain.Model.Chat;
import com.joung45387.EveryAuction.Domain.Model.Item;
import com.joung45387.EveryAuction.Domain.Model.User;
import com.joung45387.EveryAuction.Repository.ItemRepository.ItemRepository;
import com.joung45387.EveryAuction.Repository.UserRepository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Repository
@Transactional
@RequiredArgsConstructor
public class ChatRepositoryJPA implements ChatRepository{
    private final ChatRepositoryDataJPA chatRepositoryDataJPA;
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final EntityManager entityManager;

    @Override
    public List<Chat> findByItem(Long itemId){
        Item item = itemRepository.findByItemId(itemId);
        return chatRepositoryDataJPA.findByItem(item);
    }
    @Override
    public List<Chat> findByItemOneQuery(Long itemId){
        Item item = itemRepository.findByItemId(itemId);
        List<Chat> chats = entityManager.createQuery("select c from Chat c join fetch c.sender where c.item = :item", Chat.class)
                .setParameter("item", item)
                .getResultList();
        return chats;
    }
    @Override
    public Chat saveChat(ChatDTO chatDTO, Long itemId){
        User user = userRepository.findByName(chatDTO.getSender());
        Item item = itemRepository.findByItemId(itemId);
        Chat chat = Chat.builder()
                .sender(user)
                .text(chatDTO.getText())
                .item(item)
                .chatTime(LocalDateTime.now(ZoneId.of("Asia/Seoul")))
                .build();
        entityManager.persist(chat);
        return chat;
    }
}
