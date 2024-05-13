package com.example.chat.utils;

import com.example.chat.model.Message;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class CustomMessageSerializer extends JsonSerializer<Message> {

    @Override
    public void serialize(Message value, JsonGenerator gen, SerializerProvider serializers) throws IOException {

        gen.writeStartObject();
        gen.writeNumberField("id", value.getId());
        gen.writeStringField("uuid", value.getUuid());
        gen.writeStringField("content", value.getContent());
        gen.writeBooleanField("edited", value.isEdited());
        gen.writeObjectField("dateOfCreated", value.getDateOfCreated());

        gen.writeFieldName("user");
        gen.writeStartObject();
        gen.writeStringField("uuid", value.getUser().getUuid());
        gen.writeEndObject();

        gen.writeFieldName("chatRoom");
        gen.writeStartObject();
        gen.writeStringField("uuid", value.getChatRoom().getUuid());
        gen.writeEndObject();

        gen.writeEndObject();
    }

}
