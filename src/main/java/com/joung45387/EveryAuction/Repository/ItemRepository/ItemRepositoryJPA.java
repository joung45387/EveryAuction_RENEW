package com.joung45387.EveryAuction.Repository.ItemRepository;

import com.joung45387.EveryAuction.Domain.DTO.ItemDTO;
import com.joung45387.EveryAuction.Domain.Model.Item;
import com.joung45387.EveryAuction.Domain.Model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Repository
@Transactional
@RequiredArgsConstructor
public class ItemRepositoryJPA implements ItemRepository{
    private final ItemRepositoryDataJPA itemRepositoryDataJPA;
    private final EntityManager entityManager;

    @Override
    public Item saveItem(ItemDTO itemDTO, User user, String thumnailImageLink) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        Item item = Item.builder()
                .title(itemDTO.getTitle())
                .endTime(LocalDateTime.parse(itemDTO.getEndTime(), formatter))
                .thumbnailImage(thumnailImageLink)
                .startPrice(itemDTO.getStartPrice())
                .currentPrice(itemDTO.getStartPrice())
                .itemInformation(itemDTO.getContent())
                .seller(user)
                .build();
        System.out.println(item.getEndTime());
        entityManager.persist(item);
        return item;
    }

    @Override
    public Item findByItemId(Long id) {
        Item item = entityManager.find(Item.class, id);
        return item;
    }

    @Override
    public Item findImageAndItemByItemId(Long id) {
        Item item = entityManager.createQuery("select i from Item i join fetch i.itemImages where i.id=:id", Item.class)
                .setParameter("id", id)
                .getSingleResult();
        return item;
    }

    @Override
    public Item findSellerAndBuyerByItemId(Long id) {
        Item item = entityManager.createQuery("select i from Item i join fetch i.seller left join fetch i.buyer where i.id=:id", Item.class)
                .setParameter("id", id)
                .getSingleResult();
        return item;
    }

    @Override
    public void updateItemPrice(Item item, int price, User bidUser) {
        item.bidUpdate(price, bidUser);
    }

    @Override
    public List<Item> findByItemSellerId(User user) {
        List<Item> items = itemRepositoryDataJPA.findBySeller(user);
        return items;
    }

    @Override
    public List<Item> findAll(){
        TypedQuery<Item> query = entityManager.createQuery("select i from Item i where i.endTime> :now", Item.class)
                .setParameter("now", LocalDateTime.now(ZoneId.of("Asia/Seoul")));
        return query.getResultList();
    }
}
