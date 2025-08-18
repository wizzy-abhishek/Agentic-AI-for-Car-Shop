package com.carlandingClient.BookCar.service.interfaces;

import jakarta.servlet.http.HttpServletRequest;

public interface ClientService {

    String agent(HttpServletRequest httpServletRequest, String prompt);
}
