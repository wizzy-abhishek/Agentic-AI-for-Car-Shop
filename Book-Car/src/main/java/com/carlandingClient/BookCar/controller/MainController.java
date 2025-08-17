package com.carlandingClient.BookCar.controller;

import com.carlandingClient.BookCar.entity.AppUsers;
import com.carlandingClient.BookCar.repo.AppUserRepo;
import com.carlandingClient.BookCar.service.AuthLoginService;
import com.carlandingClient.BookCar.service.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.ai.chat.client.ChatClient;

import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.memory.repository.jdbc.JdbcChatMemoryRepository;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    private final AppUserRepo appUserRepo;
    private final JwtService jwtService;
    private final ChatClient chatClient;
    private final AuthLoginService authLoginService;
    private final JdbcChatMemoryRepository chatMemoryRepository;

    public MainController(AppUserRepo appUserRepo,
                          JwtService jwtService,
                          ChatClient chatClient,
                          AuthLoginService authLoginService,
                          JdbcChatMemoryRepository chatMemoryRepository) {
        this.appUserRepo = appUserRepo;
        this.jwtService = jwtService;
        this.chatClient = chatClient;
        this.authLoginService = authLoginService;
        this.chatMemoryRepository = chatMemoryRepository;
    }

    @GetMapping("{user}/endPoint")
    String getInfo(@PathVariable String user,
                   @RequestParam String prompt){

        ChatMemory chatMemory = MessageWindowChatMemory.builder()
                .chatMemoryRepository(chatMemoryRepository)
                .maxMessages(10)
                .build();
        UserMessage userMessage = new UserMessage(prompt);
        chatMemory.add(user, userMessage);

        return chatClient
                .prompt(prompt)
                .advisors(advisor -> advisor
                        .param(ChatMemory.CONVERSATION_ID, user)
                        .param("role", "ADMIN"))
                .call()
                .content();
    }

    private AppUsers fetchAppUser(HttpServletRequest httpRequest) {
        String appUserEmail = jwtService.getUserFromToken(
                authLoginService.extractTokenFromHeader(httpRequest));

        return appUserRepo.findByEmailIgnoreCase(appUserEmail)
                .orElseThrow(() -> new BadCredentialsException("App user not found"));
    }
}
