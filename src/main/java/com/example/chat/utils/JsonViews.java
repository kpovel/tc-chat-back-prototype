package com.example.chat.utils;

public final class JsonViews {
    public interface ViewFieldUiid {}
    public interface ViewFieldName{}
    public interface ViewFieldUserLogin{}
    public interface ViewFieldUserEmail{}
    public interface ViewFieldDateOfCreated {}
    public interface ViewFieldHashtag {}
    public interface ViewFieldUserDateLastVisit{}
    public interface ViewFieldDescription{}
    public interface ViewFieldMessageContent{}
    public interface ViewFieldChatName{}


    public interface ViewFieldOther{}

    public interface ViewFieldChat extends ViewFieldUiid, ViewFieldChatName, ViewFieldDescription, ViewFieldOther, ViewFieldName {}

    public interface ViewFieldUu extends ViewFieldUiid, ViewFieldName, ViewFieldUserLogin, ViewFieldUserEmail, ViewFieldDateOfCreated, ViewFieldUserDateLastVisit{}
    public interface ViewFieldUserOnboarding extends ViewFieldName, ViewFieldUserLogin, ViewFieldOther {}

    public interface ViewFieldUiidHashtagsGroups extends ViewFieldUiid, ViewFieldName, ViewFieldOther{}
    public interface ViewFieldUiidChatList extends ViewFieldUiid, ViewFieldName, ViewFieldOther, ViewFieldDescription, ViewFieldMessageContent, ViewFieldDateOfCreated{}


}
