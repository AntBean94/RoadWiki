package com.web.blog.model.repo;

import java.util.List;

import com.web.blog.model.dto.ChatRoom;

public interface ChatRoomRepo {
	
	public List<ChatRoom> selectAll();
	public ChatRoom select(String roomid);
	public ChatRoom selectByTypeName(ChatRoom chatRoom);
	public int insert(ChatRoom chatRoom);
	public int delete(ChatRoom chatRoom);
	public int update(String roomid);
	
}
