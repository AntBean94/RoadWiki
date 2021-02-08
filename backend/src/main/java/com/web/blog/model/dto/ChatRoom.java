package com.web.blog.model.dto;

import java.util.HashSet;
import java.util.Set;

import org.springframework.web.socket.WebSocketSession;

import com.web.blog.model.service.ChatService;


public class ChatRoom {
	private String roomid;
	private String name;
	private Set<WebSocketSession> sessions;
	
	
	public ChatRoom(String roomid, String name) {
		this.roomid = roomid;
		this.name = name;
		this.sessions = new HashSet<>();
	}

	public void handleAction(WebSocketSession session, ChatMsg chatMsg, ChatService chatService) {
		if(chatMsg.getType().equals(ChatMsg.MsgType.ENTER)) {
			sessions.add(session);
			chatMsg.setMsg(chatMsg.getSender() + "님이 입장하셨습니다.");
		}
		sendMsg(chatMsg, chatService);
	} 
	
	public <T> void sendMsg(T msg, ChatService chatService) {
		sessions.parallelStream().forEach(session -> chatService.sendMsg(session, msg));
	}

	@Override
	public String toString() {
		return "ChatRoom [roomid=" + roomid + ", name=" + name + ", sessions=" + sessions + "]";
	}

	public String getRoomid() {
		return roomid;
	}

	public void setRoomid(String roomid) {
		this.roomid = roomid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<WebSocketSession> getSessions() {
		return sessions;
	}

	public void setSessions(Set<WebSocketSession> sessions) {
		this.sessions = sessions;
	}
	
}
