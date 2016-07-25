package com.infosea.spring;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Hello world!
 *
 */
@Configuration
@EnableBatchProcessing
@EnableAutoConfiguration
@PropertySource(value={"classpath:application.properties","classpath:configuration.properties"})
//@PropertySource(value={"classpath:configuration.properties"}) //这两个都可以
@Import({DataSourceConfiguration.class})
@ComponentScan
public class BatchConfiguration {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;
    @Autowired
    FileDeletingTasklet fileDeletingTasklet;
    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
//                .tasklet(new Tasklet() {
//                    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) {
//                        System.out.println("step1.........");
//                        return null;
//                    }
//                })
                .tasklet( fileDeletingTasklet)
                .build();
    }

    @Bean
    public Job job(Step step1) throws Exception {
        return jobBuilderFactory.get("job1")
                .incrementer(new RunIdIncrementer())
                .start(step1)
                .build();
    }
}
