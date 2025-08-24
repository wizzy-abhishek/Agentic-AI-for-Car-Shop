/*
package com.cardetailServer.BookCarServer.tools;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class CurrencyConversionTool {

    @Value("${base-url}")
    private String BASE_URL;
    @Value("${exchange}")
    private String API_KEY;

    private final ChatClient client;

    public CurrencyConversionTool(@Lazy ChatClient client) {
        this.client = client;
    }


    @Tool(
            name = "convert_currency",
            description = """
        This tool converts an amount from one currency to another using live exchange rates. 
        - Input: amount, source currency symbol (e.g., $, €, ₹), and target currency (e.g., INR, EUR).
        - Output: converted amount with correct target currency symbol.
        - The tool always uses the provided exchange rates API response; it must not guess or assume rates.
        """
    )
    public String convertCurrency(
            @ToolParam(description = "Amount that needs to be converted") String amt,
            @ToolParam(description = "Symbol of currency (e.g. $, €, ₹) or nation") String value,
            @ToolParam(description = "Name of currency it needs to be converted into") String requiredCurrency) {

       String response =  client.prompt("""
            Convert the amount using the given exchange rate.
            
            Input:
            - Convert %s %s to %s with a symbol\s
            Example If I gave convert 100$ to Indian Rupees give me ₹8000.
            
            Rules
            - If you are not sure then write approximate.
            - Output format: "<symbol><converted_amount>"
              Example: Rs. 800 or USD 12.5
            """.formatted(amt, value, requiredCurrency))
                .call()
                .content();

        System.out.println(response);

       return response;
    }

}
*/
