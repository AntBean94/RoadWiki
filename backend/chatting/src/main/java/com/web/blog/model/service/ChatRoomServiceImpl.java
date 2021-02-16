package com.web.blog.model.service;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.web.blog.model.dto.ChatRoom;
import com.web.blog.model.repo.ChatRoomRepo;

@Service
public class ChatRoomServiceImpl implements ChatRoomService{
	
	@Autowired
	ChatRoomRepo chatRoomRepo;
	
//	private ChatRoom anonymousRoom = null;
	
	@Override
	public Map<String, Object> createOpenRoom(String name) {
		Map<String, Object> result = new HashMap<String, Object>();
		ChatRoom chatRoom = new ChatRoom(UUID.randomUUID().toString(), name, 1);
		try {
			chatRoomRepo.insert(chatRoom);
		} catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
		result.put("chatRoom", chatRoom);
		return result;
	}
	
//	@Override
//	public Map<String, Object> joinAnonymousChatRoom() {
//		Map<String, Object> result = new HashMap<String, Object>();
//		if(anonymousRoom != null) {
//			result.put("chatRoom", anonymousRoom);
//		}
//		else {
//			try {
//				ChatRoom chatRoom = chatRoomRepo.select(roomid)(new ChatRoom("", "anonymousRoom", 0))[0];
//				if(chatRoom == null) {
//					chatRoom = new ChatRoom(UUID.randomUUID().toString(), "anonymousRoom", 0);
//					chatRoomRepo.insert(chatRoom);
//				}
//				result.put("chatRoom", chatRoom);
//			} catch(Exception e) {
//				throw e;
//			}
//		}
//		return result;
//	}
	
	@Override
	public Map<String, Object> getChatRoom(String roomid) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("chatRoom", chatRoomRepo.select(roomid));
		return result;
	}
	
	@Override
	public Map<String, Object> getOpenRoom(String name) {
		Map<String, Object> result = new HashMap<String, Object>();
		for(ChatRoom cr:chatRoomRepo.selectAll()){
			System.out.println(cr);
		}
		result.put("chatRooms", chatRoomRepo.selectAll());
		return result;
	}
	
}
