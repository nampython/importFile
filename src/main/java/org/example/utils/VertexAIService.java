package org.example.utils;

import com.google.cloud.vertexai.VertexAI;
import com.google.cloud.vertexai.api.GenerateContentResponse;
import com.google.cloud.vertexai.generativeai.preview.GenerativeModel;
import com.google.cloud.vertexai.generativeai.preview.ResponseHandler;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Log4j2
public class VertexAIService {
	private static final String projectId = "positive-theme-446408-u4";
	private static final String location = "us-central1";
	private static final String modelName = "gemini-pro";

	public String textInput(String textPrompt) throws IOException {
		try (VertexAI vertexAI = new VertexAI(projectId, location)) {
			GenerativeModel model = new GenerativeModel(modelName, vertexAI);

			GenerateContentResponse response = model.generateContent(textPrompt);
			log.info("Vertex AI response: {}", response);
			return ResponseHandler.getText(response);
 		}
	}
}
