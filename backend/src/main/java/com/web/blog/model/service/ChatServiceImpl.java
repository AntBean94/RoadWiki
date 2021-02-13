package com.web.blog.model.service;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.blog.model.dto.ChatRoom;
import com.web.blog.model.repo.ChatRoomRepo;

@Service
public class ChatServiceImpl implements ChatService{
	
	@Autowired
	ChatRoomRepo chatRoomRepo;
	
	private ChatRoom anonymousRoom = null;
	
	@Override
	public Map<String, Object> createOpenRoom(String name) {
		Map<String, Object> result = new HashMap<String, Object>();
		ChatRoom chatRoom = new ChatRoom(UUID.randomUUID().toString(), name, 1);
		chatRoomRepo.insert(chatRoom);
		result.put("chatRoom", chatRoom);
		return result;
	}
	
	@Override
	public Map<String, Object> joinAnonymousChatRoom() {
		Map<String, Object> result = new HashMap<String, Object>();
		if(anonymousRoom != null) {
			result.put("chatRoom", anonymousRoom);
		}
		else {
			try {
				ChatRoom chatRoom = chatRoomRepo.selectByTypeName(new ChatRoom("", "anonymousRoom", 0))[0];
				if(chatRoom == null) {
					chatRoom = new ChatRoom(UUID.randomUUID().toString(), "anonymousRoom", 0);
					chatRoomRepo.insert(chatRoom);
				}
				result.put("chatRoom", chatRoom);
			} catch(Exception e) {
				throw e;
			}
		}
		return result;
	}
	
	@Override
	public Map<String, Object> getChatRoom(String roomid) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("chatRoom", chatRoomRepo.select(roomid));
		return result;
	}
	
	@Override
	public Map<String, Object> getOpenRoom(String name) {
		Map<String, Object> result = new HashMap<String, Object>();
		for(ChatRoom cr:chatRoomRepo.selectByTypeName(new ChatRoom("", name, 1))){
			System.out.println(cr);
		}
		result.put("chatRooms", chatRoomRepo.selectByTypeName(new ChatRoom("", name, 1)));
		return result;
	}
	
}
