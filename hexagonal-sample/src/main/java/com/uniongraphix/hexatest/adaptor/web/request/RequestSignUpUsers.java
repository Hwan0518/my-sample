package com.uniongraphix.hexatest.adaptor.web.request;

import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RequestSignUpUsers {

    private String email;
    private String password;
    private String name;
    private String phone;
    private String address;

}
