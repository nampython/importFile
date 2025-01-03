package org.example.controller;

import lombok.AllArgsConstructor;
import org.example.utils.VertexAIService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@AllArgsConstructor
public class VertexAIController {

	private final VertexAIService vertexAIService;

	@GetMapping("/vertexai")
	public String response() throws IOException {
		return vertexAIService.textInput("Hello");
	}
}
