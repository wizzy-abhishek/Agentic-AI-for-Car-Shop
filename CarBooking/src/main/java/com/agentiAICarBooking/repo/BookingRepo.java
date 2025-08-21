package com.agentiAICarBooking.repo;

import com.agentiAICarBooking.entity.Booking;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.ai.vectorstore.hanadb.HanaVectorRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class BookingRepo implements HanaVectorRepository<Booking> {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void save(String tableName, String id, String embedding, String content) {
        String[] val = content.split(" ");
        String myId = "";
        String MODEL_NAME = "";
        String BUYER_NAME = "";
        String BUYER_EMAIL = "";
        String PRICE = "";
        String CURRENCY_SYMBOL = "";

        for (String values: val){
            String[] c = values.split(":");
            if(c[0].equals("ID")){
                myId = c[1];
            }
            if(c[0].equals("MODEL_NAME")){
                MODEL_NAME = c[1];
            }
            if(c[0].equals("BUYER_NAME")){
                BUYER_NAME = c[1];
            }
            if(c[0].equals("BUYER_EMAIL")){
                BUYER_EMAIL = c[1];
            }
            if(c[0].equals("PRICE")){
                PRICE = c[1];
            }
            if(c[0].equals("CURRENCY_SYMBOL")){
                CURRENCY_SYMBOL = c[1];
            }
        }

        String sql = """
        INSERT INTO BOOKING 
        (_ID, ID, MODEL_NAME, BUYER_NAME, BUYER_EMAIL, PRICE, CURRENCY_SYMBOL, PURCHASE_DATE, CONTENT, EMBEDDING) 
        VALUES (:_id, :ID, :MODEL_NAME, :BUYER_NAME, :BUYER_EMAIL, :PRICE, :CURRENCY_SYMBOL, :PURCHASE_DATE, :content, TO_REAL_VECTOR(:embedding))
        """;

        this.entityManager.createNativeQuery(sql)
                .setParameter("ID", myId)
                .setParameter("MODEL_NAME", MODEL_NAME)
                .setParameter("BUYER_NAME", BUYER_NAME)
                .setParameter("BUYER_EMAIL", BUYER_EMAIL)
                .setParameter("PRICE", PRICE)
                .setParameter("CURRENCY_SYMBOL", CURRENCY_SYMBOL)
                .setParameter("PURCHASE_DATE", Timestamp.valueOf(LocalDateTime.now()))
                .setParameter("_id", myId)
                .setParameter("embedding", embedding)
                .setParameter("content", content)
                .executeUpdate();
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
