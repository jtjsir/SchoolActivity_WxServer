package com.du.wx.model.input;

import com.du.wx.model.common.BaseInputMessage;

public class EventInputMessage extends BaseInputMessage {

	//事件消息
    private String Event ;
    private String EventKey ;
    private String Ticket ;
	public String getEvent() {
		return Event;
	}
	public void setEvent(String event) {
		Event = event;
	}
	public String getEventKey() {
		return EventKey;
	}
	public void setEventKey(String eventKey) {
		EventKey = eventKey;
	}
	public String getTicket() {
		return Ticket;
	}
	public void setTicket(String ticket) {
		Ticket = ticket;
	}
    
    
}
