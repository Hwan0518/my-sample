package com.uniongraphix.hexatest.adaptor.web.request;

import com.uniongraphix.hexatest.application.ports.in.UsersUseCase;

public class NewWebRequest {
    private String valueA;
    private String valueB;

    public UsersUseCase.TestQuery toQuery() {
        return UsersUseCase.TestQuery.builder()
                .testA(valueA)
                .testB(valueB)
                .build();
    }
}
