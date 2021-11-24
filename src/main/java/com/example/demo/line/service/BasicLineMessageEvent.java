package com.example.demo.line.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.demo.line.vo.in.Events;
import com.example.demo.line.vo.in.LineMessageIn;

public class BasicLineMessageEvent {
	
	public ResponseEntity<String> processLineMessage(LineMessageIn lineMessageIn, String channelToken) {
		for(Events event : lineMessageIn.getEvents()) {
			if (null != event && null != event.getType()) {
				//群組
				if(null != event.getSource() && StringUtils.equals("group", event.getSource().getType())) {
					
					this.groupEvents(event, channelToken);
					
					//個人
				} else if(null != event.getSource() && StringUtils.equals("user", event.getSource().getType())) {
					
					this.userEvents(event, channelToken);
					
				}
			}
		}
		
		return new ResponseEntity<String>(HttpStatus.OK);
	}	
	
	//群組
	private void groupEvents(Events event, String channelToken) {
		
	}
	
	//個人
	private void userEvents(Events event, String channelToken) {
		String eventType = event.getType();
		//被加好友時
		if(StringUtils.equals(eventType, "follow")) {
			
			this.followEvent(event, channelToken);
			
			//被封鎖時
		} else if(StringUtils.equals(eventType, "unfollow")) {
			
			this.unfollowEvent(event, channelToken);
			
			//message
		} else if(StringUtils.equals(eventType, "message")) {
			
			this.messageEvent(event, channelToken);
			
			//機器人被加入群組/聊天室
		} else if(StringUtils.equals(eventType, "join")) {
			
			this.joinEvent(event, channelToken);
			
			//機器人已離開群組/聊天室
		} else if(StringUtils.equals(eventType, "leave")) {
			
			this.leaveEvent(event, channelToken);
			
			//使用者加入群組/聊天室	
		} else if(StringUtils.equals(eventType, "memberJoined")) {
			
			this.memberJoinedEvent(event, channelToken);
			
			//使用者離開群組/聊天室	
		} else if(StringUtils.equals(eventType, "memberLeft")) {
			
			this.memberLeftEvent(event, channelToken);
			
			//使用者點選quickReply回傳的資料
		} else if(StringUtils.equals(eventType, "postback")) {
			
			this.postbackEvent(event, channelToken);
			
		}
	}
	
//================userEvents===============	
	
	//被加好友時
	protected void followEvent(Events event, String channelToken) {}
	
	//被封鎖時
	protected void unfollowEvent(Events event, String channelToken) {}
	
	//機器人被加入群組/聊天室
	protected void joinEvent(Events event, String channelToken) {}
	
	//機器人已離開群組/聊天室
	protected void leaveEvent(Events event, String channelToken) {}
	
	//使用者加入群組/聊天室
	protected void memberJoinedEvent(Events event, String channelToken) {}
	
	//使用者離開群組/聊天室
	protected void memberLeftEvent(Events event, String channelToken) {}
	
	//使用者點選quickReply回傳的資料
	protected void postbackEvent(Events event, String channelToken) {}
	
	//message
	private void messageEvent(Events event, String channelToken) {
		if(null != event.getMessage() && null != event.getMessage().getType()) {
			String messageType = event.getMessage().getType();
			
			//文字
			if(StringUtils.equals(messageType, "text")) {
				
				this.messageEventText(event, channelToken);
				
				//照片
			} else if(StringUtils.equals(messageType, "image")) {
				
				this.messageEventImage(event, channelToken);
				
				//影片
			} else if(StringUtils.equals(messageType, "video")) {
				
				this.messageEventVideo(event, channelToken);
				
				//audio
			} else if(StringUtils.equals(messageType, "audio")) {
				
				this.messageEventAudio(event, channelToken);
				
				//檔案
			} else if(StringUtils.equals(messageType, "file")) {
				
				this.messageEventFile(event, channelToken);
				
				//location
			} else if(StringUtils.equals(messageType, "location")) {
				
				this.messageEventLocation(event, channelToken);
				
				//貼圖
			} else if(StringUtils.equals(messageType, "sticker")) {
				
				this.messageEventSticker(event, channelToken);
				
			}
		}
		
	}

//================messageEvent===============	
	
	//文字
	protected void messageEventText(Events event, String channelToken) {}
	
	//照片
	protected void messageEventImage(Events event, String channelToken) {}
	
	//影片
	protected void messageEventVideo(Events event, String channelToken) {}
	
	//audio
	protected void messageEventAudio(Events event, String channelToken) {}
	
	//檔案
	protected void messageEventFile(Events event, String channelToken) {}
	
	//location
	protected void messageEventLocation(Events event, String channelToken) {}
	
	//貼圖
	protected void messageEventSticker(Events event, String channelToken) {}
}
