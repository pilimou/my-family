package com.example.demo.line.tasks.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.format.DateTimeFormatter;

import com.example.demo.line.entity.FridgeEntity;
import com.example.demo.line.repository.FridgeRepository;
import com.example.demo.line.tasks.ProcessExpirationService;
import com.example.demo.utils.SendMessage;

@Service
public class ProcessExpirationServiceImpl implements ProcessExpirationService{

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Autowired
	private FridgeRepository fridgeRepository;
	
	@Value("${lineBot.nike.channelToken}")
	private String channelToken;
	
	@Value("${lineBot.family.groupId}")
	private String family_groupId;
	
	@Override
	public void pushExpirationMessage() throws Exception{
		Query query = new Query();
		query.addCriteria(Criteria.where("expirationDateStr").lte(LocalDateTime.now().plusDays(7).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))));
		List<FridgeEntity> fridgeEntityList = mongoTemplate.find(query, FridgeEntity.class);
		if(!fridgeEntityList.isEmpty()) {
			for(FridgeEntity vo : fridgeEntityList) {
				vo.setState("即將過期");
				String[] messages = {vo.getLineUserName() + " 買的 " 
						+ vo.getItemName() + " 保存期限 " + vo.getExpirationDateStr() + " 即將過期 "};
				String replyJson = SendMessage.replyMessageTextJson("to", family_groupId, messages);
				logger.info("sendMessage = {}", replyJson);
				SendMessage.pushMessage(replyJson, channelToken);
			}
			fridgeRepository.saveAll(fridgeEntityList);
		}
		

		
	}
	

}
