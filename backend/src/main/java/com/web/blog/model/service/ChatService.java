package com.web.blog.model.service;

import java.util.Map;

import com.web.blog.model.dto.ChatRoom;

public interface ChatService {
	public ChatRoom createChatRoom(String name);
	public Map<String, Object> joinAnonymousChatRoom();
	public Map<String, Object> getChatRoom(String roomid);
//	public ChatRoom[] getAllChatRoom();
}
