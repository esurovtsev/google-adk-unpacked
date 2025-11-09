# Google ADK Unpacked

This repository provides a hands-on learning journey through Google's Agent Development Kit (ADK) with a collection of practical Java examples. It focuses on building production-ready AI agents using Google's ADK framework, demonstrating core concepts like agent architecture, tool integration, state management, and real-world agent workflows. Designed for Java developers who want to master AI agent development, this series helps you build scalable and maintainable agent systems with Google ADK.

## Getting Started

### 1. Clone the Repository

```bash
git clone <repository-url>
cd google-adk-unpacked
```

### 2. Set Up Your Java Environment

**Prerequisites:**
- Java 21 or higher
- Maven 3.6 or higher

Verify your Java version:
```bash
java -version
```

### 3. Install Dependencies

```bash
mvn clean install
```

### 4. Set Up API Keys

Create environment variables for the required API keys:

```bash
# Required for OpenAI models (GPT-4o, GPT-4o-mini)
export OPENAI_API_KEY=your_openai_api_key_here

# Required for web search functionality (Tavily)
export TAVILY_API_KEY=your_tavily_api_key_here
```

**Getting API Keys:**
- **OpenAI API Key**: Get your API key from [OpenAI Platform](https://platform.openai.com/api-keys)
- **Tavily API Key**: Sign up at [Tavily](https://tavily.com/) to get your web search API key

## Usage

- Follow the lessons in order (organized by package: `lesson01`, `lesson02`, etc.).
- Each lesson will have an accompanying video tutorial.
- Code comments and documentation will help you understand each concept.
- Run lessons using either the CLI runner or the ADK Dev UI (instructions provided in each lesson).

## Video Tutorials

Each lesson will have a dedicated video tutorial. Links will be provided as lessons are released.

## Contents

### 1. **Research Agent with Web Search** (`lesson01`)
   
   Build your first AI agent using Google ADK that can search the web and provide investment recommendations. This lesson introduces the foundational concepts of agent development, including agent architecture, tool integration, session management, and reactive programming with RxJava.
   
   **What You'll Learn:**
   - Create an `LlmAgent` using the builder pattern with name, description, instructions, model, and tools
   - Implement custom tools using static methods with `@Schema` annotations for parameter descriptions
   - Integrate Tavily web search to give your agent access to real-time information
   - Understand the ReAct (Reason + Act) pattern: how agents reason about tasks and invoke tools
   - Manage conversation state with sessions for short-term memory
   - Work with reactive streams (`Flowable<Event>`) to process agent responses asynchronously
   - Run agents in two modes: CLI for debugging and ADK Dev UI for visual interaction
   
   **Key Concepts:**
   - **Agent Instructions**: Critical system prompts that guide agent behavior, including warnings about outdated training data and requirements to use tools for current information
   - **Tool Binding**: Register Java methods as tools using `FunctionTool.create()` so the LLM can invoke them
   - **InMemoryRunner**: The execution engine that processes agent requests and manages the agent lifecycle
   - **Sessions**: Short-term memory that maintains conversation context across multiple user interactions
   - **Events**: The reactive stream of agent activities including tool calls, reasoning steps, and final responses
   - **RunConfig**: Runtime settings for agent execution (retries, failure handling, etc.)
   
   **Real-World Example:**
   - User asks: "Research and tell me what can be a good candidate for me to invest in the AI sector"
   - Agent uses `webSearch` tool to find current information about AI companies
   - Agent analyzes search results and recommends a specific publicly tradable company
   - Response includes reasoning and ends with "CHOSEN_COMPANY: [Company Name]"
   
   **Architecture Highlights:**
   - **ROOT_AGENT**: Public static field required by ADK web server for agent discovery
   - **Reactive Programming**: Built on RxJava3 `Flowable` for asynchronous event processing
   - **Tool Schema**: `@Schema` annotations provide descriptions that help the LLM understand when and how to use tools
   - **Content Format**: Messages are structured as `Content` with `Part` objects (text, images, etc.)
   
   **Running the Agent:**
   
   *CLI Mode:*
   ```bash
   mvn exec:java -Dexec.mainClass="com.grabduck.adkunpacked.lesson01.AgentCliRunner"
   ```
   - Interactive console interface
   - Shows all events including tool calls and responses
   - Type "quit" to exit
   - Great for debugging and understanding the full event stream
   
   *ADK Dev UI Mode:*
   ```bash
   mvn compile exec:java -Dexec.mainClass="com.google.adk.web.AdkWebServer" -Dexec.args="--adk.agents.source-dir=target --server.port=8080"
   ```
   - Visual web interface at `http://localhost:8080`
   - Agent discovery automatically finds agents with `ROOT_AGENT` field
   - Chat-like interface with tool call visualization
   - Shows function calls with parameters and responses
   - Better for demonstration and user-facing interactions
   
   **Files:**
   - `ResearchAgent.java` - Agent definition with OpenAI GPT-4o-mini model and web search tool
   - `ResearchTools.java` - Tavily web search implementation with configurable result limits
   - `AgentCliRunner.java` - Console runner with session management and event processing
   
   **Video Tutorial:** [Google ADK - Building a Java AI Agent with Google ADK – Demo Using LLM + Web Search Tool](https://www.youtube.com/watch?v=3op9VGvkynU)

## Project Structure

```
google-adk-unpacked/
├── src/
│   └── main/
│       └── java/
│           └── com/
│               └── grabduck/
│                   └── adkunpacked/
│                       ├── lesson01/          # First lesson: Research Agent
│                       │   ├── ResearchAgent.java
│                       │   ├── ResearchTools.java
│                       │   └── AgentCliRunner.java
│                       └── lesson02/          # Future lessons...
├── pom.xml                                    # Maven dependencies
└── README.md
```

## Contributing

Feedback and contributions are welcome! Please open issues or submit pull requests for suggestions and improvements.

## License

[Specify your license here, e.g., MIT]

---

*This README will be updated as the course progresses. Stay tuned for new lessons and videos!*
