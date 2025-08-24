package com.agentiAICarBooking.beans;

import com.agentiAICarBooking.repo.BookingEmbedRepo;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.hanadb.HanaCloudVectorStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HanaVectorSore {

    private final BookingEmbedRepo bookingRepo;

    public HanaVectorSore(BookingEmbedRepo bookingRepo) {
        this.bookingRepo = bookingRepo;
    }

    @Bean
    public HanaCloudVectorStore hanaCloudVectorStore(EmbeddingModel embeddingModel){
        return HanaCloudVectorStore.builder(bookingRepo, embeddingModel)
                .tableName("BOOKING_EMBED")
                .build();
    }


}
