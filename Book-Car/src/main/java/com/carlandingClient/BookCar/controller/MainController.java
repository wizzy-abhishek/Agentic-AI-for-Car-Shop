package com.carlandingClient.BookCar.controller;

import org.springframework.ai.chat.client.ChatClient;

import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.memory.repository.jdbc.JdbcChatMemoryRepository;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    private final ChatClient chatClient;
    private final JdbcChatMemoryRepository chatMemoryRepository;

    public MainController(ChatClient chatClient,
                          JdbcChatMemoryRepository chatMemoryRepository) {
        this.chatClient = chatClient;
        this.chatMemoryRepository = chatMemoryRepository;
    }

    @GetMapping("{user}/endPoint")
    String getInfo(@PathVariable String user,
                   @RequestParam String prompt){

        ChatMemory chatMemory = MessageWindowChatMemory.builder()
                .chatMemoryRepository(chatMemoryRepository)
                .maxMessages(10)
                .build();
        UserMessage userMessage = new UserMessage(prompt);
        chatMemory.add(user, userMessage);

        return chatClient
                .prompt(prompt)
                .advisors(advisor -> advisor
                        .param(ChatMemory.CONVERSATION_ID, user))
                .call()
                .content();
    }
}
