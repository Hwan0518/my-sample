package com.uniongraphix.hexatest.domain;

import com.uniongraphix.hexatest.adaptor.infrastructure.mysql.entity.UsersEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.modelmapper.ModelMapper;

@Getter
@Builder
@AllArgsConstructor
public class Users {
    private static ModelMapper modelMapper;
    // 우리가 실제로 다룰 domain. list 타입도 허용된다
    private Long id;
    private String email;
    private String password;
    private String name;
    private String phone;
    private String address; // -> 유저가 집이 여러채인 경우를 생각한다면, private List addressList로 만들고 entity에서만 string으로 저장한다

    // Service에서 사용할 Domain 로직
    public static Users signInUsers(String email, String password) {
        return Users.builder()
            .email(email)
            .password(password)
            .build();
    }

    public static Users signUpUsers(String email, String password, String name, String phone, String address) {
        return Users.builder()
            .email(email)
            .password(password)
            .name(name)
            .phone(phone)
            .address(address)
            .build();
    }

    // Entity를 Domain으로 바꿔주는 로직. Driven Adpater에서 사용된다
    public static Users formUsersEntity(UsersEntity usersEntity) {
        return Users.builder()
            .id(usersEntity.getId())
            .email(usersEntity.getEmail())
            .password(usersEntity.getPassword())
            .name(usersEntity.getName())
            .phone(usersEntity.getPhone())
            .address(usersEntity.getAddress())
            .build();
    }

    public static Users formTest(UsersEntity usersEntity) {
        return modelMapper.map(usersEntity, Users.class);
    }

}
