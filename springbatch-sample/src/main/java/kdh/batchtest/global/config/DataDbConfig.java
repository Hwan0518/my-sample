package kdh.batchtest.global.config;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;


@Configuration
// 특정한 entity package 에 값을 부여함
@EnableJpaRepositories(
	basePackages = "kdh.batchtest.domain.test.infrastructure.jpa", // 어떤 패키지에 대해서 동작할것인가?
	entityManagerFactoryRef = "dataEntityManager",  // 작성할 메서드 명
	transactionManagerRef = "dataTransactionManager" // 작성할 메서드 명
)
public class DataDbConfig {

	@Bean
	@ConfigurationProperties(prefix = "spring.datasource-data")
	public DataSource dataDbSource() {
		return DataSourceBuilder.create().build();
	}


	@Bean
	public LocalContainerEntityManagerFactoryBean dataEntityManager() {
		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
		// em에 datasource 설정
		em.setDataSource(dataDbSource());
		// entity가 위치할 패키지 설정
		em.setPackagesToScan(new String[]{"kdh.batchtest.domain.test.infrastructure.entity"});
		// hibernate 구현체로 jpa 사용
		em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());

		// db properties 설정
		HashMap<String, Object> properties = new HashMap<>();
		// ddl auto 설정
		properties.put("hibernate.hbm2ddl.auto", "update");
		properties.put("hibernate.show_sql", "true");
		// properties 설정을 jpa에 적용
		em.setJpaPropertyMap(properties);
		return em;
	}


	@Bean
	public PlatformTransactionManager dataTransactionManager() {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(dataEntityManager().getObject());
		return transactionManager;
	}
}
