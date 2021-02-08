package com.web.blog.component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.web.blog.model.dto.ChatMsg;
import com.web.blog.model.dto.ChatRoom;
import com.web.blog.model.service.ChatService;

@Component
public class ChatWebSocketHandler extends TextWebSocketHandler{

	private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

	@Autowired
	ObjectMapper objectMapper;
	
	@Autowired
	ChatService chatServ;
	
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		String payload = message.getPayload();
		logger.info("payload : " + payload );

//		TextMessage textMessage = new TextMessage("Open chatting");
//		session.sendMessage(textMessage);
		
		ChatMsg chatMsg = objectMapper.readValue(payload, ChatMsg.class);
		ChatRoom room = chatServ.findRoomById(chatMsg.getRoomid());
		room.handleAction(session, chatMsg, chatServ);
	}

}
