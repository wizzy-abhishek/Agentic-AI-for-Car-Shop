package com.cardetailServer.BookCarServer.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;

import org.springframework.ai.tool.ToolCallback;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

@Configuration
public class ChatClientBean {

    @Value("classpath:GlobalPromptForCarDetails.st")
    private Resource resource;

    @Bean
    public ChatClient chatClient(ChatClient.Builder chatClient,
                                 ToolCallback[] toolCallbacks,
                                 QuestionAnswerAdvisor questionAnswerAdvisor) {

        System.out.println(resource.getDescription());
        return chatClient
                .defaultSystem(resource)
                .defaultTools(toolCallbacks)
                .defaultAdvisors(questionAnswerAdvisor)
                .build();
    }

}
