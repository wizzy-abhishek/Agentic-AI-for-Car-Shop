package com.agentiAICarBooking.beans;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.mcp.SyncMcpToolCallbackProvider;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatClientBean {

    private final String resource = """
    You are an AI agent. You book car orders. You have a tool called bookACar.
    Check that the car user is asking is available or not.""";

    @Bean
    public ChatClient defaultChatClient(ChatClient.Builder chatClient,
                                        QuestionAnswerAdvisor advisor,
                                        ToolCallback[] toolCallbacks){

        return chatClient
                .defaultSystem(resource)
                .defaultTools(toolCallbacks)
                .defaultAdvisors(advisor)
                .build();
    }



}
