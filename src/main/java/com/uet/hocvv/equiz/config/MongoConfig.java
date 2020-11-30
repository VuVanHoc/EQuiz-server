package com.uet.hocvv.equiz.config;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.Collection;
import java.util.Collections;

@EnableMongoRepositories(basePackages = "com.uet.hocvv.equiz.repository")
@Configuration
public class MongoConfig extends AbstractMongoClientConfiguration {
	
	@Value("${spring.data.mongodb.uri}")
	private String mongoUri;
	
	
	@Override
	protected String getDatabaseName() {
		return "equiz";
	}
	
	@Override
	public MongoClient mongoClient() {
		ConnectionString connectionString = new ConnectionString(mongoUri);
		MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
				.applyConnectionString(connectionString)
				.build();
		
		return MongoClients.create(mongoClientSettings);
	}
	
	@Override
	public Collection getMappingBasePackages() {
		return Collections.singleton("com.uet.hocvv.equiz");
	}
	
}
