package com.example.chat.utils;

public final class JsonViews {
    public interface ViewFieldUuId {}
    public interface ViewFieldName{}
    public interface ViewFieldUserLogin{}
    public interface ViewFieldUserEmail{}
    public interface ViewFieldDateOfCreated {}
    public interface ViewFieldHashtag {}
    public interface ViewFieldUserDateLastVisit{}
    public interface ViewFieldDescription{}
    public interface ViewFieldMessageContent{}


    public interface ViewFieldOther{}

    public interface ViewFieldChatRoom {}
    public interface ViewFieldUu extends ViewFieldUuId, ViewFieldName, ViewFieldUserLogin, ViewFieldUserEmail, ViewFieldDateOfCreated, ViewFieldUserDateLastVisit{}
    public interface ViewFieldUserOnboarding extends ViewFieldName, ViewFieldUserLogin, ViewFieldOther {}

    public interface ViewFieldUuHashtagsGroups extends ViewFieldUuId, ViewFieldName, ViewFieldOther{}
    public interface ViewFieldUuChatList extends ViewFieldUuId, ViewFieldName, ViewFieldOther, ViewFieldDescription, ViewFieldMessageContent, ViewFieldDateOfCreated{}

}
