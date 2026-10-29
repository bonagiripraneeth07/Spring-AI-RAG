package com.example.springAI_RAG_miniProject;

import jakarta.annotation.PostConstruct;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.TextReader;
import org.springframework.ai.transformer.splitter.TextSplitter;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.vectorstore.pgvector.PgVectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Component
public class DataInitializer {
    @Autowired
    private VectorStore vectorStore;

    @PostConstruct
    public void initData(){
        TextReader textReader = new TextReader(new ClassPathResource("ProductsDetails.txt"));
        TokenTextSplitter splitter = TokenTextSplitter
                                        .builder()
                                        .withChunkSize(200)
                                        .withMinChunkSizeChars(100)
                                        .build();
        List<Document> documents =splitter.split(textReader.get());
        vectorStore.add(documents);
      }

}
