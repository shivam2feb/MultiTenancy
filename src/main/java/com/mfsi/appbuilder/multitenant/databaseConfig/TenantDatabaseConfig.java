package com.mfsi.appbuilder.multitenant.databaseConfig;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManagerFactory;

import org.hibernate.MultiTenancyStrategy;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.mfsi.appbuilder.multitenant.config.CurrentTenantIdentifierResolverImpl;
import com.mfsi.appbuilder.multitenant.config.DataSourceBasedMultiTenantConnectionProviderImpl;
import com.mfsi.appbuilder.tenant.entity.API;
import com.mfsi.appbuilder.tenant.repository.TenantAPIRepository;

@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages = { "com.mfsi.appbuilder.tenant.repository", "com.mfsi.appbuilder.tenant.entity" })
@EnableJpaRepositories(basePackages = { "com.mfsi.appbuilder.tenant.repository", "com.mfsi.appbuilder.tenant.service" }, 
	entityManagerFactoryRef = "tenantEntityManagerFactory", 
	transactionManagerRef = "tenantTransactionManager")
public class TenantDatabaseConfig {


	@Bean(name = "tenantJpaVendorAdapter")
	public JpaVendorAdapter jpaVendorAdapter() {
		return new HibernateJpaVendorAdapter();
	}

	@Bean(name = "tenantTransactionManager")
	public JpaTransactionManager transactionManager(
			@Qualifier("tenantEntityManagerFactory") EntityManagerFactory tenantEntityManager) {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(tenantEntityManager);
		return transactionManager;
	}

	/**
	 * The multi tenant connection provider
	 * 
	 * @return
	 */
	@Bean(name = "datasourceBasedMultitenantConnectionProvider")
	public MultiTenantConnectionProvider multiTenantConnectionProvider() {
		// Autowires the multi connection provider
		return new DataSourceBasedMultiTenantConnectionProviderImpl();
	}

	/**
	 * The current tenant identifier resolver
	 * 
	 * @return
	 */
	@Bean(name = "currentTenantIdentifierResolver")
	public CurrentTenantIdentifierResolver currentTenantIdentifierResolver() {
		return new CurrentTenantIdentifierResolverImpl();
	}

	/**
	 * Creates the entity manager factory bean which is required to access the
	 * JPA functionalities provided by the JPA persistence provider, i.e.
	 * Hibernate in this case.
	 * 
	 * @param connectionProvider
	 * @param tenantResolver
	 * @return
	 */
	@Bean(name = "tenantEntityManagerFactory")
	@ConditionalOnBean(name = "mongoTransactionManager")
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(
			@Qualifier("datasourceBasedMultitenantConnectionProvider") MultiTenantConnectionProvider connectionProvider,
			@Qualifier("currentTenantIdentifierResolver") CurrentTenantIdentifierResolver tenantResolver) {

		LocalContainerEntityManagerFactoryBean emfBean = new LocalContainerEntityManagerFactoryBean();
		//All tenant related entities, repositories and service classes must be scanned
		emfBean.setPackagesToScan(
				API.class.getPackage().getName(),
				TenantAPIRepository.class.getPackage().getName()
				//,ActorService.class.getPackage().getName()
				);
		emfBean.setJpaVendorAdapter(jpaVendorAdapter());
		emfBean.setPersistenceUnitName("tenantdb-persistence-unit");
		Map<String, Object> properties = new HashMap<>();
		properties.put(org.hibernate.cfg.Environment.MULTI_TENANT,
				MultiTenancyStrategy.SCHEMA);
		properties.put(
				org.hibernate.cfg.Environment.MULTI_TENANT_CONNECTION_PROVIDER,
				connectionProvider);
		properties.put(
				org.hibernate.cfg.Environment.MULTI_TENANT_IDENTIFIER_RESOLVER,
				tenantResolver);
		properties.put(org.hibernate.cfg.Environment.DIALECT,
				"org.hibernate.dialect.MySQL5Dialect");
		properties.put(org.hibernate.cfg.Environment.SHOW_SQL, true);
		properties.put(org.hibernate.cfg.Environment.FORMAT_SQL, true);
		properties.put(org.hibernate.cfg.Environment.HBM2DDL_AUTO, "update");

		emfBean.setJpaPropertyMap(properties);
		System.out.println("tenantEntityManagerFactory set up successfully!");
		return emfBean;
	}
}