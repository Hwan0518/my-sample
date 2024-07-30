package com.uniongraphix.hexatest.application.service;

import com.uniongraphix.hexatest.application.ports.in.UsersUseCase;
import com.uniongraphix.hexatest.application.ports.out.dto.UsersDto;
import com.uniongraphix.hexatest.application.ports.out.port.UsersPort;
import com.uniongraphix.hexatest.domain.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsersService implements UsersUseCase { // in Port에 위치한 UseCase의 구현체임

    // UseCase는 application의 out Port를 통해서, Adapter의 out에 위치한 Persistence Adapter와 통신한다
    private final UsersPort usersPort;

    // UseCase에 구현된 메소드를 오버라이드해서 주요 비즈니스 로직을 작성함
    @Override
    public UsersDto signInUsers(SignInQuery signInQuery) {
        Users users = usersPort.signInUsers(Users.signInUsers(signInQuery.getEmail(), signInQuery.getPassword()));
        return UsersDto.formUsers(users);
    }

    @Override
    public UsersDto signUpUsers(SignUpQuery signUpQuery) {
        Users users = usersPort.signUpUsers(Users.signUpUsers(
                signUpQuery.getEmail(),
                signUpQuery.getPassword(),
                signUpQuery.getName(),
                signUpQuery.getPhone(),
                signUpQuery.getAddress()
                ));
        return UsersDto.formUsers(users);
    }
}
