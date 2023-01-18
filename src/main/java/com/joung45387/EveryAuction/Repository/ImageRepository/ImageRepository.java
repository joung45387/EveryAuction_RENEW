package com.joung45387.EveryAuction.Repository.ImageRepository;

import com.joung45387.EveryAuction.Domain.Model.Item;

public interface ImageRepository {
    void saveImage(String imageLink, Item item);
}
