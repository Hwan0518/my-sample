package kdh.batchtest.domain.test.batch.joblauncher;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.annotation.Configuration;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;


@Slf4j
@Configuration
@RequiredArgsConstructor
public class JpaBatchScheduler {

	// batch
	private final JobLauncher jobLauncher;
	private final JobRegistry jobRegistry;

//	@Scheduled(cron = "10 * * * * *", zone = "Asia/Seoul")
	public void runJpaJob() throws Exception {
		log.info("JpaBatchScheduler 실행, 시간 = {}", LocalDateTime.now());
		// jobParameter -> 원하는 조건에 맞춰서 잘 설정 해야함
		// ex) 1시간에 한번씩만 실행되게 하고싶다 = new SimpleDateFormat("yyyy-MM-dd-hh")
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss");
		String date = simpleDateFormat.format(new Date());
		JobParameters jobParameters = new JobParametersBuilder()
			.addString("date", date)
			.toJobParameters();
		// jobLauncher
		jobLauncher.run(jobRegistry.getJob("jpaJob"), jobParameters);
	}

}
