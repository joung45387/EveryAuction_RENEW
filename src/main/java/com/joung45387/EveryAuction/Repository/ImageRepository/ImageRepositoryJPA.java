package com.joung45387.EveryAuction.Repository.ImageRepository;

import com.joung45387.EveryAuction.Domain.Model.Image;
import com.joung45387.EveryAuction.Domain.Model.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Repository
@Transactional
@RequiredArgsConstructor
public class ImageRepositoryJPA implements ImageRepository {
    private final EntityManager entityManager;
    @Override
    public void saveImage(String imageLink, Item item){
        Image image = Image.builder().item(item).imageLink(imageLink).build();
        entityManager.persist(image);
    }
}
