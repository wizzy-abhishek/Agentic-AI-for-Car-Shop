package com.userInterface.BookCar.service.interfaces;

import jakarta.servlet.http.HttpServletRequest;

public interface ClientService {

    String agent(HttpServletRequest httpServletRequest, String prompt);
}
