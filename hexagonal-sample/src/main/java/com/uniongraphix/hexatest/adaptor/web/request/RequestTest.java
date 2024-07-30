package com.uniongraphix.hexatest.adaptor.web.request;

import com.uniongraphix.hexatest.application.ports.in.UsersUseCase;
import lombok.Getter;

@Getter
public class RequestTest {
    private String testA;
    private String testB;

    public UsersUseCase.TestQuery toQuery() {
        return UsersUseCase.TestQuery.builder()
                .testA(testA)
                .testB(testB)
                .build();
    }
}
