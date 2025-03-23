package org.example.shared;

import io.micronaut.context.annotation.Context;
import jakarta.annotation.PreDestroy;

import static org.graalvm.python.embedding.GraalPyResources.createContext;

@Context
public final class GraalPyContext {

    public static final String PYTHON = "python";
    private final org.graalvm.polyglot.Context context;

    public GraalPyContext() {
        context = createContext();
        context.initialize(PYTHON);
    }

    public org.graalvm.polyglot.Context get() {
        return context;
    }

    @PreDestroy
    void close() {
        try {
            context.close(true);
        } catch (Exception e) {
            // ignore
        }
    }

    // Convenience method added if desired
    public org.graalvm.polyglot.Value eval(String script) {
        return context.eval(PYTHON, script);
    }
}