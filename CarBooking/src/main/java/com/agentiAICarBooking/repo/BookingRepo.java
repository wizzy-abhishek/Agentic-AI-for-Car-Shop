package com.agentiAICarBooking.repo;

import com.agentiAICarBooking.entity.Booking;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
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

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    @Transactional
    public void save(String tableName, String id, String embedding, String content) {
        String[] val = content.split(";");
        String myId = "";
        String MODEL_NAME = "";
        String BUYER_NAME = "";
        String BUYER_EMAIL = "";
        String PRICE = "";
        String CURRENCY_SYMBOL = "";
        String USER_CUSTOMIZATION = "";

        for (String values: val){
            String[] c = values.split(":");
            c[0] = c[0].trim();
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
            if(c[0].equals("USER_CUSTOMIZATION")){
                USER_CUSTOMIZATION = c[1];
            }
        }

        String sql = """
                            INSERT INTO BOOKING 
                            (_ID, ID, MODEL_NAME, BUYER_NAME, BUYER_EMAIL, PRICE, CURRENCY_SYMBOL, PURCHASE_DATE, CONTENT, EMBEDDING, USER_CUSTOMIZATION) 
                            VALUES (:_id, :ID, :MODEL_NAME, :BUYER_NAME, :BUYER_EMAIL, :PRICE, :CURRENCY_SYMBOL, :PURCHASE_DATE, :content, TO_REAL_VECTOR(:embedding), :USER_CUSTOMIZATION)
                        """;

        this.entityManager.createNativeQuery(sql)
                .setParameter("MODEL_NAME", MODEL_NAME)
                .setParameter("BUYER_NAME", BUYER_NAME)
                .setParameter("BUYER_EMAIL", BUYER_EMAIL)
                .setParameter("PRICE", Double.parseDouble(PRICE))
                .setParameter("CURRENCY_SYMBOL", CURRENCY_SYMBOL)
                .setParameter("PURCHASE_DATE", Timestamp.valueOf(LocalDateTime.now()))
                .setParameter("_id", myId)
                .setParameter("ID", myId)
                .setParameter("embedding", embedding)
                .setParameter("content", content)
                .setParameter("USER_CUSTOMIZATION", USER_CUSTOMIZATION)
                .executeUpdate();
    }

    @Override
    @Transactional
    public int deleteEmbeddingsById(String tableName, List<String> idList) {
        String sql = "DELETE FROM " + tableName + " WHERE _ID IN :idList";
        Query query = entityManager.createNativeQuery(sql);
        query.setParameter("idList", idList);
        return query.executeUpdate();
    }

    @Override
    @Transactional
    public int deleteAllEmbeddings(String tableName) {
        String sql = "DELETE FROM " + tableName;
        return entityManager.createNativeQuery(sql).executeUpdate();
    }

    @Override
    public List<Booking> cosineSimilaritySearch(String tableName, int topK, String queryEmbedding) {

        String sql = """
            SELECT TOP :topK * FROM %s\s
            ORDER BY COSINE_SIMILARITY(EMBEDDING, TO_REAL_VECTOR(:queryEmbedding)) DESC
           \s""".formatted(tableName);

        System.out.println(queryEmbedding);

        List resultList = entityManager.createNativeQuery(sql, Booking.class)
                .setParameter("queryEmbedding", queryEmbedding)
                .setParameter("topK", topK)
                .getResultList();

        System.out.println(resultList.size());
        System.out.println("Hello");
        resultList.forEach(System.out::println);

        return resultList;
    }

    private Booking mapToObject(Object[] row) {
        if (row.length < 10) {
            throw new IllegalArgumentException("Invalid row size for mapping to Booking object.");
        }

        try {
            return new Booking(
                    row[1] != null ? (String) row[1] : null,
                    row[2] != null ? (String) row[2] : null,
                    row[3] != null ? (String) row[3] : null,
                    row[4] != null ? (String) row[4] : null,
                    row[5] != null ? (String) row[5] : null,
                    row[6] != null ? (Timestamp) row[6] : null,
                    row[0] != null ? (String) row[0] : null,
                    row[7] != null ? (String) row[7] : null,
                    row[9] != null ? (String) row[9] : null
            );
        } catch (Exception e) {
            throw new RuntimeException("Error mapping row to Booking object.", e);
        }
    }
}

