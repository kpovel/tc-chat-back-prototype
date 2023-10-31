package com.example.chat.model;

public final class Views {
    public interface ViewFieldId{}
    public interface ViewFieldName{}
    public interface ViewFieldUserLogin{}
    public interface ViewFieldUserEmail{}
    public interface ViewFieldUserDateOfCreated{}
    public interface ViewFieldUserDateLastVisit{}

    public interface ViewFieldChatRoom {}
    public interface ViewFieldUser extends ViewFieldId, ViewFieldName, ViewFieldUserLogin, ViewFieldUserEmail, ViewFieldUserDateOfCreated, ViewFieldUserDateLastVisit{}
}
