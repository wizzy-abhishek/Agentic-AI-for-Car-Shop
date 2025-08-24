package com.cardetailServer.BookCarServer.tools;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class CarDescriptionTools {

    private final ChatClient ai;

    public CarDescriptionTools(@Lazy ChatClient ai) {
        this.ai = ai;
    }

    @Tool(
            name = "carSearch",
            description = """
                Answer car-related queries:
                - If user asks about any specific model describe that model, and tell about other models in that range and category.\s
                - Category (SUV, Sedan) → list models with descriptions\s
                - Price queries → give correct price\s
    """
    )
    public String carSearch(
            @ToolParam(description = "The user's query (car model, category, or anything about car)") String query) {

        System.out.println("carSearch input: " + query);

        String response = ai.prompt()
                .user(query)
                .call()
                .content();

        System.out.println("carSearch response: " + response);
        return response;
    }
}




