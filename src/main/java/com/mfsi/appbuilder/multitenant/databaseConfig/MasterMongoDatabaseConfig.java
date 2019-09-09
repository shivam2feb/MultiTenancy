package com.mfsi.appbuilder.multitenant.databaseConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.MongoClient;

@Configuration
@EnableMongoRepositories(basePackages= {"com.mfsi.appbuilder.master.document","com.mfsi.appbuilder.master.repository"})
public class MasterMongoDatabaseConfig extends AbstractMongoConfiguration{
	
	@Autowired
	private MasterDatabaseConfigProperties mDBConfig;

	@Bean
	MongoTransactionManager mongoTransactionManager(MongoDbFactory dbFactory) {
		return new MongoTransactionManager(dbFactory);
		
	}
	
	@Override
	public MongoClient mongoClient() {
		return new MongoClient(mDBConfig.getUrl(),mDBConfig.getPort());
	}

	@Override
	protected String getDatabaseName() {
		return mDBConfig.getDatabasename();
	}	
}
