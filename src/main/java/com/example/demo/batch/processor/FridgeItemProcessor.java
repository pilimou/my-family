package com.example.demo.batch.processor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.example.demo.line.entity.FridgeEntity;
import com.example.demo.utils.SendMessage;

@Component
public class FridgeItemProcessor implements ItemProcessor<FridgeEntity, FridgeEntity>{
	
	private static final Logger logger = LoggerFactory.getLogger(FridgeEntity.class);
	
	@Value("${lineBot.nike.channelToken}")
	private String channelToken;

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
		String replyJson = SendMessage.replyMessageTextJson("to", "Uaae19ff20461f35d3db44135adfe2e56", messages);
		logger.info("sendMessage = {}", replyJson);
		SendMessage.pushMessage(replyJson, channelToken);

		return newItem;
	}

}
