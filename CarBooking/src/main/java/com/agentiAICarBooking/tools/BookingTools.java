package com.agentiAICarBooking.tools;

import com.agentiAICarBooking.entity.Booking;
import com.agentiAICarBooking.repo.BookingRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.ai.document.Document;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Component
public class BookingTools {

    private final BookingRepo bookingRepo;

    private final VectorStore vectorStore;
    private final ChatClient chatClient;

    public BookingTools(VectorStore vectorStore,
                        @Lazy ChatClient chatClient,
                        BookingRepo bookingRepo) {
        this.vectorStore = vectorStore;
        this.chatClient = chatClient;
        this.bookingRepo = bookingRepo;
    }

    @Tool(
            name = "bookACar",
            description = """
        Used to book a car.\s
        Mandatory inputs:\s
        - Car model name\s
        - Price of the car\s
        - Currency symbol ($, €, ₹, etc.)\s
        Do not make assumptions. \s
        Replace all ; with , with available.
       """
    )
    public String bookACar(@ToolParam(description = "complete model name of car") String modelName,
                           @ToolParam(description = "role of the user") String role,
                           @ToolParam(description = "email of the user") String email,
                           @ToolParam(description = "full user_name")String user_name,
                           @ToolParam(description = "Price of the model. Just the numeric Value") long value,
                           @ToolParam(description = "character of the currency just one value as it is char") char currency,
                           @ToolParam(description = "complete customization that users wants like color, alloy, etc") String description){

        Booking booking = new Booking(modelName, user_name, email, new BigDecimal(value), currency, description);

        String content = """
                ID:%s,\s
                MODEL_NAME:%s,\s
                BUYER_NAME:%s,\s
                BUYER_EMAIL:%s,\s
                PRICE:%s,\s
                CURRENCY_SYMBOL:%s,\s
                PURCHASE_DATE:%s,\s
                USER_CUSTOMIZATION:%s
                """
                .formatted(booking.getId(),
                        booking.getModelName(),
                        booking.getBuyerName(),
                        booking.getBuyerEmail(),
                        booking.getPrice(),
                        booking.getCurrencySymbol(),
                        booking.getPurchaseDate(),
                        booking.getUserDescription());

        Document doc = new Document(content);
        try{
            vectorStore.add(List.of(doc));
            bookingRepo.save(booking);

        }catch (Exception e){
            log.error("Could not save : {}", e.getMessage());
        }


        return "Booking successful for model " + booking.getModelName() + " by " + booking.getBuyerName();
    }


    @Tool(description = "get booking details")
    public String search(@ToolParam(description = "what user is searching for") String prompt){
        List<Document> documents = vectorStore.similaritySearch(prompt);
        String collect = documents.stream()
                .map(Document::getText)
                .collect(Collectors.joining(System.lineSeparator()));

        var similarDocsMessage = new SystemPromptTemplate("Based on the following: {documents}")
                .createMessage(Map.of("documents", collect));



        var userMessage = new UserMessage(prompt);
        Prompt p = new Prompt(List.of(similarDocsMessage, userMessage));

        return chatClient
                .prompt(p)
                .call()
                .content();
    }

}

