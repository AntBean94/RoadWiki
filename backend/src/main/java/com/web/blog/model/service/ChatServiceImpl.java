package com.web.blog.model.service;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.blog.model.dto.ChatRoom;
import com.web.blog.model.repo.ChatRepo;

@Service
public class ChatServiceImpl implements ChatService{
	
	@Autowired
	ChatRepo chatRepo;
	
	private ChatRoom room;
	
	@PostConstruct
	private void init() {
		room = this.createChatRoom("room");
	}
	
	@Override
	public ChatRoom createChatRoom(String name) {
		ChatRoom chatRoom = new ChatRoom(UUID.randomUUID().toString(), name);
		chatRepo.insertChatRoom(chatRoom);
		return chatRoom;
	}
	
	@Override
	public Map<String, Object> joinAnonymousChatRoom() {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("chatRoom", room);
		return result;
	}
	
	@Override
	public Map<String, Object> getChatRoom(String roomid) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("chatRoom", room);
		return result;
	}
	
//	@Override
//	public ChatRoom[] getAllChatRoom() {
//		return null;
//	}
	
}
