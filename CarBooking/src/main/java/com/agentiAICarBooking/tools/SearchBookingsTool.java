package com.agentiAICarBooking.tools;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.ai.document.Document;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SearchBookingsTool {

    private final VectorStore vectorStore;
    private final ChatClient chatClient;

    public SearchBookingsTool(VectorStore vectorStore, ChatClient chatClient) {
        this.vectorStore = vectorStore;
        this.chatClient = chatClient;
    }

    @Tool(description = "get user's specific booking details")
    public String search(@ToolParam(description = "what user is searching for") String prompt,
                         @ToolParam(description = "Users email") String email){

        prompt = prompt
                .concat("""
                        Search for car which has user email as %s.\s
                        Do not return any other booking.
                        """.formatted(email)) ;

        List<Document> documents = vectorStore.similaritySearch(prompt);
        String collect = documents.stream()
                .map(Document::getText)
                .collect(Collectors.joining(System.lineSeparator()));

        var similarDocsMessage = new SystemPromptTemplate("Based on the following: {documents}")
                .createMessage(Map.of("documents", collect));

        var userMessage = new UserMessage(prompt);
        Prompt promptGen = new Prompt(List.of(similarDocsMessage, userMessage));

        return chatClient
                .prompt(promptGen)
                .call()
                .content();
    }


}
