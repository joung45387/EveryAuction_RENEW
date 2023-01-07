package com.joung45387.EveryAuction.Repository.ItemRepository;

import com.joung45387.EveryAuction.Domain.Model.Item;
import com.joung45387.EveryAuction.Domain.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepositoryDataJPA extends JpaRepository<Item, Long> {
    public Item findBySeller(User seller);
}
