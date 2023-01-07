package com.joung45387.EveryAuction.Repository.ItemRepository;

import com.joung45387.EveryAuction.Domain.DTO.ItemDTO;
import com.joung45387.EveryAuction.Domain.Model.Item;
import com.joung45387.EveryAuction.Domain.Model.User;
import org.springframework.web.multipart.MultipartFile;

public interface ItemRepository {
    public Item saveItem(ItemDTO itemDTO, User user, byte[] file);
    public Item findByItemId(Long id);
    public Item findByItemSellerId(Long id);

}