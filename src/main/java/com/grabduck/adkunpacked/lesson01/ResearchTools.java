package com.grabduck.adkunpacked.lesson01;

import java.util.Map;
import java.util.stream.Collectors;

import com.google.adk.tools.Annotations.Schema;
import dev.langchain4j.web.search.WebSearchEngine;
import dev.langchain4j.web.search.WebSearchRequest;
import dev.langchain4j.web.search.tavily.TavilyWebSearchEngine;

/**
 * Research tools using LangChain4j web search.
 * Set TAVILY_API_KEY environment variable to use.
 */
public class ResearchTools {
    
    private static final WebSearchEngine SEARCH_ENGINE = TavilyWebSearchEngine.builder()
        .apiKey(System.getenv("TAVILY_API_KEY"))
        .includeRawContent(true)  // Include actual content in search results
        .build();
    
    /**
     * General-purpose web search using Tavily.
     */
    @Schema(description = "Search the web for recent information about companies, products, or topics")
    public static Map<String, Object> webSearch(
        @Schema(name = "query", description = "The search query in plain language (e.g., 'emerging AI hardware companies')") 
        String query,
        @Schema(name = "maxResults", description = "Number of results to return (default 5, max 10)") 
        Integer maxResults) {
        
        int limit = maxResults != null ? Math.min(Math.max(maxResults, 1), 10) : 5;
        
        var request = WebSearchRequest.builder()
            .searchTerms(query)
            .maxResults(limit)
            .build();
        
        var results = SEARCH_ENGINE.search(request).results().stream()
            .map(result -> Map.of(
                "title", result.title(),
                "url", result.url().toString(),
                "snippet", result.content() != null ? result.content() : ""
            ))
            .collect(Collectors.toList());
        
        return Map.of(
            "query", query,
            "results", results
        );
    }
    
    
    // Prevent instantiation
    private ResearchTools() {}
}
