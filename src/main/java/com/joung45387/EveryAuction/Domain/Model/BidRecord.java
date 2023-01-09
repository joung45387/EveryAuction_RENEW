package com.joung45387.EveryAuction.Domain.Model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BidRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    private User bidUser;
    @ManyToOne(fetch = FetchType.LAZY)
    private Item item;
    private LocalDateTime bidTime;
    private int bidPrice;
}

