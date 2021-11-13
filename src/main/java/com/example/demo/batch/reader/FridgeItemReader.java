package com.example.demo.batch.reader;

import java.util.HashMap;
import java.util.Map;

import org.springframework.batch.item.data.MongoItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.format.DateTimeFormatter;

import com.example.demo.line.entity.FridgeEntity;
@Component
public class FridgeItemReader {
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
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

}
