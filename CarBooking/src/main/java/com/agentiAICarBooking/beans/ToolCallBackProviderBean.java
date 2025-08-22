package com.agentiAICarBooking.beans;

import com.agentiAICarBooking.tools.BookingTools;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ToolCallBackProviderBean {

    @Bean
    public ToolCallbackProvider carBookingTool(BookingTools bookingTools){
        return MethodToolCallbackProvider
                .builder()
                .toolObjects(bookingTools)
                .build();
    }
}
