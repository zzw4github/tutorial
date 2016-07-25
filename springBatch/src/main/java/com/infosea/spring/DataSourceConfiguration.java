package com.infosea.spring;

import org.springframework.batch.core.configuration.annotation.BatchConfigurer;
import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

/**
 * Created by infosea on 2016/7/6.
 */
@Configuration
public class DataSourceConfiguration {
    @Bean
    @Primary
    @ConfigurationProperties(prefix="datasource.spring")
    public DataSource   springDataSource() {
        return DataSourceBuilder.create().build();
    }

//    @Bean
//    BatchConfigurer configurer(DataSource dataSource){
//        return new DefaultBatchConfigurer(dataSource);
//    }

//    @Bean(name="secondaryDataSource")
//    @ConfigurationProperties(prefix="datasource.secondary")
//    public DataSource secondaryDataSource() {
//        System.out.println("-------------------- secondaryDataSource init ---------------------");
//        return DataSourceBuilder.create().build();
//    }
}
