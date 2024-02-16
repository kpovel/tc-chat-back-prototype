package com.example.chat.utils.mapper;

public interface Mappable<M, R> {

    M toModel(R request);

    R toDto(M entity);


}
