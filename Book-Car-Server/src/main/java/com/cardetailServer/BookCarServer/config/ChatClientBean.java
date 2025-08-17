package com.cardetailServer.BookCarServer.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatClientBean {

    @Bean
    public ChatClient chatClient(ChatClient.Builder chatClient){
        return chatClient
                .build();
    }

}
