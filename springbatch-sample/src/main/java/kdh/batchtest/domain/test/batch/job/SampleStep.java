package kdh.batchtest.domain.test.batch.job;


import kdh.batchtest.domain.test.infrastructure.entity.AfterEntity;
import kdh.batchtest.domain.test.infrastructure.entity.BeforeEntity;
import kdh.batchtest.domain.test.infrastructure.jpa.AfterJpaRepository;
import kdh.batchtest.domain.test.infrastructure.jpa.BeforeJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ValidationException;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.skip.SkipLimitExceededException;
import org.springframework.batch.core.step.skip.SkipPolicy;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.data.builder.RepositoryItemReaderBuilder;
import org.springframework.batch.item.data.builder.RepositoryItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.PlatformTransactionManager;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;


@Slf4j
@Configuration
@RequiredArgsConstructor
public class SampleStep {

	// batch
	private final JobRepository jobRepository;
	private final PlatformTransactionManager platformTransactionManager;
	// repository
	private final BeforeJpaRepository beforeJpaRepository;
	private final AfterJpaRepository afterJpaRepository;


	/**
	 * SampleStep
	 * 1. Step 설정
	 * 2. SkipPolicy 설정
	 * 3. Step Listener 설정
	 */


	// 1. Step 설정: 실제 배치 처리가 일어나는 곳으로, [reader -> processor -> writer]의 구조로 이루어져 있다
	@Bean
	public Step sampleJobStep() {
		return new StepBuilder("sampleJobStep", jobRepository)
			// <reader에서 읽어들인 데이터, writer에 저장할 데이터>
			.<BeforeEntity, AfterEntity>chunk(10, platformTransactionManager) // chunk: 데이터를 읽어들이는 단위
			.reader(sampleReader())             // read -> chunk 만큼 실행됨
			.processor(sampleProcessor())       // process -> chunk 만큼 실행됨
			.writer(sampleWriter())             // write -> 한번 실행됨
			// Skip
			.faultTolerant()                    // 오류 발생시 skip / retry 설정
			.skipPolicy(new CustomSkipPolicy()) // skip 정책 설정 -> skipLimit을 무한대로 설정하기 위해서는 꼭 필요
			.skip(Exception.class)              // skip 할 예외 타입 -> 보통 큰 범위로 skip을 설정
			.skip(FileNotFoundException.class)
			.noSkip(IOException.class)          // skip 하지 않을 예외 타입
			.noSkip(RuntimeException.class)
			.skipLimit(10)                      // skip 횟수 제한 -> 최대 n개를 skip 할 수 있음
			// Retry
			.retry(SQLException.class)              // retry 할 예외 타입 -> 보통 SQL,IO 등의 예외 타입을 retry
			.retry(IOException.class)
			.noRetry(FileNotFoundException.class)   // retry 하지 않을 예외 타입
			.noRetry(RuntimeException.class)
			.retryLimit(3)                          // retry 횟수 제한
			.noRollback(ValidationException.class)  // writer에서 트랜잭션 rollback 하지 않을 예외 타입
			// Step Listener
			.listener(sampleStepExecutionListener())// step listener 설정
			.build();
	}


	// 2. SkipPolicy 설정: skip 정책을 설정할 수 있다
	public class CustomSkipPolicy implements SkipPolicy {
		@Override
		public boolean shouldSkip(Throwable t, long skipCount) throws SkipLimitExceededException {
			return true;
		}
	}



	// 3. Step Listener 설정: step의 실행 전후에 특정 작업을 수행할 수 있다
	// ex) 로그를 남기거나, 다음 step 준비 상태를 확인하거나, 변수를 초기화 시키는 작업 등
	@Bean
	public StepExecutionListener sampleStepExecutionListener() {
		return new StepExecutionListener() {
			@Override
			public void beforeStep(StepExecution stepExecution) {
				// step 이전 실행할 로직
				log.info("Before Step");
			}

			@Override
			public ExitStatus afterStep(StepExecution stepExecution) {
				// step 이후 실행할 로직
				log.info("After Step");
				return ExitStatus.COMPLETED;
			}
		};
	}



	@Bean
	public RepositoryItemReader<BeforeEntity> sampleReader() {
		return new RepositoryItemReaderBuilder<BeforeEntity>()
			.name("sampleReader")
			.methodName("findAll")
			.sorts(Map.of("id", Sort.Direction.ASC))
			.pageSize(10)
			.repository(beforeJpaRepository)
			.build();
	}


	@Bean
	public ItemProcessor<BeforeEntity, AfterEntity> sampleProcessor() {
		return item -> AfterEntity.builder()
			.build();
	}


	@Bean
	RepositoryItemWriter<AfterEntity> sampleWriter() {
		return new RepositoryItemWriterBuilder<AfterEntity>()
			.repository(afterJpaRepository)
			.build();
	}

}
