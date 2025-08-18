package com.carlandingClient.BookCar.config;

import io.modelcontextprotocol.client.McpSyncClient;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.mcp.SyncMcpToolCallbackProvider;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.Resource;

import java.util.Arrays;
import java.util.List;

@Configuration
public class ChatClientBean {

    @Value("classpath:GlobalPrompt.st")
    private Resource GLOBAL_PROMPT;

    private final List<McpSyncClient> clients;
    private final SyncMcpToolCallbackProvider toolCallbackProvider;

    public ChatClientBean(List<McpSyncClient> clients,
                          SyncMcpToolCallbackProvider toolCallbackProvider) {
        this.clients = clients;
        this.toolCallbackProvider = toolCallbackProvider;
    }

    @Bean
    @Primary
    public ChatClient chatClient(ChatClient.Builder chatClientBuilder, ChatMemory chatMemory){
        ToolCallback[] toolCallbacks = toolCallbackProvider.getToolCallbacks();

        return chatClientBuilder
                .defaultAdvisors(MessageChatMemoryAdvisor
                        .builder(chatMemory)
                        .build())
                .defaultToolCallbacks(toolCallbacks)
                .defaultSystem(GLOBAL_PROMPT)
                .build();
    }

}
