package com.web.blog.model.dto;

import java.util.UUID;


public class ChatRoom {
	private String roomid;
	private String name;
	
	public ChatRoom(String roomid, String name) {
		this.roomid = roomid;
		this.name = name;
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

	@Override
	public String toString() {
		return "ChatRoom [roomid=" + roomid + ", name=" + name + "]";
	}
	
	
	
}
