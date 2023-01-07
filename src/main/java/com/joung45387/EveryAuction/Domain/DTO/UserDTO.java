package com.joung45387.EveryAuction.Domain.DTO;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Getter
@Setter
public class UserDTO {
    private String username;
    private String password;
    private String name;
    private String phoneNumber;
    private String address;
}