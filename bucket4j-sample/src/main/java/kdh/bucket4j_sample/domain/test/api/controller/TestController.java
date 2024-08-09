package kdh.bucket4j_sample.domain.test.api.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;


@RestController
@RequestMapping("api")
@RequiredArgsConstructor
public class TestController {

	/**
	 * TestController
	 * 1. greedyBucket API
	 * 2. intervalBucket API
	 * 3. noneBucket API
	 */


	@GetMapping(value = "/hello", produces = "text/plain; charset=UTF-8")
	public ResponseEntity<String> getData1() {
		LocalDateTime localDate = LocalDateTime.now();
		return ResponseEntity.ok("Hello, Bucket4J !\n 현재 날짜 : " + localDate + "\n 토큰은 Greedy하게 채워집니다!");
	}

	@GetMapping(value = "/welcome", produces = "text/plain; charset=UTF-8")
	public ResponseEntity<String> getData2() {
		LocalDateTime localDate = LocalDateTime.now();
		return ResponseEntity.ok("Hello, Bucket4J !\n 현재 날짜 : " + localDate + "\n 토큰은 Interval하게 채워집니다!");
	}

	@GetMapping(value = "/none", produces = "text/plain; charset=UTF-8")
	public ResponseEntity<String> getData3() {
		LocalDateTime localDate = LocalDateTime.now();
		return ResponseEntity.ok("Hello, Bucket4J !\n 현재 날짜 : " + localDate + "\n 토큰이 없는 API입니다!");
	}

}
