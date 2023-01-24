package com.joung45387.EveryAuction.Repository.BidRecordRepository;

import com.joung45387.EveryAuction.Domain.DTO.PurchaseInfoDTO;
import com.joung45387.EveryAuction.Domain.Model.BidRecord;
import com.joung45387.EveryAuction.Domain.Model.Item;
import com.joung45387.EveryAuction.Domain.Model.User;
import com.joung45387.EveryAuction.Repository.ItemRepository.ItemRepository;
import com.joung45387.EveryAuction.Repository.UserRepository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@Transactional
@RequiredArgsConstructor
public class BidRecordRepositoryJPA implements BidRecordRepository{
    private final EntityManager entityManager;
    private final BidRecordRepositoryDataJPA bidRecordRepositoryDataJPA;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;
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

    @Override
    public List<PurchaseInfoDTO> myPurchase(Long id){
        Query nativeQuery = entityManager.createNativeQuery("select i.* from bid_record b\n" +
                "join item i on b.item_id = i.id\n" +
                "where b.bid_user_id = "+id+"\n" +
                "and i.end_time < now() \n"+
                "group by b.item_id");
        List<Object[]> resultList = nativeQuery.getResultList();
        List<PurchaseInfoDTO> purchaseInfoDTOStream = resultList.stream().map(o -> new PurchaseInfoDTO(
                ((BigInteger) o[0]).longValue(),
                (int) o[1],
                ((Timestamp) o[2]).toLocalDateTime(),
                (String) o[3],
                (int) o[4],
                (String) o[5],
                (String) o[6],
                ((BigInteger) o[7]).longValue(),
                ((BigInteger) o[8]).longValue()
        )).collect(Collectors.toList());
        return purchaseInfoDTOStream;
    }

    @Override
    public List<Item> mySales(Long id){
        User user = userRepository.findByUserId(id);
        List<Item> sellerItem = entityManager.createQuery("select i from Item i left join fetch i.buyer where i.seller=:seller and i.endTime<now()", Item.class)
                .setParameter("seller", user)
                .getResultList();
        return sellerItem;
    }
}
