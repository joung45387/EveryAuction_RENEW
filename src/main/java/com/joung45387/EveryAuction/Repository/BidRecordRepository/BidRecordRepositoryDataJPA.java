package com.joung45387.EveryAuction.Repository.BidRecordRepository;

import com.joung45387.EveryAuction.Domain.Model.BidRecord;
import com.joung45387.EveryAuction.Domain.Model.Item;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BidRecordRepositoryDataJPA extends JpaRepository<BidRecord, Long> {
    public List<BidRecord> findByItem(Item item, Sort sort);
}
