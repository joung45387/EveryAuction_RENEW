package com.joung45387.EveryAuction.Domain.Model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

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
    @Lob
    @Column(columnDefinition = "MEDIUMBLOB")
    private byte[] itemPhoto;
    private String itemInformation;
    private String title;
    private LocalDateTime endTime;
    private int currentPrice;
    @ManyToOne(fetch = FetchType.LAZY)
    private User buyer;

}
