package com.agentiAICarBooking.beans;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

@Configuration
public class ChatClientBean {

    @Value("classpath:GlobalPromptCarBooking.st")
    private Resource resource;

    @Bean
    public ChatClient defaultChatClient(ChatClient.Builder chatClient,
                                        QuestionAnswerAdvisor advisor,
                                        ToolCallback[] tools){
        return chatClient
                .defaultSystem(resource)
                .defaultAdvisors(advisor)
                .defaultToolCallbacks(tools)
                .build();
    }

}
