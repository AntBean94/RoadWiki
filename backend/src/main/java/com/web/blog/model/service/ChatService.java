package com.web.blog.model.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.socket.WebSocketSession;

import com.web.blog.model.dto.ChatRoom;

public interface ChatService {

	public List<ChatRoom> findAllRoom();
	public ChatRoom findRoomById(String roomid);
	public ChatRoom createRoom(String name);
	public <T> void sendMsg(WebSocketSession session, T msg);
}
