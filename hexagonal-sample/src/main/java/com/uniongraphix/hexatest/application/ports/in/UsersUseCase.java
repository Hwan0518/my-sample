package com.uniongraphix.hexatest.application.ports.in;

import com.uniongraphix.hexatest.adaptor.web.request.RequestSignUpUsers;
import com.uniongraphix.hexatest.adaptor.web.request.RequestUsers;
import com.uniongraphix.hexatest.application.ports.out.dto.UsersDto;
import lombok.Builder;
import lombok.Getter;

public interface UsersUseCase { // UseCase는 application의 In Port 역할을 한다

    // Service에서 구현할 메소드
    UsersDto signInUsers(SignInQuery signInQuery);
    UsersDto signUpUsers(SignUpQuery signUpQuery);

    @Getter
    @Builder
    class SignInQuery {

        private String email;
        private String password;

        // Request를 UseCase의 method로 바꿔주는 역할
        // -> 원론적으로는 web과의 의존성을 끊기위해 이 로직이 request에 들어가있는게 맞지만, web과의 연결성이 끊길일이 없기에 여기에 적어도 무관
        public static SignInQuery toQuery(RequestUsers requestUsers) {
            return SignInQuery.builder()
                    .email(requestUsers.getEmail())
                    .password(requestUsers.getPassword())
                    .build();
        }
    }

    @Getter
    @Builder
    class SignUpQuery {

        private String email;
        private String password;
        private String name;
        private String phone;
        private String address;

        // requestSignUpUsers를 SignUpQuery로 바꿔주는 역할
        public static SignUpQuery toQuery(RequestSignUpUsers requestSignUpUsers) {
            return SignUpQuery.builder()
                    .email(requestSignUpUsers.getEmail())
                    .password(requestSignUpUsers.getPassword())
                    .name(requestSignUpUsers.getName())
                    .phone(requestSignUpUsers.getPhone())
                    .address(requestSignUpUsers.getAddress())
                    .build();

        }

    }

    @Getter
    @Builder
    class TestQuery {
        private String testA;
        private String testB;
    }


}
