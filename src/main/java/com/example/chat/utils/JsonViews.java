package com.example.chat.utils;

public final class JsonViews {
    public interface ViewFieldUUID {}
    public interface ViewFieldName{}
    public interface ViewFieldUserLogin{}
    public interface ViewFieldUserEmail{}
    public interface ViewFieldDateOfCreated {}
    public interface ViewFieldHashtag {}
    public interface ViewFieldUserDateLastVisit{}
    public interface ViewFieldDescription{}
    public interface ViewFieldMessageContent{}
    public interface ViewFieldChatName{}
    public interface ViewFieldMessages{}
    public interface ViewFieldUser{}
    public interface ViewFieldUserImage{}




    public interface ViewFieldOther{}

    public interface ViewFieldChat extends ViewFieldUUID, ViewFieldChatName, ViewFieldDescription, ViewFieldOther, ViewFieldName {}

    public interface ViewFieldUu extends ViewFieldUUID, ViewFieldName, ViewFieldUserLogin, ViewFieldUserEmail, ViewFieldDateOfCreated, ViewFieldUserDateLastVisit{}
    public interface ViewFieldUserOnboarding extends ViewFieldName, ViewFieldUserLogin, ViewFieldOther, ViewFieldUserImage {}

    public interface ViewFieldUUIDHashtagsGroups extends ViewFieldUUID, ViewFieldName, ViewFieldOther{}
    public interface ViewFieldUUIDChatList extends ViewFieldUUID, ViewFieldName, ViewFieldOther, ViewFieldDescription, ViewFieldMessageContent, ViewFieldDateOfCreated{}
    public interface ViewFieldChatRoom extends ViewFieldUUID, ViewFieldName, ViewFieldOther, ViewFieldDescription, ViewFieldMessageContent, ViewFieldDateOfCreated, ViewFieldMessages{}

    public interface ViewMessage extends ViewFieldUUID, ViewFieldMessageContent, ViewFieldDateOfCreated, ViewFieldUser, ViewFieldUserImage, ViewFieldName {}

}
