package com.example.chat.utils.mapper;

import com.example.chat.model.Hashtag;
import com.example.chat.payload.request.HashtagRequest;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface HashtagMapper extends Mappable<Hashtag, HashtagRequest> {
}
