package kdh.batchtest.domain.test.batch.job;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;


@Slf4j
@Configuration
@RequiredArgsConstructor
public class SampleJob {

	// batch
	private final JobRepository jobRepository;
	private final PlatformTransactionManager platformTransactionManager;

	/**
	 * SampleJob
	 * 1. Job 설정 - 순차적 step
	 * 2. Job 설정 - 조건부 step
	 * 3. Job Listener 설정
	 */

	// 1. Job 설정 - 순차적 step
	@Bean
	public Job sampleJob1() {
		return new JobBuilder("sampleJob1", jobRepository)
			// start: 첫 번째로 실행할 Step을 설정
			.start(sampleJobStep1())
			// next: 순차적으로 Step을 실행할 수 있도록 설정 -> 단, 먼저 실행되는 Step이 성공적으로 끝나야 다음 Step이 실행됨
			.next(sampleJobStep2())
			.next(sampleJobStep3())
			// listener: Job 실행 전후에 실행할 코드를 정의할 수 있음
			.listener(jobExecutionListener())
			.build();
	}


	// 2. Job 설정 - 조건부 step
	@Bean
	public Job sampleJob2() {
		return new JobBuilder("sampleJob2", jobRepository)
			.start(sampleJobStep1())
			// on: spring batch flow 시작
			// on, from: Step의 실행 결과에 따라 다음 실행할 Step을 설정
			//          -> 와일드카드("*")를 사용하면 모든 결과에 대해 설정한 Step을 실행
			.on("*").to(sampleJobStep2())
			.from(sampleJobStep2()).on("COMPLETED").to(sampleJobStep3())
			.from(sampleJobStep2()).on("FAILED").fail()
			.end()
			.build();
	}


	// 3. Job Listener 설정: Job 실행 전후에 실행할 코드를 정의할 수 있음
	@Bean
	public JobExecutionListener jobExecutionListener() {
		return new JobExecutionListener() {
			@Override
			public void beforeJob(JobExecution jobExecution) {
				// Job 실행 전 실행할 로직 - 특정 api 호출, 로깅, job 실행시간 측정 등의 작업이 가능하다
				JobExecutionListener.super.beforeJob(jobExecution);
			}

			@Override
			public void afterJob(JobExecution jobExecution) {
				// Job 실행 후 실행할 로직 - 특정 api 호출, 로깅, job 실행시간 측정 등의 작업이 가능하다
				JobExecutionListener.super.afterJob(jobExecution);
			}
		};
	}

////////////////////////////////////////////////////////

	private Step sampleJobStep3() {
		return null;
	}

	private Step sampleJobStep2() {
		return null;
	}

	private Step sampleJobStep1() {
		return null;
	}

}
