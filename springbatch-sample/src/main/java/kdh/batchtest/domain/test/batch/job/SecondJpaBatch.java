package kdh.batchtest.domain.test.batch.job;


import kdh.batchtest.domain.test.infrastructure.entity.WinEntity;
import kdh.batchtest.domain.test.infrastructure.jpa.WinJpaRepository;
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

import java.util.Collections;
import java.util.Map;


@Slf4j
@Configuration
@RequiredArgsConstructor
public class SecondJpaBatch {

	// batch
	private final JobRepository jobRepository;
	private final PlatformTransactionManager platformTransactionManager;
	// repository
	private final WinJpaRepository winJpaRepository;

	/**
	 * JpaBatch
	 * 1. secondJpaJob
	 * 2. step
	 * 3. reader
	 * 4. processor
	 * 5. writer
	 */

	// 1. JpaJob
	// job을 정의한다
	@Bean
	public Job secondJpaJob() {
		return new JobBuilder("secondJpaJob", jobRepository)
			.start(secondJpaJobStep())
			//			.next() job이 여러 단계라면 next를 사용
			//			.next()
			.build();
	}


	// 2. step
	// 실제 배치 처리가 일어나는 곳으로, [reader -> processor -> writer]의 구조로 이루어져 있다
	@Bean
	public Step secondJpaJobStep() {
		log.info("second jpaJobStep 실행");
		return new StepBuilder("secondJpaJobStep", jobRepository)
			// <reader에서 읽어들인 데이터, writer에 저장할 데이터>
			.<WinEntity, WinEntity>chunk(10, platformTransactionManager) // chunk: 데이터를 읽어들이는 단위
			.reader(winReader())
			.processor(trueProcessor())
			.writer(winWriter())
			.build();
	}


	// 3. reader
	// 데이터를 읽어들이는 역할을 한다. 데이터를 읽어들일 쿼리 방식에 따라 Reader를 선택한다
	// RepositoryItemReader: JPA Repository를 이용하여 데이터를 읽어들이는 Reader
	@Bean
	public RepositoryItemReader<WinEntity> winReader() {
		return new RepositoryItemReaderBuilder<WinEntity>()
			.name("winReader")
			.pageSize(10) // chunk와 동일한 사이즈로 선택한다
			.methodName("findByWinGreaterThanEqual") // JPA Repository의 메서드명을 지정한다
			.arguments(Collections.singletonList(10L)) // 위에서 정의한 메서드에 전달할 인자값을 지정한다. 인수로 List를 넘겨야함
			.repository(winJpaRepository)
			.sorts(Map.of("id", Sort.Direction.ASC)) // 데이터를 읽어들일 때 정렬 방식을 지정한다. 인수로 Map을 넘겨야함
			.build();
	}


	// 4. processor
	// 데이터를 가공하는 역할을 한다. 데이터를 가공하는 방식에 따라 Processor를 선택한다
	@Bean
	public ItemProcessor<WinEntity, WinEntity> trueProcessor() {
		// win이 10L 이상인 데이터만 넘어온다
		return  item -> {
			item.updateReward(true);
			return item;
		};
	}


	// 5. writer
	// 데이터를 저장하는 역할을 한다. 데이터를 저장하는 방식에 따라 Writer를 선택한다
	@Bean
	public RepositoryItemWriter<WinEntity> winWriter() {
		return new RepositoryItemWriterBuilder<WinEntity>()
			.repository(winJpaRepository)
			.methodName("save")
			.build();
	}



}
