package com.uniongraphix.hexatest.adaptor.web.controller;

import com.uniongraphix.hexatest.adaptor.web.request.RequestSignUpUsers;
import com.uniongraphix.hexatest.adaptor.web.request.RequestUsers;
import com.uniongraphix.hexatest.application.ports.in.UsersUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/api/v1/users")
public class UsersController {

    private final UsersUseCase usersUseCase; //in에 위치한 controller는, application의 in Port에 위치한 UseCase와 통신함

    // UseCase를 통해서, 그 구현체인 Service에 구현된 메소드를 호출하여 데이터를 가져온다
    @PostMapping("/register")
    public void signUp(@RequestBody RequestSignUpUsers requestSignUpUsers) {
        log.info("requestSignUpUsers: {}", requestSignUpUsers);
        usersUseCase.signUpUsers(UsersUseCase.SignUpQuery.toQuery(requestSignUpUsers));
    }

    @PostMapping("/signin")
    public void signIn(@RequestBody RequestUsers requestUsers) {
        usersUseCase.signInUsers(UsersUseCase.SignInQuery.toQuery(requestUsers));
    }


}
