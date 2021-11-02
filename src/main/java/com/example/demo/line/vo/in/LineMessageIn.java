package com.example.demo.line.vo.in;

import java.util.List;

public class LineMessageIn {
	
	private String destination;
	
	private List<Events> events;

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public List<Events> getEvents() {
		return events;
	}

	public void setEvents(List<Events> events) {
		this.events = events;
	}

	
	
}
