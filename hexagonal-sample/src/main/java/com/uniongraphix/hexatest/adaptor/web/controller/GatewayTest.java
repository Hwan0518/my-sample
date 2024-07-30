package com.uniongraphix.hexatest.adaptor.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
public class GatewayTest {

    @GetMapping("/welcome")
    public String welcome() {
        return "Welcome to the User Service.";
    }
}
