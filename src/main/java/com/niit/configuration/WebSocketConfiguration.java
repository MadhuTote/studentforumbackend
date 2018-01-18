package com.niit.configuration;

import java.util.List;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.handler.invocation.HandlerMethodArgumentResolver;
import org.springframework.messaging.handler.invocation.HandlerMethodReturnValueHandler;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;


@Configuration
@EnableWebSocketMessageBroker  // enable broker based stomp messaging
@EnableScheduling
@ComponentScan(basePackages="com.niit")
public class WebSocketConfiguration implements WebSocketMessageBrokerConfigurer{
	
	
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		System.out.println("REGISTER STOMP ENDPOINTS...");
		registry.addEndpoint("/portfolio").withSockJS();
		
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer#configureMessageBroker(org.springframework.messaging.simp.config.MessageBrokerRegistry)
	 * 
	 * message broker get the message from the server to the client
	 * queue and topic are end points to the clients[from server to client]
	 * controller has to send message to the client using the endpoint/queue , topic
	 * if client has to send message to the application[controller]prefix app will be used
	 * data from client to server /app/chat
     */
	
	
	public void configureMessageBroker(MessageBrokerRegistry registry) {
		System.out.println("CONFIGURE MESSAGE BROKER REGISTRY");
		//to send message from spring controller to client
		registry.enableSimpleBroker("/queue/", "/topic/");
		//to send message from client to sprint controller
		registry.setApplicationDestinationPrefixes("/app");

	}
     
	public void configureClientInboundChannel(ChannelRegistration registration) {
		// TODO Auto-generated method stub
		
	}

	
	public void configureClientOutboundChannel(ChannelRegistration registration) {
		// TODO Auto-generated method stub
		
	}

	


	
	
	
}
