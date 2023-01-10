package com.joung45387.EveryAuction.Repository.ItemRepository;

import com.joung45387.EveryAuction.Domain.DTO.ItemDTO;
import com.joung45387.EveryAuction.Domain.Model.Item;
import com.joung45387.EveryAuction.Domain.Model.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ItemRepository {
    public Item saveItem(ItemDTO itemDTO, User user, byte[] file);
    public Item findByItemId(Long id);

    public void updateItemPrice(Item item, int price, User bidUser);

    public Item findByItemSellerId(Long id);

    List<Item> findAll();
}