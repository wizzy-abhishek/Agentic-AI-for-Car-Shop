package com.userInterface.BookCar.controller;

import com.carlandingClient.BookCar.service.interfaces.ClientService;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    private final ClientService clientService;

    public MainController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("endPoint")
    public ResponseEntity<String> agents(HttpServletRequest httpServletRequest,
                                         @RequestParam String prompt){
        String response = clientService.agent(httpServletRequest, prompt);
        return ResponseEntity.ok(response);
    }

}
