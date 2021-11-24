package com.example.demo.batch.config;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.data.MongoItemReader;
import org.springframework.batch.item.data.MongoItemWriter;
import org.springframework.batch.item.data.builder.MongoItemWriterBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.format.DateTimeFormatter;

import com.example.demo.batch.listener.JobCompletionNotificationListener;
import com.example.demo.batch.processor.FridgeItemProcessor;
import com.example.demo.line.entity.FridgeEntity;

public class BatchConfiguration {
	
	@Autowired
    public JobBuilderFactory jobBuilderFactory;
	
    @Autowired
    public StepBuilderFactory stepBuilderFactory; 
    
    @Autowired
    public FridgeItemProcessor fridgeItemProcessor;
    
    @Autowired
    JobCompletionNotificationListener jobCompletionNotificationListener;
    
    @Autowired
    MongoTemplate mongoTemplate;
    
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    
    @Bean
    public Job updateUserJob()
            throws Exception {
    	logger.info("updateUserJob");
    	System.out.println("updateUserJob");
        return this.jobBuilderFactory.get("updateUserJob").incrementer(new RunIdIncrementer())
                .listener(jobCompletionNotificationListener).start(step1()).build();
    }
    
    @Bean
    public MongoItemReader<FridgeEntity> reader() throws Exception{
    	MongoItemReader<FridgeEntity> reader = new MongoItemReader<FridgeEntity>();
    	reader.setCollection("FAMILY_FRIDGE");
    	reader.setTemplate(mongoTemplate);
    	reader.setQuery("{expirationDateStr:{$lte : '" + LocalDateTime.now().plusDays(7).format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) +"'}}");
    	reader.setTargetType(FridgeEntity.class);
    	Map<String,Sort.Direction> sortMap = new HashMap<>();
    	sortMap.put("_id",Direction.ASC);
    	reader.setSort(sortMap);
		return reader;
    	
    }
    
    
    @Bean
    public FridgeItemProcessor processor() {
        return new FridgeItemProcessor();
    }
    
    
    @Bean
    public MongoItemWriter<FridgeEntity> writer() {
       	return new MongoItemWriterBuilder<FridgeEntity>().template(mongoTemplate).collection("FAMILY_FRIDGE")
                .build();
    }
    
    
    @Bean
    public Step step1()
            throws Exception {
    	logger.info("step1");
    	System.out.println("step1");
        return this.stepBuilderFactory.get("step1").<FridgeEntity, FridgeEntity>chunk(5).reader(reader())
                .processor(fridgeItemProcessor).writer(writer()).build();
    }
    
    
    

}
