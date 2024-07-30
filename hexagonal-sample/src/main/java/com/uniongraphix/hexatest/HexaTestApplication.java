package com.uniongraphix.hexatest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class HexaTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(HexaTestApplication.class, args);
	}

}
