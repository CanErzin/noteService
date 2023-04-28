package com.canerzin.notes.service.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "Note")
@Data
public class Note {
    @Id
    private String id;
    private String content;
    private int like ;
    private LocalDateTime createDateTime;
}
