package com.web.blog.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.web.blog.model.dto.ChatRoom;
import com.web.blog.model.service.ChatService;

@RestController
@RequestMapping("/chat")
public class ChatController {
	
	@Autowired
	ChatService chatServ;
	
	@PostMapping
	public Object createRoom(@RequestParam String name) {
		Map<String, ChatRoom> result = new HashMap<>();
		ChatRoom chatRoom = null;
		try {
			chatRoom = chatServ.createRoom(name);
		} catch(Exception e) {
			e.printStackTrace();
		}
		System.out.println(chatRoom);
		result.put("chatRoom", chatRoom);
		return chatRoom;
	}
	
	@GetMapping
	public List<ChatRoom> findAllRoom() {
		return chatServ.findAllRoom();
	}
	
}
