package com.agentiAICarBooking.repo;

import com.agentiAICarBooking.entity.Booking;
import org.springframework.ai.vectorstore.hanadb.HanaVectorRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BookingRepo implements HanaVectorRepository<Booking> {

    @Override
    public void save(String tableName, String id, String embedding, String content) {

    }

    @Override
    public int deleteEmbeddingsById(String tableName, List<String> idList) {
        return 0;
    }

    @Override
    public int deleteAllEmbeddings(String tableName) {
        return 0;
    }

    @Override
    public List<Booking> cosineSimilaritySearch(String tableName, int topK, String queryEmbedding) {
        return List.of();
    }
}
