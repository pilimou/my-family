//package com.example.demo.utils;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.apache.commons.lang3.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.client.SimpleClientHttpRequestFactory;
//import org.springframework.web.client.RestTemplate;
//import org.threeten.bp.LocalDateTime;
//import org.threeten.bp.format.DateTimeFormatter;
//
//import com.example.demo.line.vo.out.Reply;
//import com.example.demo.line.vo.out.TextMessages;
//import com.example.demo.line.vo.out.quickreply.Action;
//import com.example.demo.line.vo.out.quickreply.Items;
//import com.example.demo.line.vo.out.quickreply.QuickReply;
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//public class SendMessage {
//
//	private static final Logger logger = LoggerFactory.getLogger(SendMessage.class);
//
//	private final static RestTemplate restTemplate;
//	
//	private final static String  lineReplyUrl = "https://api.line.me/v2/bot/message/reply";
//	private final static String  lineBroadcast = "https://api.line.me/v2/bot/message/broadcast";
//	private final static String  linePush = "https://api.line.me/v2/bot/message/push";
//
//	static {
//		SimpleClientHttpRequestFactory rf = new SimpleClientHttpRequestFactory();
//	    rf.setBufferRequestBody(false);
//		restTemplate = new RestTemplate(rf);
//	}
//
//	/**
//	 * 接收訊息後回傳訊息
//	 * 
//	 * @param replyJson
//	 * @param channelToken
//	 * @param lineReplyUrl
//	 */
//	public static void replyMessage(String replyJson, String channelToken) throws Exception {
//		
//		logger.info("replyJson = {}", replyJson);
//		
//		restTemplate.exchange(lineReplyUrl, HttpMethod.POST,
//				new HttpEntity<>(replyJson, getHttpHeaders(channelToken)),
//				String.class);
//
//	}
//	
//	/**
//	 * 全體推播
//	 * 
//	 * @param replyJson
//	 * @param channelToken
//	 * @param lineReplyUrl
//	 */
//	public static void broadcastMessage(String replyJson, String channelToken) throws Exception {
//		
//		logger.info("replyJson = {}", replyJson);
//		
//		restTemplate.exchange(lineBroadcast, HttpMethod.POST,
//				new HttpEntity<>(replyJson, getHttpHeaders(channelToken)),
//				String.class);
//
//	}
//	
//	/**
//	 * 推播to a user, group, or room
//	 * 
//	 * @param replyJson
//	 * @param channelToken
//	 * @param lineReplyUrl
//	 */
//	public static void pushMessage(String replyJson, String channelToken) throws Exception {
//		
//		logger.info("replyJson = {}", replyJson);
//		
//		restTemplate.exchange(linePush, HttpMethod.POST,
//				new HttpEntity<>(replyJson, getHttpHeaders(channelToken)),
//				String.class);
//
//	}
//
//	//header
//	private static HttpHeaders getHttpHeaders(String channelToken) {
//		HttpHeaders headers = new HttpHeaders();
//		headers.add("Content-Type", "application/json");
//		headers.add("Authorization", "Bearer {" + channelToken + "}");
//		return headers;
//	}
//
//	//文字
//	public static String replyMessageTextJson(String messageActionType, String replyToken, String[] messages) throws Exception{
//		Reply reply = new Reply();
//		List<TextMessages> textList = replyMessage(messages);
//		reply.setMessages(textList);
//		if (StringUtils.equals(messageActionType, "reply")) {
//			reply.setReplyToken(replyToken);
//		} else if (StringUtils.equals(messageActionType, "broadcast")) {
//			
//		} else if(StringUtils.equals(messageActionType, "to")) {
//			reply.setTo(replyToken);
//		}
//		return new ObjectMapper().writeValueAsString(reply);
//	}
//	
//	private static List<TextMessages> replyMessage(String[] messages){
//		List<TextMessages> textList = new ArrayList<>();
//		for(String message : messages) {
//			TextMessages textMessages = new TextMessages();
//			textMessages.setType("text");
//			textMessages.setText(message);
//			textList.add(textMessages);
//		}
//		return textList;
//	}
//	
//	//quickReply日期
//	public static String quickReplyMessageTextJson_dateTime(String replyToken, String message, String replyData) throws Exception{
//	
//		
//		List<Items> itemsList = new ArrayList<>();
//		Items items = new Items();
//		Action action = new Action();
//		action.setType("datetimepicker");
//		action.setLabel("選擇日期時間");
//		action.setData(replyData);
//		action.setMode("date");
//		action.setInitial(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
//		action.setMax(LocalDateTime.now().plusYears(2).format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
//		action.setMin(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
//		items.setType("action");
//		items.setAction(action);
//		itemsList.add(items);
//		QuickReply quickReply = new QuickReply();
//		quickReply.setItems(itemsList);
//		
//		Reply reply = new Reply();
//		List<TextMessages> textList = new ArrayList<>();
//		TextMessages textMessages = new TextMessages();
//		textMessages.setType("text");
//		textMessages.setText(message);
//		textMessages.setQuickReply(quickReply);
//		textList.add(textMessages);
//		reply.setMessages(textList);
//		reply.setReplyToken(replyToken);
//		
//		return new ObjectMapper().writeValueAsString(reply);
//	}
//	
//
//}
