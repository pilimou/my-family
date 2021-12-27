package com.example.demo.line.service;

public interface SendMessageService {
	
	/**
	 * 接收訊息後回傳訊息
	 * 
	 * @param replyJson
	 * @param channelToken
	 * @param lineReplyUrl
	 */
	void replyMessage(String replyJson, String channelToken);
	
	/**
	 * 全體推播
	 * 
	 * @param replyJson
	 * @param channelToken
	 * @param lineReplyUrl
	 */
	void broadcastMessage(String replyJson, String channelToken);
	
	/**
	 * 推播to a user, group, or room
	 * 
	 * @param replyJson
	 * @param channelToken
	 * @param lineReplyUrl
	 */	
	void pushMessage(String replyJson, String channelToken);
	
	//文字
	String replyMessageTextJson(String messageActionType, String replyToken, String[] messages);
	
	//quickReply日期
	String quickReplyMessageTextJson_dateTime(String replyToken, String message, String replyData);
	
	
}
