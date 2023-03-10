package com.joung45387.EveryAuction.Repository.ItemRepository;

import com.joung45387.EveryAuction.Domain.DTO.ItemDTO;
import com.joung45387.EveryAuction.Domain.Model.Item;
import com.joung45387.EveryAuction.Domain.Model.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ItemRepository {
    public Item saveItem(ItemDTO itemDTO, User user, String thumnailImageLink);
    public Item findByItemId(Long id);

    Item findImageAndItemByItemId(Long id);

    Item findSellerAndBuyerByItemId(Long id);

    public void updateItemPrice(Item item, int price, User bidUser);

    public List<Item> findByItemSellerId(User user);

    List<Item> findAll();
}