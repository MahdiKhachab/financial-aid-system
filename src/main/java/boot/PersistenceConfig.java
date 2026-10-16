package boot;

import javax.sql.DataSource;

import jakarta.persistence.EntityManagerFactory;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.EclipseLinkJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * EclipseLink persistence unit from META-INF/persistence.xml, backed by the Spring Boot DataSource.
 * Replaces the WildFly JTA datasource. Spring Boot's Hibernate auto-configuration is not on the
 * classpath, and no open-EntityManager-in-view filter is registered, so each facade call keeps
 * its own transaction and persistence context as it did with the EJBs.
 */
@Configuration
@EnableTransactionManagement
public class PersistenceConfig {

	public static final String PERSISTENCE_UNIT = "ShariaFunds-persistence-unit";

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
		LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
		emf.setPersistenceUnitName(PERSISTENCE_UNIT);
		emf.setDataSource(dataSource);
		emf.setJpaVendorAdapter(new EclipseLinkJpaVendorAdapter());
		return emf;
	}

	@Bean
	public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
		return new JpaTransactionManager(entityManagerFactory);
	}
}
