package com.agentiAICarBooking.tools;

import com.agentiAICarBooking.entity.Booking;
import com.agentiAICarBooking.repo.BookingRepo;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ToolContext;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class BookingTools {

    private final ChatClient client;
    private final VectorStore vectorStore;
    private final EmbeddingModel embeddingModel;
    private final BookingRepo bookingRepo;

    public BookingTools(@Lazy ChatClient client,
                        VectorStore vectorStore,
                        EmbeddingModel embeddingModel,
                        BookingRepo bookingRepo) {
        this.client = client;
        this.vectorStore = vectorStore;
        this.embeddingModel = embeddingModel;
        this.bookingRepo = bookingRepo;
    }

    @Tool(name = "bookACar", description = """ 
            This is used to book a car.
            It has some mandatory requirement. They are:
                - Model name of for which user will book
                - Price of the car model
                - Symbol of the currency in which the currency is provided like $, €, ₹, etc.
            Do not hallucinate about any assumption.
            """ )
    public String bookACar(@ToolParam(description = "model name of car") String modelName,
                           @ToolParam(description = "user_name")String user_name,
                           @ToolParam(description = "Price of the model. Just the numeric Value") long value,
                           @ToolParam(description = "character of the currency just one value as it is char") char currency,
                           @ToolParam(description = "tool context is set always bring them") ToolContext toolContext){
        String email = "***";
        System.out.println("Hello" + toolContext.getContext().size() );
        toolContext.getContext().forEach((s,d)-> System.out.println(s + d.toString()));

        Booking booking = new Booking(modelName, user_name, email, new BigDecimal(value), currency);

        String content = """
                ID:%s MODEL_NAME:%s BUYER_NAME:%s BUYER_EMAIL:%s PRICE:%s CURRENCY_SYMBOL:%s PURCHASE_DATE:%s"""
                .formatted(booking.getId(),
                        booking.getModelName(),
                        booking.getBuyerName(),
                        booking.getBuyerEmail(),
                        booking.getPrice(),
                        booking.getCurrencySymbol(),
                        booking.getPurchaseDate());

        System.out.println(content);

        Document doc = new Document(content);

        vectorStore.accept(List.of(doc));

        return "Booking successful for model " + booking.getModelName() + " by " + booking.getBuyerName();
    }

}

