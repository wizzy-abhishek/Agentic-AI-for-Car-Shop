package com.agentiAICarBooking.beans;

import com.agentiAICarBooking.tools.BookingTools;
import com.agentiAICarBooking.tools.DateTimeTool;
import com.agentiAICarBooking.tools.SearchBookingsTool;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ToolCallBackProviderBean {

    @Bean
    public ToolCallbackProvider carBookingTool(BookingTools bookingTools,
                                               SearchBookingsTool searchBookingsTool,
                                               DateTimeTool dateTimeTool){
        return MethodToolCallbackProvider
                .builder()
                .toolObjects(bookingTools,
                        searchBookingsTool,
                        dateTimeTool)
                .build();
    }
}
