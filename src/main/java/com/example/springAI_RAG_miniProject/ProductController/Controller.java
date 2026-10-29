package com.example.springAI_RAG_miniProject.ProductController;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    private ChatClient chatClient;
    @Autowired
    private VectorStore vectorStore;

        public Controller(OllamaChatModel chatModel){
            this.chatClient = ChatClient.create(chatModel);
        }

    @PostMapping("/api/get")
    public String getDetails(@RequestParam String query){
                return chatClient.prompt(query)
                        .advisors(QuestionAnswerAdvisor.builder(vectorStore).build())
                        .call()
                        .content();
    }
}
