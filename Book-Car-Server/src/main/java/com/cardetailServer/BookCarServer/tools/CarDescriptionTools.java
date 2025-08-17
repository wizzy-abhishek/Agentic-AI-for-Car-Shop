package com.cardetailServer.BookCarServer.tools;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class CarDescriptionTools {

    private final ChatClient ai;
    private final QuestionAnswerAdvisor qaa;
    private final QuestionAnswerAdvisor questionAnswerAdvisor;

    public CarDescriptionTools(@Lazy ChatClient ai,
                               @Qualifier("oneCar")
                               QuestionAnswerAdvisor qaa,
                               @Qualifier("questionAnswerAdvisor")
                               QuestionAnswerAdvisor questionAnswerAdvisor) {
        this.ai = ai;
        this.qaa = qaa;
        this.questionAnswerAdvisor = questionAnswerAdvisor;
    }

    @Tool(name = "carDetails",
            description = "This tool provides the type/list of cars we sell, not if it is available or not")
    public String carDetails(@ToolParam(required = false, description = "The user id to keep chat memory") String user,
                          @ToolParam(description = "What is the type of car user is looking for") String question){

        System.out.println("Tool 1 called");

        return this.ai
                .prompt()
                .advisors(questionAnswerAdvisor)
                .user(question)
                .call().content();
    }

    @Tool(name = "isCarAvailable",
            description = "This tool provides if a car is available in stock to purchase or not")
    public String isCarAvailable(
            @ToolParam(description = "The model of the car")String modelName){

        System.out.println("Tool 2 called");

        String answer = ai
                .prompt("Never give the quantity of car. Always say that whether is it available or not")
                .advisors(qaa)
                .call()
                .content();
        System.out.println(answer);
        return answer;
    }

}



