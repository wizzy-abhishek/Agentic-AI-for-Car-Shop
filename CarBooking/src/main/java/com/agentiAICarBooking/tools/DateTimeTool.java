package com.agentiAICarBooking.tools;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class DateTimeTool {

    @Tool(description = "Get current date and time")
    public String getCurrentDateAndTime(){
        return LocalDateTime.now().toString();
    }
}
