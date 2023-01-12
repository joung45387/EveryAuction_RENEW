package com.joung45387.EveryAuction.Repository.ChatRepository;

import com.joung45387.EveryAuction.Domain.Model.BidRecord;
import com.joung45387.EveryAuction.Domain.Model.Chat;
import com.joung45387.EveryAuction.Domain.Model.Item;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatRepositoryDataJPA extends JpaRepository<Chat, Long> {
    public List<Chat> findByItem(Item item);

}
