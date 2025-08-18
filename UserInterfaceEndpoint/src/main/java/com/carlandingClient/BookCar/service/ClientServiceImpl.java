package com.carlandingClient.BookCar.service;

import com.carlandingClient.BookCar.entity.AppUsers;
import com.carlandingClient.BookCar.repo.AppUserRepo;
import com.carlandingClient.BookCar.service.auth.AuthLoginService;
import com.carlandingClient.BookCar.service.auth.JwtService;
import com.carlandingClient.BookCar.service.interfaces.ClientService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.memory.repository.jdbc.JdbcChatMemoryRepository;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

@Service
public class ClientServiceImpl implements ClientService {

    private final AppUserRepo appUserRepo;
    private final JwtService jwtService;
    private final AuthLoginService authLoginService;
    private final ChatClient chatClient;
    private final JdbcChatMemoryRepository chatMemoryRepository;

    public ClientServiceImpl(AppUserRepo appUserRepo,
                             JwtService jwtService,
                             AuthLoginService authLoginService,
                             @Qualifier("chatClient") ChatClient chatClient,
                         JdbcChatMemoryRepository chatMemoryRepository) {

        this.appUserRepo = appUserRepo;
        this.jwtService = jwtService;
        this.authLoginService = authLoginService;
        this.chatClient = chatClient;
        this.chatMemoryRepository = chatMemoryRepository;
    }

    private AppUsers fetchAppUser(HttpServletRequest httpRequest) {
        String appUserEmail = jwtService.getUserFromToken(
                authLoginService.extractTokenFromHeader(httpRequest));

        return appUserRepo.findByEmailIgnoreCase(appUserEmail)
                .orElseThrow(() -> new BadCredentialsException("App user not found"));
    }

    @Override
    public String agent(HttpServletRequest httpServletRequest, String prompt) {

        AppUsers appUsers = fetchAppUser(httpServletRequest);

        ChatMemory chatMemory = MessageWindowChatMemory.builder()
                .chatMemoryRepository(chatMemoryRepository)
                .maxMessages(10)
                .build();
        UserMessage userMessage = new UserMessage(prompt);
        chatMemory.add(appUsers.getEmail(), userMessage);

        String response = chatClient
                .prompt(prompt)
                .advisors(advisor -> advisor
                        .param(ChatMemory.CONVERSATION_ID, appUsers.getEmail())
                        .param("role", appUsers.getUserRole()))
                .call()
                .content();
        System.out.println(response);
        return response;
    }
}
