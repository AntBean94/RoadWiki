package com.web.blog.model.repo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Repository;

import com.web.blog.model.dto.ChatRoom;

@Repository
public class ChatRepo {
	private Map<String, ChatRoom> chatRoomMap;
	
	@PostConstruct
	private void init() {
		chatRoomMap = new LinkedHashMap<>();
	}
	
	public List<ChatRoom> findAllRoom() {
		List chatRooms = new ArrayList<>(chatRoomMap.values());
		Collections.reverse(chatRooms);
		return chatRooms;
	}
	
	public ChatRoom selectChatRoom(String roomid) {
		return chatRoomMap.get(roomid);
	}
	
	public int insertChatRoom(ChatRoom chatRoom) {
		chatRoomMap.put(chatRoom.getRoomid(), chatRoom);
		return 1;
	}
	
}
