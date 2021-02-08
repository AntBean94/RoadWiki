package com.web.blog.model.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.web.blog.model.dto.ChatRoom;

@Service
public class ChatServiceImpl implements ChatService{

	private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());
	
	@Autowired
	ObjectMapper objectMapper;
	
	private Map<String, ChatRoom> chatRooms;
	
	@PostConstruct
	private void init() {
		chatRooms = new LinkedHashMap<>();
	}
	
	public List<ChatRoom> findAllRoom() {
		return new ArrayList<>(chatRooms.values());
	}
	
	public ChatRoom findRoomById(String roomid) {
		return chatRooms.get(roomid);
	}
	
	public ChatRoom createRoom(String name) {
		String randomid = UUID.randomUUID().toString();
		ChatRoom chatRoom = new ChatRoom(randomid, name);
		chatRooms.put(randomid, chatRoom);
		return chatRoom;
	}
	
	public <T> void sendMsg(WebSocketSession session, T msg) {
		try {
			session.sendMessage(new TextMessage(objectMapper.writeValueAsString(msg)));
		} catch(IOException e) {
			logger.error(e.getMessage());
		}
	}
	
}
