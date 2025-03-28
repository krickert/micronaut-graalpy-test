/*
 * Copyright (c) 2024, Oracle and/or its affiliates.
 *
 * Licensed under the Universal Permissive License v 1.0 as shown at https://opensource.org/license/UPL.
 */

package org.example.one;

import io.micronaut.context.annotation.Bean;
import org.example.shared.GraalPyContext;
import org.graalvm.polyglot.Value;

import java.util.Map;

@Bean
public class SentimentAnalysis {

    private final SentimentIntensityAnalyzer sentimentIntensityAnalyzer;

    public SentimentAnalysis(GraalPyContext context) {
        Value value = context.get().eval("python", """
                from vader_sentiment.vader_sentiment import SentimentIntensityAnalyzer
                SentimentIntensityAnalyzer() # ①
                """);
        sentimentIntensityAnalyzer = value.as(SentimentIntensityAnalyzer.class); // ②
    }

    public Map<String, Double> getPolarityScores(String text) {
        return sentimentIntensityAnalyzer.polarity_scores(text); // ③
    }
}
