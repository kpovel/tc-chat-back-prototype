package com.example.chat.payload.response;

import com.example.chat.model.Message;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PageMessagesResponse {

    private List<Message> messages;

    private int totalPages;
    private long totalElements;
    private int currentPage;

    public PageMessagesResponse(List<Message> messages, int totalPages, long totalElements, int currentPage) {
        this.messages = messages;
        this.totalPages = totalPages;
        this.totalElements = totalElements;
        this.currentPage = currentPage;
    }
}
