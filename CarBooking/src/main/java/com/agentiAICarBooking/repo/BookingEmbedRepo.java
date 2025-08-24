package com.agentiAICarBooking.repo;

import com.agentiAICarBooking.entity.BookingEmbed;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.springframework.ai.vectorstore.hanadb.HanaVectorRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BookingEmbedRepo implements HanaVectorRepository<BookingEmbed> {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void save(String tableName, String id, String embedding, String content) {

        String sql = """
                        INSERT INTO BOOKING_EMBED\s
                        (_ID, CONTENT, EMBEDDING)\s
                        VALUES (:_id, :content, TO_REAL_VECTOR(:embedding))
                        """;

        this.entityManager.createNativeQuery(sql)
                .setParameter("embedding", embedding)
                .setParameter("content", content)
                .setParameter("_id", id)
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
    public List<BookingEmbed> cosineSimilaritySearch(String tableName, int topK, String queryEmbedding) {

        System.out.println("Hello " + tableName);

        String sql = """
            SELECT TOP :topK * FROM %s\s
            ORDER BY COSINE_SIMILARITY(EMBEDDING, TO_REAL_VECTOR(:queryEmbedding)) DESC
           \s""".formatted(tableName);

        List resultList = entityManager.createNativeQuery(sql, BookingEmbed.class)
                .setParameter("queryEmbedding", queryEmbedding)
                .setParameter("topK", topK)
                .getResultList();

        System.out.println(resultList.size());
        System.out.println("Hello");

        return resultList;
    }
}

