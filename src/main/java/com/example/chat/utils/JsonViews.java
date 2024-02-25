package com.example.chat.utils;

public final class JsonViews {
    public interface ViewFieldUIID {}
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

    public interface ViewFieldChat extends ViewFieldUIID, ViewFieldChatName, ViewFieldDescription, ViewFieldOther, ViewFieldName {}

    public interface ViewFieldUu extends ViewFieldUIID, ViewFieldName, ViewFieldUserLogin, ViewFieldUserEmail, ViewFieldDateOfCreated, ViewFieldUserDateLastVisit{}
    public interface ViewFieldUserOnboarding extends ViewFieldName, ViewFieldUserLogin, ViewFieldOther, ViewFieldUserImage {}

    public interface ViewFieldUiidHashtagsGroups extends ViewFieldUIID, ViewFieldName, ViewFieldOther{}
    public interface ViewFieldUiidChatList extends ViewFieldUIID, ViewFieldName, ViewFieldOther, ViewFieldDescription, ViewFieldMessageContent, ViewFieldDateOfCreated{}
    public interface ViewFieldChatRoom extends ViewFieldUIID, ViewFieldName, ViewFieldOther, ViewFieldDescription, ViewFieldMessageContent, ViewFieldDateOfCreated, ViewFieldMessages{}

    public interface ViewMessage extends ViewFieldUIID, ViewFieldMessageContent, ViewFieldDateOfCreated, ViewFieldUser, ViewFieldUserImage, ViewFieldName {}

}
