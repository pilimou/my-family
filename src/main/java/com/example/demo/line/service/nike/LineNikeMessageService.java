package com.example.demo.line.service.nike;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.format.DateTimeFormatter;

import com.example.demo.line.entity.FridgeEntity;
import com.example.demo.line.repository.FridgeRepository;
import com.example.demo.line.service.BasicLineMessageEvent;
import com.example.demo.line.vo.in.Events;
import com.example.demo.line.vo.out.flexmessage.FlexBody;
import com.example.demo.line.vo.out.flexmessage.FlexBodyContents;
import com.example.demo.line.vo.out.flexmessage.FlexContents;
import com.example.demo.line.vo.out.flexmessage.FlexHero;
import com.example.demo.line.vo.out.flexmessage.FlexMessages;
import com.example.demo.line.vo.out.flexmessage.PushFlex;
import com.example.demo.line.vo.out.quickreply.Action;
import com.example.demo.utils.CommonTool;
import com.example.demo.utils.SendMessage;
import com.example.demo.web.entity.AppUserEntity;
import com.example.demo.web.repository.AppUserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class LineNikeMessageService extends BasicLineMessageEvent{
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private final String callBackText = "!";
	
	@Autowired
	private FridgeRepository fridgeRepository;
	
	@Autowired
	private AppUserRepository appUserRepository;
	
	
	//被加好友時
	@Override
	protected void followEvent(Events event, String channelToken) {
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
	
	//文字
	@Override
	protected void messageEventText(Events event, String channelToken) {
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
				
			} else if(replyData.contains(" ") && replyData.split(" ")[0].trim().equals("刪除")) {
				//刪除該物品保存期限最近的一筆
				this.deleteFridgeEntity(event, channelToken, replyData);
		
			} else if(StringUtils.equals(replyData, "冰箱")){
				//回複網頁URL
				this.sendWebURL(event, channelToken, replyData);
			} else if(StringUtils.equals(replyData, "指令")){
				//回複目前指令
				this.sendCommand(event, channelToken, replyData);
			} else if(!StringUtils.equals(replyData, "刪除")){
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
	
	//使用者點選quickReply回傳的資料
	@Override
	protected void postbackEvent(Events event, String channelToken) {
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
			//flex message
			String myCard = this.getWeb_URI_FlexJson(event.getSource().getUserId());
			SendMessage.pushMessage(myCard, channelToken);
			//單純回復訊息
//			String[] replyText = {"請至網頁查看", "https://newfamily1113-007-2ufd6oyakq-uc.a.run.app/home/body"};
//			String replyJson = SendMessage.replyMessageTextJson("reply", event.getReplyToken(), replyText);
//			SendMessage.replyMessage(replyJson, channelToken);
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
	
	//刪除該物品保存期限最近的一筆
	private void deleteFridgeEntity(Events event, String channelToken, String replyData) {
		String replyJson = null;
		try {
			FridgeEntity deleteEntity =  fridgeRepository.findTopByItemNameOrderByExpirationDateStrAsc(replyData.split(" ")[1].trim());
			if(null != deleteEntity) {
				fridgeRepository.delete(deleteEntity);
				replyJson = SendMessage.replyMessageTextJson("reply", event.getReplyToken(), new String[] {deleteEntity.getItemName() + " 保存期限 " + deleteEntity.getExpirationDateStr() + " 已刪除"});
			} else {
				replyJson = SendMessage.replyMessageTextJson("reply", event.getReplyToken(), new String[] {"刪除失敗 該物品無紀錄!"});
			}
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
	
	//flex message json
	private String getWeb_URI_FlexJson(String pushToken) throws Exception{
		Action action = new Action();
		action.setType("uri");
		action.setLabel("冰箱網頁連結");
		action.setUri("https://newfamily1113-007-2ufd6oyakq-uc.a.run.app/home/body");
		
		List<FlexBodyContents> flexBodyContentsList = new ArrayList<>();
		FlexBodyContents flexBodyContents = new FlexBodyContents();
		flexBodyContents.setType("button");
		flexBodyContents.setAction(action);
		flexBodyContents.setOffsetTop("none");
		flexBodyContents.setStyle("link");
		flexBodyContentsList.add(flexBodyContents);
		
		FlexBody flexBody = new FlexBody();
		flexBody.setType("box");
		flexBody.setLayout("vertical");
		flexBody.setContents(flexBodyContentsList);
		
		FlexHero flexHero = new FlexHero();
		flexHero.setType("image");
		flexHero.setUrl("https://scontent.ftpe11-2.fna.fbcdn.net/v/t39.30808-6/255650310_4999619113400103_324230386128075232_n.jpg?_nc_cat=105&ccb=1-5&_nc_sid=730e14&_nc_ohc=e69cM6q7xgEAX95teI-&_nc_ht=scontent.ftpe11-2.fna&oh=04ea75872d36a0ee8bcf9ad487972953&oe=6199205F");
		flexHero.setSize("full");
		flexHero.setAspectMode("cover");
		
		FlexContents flexContents = new FlexContents();
		flexContents.setHero(flexHero);
		flexContents.setBody(flexBody);
		flexContents.setType("bubble");
		
		List<FlexMessages> flexmessagesList = new ArrayList<>();
		FlexMessages flexMessages = new FlexMessages();
		flexMessages.setType("flex");
		flexMessages.setAltText("This is a Flex Message");
		flexMessages.setContents(flexContents);
		flexmessagesList.add(flexMessages);
		
		PushFlex pushFlex = new PushFlex();
		pushFlex.setTo(pushToken);
		pushFlex.setMessages(flexmessagesList);
		
		
		return new ObjectMapper().writeValueAsString(pushFlex);
	}
}
