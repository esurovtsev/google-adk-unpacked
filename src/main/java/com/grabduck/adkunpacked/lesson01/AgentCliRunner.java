package com.grabduck.adkunpacked.lesson01;

import com.google.adk.agents.RunConfig;
import com.google.adk.runner.InMemoryRunner;
import com.google.adk.sessions.Session;
import com.google.genai.types.Content;
import com.google.adk.events.Event;
import com.google.genai.types.Part;
import java.util.Scanner;
import io.reactivex.rxjava3.core.Flowable;

import static java.nio.charset.StandardCharsets.UTF_8;

public class AgentCliRunner {
    public static void main(String[] args) {
        // "runtime settings" for the agent
        RunConfig runConfig = RunConfig.builder().build();
        // define an engine for executing the agent
        InMemoryRunner runner = new InMemoryRunner(ResearchAgent.getInstance());
        var userId = "Evgeny";
        // create a session for the user (think of this as a short term memory of the conversation)
        Session session = runner
                .sessionService()
                // runner.appName() == ResearchAgent.getInstance().name()
                .createSession(runner.appName(), userId)
                .blockingGet();

        try (Scanner scanner = new Scanner(System.in, UTF_8)) {
            while (true) {
                // getting input from user
                // hey do a research and tell me what can be a good candidate for me to invest in AI sector. today is Nov 1st 2025
                System.out.print(userId + "> ");
                String userInput = scanner.nextLine();
                if ("quit".equalsIgnoreCase(userInput)) {
                    break;
                }

                // Convert user input to ADK message format
                Content userMsg = Content.fromParts(Part.fromText(userInput));
                
                // Get response stream and extract final answer
                Flowable<Event> events = runner.runAsync(session.userId(), session.id(), userMsg, runConfig);

                // Print everything - messages, tool calls, etc.
                events.blockingForEach(event -> {
                    System.out.println("=== " + event.getClass().getSimpleName() + " ===");
                    if (event.content().isPresent()) {
                        System.out.println("Content: " + event.stringifyContent());
                    }
                    if (event.finalResponse()) {
                        System.out.println("(Final Response)");
                    }
                    System.out.println();
                });
            }
        }
    }
}
