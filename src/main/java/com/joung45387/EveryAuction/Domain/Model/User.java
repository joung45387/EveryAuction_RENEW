package com.joung45387.EveryAuction.Domain.Model;

import com.joung45387.EveryAuction.Domain.DTO.OAuthSignUpDTO;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String username;
    private String password;
    @Column(unique = true)
    private String name;
    private String phoneNumber;
    private String address;
    private String role = "normal"; //normal, admin
    private String provider;
    @CreationTimestamp
    private Timestamp createTime;

    public void updateOAuthUser(OAuthSignUpDTO oAuthSignUpDTO){
        phoneNumber = oAuthSignUpDTO.getPhoneNumber();
        name = oAuthSignUpDTO.getName();
        address = oAuthSignUpDTO.getAddress();
    }
}
