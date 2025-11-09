package com.grabduck.adkunpacked.lesson01;

import com.google.adk.agents.BaseAgent;
import com.google.adk.agents.LlmAgent;
import com.google.adk.tools.FunctionTool;
import dev.langchain4j.model.openai.OpenAiChatModel;
import com.google.adk.models.langchain4j.LangChain4j;

public class ResearchAgent {
    // ADK web server requires this exact field name and visibility
    public static final BaseAgent ROOT_AGENT = createInstance();

    public static BaseAgent getInstance() {
        return ROOT_AGENT;
    }

    private static BaseAgent createInstance() {
        // Configure OpenAI model
        OpenAiChatModel openaiModel = OpenAiChatModel.builder()
            .apiKey(System.getenv("OPENAI_API_KEY"))
            .modelName("gpt-4o-mini")
            .build();
        
        // Wrap in ADK adapter
        var model = new LangChain4j(openaiModel);
        
        return LlmAgent.builder()
            .name("research")
            .description("Research Agent that recommends promising companies for investment")
            .instruction("""
                You are a Research Agent that recommends ONE promising company for investment based on user requests.
                
                CRITICAL: Your training data is outdated. Investment decisions require CURRENT market data.
                You MUST use webSearch to get today's information before making any recommendation.
                
                Process:
                1. Use webSearch to find current information about the requested sector
                2. Analyze the results
                3. Recommend exactly ONE publicly tradable company
                
                Rules:
                - Always search first, never answer from memory alone
                - Recommend exactly ONE company that is publicly tradable
                - Be factual and concise
                - Don't place trades or fabricate data
                - End with: CHOSEN_COMPANY: <Company Name>
                
                Output: 1-2 sentence explanation followed by the company name.
                """)
            .model(model)
            .tools(FunctionTool.create(ResearchTools.class, "webSearch"))
            .build();
    }
}
