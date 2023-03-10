package com.joung45387.EveryAuction.Domain.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class PurchaseInfoDTO {
    Long id;
    int current_price;
    LocalDateTime end_time;
    String item_information;
    int start_price;
    String thumbnail;
    String title;
    Long buyer_id;
    Long seller_id;
}
