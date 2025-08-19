package com.cardetailServer.BookCarServer.config;

import com.cardetailServer.BookCarServer.tools.CarDescriptionTools;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ToolCallbackProvideBean {

    @Bean
    public ToolCallbackProvider carDetailTool(CarDescriptionTools carDescriptionTools){
        return MethodToolCallbackProvider
                .builder()
                .toolObjects(carDescriptionTools)
                .build();
    }

}
