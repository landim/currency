package com.crossover.exchange;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;

@Configuration
@ComponentScan
@EnableAutoConfiguration
@SpringBootApplication
@PropertySource("classpath:config.properties")
public class App extends AbstractMongoConfiguration {

	@Bean
	public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}

	public @Bean Mongo mongo() throws Exception {
		return new MongoClient("localhost", 27017);
	}

	public @Bean MongoTemplate mongoTemplate() throws Exception {
		return new MongoTemplate(mongo(), getDatabaseName());
	}

	@Override
	protected String getDatabaseName() {
		return "exchange";
	}

	@Override
	protected String getMappingBasePackage() {
		return "com.oreilly.springdata.mongodb";
	}

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}

}
