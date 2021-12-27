package com.example.demo.batch.processor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.example.demo.line.entity.FridgeEntity;
import com.example.demo.line.service.SendMessageService;

public class FridgeItemProcessor implements ItemProcessor<FridgeEntity, FridgeEntity>{
	
	private static final Logger logger = LoggerFactory.getLogger(FridgeEntity.class);
	
	@Value("${lineBot.nike.channelToken}")
	private String channelToken;
	
	@Value("${lineBot.family.groupId}")
	private String family_groupId;
	
	@Autowired
	private SendMessageService sendMessageService;

	@Override
	public FridgeEntity process(FridgeEntity item) throws Exception {
		
		logger.info("processing FridgeEntity data = {}", item);
		
		FridgeEntity newItem = new FridgeEntity();
		newItem.setAccount(item.getAccount());
		newItem.setExpirationDateStr(item.getExpirationDateStr());
		newItem.setId(item.getId());
		newItem.setItemName(item.getItemName());
		newItem.setLineUserId(item.getLineUserId());
		newItem.setLineUserName(item.getLineUserName());
		newItem.setProcessDateStr(item.getProcessDateStr());
		newItem.setState("即將過期");		
		String[] messages = {item.getLineUserName() + " 買的 " 
				+ item.getItemName() + " 保存期限 " + item.getExpirationDateStr() + " 即將過期 "};
		String replyJson = sendMessageService.replyMessageTextJson("to", "family_groupId", messages);
		logger.info("sendMessage = {}", replyJson);
		sendMessageService.pushMessage(replyJson, channelToken);

		return newItem;
	}

}
