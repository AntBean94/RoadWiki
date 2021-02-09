package com.web.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

import com.web.blog.model.dto.ChatMsg;

@Controller
public class ChatController {
	
	@Autowired
	SimpMessageSendingOperations messagingTemplate;
	
	@MessageMapping("/chat/message")
	public void message(ChatMsg message) {
		System.out.println("----------------- message in ----------------------");
		if(ChatMsg.MsgType.JOIN.equals(message.getType()))
			message.setMsg(message.getSender() + "님이 입장하셨습니다.");
		messagingTemplate.convertAndSend("/sub/chat/room/" + message.getRoomid(), message);
	}
}
