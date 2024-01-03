package com.example.chat.utils;

public final class JsonViews {
    public interface ViewFieldId{}
    public interface ViewFieldName{}
    public interface ViewFieldUserLogin{}
    public interface ViewFieldUserEmail{}
    public interface ViewFieldUserDateOfCreated{}
    public interface ViewFieldUserDateLastVisit{}

    public interface ViewFieldOther{}

    public interface ViewFieldChatRoom {}
    public interface ViewFieldUser extends ViewFieldId, ViewFieldName, ViewFieldUserLogin, ViewFieldUserEmail, ViewFieldUserDateOfCreated, ViewFieldUserDateLastVisit{}

    public interface ViewFieldHashtagsGroups extends ViewFieldId, ViewFieldName, ViewFieldOther{}
}
