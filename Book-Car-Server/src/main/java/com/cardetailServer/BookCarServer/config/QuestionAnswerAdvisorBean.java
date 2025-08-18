package com.cardetailServer.BookCarServer.config;

import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class QuestionAnswerAdvisorBean {

    @Bean(name = "questionAnswerAdvisor")
    public QuestionAnswerAdvisor questionAnswerAdvisor(VectorStore vectorStore){
        return QuestionAnswerAdvisor.builder(vectorStore)
                .searchRequest(SearchRequest
                        .builder()
                        .similarityThreshold(0.75)
                        .topK(10)
                        .build())
                .build();
    }

}
