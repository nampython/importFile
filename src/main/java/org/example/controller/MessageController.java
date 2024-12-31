package org.example.controller;

import com.linecorp.bot.messaging.client.MessagingApiClient;
import com.linecorp.bot.messaging.model.ReplyMessageRequest;
import com.linecorp.bot.messaging.model.TextMessage;
import com.linecorp.bot.spring.boot.handler.annotation.EventMapping;
import com.linecorp.bot.spring.boot.handler.annotation.LineMessageHandler;
import com.linecorp.bot.webhook.model.Event;
import com.linecorp.bot.webhook.model.MessageEvent;
import com.linecorp.bot.webhook.model.TextMessageContent;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@LineMessageHandler
@Log4j2
@AllArgsConstructor
public class MessageController {

	private final MessagingApiClient messageApiClient;


	@GetMapping("/chat")
	public String chat() {
		return "Hello World";
	}

	@EventMapping
	public void handleTextMessageEvent(MessageEvent event) {
		log.info("event: {}", event);
		if (event.message() instanceof TextMessageContent) {
			TextMessageContent message = (TextMessageContent) event.message();
			final String originalMessageText = " How may i Help you? " + message.text();
			messageApiClient.replyMessage(new ReplyMessageRequest(
					event.replyToken(),
					List.of(new TextMessage(originalMessageText)),
					false));
		}
	}


	@EventMapping
	public void handleDefaultMessageEvent(Event event) {
		System.out.println("event: " + event);
	}
}
