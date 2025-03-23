package org.example.three;

import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.QueryValue;

@Controller("/deepseek")
public class DeepSeekController {

    private final DeepSeekChat deepSeekChat;

    public DeepSeekController(DeepSeekChat deepSeekChat) {
        this.deepSeekChat = deepSeekChat;
    }

    @Get(value = "/query", produces = MediaType.TEXT_PLAIN)
    public String queryDeepSeek(@QueryValue String prompt) {
        return deepSeekChat.generateResponse(prompt);
    }
}