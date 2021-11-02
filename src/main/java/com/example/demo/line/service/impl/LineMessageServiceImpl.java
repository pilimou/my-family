package com.example.demo.line.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.format.DateTimeFormatter;

import com.example.demo.line.entity.FridgeEntity;
import com.example.demo.line.repository.FridgeRepository;
import com.example.demo.line.service.LineMessageService;
import com.example.demo.line.service.MessageEventService;
import com.example.demo.line.vo.in.Events;
import com.example.demo.line.vo.in.LineMessageIn;
import com.example.demo.utils.CommonTool;
import com.example.demo.utils.SendMessage;

@Service
public class LineMessageServiceImpl implements LineMessageService, MessageEventService{
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private final String callBackText = "!";
	
	@Autowired
	private FridgeRepository fridgeRepository;

	@Override
	public ResponseEntity<String> processLineMessage(LineMessageIn lineMessageIn, String channelToken) {
		String eventType = null;
		for(Events event : lineMessageIn.getEvents()) {
			if (null != event && null != event.getType()) {
				eventType = event.getType();
				
				//被加好友時
				if(StringUtils.equals(eventType, "follow")) {
					
					
				
				//被封鎖時
				} else if(StringUtils.equals(eventType, "unfollow")) {
				
					
				
				//message
				} else if(StringUtils.equals(eventType, "message")) {
				
					this.messageEvent(event, channelToken);
				
				//機器人被加入群組/聊天室
				} else if(StringUtils.equals(eventType, "join")) {
					
					
					
				//機器人已離開群組/聊天室
				} else if(StringUtils.equals(eventType, "leave")) {
					
					
					
				//使用者加入群組/聊天室	
				} else if(StringUtils.equals(eventType, "memberJoined")) {
					
					
					
				//使用者加入群組/聊天室	
				} else if(StringUtils.equals(eventType, "memberLeft")) {
					
					
				//使用者點選quickReply回傳的資料
				} else if(StringUtils.equals(eventType, "postback")) {
					
					this.postbackEvent(event, channelToken);
					
				}
				
			}
		}
		return null;
	}
	
	
	private void messageEvent(Events event, String channelToken) {
		String messageType = null;
		if(null != event.getMessage() && null != event.getMessage().getType()) {
			messageType = event.getMessage().getType();
			
			//文字
			if(StringUtils.equals(messageType, "text")) {
		
				this.messageEventText(event, channelToken);
			
			//照片	
			} else if(StringUtils.equals(messageType, "image")) {
				
			
				
			//影片
			} else if(StringUtils.equals(messageType, "video")) {
				
			
				
			//audio
			} else if(StringUtils.equals(messageType, "audio")) {
				
			
				
			//檔案
			} else if(StringUtils.equals(messageType, "file")) {
				
			
				
			//location
			} else if(StringUtils.equals(messageType, "location")) {
				
			
				
			//貼圖
			} else if(StringUtils.equals(messageType, "sticker")) {
				
			
				
			}
		}
	}
	
	//使用者點選quickReply回傳的資料
	private void postbackEvent(Events event, String channelToken) {
		if(null != event.getPostback() && null != event.getPostback().getData()) {
			FridgeEntity fridgeEntity = this.getFridgeEntity(event);
			fridgeRepository.save(fridgeEntity);
			try {
				String replyJson = SendMessage.replyMessageTextJson(event.getReplyToken(), "已存檔");
				SendMessage.replyMessage(replyJson, channelToken);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
	}

	@Override
	public void messageEventText(Events event, String channelToken) {
		String replyText = null;
		String replyJson = null;
		String text = event.getMessage().getText().substring(0, 1);
		text = CommonTool.todbc(text);
		if(StringUtils.equals(text, callBackText)) {
			String replyData = event.getMessage().getText().substring(1, event.getMessage().getText().length());
			replyText = replyData;
			try {
				replyJson = SendMessage.quickReplyMessageTextJson_dateTime(event.getReplyToken(), replyText, replyData);
				SendMessage.replyMessage(replyJson, channelToken);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
		
	}
	
	//處理要存入db的entity
	private FridgeEntity getFridgeEntity(Events event) {
		FridgeEntity fridgeEntity = new FridgeEntity();
		fridgeEntity.setAccount("pilimou");
		fridgeEntity.setLineUserId(event.getSource().getUserId());
		fridgeEntity.setLineUserName("閃電霹靂貓");
		fridgeEntity.setItemName(event.getPostback().getData());
		fridgeEntity.setExpirationDateStr(event.getPostback().getParams().getDate());
		fridgeEntity.setProcessDateStr(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
		return fridgeEntity;
	}
	
	@Override
	public void messageEventImage(Events event, String channelToken) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void messageEventVideo(Events event, String channelToken) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void messageEventAudio(Events event, String channelToken) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void messageEventFile(Events event, String channelToken) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void messageEventLocation(Events event, String channelToken) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void messageEventSticker(Events event, String channelToken) {
		// TODO Auto-generated method stub
		
	}
	
	

}
