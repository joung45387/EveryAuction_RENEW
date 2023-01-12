package com.joung45387.EveryAuction.Domain.DTO;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ChatDTO {
    private String sender;
    private String text;
    private LocalDateTime time;
}
