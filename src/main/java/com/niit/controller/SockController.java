package com.niit.controller;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

import com.niit.model.Chat;


@Controller
public class SockController {
private SimpMessagingTemplate messagingTemplate;
//username will be added to the list whenever user joins the chat room;
private List<String> users = new ArrayList<String>();
//The object of type MessagingTemplate will be injected[autowired] by spring controller

@Autowired
public SockController(SimpMessagingTemplate messagingTemplate) {
       super();
	 this.messagingTemplate = messagingTemplate;
}


//scope.stompClient.subscribe("app/join/$scope.userName")
@SubscribeMapping("/join/{username}")
public List<String> join(@DestinationVariable("username") String username) {
System.out.println("username in sockcontroller" + username);
		 
		 if(!users.contains(username)) {
				users.add(username);
			}


		System.out.println("====JOIN==== " + username);

		// notify all subscribers of new user

		messagingTemplate.convertAndSend("/topic/join", username);

		return users;

	}


@MessageMapping(value = "/chat")
public void chatReveived(Chat chat) {
   if ("all".equals(chat.getTo())) {

			System.out.println("IN CHAT REVEIVED " + chat.getMessage() + " " + chat.getFrom() + " to " + chat.getTo());

			messagingTemplate.convertAndSend("/queue/chats", chat);
		}

		else {

			System.out.println("CHAT TO " + chat.getTo() + " From " + chat.getFrom() + " Message " + chat.getMessage());

			messagingTemplate.convertAndSend("/queue/chats/" + chat.getTo(), chat);

			messagingTemplate.convertAndSend("/queue/chats/" + chat.getFrom(), chat);

		}

	}

}
