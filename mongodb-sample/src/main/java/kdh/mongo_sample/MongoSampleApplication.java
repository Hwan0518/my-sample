package kdh.mongo_sample;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;


@EnableMongoAuditing // @CreatedDate, @LastModifiedDate 사용을 위한 어노테이션
@SpringBootApplication
public class MongoSampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(MongoSampleApplication.class, args);
	}
}
