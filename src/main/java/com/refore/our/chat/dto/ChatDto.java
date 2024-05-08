package com.refore.our.chat.dto;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;

import java.time.LocalDate;

@Data
@Document(collection = "reforeChat")
public class ChatDto {

    @Id
    private String id;
    private String msg;
    private String sender;
    private String receiver;

    private LocalDate createTime;

}
