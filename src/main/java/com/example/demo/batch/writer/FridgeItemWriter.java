package com.example.demo.batch.writer;

import org.springframework.batch.item.data.MongoItemWriter;
import org.springframework.batch.item.data.builder.MongoItemWriterBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import com.example.demo.line.entity.FridgeEntity;

@Component
public class FridgeItemWriter {

	@Autowired
	private MongoTemplate mongoTemplate;
	
	public MongoItemWriter<FridgeEntity> writer() {
    	return new MongoItemWriterBuilder<FridgeEntity>().template(mongoTemplate).collection("FAMILY_FRIDGE")
                .build();
    }
}
