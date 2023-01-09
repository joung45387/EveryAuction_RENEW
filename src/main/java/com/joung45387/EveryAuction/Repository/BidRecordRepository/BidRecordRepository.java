package com.joung45387.EveryAuction.Repository.BidRecordRepository;

import com.joung45387.EveryAuction.Domain.DTO.ItemDTO;
import com.joung45387.EveryAuction.Domain.Model.BidRecord;
import com.joung45387.EveryAuction.Domain.Model.Item;
import com.joung45387.EveryAuction.Domain.Model.User;

import java.util.List;

public interface BidRecordRepository {

    BidRecord saveBidRecord(User user, Item item, int price);

    List<BidRecord> findByItem(Item item);

    BidRecord findById(Long id);
}
