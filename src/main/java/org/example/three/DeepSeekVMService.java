package org.example.three;

import io.micronaut.context.annotation.Value;
import io.micronaut.runtime.context.scope.Refreshable;
import jakarta.annotation.PostConstruct;
import org.graalvm.polyglot.Context;

import jakarta.inject.Singleton;

@Singleton
@Refreshable // Makes the service reloadable if needed
public class DeepSeekVMService {

    private Context context;
    private org.graalvm.polyglot.Value pyGenerateFunction;

    @Value("${deepseek.script-path:classpath:deepseek_vm.py}")
    private String scriptPath;

    @PostConstruct
    void init() {
        context = Context.newBuilder("python").allowExperimentalOptions(true)
                .allowAllAccess(true).allowAllAccess(true)
                .build();
        context.eval("python", "import transformers");

        context.eval("python", loadPythonScript());
        pyGenerateFunction = context.getBindings("python").getMember("generate_response");
    }

    private String loadPythonScript() {
        // Load the Python script as String from the classpath
        try (var inputStream = getClass().getClassLoader().getResourceAsStream("deepseek_vm.py")) {
            assert inputStream != null;
            return new String(inputStream.readAllBytes());
        } catch (Exception e) {
            throw new RuntimeException("Failed to load Python script", e);
        }
    }

    public String generateResponse(String prompt) {
        return pyGenerateFunction.execute(prompt).asString();
    }
}
