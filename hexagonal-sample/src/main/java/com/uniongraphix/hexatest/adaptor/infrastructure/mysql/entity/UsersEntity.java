package com.uniongraphix.hexatest.adaptor.infrastructure.mysql.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class UsersEntity {
    // db에 들어갈 entity 형태. domain과 다를 수 있다
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String password;
    private String name;
    private String phone;
    private String address;

//    public static UsersEntity signInUsers(String email, String password) {
//        return UsersEntity.builder()
//            .email(email)
//            .password(password)
//            .build();
//    }

    // Entity는 DB에 저장되는 객체라고 생각하면 됨. 따라서 Persistence Adapter에서 사용된다
    public static UsersEntity signUpUsers(String email, String password, String name, String phone, String address) {
        return UsersEntity.builder()
            .email(email)
            .password(password)
            .name(name)
            .phone(phone)
            .address(address)
            .build();
    }
}
