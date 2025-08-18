package com.cardetailServer.BookCarServer.config;

import com.cardetailServer.BookCarServer.tools.CarDescriptionTools;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.support.ToolCallbacks;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.beans.factory.annotation.Autowired;
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
        return chatClient
                .defaultSystem(resource)
                .defaultTools(toolCallbacks)
                .defaultAdvisors(questionAnswerAdvisor)
                .build();
    }

}
