package com.example.demo.line.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
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
import com.example.demo.web.entity.AppUserEntity;
import com.example.demo.web.repository.AppUserRepository;

@Service
public class LineMessageServiceImpl implements LineMessageService, MessageEventService{
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private final String callBackText = "!";
	
	@Autowired
	private FridgeRepository fridgeRepository;
	
	@Autowired
	private AppUserRepository appUserRepository;

	@Override
	public ResponseEntity<String> processLineMessage(LineMessageIn lineMessageIn, String channelToken) {
		String eventType = null;
		for(Events event : lineMessageIn.getEvents()) {
			if (null != event && null != event.getType()) {
				eventType = event.getType();
				//群組
				if(null != event.getSource() && StringUtils.equals("group", event.getSource().getType())) {
					return new ResponseEntity<String>(HttpStatus.OK);
				//個人	
				} else if(null != event.getSource() && StringUtils.equals("user", event.getSource().getType())) {
					
					//被加好友時
					if(StringUtils.equals(eventType, "follow")) {
						
						this.followEvent(event, channelToken);
						
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
		}
		return null;
	}
	
	//被加好友時
	private void followEvent(Events event, String channelToken) {
		if(StringUtils.isNoneBlank(event.getSource().getUserId())) {
			AppUserEntity appUserEntity = appUserRepository.findByLineUserId(event.getSource().getUserId());
			if(null == appUserEntity) {
				appUserEntity= new AppUserEntity();
				appUserEntity.setLineUserId(event.getSource().getUserId());
				appUserEntity.setRoleId("2");
				appUserRepository.save(appUserEntity);
				String[] messages = {"你也不想努力了嗎?", "請先設定您的暱稱，輸入", "!暱稱 XXXX", "XXXX為您想要設定的暱稱"};
				String replyJson;
				try {
					replyJson = SendMessage.replyMessageTextJson("to", event.getSource().getUserId(), messages);
					SendMessage.pushMessage(replyJson, channelToken);
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}
				
				
			}
		}
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
			String[] message = {"已存檔"}; 
			try {
				String replyJson = SendMessage.replyMessageTextJson("reply",event.getReplyToken(), message);
				SendMessage.replyMessage(replyJson, channelToken);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
	}

	@Override
	public void messageEventText(Events event, String channelToken){
		//第一位轉半形
		String text = event.getMessage().getText().substring(0, 1);
		text = CommonTool.todbc(text);
		
		//如果訊息開頭是"!"
		if(StringUtils.equals(text, callBackText)) {
			
			String replyData = event.getMessage().getText().substring(1, event.getMessage().getText().length());
			AppUserEntity appUserEntity = appUserRepository.findByLineUserId(event.getSource().getUserId());
			
			//如果是暱稱
			if(replyData.contains(" ") && replyData.split(" ")[0].trim().equals("暱稱")) {
				//如果資料庫有資料
				if(null != appUserEntity) {
					//存入暱稱
					this.setNickName(event, channelToken, replyData, appUserEntity);
				}
				
			} else if(StringUtils.equals(replyData, "冰箱")){
				//回複網頁URL
				this.sendWebURL(event, channelToken, replyData);
			} else if(StringUtils.equals(replyData, "指令")){
				//回複目前指令
				this.sendCommand(event, channelToken, replyData);
			} else {
				//如果資料庫有帳號但是還沒設定暱稱 
				if(null != appUserEntity && null == appUserEntity.getLineUserName()) {
					//發訊息請使用者設定暱稱
					this.sendMessageToSetNickName(event, channelToken, replyData, appUserEntity);
					
				} else {
					//已有暱稱 就直接回quickReply讓使用者點選保存期限
					this.sendQuickReply(event, channelToken, replyData, appUserEntity);
				}
				
			}
			
		}
		
	}
	
	//發訊息請使用者設定暱稱
	private void setNickName(Events event, String channelToken, String replyData, AppUserEntity appUserEntity) {

		appUserEntity.setLineUserName(replyData.split(" ")[1].trim());
		appUserRepository.save(appUserEntity);
		try {
			String[] replyText = {replyData.split(" ")[1] + " 已儲存!", "可以輸入", "!指令", "來查看可用命令"};
			String replyJson = SendMessage.replyMessageTextJson("reply", event.getReplyToken(), replyText);
			SendMessage.replyMessage(replyJson, channelToken);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	
	}
	
	//發訊息請使用者設定暱稱
	private void sendMessageToSetNickName(Events event, String channelToken, String replyData, AppUserEntity appUserEntity) {
		try {
			String[] replyText = {"請先設定暱稱! 輸入", "!暱稱 XXXX", "XXXX為您想要設定的暱稱"};
			String replyJson = SendMessage.replyMessageTextJson("reply", event.getReplyToken(), replyText);
			SendMessage.replyMessage(replyJson, channelToken);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	//已有暱稱 就直接回quickReply讓使用者點選保存期限
	private void sendQuickReply(Events event, String channelToken, String replyData, AppUserEntity appUserEntity) {
		String replyText = "請選擇保存期限";
		try {
			String replyJson = SendMessage.quickReplyMessageTextJson_dateTime(event.getReplyToken(), replyText, replyData);
			SendMessage.replyMessage(replyJson, channelToken);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}	
	}
	
	//發送網頁URL
	private void sendWebURL(Events event, String channelToken, String replyData) {
		try {
			String[] replyText = {"請至網頁查看", "https://newfamily1113-007-2ufd6oyakq-uc.a.run.app/home/body"};
			String replyJson = SendMessage.replyMessageTextJson("reply", event.getReplyToken(), replyText);
			SendMessage.replyMessage(replyJson, channelToken);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	//回複目前指令
	private void sendCommand(Events event, String channelToken, String replyData) {
		try {
			String[] replyText = {"修改暱稱 !暱稱 XXXX", "查看冰箱 !冰箱", "紀錄東西到冰箱 !XX"};
			String replyJson = SendMessage.replyMessageTextJson("reply", event.getReplyToken(), replyText);
			SendMessage.replyMessage(replyJson, channelToken);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	//處理要存入db的entity
	private FridgeEntity getFridgeEntity(Events event) {
		AppUserEntity appUserEntity = appUserRepository.findByLineUserId(event.getSource().getUserId());
		FridgeEntity fridgeEntity = new FridgeEntity();
//		fridgeEntity.setAccount("pilimou");
		fridgeEntity.setLineUserId(event.getSource().getUserId());
		fridgeEntity.setLineUserName(appUserEntity.getLineUserName());
		fridgeEntity.setItemName(event.getPostback().getData());
		fridgeEntity.setExpirationDateStr(event.getPostback().getParams().getDate());
		fridgeEntity.setProcessDateStr(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
		return fridgeEntity;
	}
	
	@Override
	public void messageEventImage(Events event, String channelToken) {
		
	}


	@Override
	public void messageEventVideo(Events event, String channelToken) {
		
	}


	@Override
	public void messageEventAudio(Events event, String channelToken) {
		
	}


	@Override
	public void messageEventFile(Events event, String channelToken) {
		
	}


	@Override
	public void messageEventLocation(Events event, String channelToken) {
		
	}


	@Override
	public void messageEventSticker(Events event, String channelToken) {
		
	}
	
	

}
