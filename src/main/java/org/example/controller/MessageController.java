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
import org.example.utils.VertexAIService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@LineMessageHandler
@Log4j2
@AllArgsConstructor
public class MessageController {

	private final MessagingApiClient messageApiClient;
	private final VertexAIService vertexAIService;

	@GetMapping("/chat")
	public String chat() {
		return "Hello World";
	}

	@EventMapping
	public void handleTextMessageEvent(MessageEvent event) throws IOException {
		log.info("event: {}", event);
		if (event.message() instanceof TextMessageContent) {
			TextMessageContent message = (TextMessageContent) event.message();
			String responseVertex = vertexAIService.textInput(message.text());
			messageApiClient.replyMessage(new ReplyMessageRequest(
					event.replyToken(),
					List.of(new TextMessage(responseVertex)),
					false));
		}
	}


	@EventMapping
	public void handleDefaultMessageEvent(Event event) {
		System.out.println("event: " + event);
	}
}
