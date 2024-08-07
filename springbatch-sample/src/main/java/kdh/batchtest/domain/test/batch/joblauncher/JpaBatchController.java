package kdh.batchtest.domain.test.batch.joblauncher;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class JpaBatchController {

	// batch
	private final JobLauncher jobLauncher;
	private final JobRegistry jobRegistry;

	@GetMapping("/jpaBatch/Controller")
	public String jobBatchApi(@RequestParam("value") String value) throws Exception {
		log.info("JpaBatchController 실행");
		// jobParameter
		JobParameters jobParameters = new JobParametersBuilder()
			.addString("value", value) // Double, Long, Date, String 타입만 가능
			.toJobParameters();
		// jobLauncher
		jobLauncher.run(jobRegistry.getJob("jpaJob"), jobParameters);
		return "ok";
	}


	@GetMapping("/jpaBatch/Controller/second")
	public String secondJobBatchApi(@RequestParam("value") String value) throws Exception {
		log.info("JpaBatchController 실행");
		// jobParameter
		JobParameters jobParameters = new JobParametersBuilder()
			.addString("value", value) // Double, Long, Date, String 타입만 가능
			.toJobParameters();
		// jobLauncher
		jobLauncher.run(jobRegistry.getJob("secondJpaJob"), jobParameters);
		return "ok";
	}

}
