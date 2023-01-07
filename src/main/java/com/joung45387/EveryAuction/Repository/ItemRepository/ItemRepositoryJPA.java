package com.joung45387.EveryAuction.Repository.ItemRepository;

import com.joung45387.EveryAuction.Domain.DTO.ItemDTO;
import com.joung45387.EveryAuction.Domain.Model.Item;
import com.joung45387.EveryAuction.Domain.Model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Repository
@Transactional
@RequiredArgsConstructor
public class ItemRepositoryJPA implements ItemRepository{
    private final ItemRepositoryDataJPA itemRepositoryDataJPA;
    private final EntityManager entityManager;

    @Override
    public Item saveItem(ItemDTO itemDTO, User user, byte[] file) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        Item item = Item.builder()
                .title(itemDTO.getTitle())
                .endTime(LocalDateTime.parse(itemDTO.getEndTime(), formatter))
                .startPrice(itemDTO.getStartPrice())
                .itemPhoto(file)
                .itemInformation(itemDTO.getContent())
                .seller(user)
                .build();
        entityManager.persist(item);
        return item;
    }

    @Override
    public Item findByItemId(Long id) {
        Item item = entityManager.find(Item.class, id);
        return item;
    }

    @Override
    public Item findByItemSellerId(Long id) {
        return null;
    }
}
