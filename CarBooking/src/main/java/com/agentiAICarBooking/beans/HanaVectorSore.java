package com.agentiAICarBooking.beans;

import com.agentiAICarBooking.repo.BookingRepo;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.hanadb.HanaCloudVectorStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HanaVectorSore {

    private final BookingRepo bookingRepo;

    public HanaVectorSore(BookingRepo bookingRepo) {
        this.bookingRepo = bookingRepo;
    }


    @Bean
    public HanaCloudVectorStore hanaCloudVectorStore(EmbeddingModel embeddingModel){
        return HanaCloudVectorStore.builder(bookingRepo, embeddingModel)
                .build();
    }
}
