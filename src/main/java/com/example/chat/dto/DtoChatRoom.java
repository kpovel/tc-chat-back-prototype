package com.example.chat.dto;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class DtoChatRoom {

    private String name;

    private Integer userLimit;

    boolean archived = false;

    public DtoChatRoom() {
    }
}
