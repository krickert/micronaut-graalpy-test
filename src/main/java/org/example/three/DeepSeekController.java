package org.example.three;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.QueryValue;

@Controller("/deepseek")
public class DeepSeekController {

    private final DeepSeekVMService deepSeekVMService;

    public DeepSeekController(DeepSeekVMService deepSeekVMService) {
        this.deepSeekVMService = deepSeekVMService;
    }

    @Get("/generate")
    public String generate(@QueryValue("prompt") String prompt) {
        return deepSeekVMService.generateResponse(prompt);
    }
}
