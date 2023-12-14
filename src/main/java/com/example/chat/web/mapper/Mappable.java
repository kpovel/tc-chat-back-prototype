package com.example.chat.web.mapper;

public interface Mappable<M, R> {

    M toModel(R request);

    R toDto(M entity);


}
