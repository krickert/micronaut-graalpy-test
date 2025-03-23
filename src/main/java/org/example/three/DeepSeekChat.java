package org.example.three;

import io.micronaut.context.annotation.Bean;
import org.example.shared.GraalPyContext;
import org.graalvm.polyglot.Value;

@Bean
public class DeepSeekChat {

    private static final String PYTHON = "python";
    private final Value generateResponseFunction;

    public DeepSeekChat(GraalPyContext context) {
        this.generateResponseFunction = context.get().eval(PYTHON, """
            from transformers import AutoModelForCausalLM, AutoTokenizer
            import torch

            model_name = "deepseek-ai/deepseek-coder-1.3b-base"
            tokenizer = AutoTokenizer.from_pretrained(model_name)
            model = AutoModelForCausalLM.from_pretrained(model_name)

            def generate_response(prompt: str):
                inputs = tokenizer(prompt, return_tensors="pt")
                outputs = model.generate(**inputs, max_length=50)
                return tokenizer.decode(outputs[0], skip_special_tokens=True)

            generate_response # Expose the function reference
            """);
    }

    public String generateResponse(String prompt) {
        return generateResponseFunction.execute(prompt).asString();
    }
}