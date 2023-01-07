package com.joung45387.EveryAuction.Domain.DTO;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ItemDTO {
    String title;
    String content;
    int startPrice;
    String endTime;
}
