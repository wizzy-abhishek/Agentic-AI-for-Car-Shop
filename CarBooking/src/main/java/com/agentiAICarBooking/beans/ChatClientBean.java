package com.agentiAICarBooking.beans;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.mcp.SyncMcpToolCallbackProvider;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.Resource;

import java.security.PrivateKey;

@Configuration
public class ChatClientBean {

    @Value("classpath:GlobalPromptCarBooking.st")
    private Resource PROMPT;

    @Bean
    public ChatClient defaultChatClient(ChatClient.Builder chatClient,
                                        QuestionAnswerAdvisor advisor,
                                        ToolCallback[] toolCallbacks){

        return chatClient
                .defaultSystem(PROMPT)
                .defaultToolCallbacks(toolCallbacks)
                .defaultAdvisors(advisor)
                .build();
    }



}
