package com.example.demo.batch.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.data.MongoItemReader;
import org.springframework.batch.item.data.MongoItemWriter;
import org.springframework.batch.item.data.builder.MongoItemWriterBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.example.demo.batch.listener.JobCompletionNotificationListener;
import com.example.demo.batch.processor.FridgeItemProcessor;
import com.example.demo.batch.reader.FridgeItemReader;
import com.example.demo.batch.writer.FridgeItemWriter;
import com.example.demo.line.entity.FridgeEntity;

@Configuration
@EnableBatchProcessing
@EnableScheduling
public class BatchConfiguration {
	
	@Autowired
    public JobBuilderFactory jobBuilderFactory;
	
    @Autowired
    public StepBuilderFactory stepBuilderFactory;
    
    @Autowired
    public FridgeItemReader fridgeItemReader;
    
    @Autowired
    public FridgeItemProcessor fridgeItemProcessor;
    
    @Autowired
    public FridgeItemWriter fridgeItemWriter;
    
    @Autowired
    JobCompletionNotificationListener jobCompletionNotificationListener;
    
    @Autowired
    MongoTemplate mongoTemplate;
    
    
    
    @Bean
    public Job updateUserJob()
            throws Exception {

        return this.jobBuilderFactory.get("updateUserJob").incrementer(new RunIdIncrementer())
                .listener(jobCompletionNotificationListener).start(step1()).build();
    }
    
//    @Bean
//    public MongoItemReader<FridgeEntity> reader() throws Exception{
//    	MongoItemReader<FridgeEntity> reader = new MongoItemReader<FridgeEntity>();
//    	reader.setCollection("FAMILY_FRIDGE");
//    	reader.setTemplate(mongoTemplate);
//    	reader.setQuery("{itemName:'水餃'}");
//    	reader.setTargetType(FridgeEntity.class);
//    	Map<String,Sort.Direction> sortMap = new HashMap<>();
//    	sortMap.put("_id",Direction.ASC);
//    	reader.setSort(sortMap);
//		return reader;
//    	
//    }
    
    
//    @Bean
//    public FridgeItemProcessor processor() {
//        return new FridgeItemProcessor();
//    }
    
    
//    @Bean
//    public MongoItemWriter<FridgeEntity> writer() {
//    	return new MongoItemWriterBuilder<FridgeEntity>().template(mongoTemplate).collection("FRIDGE_ITEM_EXPIRED")
//                .build();
//    }
    
    
    @Bean
    public Step step1()
            throws Exception {

        return this.stepBuilderFactory.get("step1").<FridgeEntity, FridgeEntity>chunk(5).reader(fridgeItemReader.reader())
                .processor(fridgeItemProcessor).writer(fridgeItemWriter.writer()).build();
    }
    
    
    

}
