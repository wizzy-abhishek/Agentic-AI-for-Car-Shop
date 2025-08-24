package com.agentiAICarBooking.beans;

import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.hanadb.HanaCloudVectorStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QuestionAnswerAdvisorConfig{

    @Bean
    public QuestionAnswerAdvisor questionAnswerAdvisor(HanaCloudVectorStore vectorStore){
        return QuestionAnswerAdvisor.builder(vectorStore)
                .searchRequest(SearchRequest
                        .builder()
                        .similarityThreshold(0.6)
                        .topK(9)
                        .build())
                .build();
    }
}
