package kdh.bucket4j_sample.global.config.ratelimiter;


import io.github.bucket4j.Bucket;
import io.github.bucket4j.ConsumptionProbe;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Duration;
import java.util.List;


// 모든 request마다 실행되기 위해 OncePerRequestFilter가 아닌 Filter를 사용한다
@Slf4j
@Component
@RequiredArgsConstructor
public class TestApiThrottlingFilter implements Filter {

	// static
	private static final String STOP_REQUEST = "토큰이 없어요! 제발 멈춰주세요";
	private static final List<String> GREEDY_API = List.of("/api/hello");
	private static final List<String> INTERVAL_API = List.of("/api/welcome");

	// bucket1 - duration/refill 초마다 1개의 토큰이 추가됨
	private static final Bucket greedyBucket = createGreedyBucket(3, 3, Duration.ofSeconds(9));
	// bucket2 - duration마다 refill개가 한번에 추가됨
	private static final Bucket intervalBucket = createIntervalBucket(3, 3, Duration.ofSeconds(9));


	/**
	 * Throttling Filter
	 * 1. 필터 실행
	 * 2. bucket 생성
	 * 3. api 토큰 확인
	 */


	// 1. 필터 실행
	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		// 세션이 존재하면 반환하고, 없으면 새로 생성하기 위해 true를 인수로 전달
		HttpSession session = request.getSession(true);

		// api 경로에 포함된다면 토큰 사용
		if (GREEDY_API.contains(request.getRequestURI())) {
			log.info("greedy bucket 사용");
			checkApi(greedyBucket, filterChain, servletRequest, servletResponse);
		} else if (INTERVAL_API.contains(request.getRequestURI())) {
			log.info("interval bucket 사용");
			checkApi(intervalBucket, filterChain, servletRequest, servletResponse);
		}

		// 아니라면 필터 계속 진행
		filterChain.doFilter(servletRequest, servletResponse);
	}


	// 2. bucket 생성
	// duration동안 refill개의 토큰을 평균 시간마다 채워넣는다
	private static Bucket createGreedyBucket(int capacity, int refill, Duration duration) {
		return Bucket.builder()
			.addLimit(limit -> limit
				.capacity(capacity)
				.refillGreedy(refill, duration))
			.build();
	}

	// duration에 한번에 모든 토큰을 채워넣는다
	private static Bucket createIntervalBucket(int capacity, int refill, Duration duration) {
		return Bucket.builder()
			.addLimit(limit -> limit
				.capacity(capacity)
				.refillIntervally(refill, duration))
			.build();
	}


	// 3. api 토큰 확인
	private void checkApi(Bucket bucket, FilterChain filterChain, ServletRequest servletRequest, ServletResponse servletResponse) throws IOException, ServletException {
		ConsumptionProbe probe = bucket.tryConsumeAndReturnRemaining(1);
		// 토큰이 남아있으면 요청을 처리하고, 없으면 429 에러를 반환
		if (probe.isConsumed()) {
			filterChain.doFilter(servletRequest, servletResponse);
			log.info("현재 남은 토큰 수: {}", bucket.getAvailableTokens());
		} else {
			// 리필까지 남은 시간
			long waitForRefill = probe.getNanosToWaitForRefill() / 1_000_000_000;
			// 응답
			HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
			httpResponse.setContentType("text/plain; charset=UTF-8");
			httpResponse.setStatus(429);
			servletResponse.getWriter().write(STOP_REQUEST + String.format("\n %s초 뒤에 다시 시도해주세요!", waitForRefill));
		}
	}

}
