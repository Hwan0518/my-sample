package kdh.batchtest.domain.test.batch.job;


import kdh.batchtest.domain.test.infrastructure.entity.AfterEntity;
import kdh.batchtest.domain.test.infrastructure.entity.BeforeEntity;
import kdh.batchtest.domain.test.infrastructure.jpa.AfterJpaRepository;
import kdh.batchtest.domain.test.infrastructure.jpa.BeforeJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.data.builder.RepositoryItemReaderBuilder;
import org.springframework.batch.item.data.builder.RepositoryItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.Map;


@Slf4j
@Configuration
@RequiredArgsConstructor
public class JpaBatch {

	// batch
	private final JobRepository jobRepository;
	private final PlatformTransactionManager platformTransactionManager;
	// repository
	private final BeforeJpaRepository beforeJpaRepository;
	private final AfterJpaRepository afterJpaRepository;

	/**
	 * JpaBatch
	 * 1. jpaJob
	 * 2. step
	 * 3. reader
	 * 4. processor
	 * 5. writer
	 */


	// 1. JpaJob
	// job을 정의한다
	@Bean
	public Job jpaJob() {
		return new JobBuilder("jpaJob", jobRepository)
			.start(jpaJobStep())
//			.next() job이 여러 단계라면 next를 사용
//			.next()
			.build();
	}


	// 2. step
	// 실제 배치 처리가 일어나는 곳으로, [reader -> processor -> writer]의 구조로 이루어져 있다
	@Bean
	public Step jpaJobStep() {
		log.info("jpaJobStep 실행");
		return new StepBuilder("jpaJobStep", jobRepository)
			// <reader에서 읽어들인 데이터, writer에 저장할 데이터>
			.<BeforeEntity, AfterEntity>chunk(10, platformTransactionManager) // chunk: 데이터를 읽어들이는 단위
			.reader(beforeReader())
			.processor(middleProcessor())
			.writer(afterWriter())
			.build();
	}


	// 3. reader
	// 데이터를 읽어들이는 역할을 한다. 데이터를 읽어들일 쿼리 방식에 따라 Reader를 선택한다
	// RepositoryItemReader: JPA Repository를 이용하여 데이터를 읽어들이는 Reader
	@Bean
	public RepositoryItemReader<BeforeEntity> beforeReader() {
		log.info("beforeReader 실행");
		return new RepositoryItemReaderBuilder<BeforeEntity>()
			.name("beforeReader")
			.pageSize(10) // chunk와 동일한 사이즈로 선택한다
			.methodName("findAll") // JPA Repository의 메서드명을 지정한다
			.repository(beforeJpaRepository)
			.sorts(Map.of("id", Sort.Direction.ASC)) // 데이터를 읽어들일 때 정렬 방식을 지정한다
			.build();
	}


	// 4. processor
	// 데이터를 가공하는 역할을 한다. 데이터를 가공하는 방식에 따라 Processor를 선택한다
	@Bean
	public ItemProcessor<BeforeEntity, AfterEntity> middleProcessor() {
		log.info("processor 실행");
		return new ItemProcessor<BeforeEntity, AfterEntity>() {
			@Override
			public AfterEntity process(BeforeEntity item) throws Exception {
				return AfterEntity.builder()
					.userName(item.getUserName())
					.build();
			}
		};
		// 다음처럼 람다식으로도 작성 가능
//		return item -> {
//			return AfterEntity.builder()
//				.userName(item.getUserName())
//				.build();
//		};
	}


	// 5. writer
	// 데이터를 저장하는 역할을 한다. 데이터를 저장하는 방식에 따라 Writer를 선택한다
	@Bean
	public RepositoryItemWriter<AfterEntity> afterWriter() {
		log.info("afterWriter 실행");
		return new RepositoryItemWriterBuilder<AfterEntity>()
			.repository(afterJpaRepository)
			.methodName("save")
			.build();
	}


}
