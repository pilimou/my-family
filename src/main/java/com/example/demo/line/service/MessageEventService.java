package com.example.demo.line.service;

import com.example.demo.line.vo.in.Events;

public interface MessageEventService {
	
	void messageEventText(Events event, String channelToken);
	
	void messageEventImage(Events event, String channelToken);
	
	void messageEventVideo(Events event, String channelToken);
	
	void messageEventAudio(Events event, String channelToken);
	
	void messageEventFile(Events event, String channelToken);
	
	void messageEventLocation(Events event, String channelToken);
	
	void messageEventSticker(Events event, String channelToken);
}
