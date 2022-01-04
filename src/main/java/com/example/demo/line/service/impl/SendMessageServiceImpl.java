package com.example.demo.line.service.impl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.demo.line.service.SendMessageService;
import com.example.demo.line.vo.out.Reply;
import com.example.demo.line.vo.out.TextMessages;
import com.example.demo.line.vo.out.quickreply.Action;
import com.example.demo.line.vo.out.quickreply.Items;
import com.example.demo.line.vo.out.quickreply.QuickReply;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class SendMessageServiceImpl implements SendMessageService{
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private RestTemplate restTemplate;
	
	private final String  lineReplyUrl = "https://api.line.me/v2/bot/message/reply";
	private final String  lineBroadcast = "https://api.line.me/v2/bot/message/broadcast";
	private final String  linePush = "https://api.line.me/v2/bot/message/push";
	
	/**
	 * 接收訊息後回傳訊息
	 * 
	 * @param replyJson
	 * @param channelToken
	 * @param lineReplyUrl
	 */
	@Override
	public void replyMessage(String replyJson, String channelToken) {
		logger.info("replyJson = {}", replyJson);
		
		restTemplate.exchange(lineReplyUrl, HttpMethod.POST,
				new HttpEntity<>(replyJson, getHttpHeaders(channelToken)),
				String.class);
		
	}
	
	/**
	 * 全體推播
	 * 
	 * @param replyJson
	 * @param channelToken
	 * @param lineReplyUrl
	 */
	@Override
	public void broadcastMessage(String replyJson, String channelToken) {
		logger.info("replyJson = {}", replyJson);
		
		restTemplate.exchange(lineBroadcast, HttpMethod.POST,
				new HttpEntity<>(replyJson, getHttpHeaders(channelToken)),
				String.class);
		
	}
	
	/**
	 * 推播to a user, group, or room
	 * 
	 * @param replyJson
	 * @param channelToken
	 * @param lineReplyUrl
	 */
	@Override
	public void pushMessage(String replyJson, String channelToken) {
		logger.info("replyJson = {}", replyJson);
		
		restTemplate.exchange(linePush, HttpMethod.POST,
				new HttpEntity<>(replyJson, getHttpHeaders(channelToken)),
				String.class);
		
	}
	
	//文字
	@Override
	public String replyMessageTextJson(String messageActionType, String replyToken, String[] messages) {
		Reply reply = new Reply();
		List<TextMessages> textList = this.replyMessage(messages);
		reply.setMessages(textList);
		if (StringUtils.equals(messageActionType, "reply")) {
			reply.setReplyToken(replyToken);
		} else if (StringUtils.equals(messageActionType, "broadcast")) {
			
		} else if(StringUtils.equals(messageActionType, "to")) {
			reply.setTo(replyToken);
		}
		return this.writeValueAsString(reply);
	}
	
	private List<TextMessages> replyMessage(String[] messages){
		List<TextMessages> textList = new ArrayList<>();
		for(String message : messages) {
			TextMessages textMessages = new TextMessages();
			textMessages.setType("text");
			textMessages.setText(message);
			textList.add(textMessages);
		}
		return textList;
	}
	
	//quickReply日期
	@Override
	public String quickReplyMessageTextJson_dateTime(String replyToken, String message, String replyData) {
		List<Items> itemsList = new ArrayList<>();
		Items items = new Items();
		Action action = new Action();
		action.setType("datetimepicker");
		action.setLabel("選擇日期時間");
		action.setData(replyData);
		action.setMode("date");
		action.setInitial(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
		action.setMax(LocalDateTime.now().plusYears(2).format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
		action.setMin(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
		items.setType("action");
		items.setAction(action);
		itemsList.add(items);
		QuickReply quickReply = new QuickReply();
		quickReply.setItems(itemsList);
		
		Reply reply = new Reply();
		List<TextMessages> textList = new ArrayList<>();
		TextMessages textMessages = new TextMessages();
		textMessages.setType("text");
		textMessages.setText(message);
		textMessages.setQuickReply(quickReply);
		textList.add(textMessages);
		reply.setMessages(textList);
		reply.setReplyToken(replyToken);
		
		return this.writeValueAsString(reply);
	}
	
	//header
	private static HttpHeaders getHttpHeaders(String channelToken) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		headers.add("Authorization", "Bearer {" + channelToken + "}");
		return headers;
	}
	
	private String writeValueAsString(Reply reply) {
		String str = null;
		try {
			str = new ObjectMapper().writeValueAsString(reply);
		} catch (JsonProcessingException e) {
			logger.warn(e.getMessage());
		}	
		
		return str;
	}

}
