package com.joung45387.EveryAuction.Repository.BidRecordRepository;

import com.joung45387.EveryAuction.Domain.Model.BidRecord;
import com.joung45387.EveryAuction.Domain.Model.Item;
import com.joung45387.EveryAuction.Domain.Model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Repository
@Transactional
@RequiredArgsConstructor
public class BidRecordRepositoryJPA implements BidRecordRepository{
    private final EntityManager entityManager;
    private final BidRecordRepositoryDataJPA bidRecordRepositoryDataJPA;
    @Override
    public BidRecord saveBidRecord(User user, Item item, int price) {
        BidRecord bidRecord = BidRecord.builder()
                .bidPrice(price)
                .bidTime(LocalDateTime.now(ZoneId.of("Asia/Seoul")))
                .bidUser(user)
                .item(item)
                .build();
        entityManager.persist(bidRecord);
        return bidRecord;
    }

    @Override
    public List<BidRecord> findByItem(Item item){
        return bidRecordRepositoryDataJPA.findByItem(item, Sort.by(Sort.Direction.DESC, "bidPrice"));
    }

    @Override
    public BidRecord findById(Long id){
        BidRecord bidRecord = entityManager.find(BidRecord.class, id);
        return bidRecord;
    }
}
