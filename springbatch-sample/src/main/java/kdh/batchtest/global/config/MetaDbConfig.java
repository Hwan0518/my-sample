package kdh.batchtest.global.config;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;


@Configuration
public class MetaDbConfig {

	@Bean
	@Primary // DB 두 개를 연결해야 하므로, 충돌 방지를 위해 Primary 사용
	@ConfigurationProperties(prefix = "spring.datasource-meta")
	// prefix에 해당하는 정보로 application.properties에서 정보를 가져와서 DataSource를 생성
	public DataSource metaDbSource() {
		return DataSourceBuilder.create().build();
	}


	@Bean
	@Primary // DB 두 개를 연결해야 하므로, 충돌 방지를 위해 Primary 사용
	public PlatformTransactionManager metaTransactionManager() {
		return new DataSourceTransactionManager(metaDbSource());
	}
}
