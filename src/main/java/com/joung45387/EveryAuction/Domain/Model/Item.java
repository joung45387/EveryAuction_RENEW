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

    @ManyToOne(fetch = FetchType.EAGER)
    private User seller;

    private int startPrice;
    @Lob
    @Column(columnDefinition = "MEDIUMBLOB")
    private byte[] itemPhoto;
    private String itemInformation;
    private String title;
    private LocalDateTime endTime;
    private int currentPrice;
    @ManyToOne(fetch = FetchType.LAZY)
    private User buyer;

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
    private List<BidRecord> bidRecords = new ArrayList<>();
    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

    public void bidUpdate(int price, User bidUser){
        currentPrice = price;
        buyer = bidUser;
    }
}
