package com.joung45387.EveryAuction.Domain.Model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User seller;

    private int startPrice;
    private String thumbnailImage;
    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
    private List<Image> itemImages;
    private String itemInformation;
    private String title;
    @Setter
    private LocalDateTime endTime;
    private int currentPrice;
    @ManyToOne(fetch = FetchType.LAZY)
    private User buyer;

    @Builder.Default
    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
    private List<BidRecord> bidRecords = new ArrayList<>();
    @Builder.Default
    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

    public void bidUpdate(int price, User bidUser){
        currentPrice = price;
        buyer = bidUser;
    }
}
