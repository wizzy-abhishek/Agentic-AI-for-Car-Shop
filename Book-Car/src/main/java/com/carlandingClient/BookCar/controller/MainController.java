package com.carlandingClient.BookCar.controller;

import org.springframework.ai.chat.client.ChatClient;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    private final ChatClient chatClient;

    public MainController(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @GetMapping("{user}/endPoint")
    String getInfo(@PathVariable String user,
                   @RequestParam String prompt){

        return chatClient
                .prompt(prompt)
                .call()
                .content();
    }
}
