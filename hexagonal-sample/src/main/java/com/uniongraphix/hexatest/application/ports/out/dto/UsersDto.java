package com.uniongraphix.hexatest.application.ports.out.dto;

import com.uniongraphix.hexatest.domain.Users;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class UsersDto {

    private String email;
    private String password;
    private String name;
    private String phone;
    private String address;

    public static UsersDto formUsers(Users users) {
        return UsersDto.builder()
            .email(users.getEmail())
            .password(users.getPassword())
            .name(users.getName())
            .phone(users.getPhone())
            .address(users.getAddress())
            .build();
    }
}
