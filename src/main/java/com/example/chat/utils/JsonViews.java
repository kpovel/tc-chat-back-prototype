package com.example.chat.utils;

public final class JsonViews {
    public interface ViewFieldId {}
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
    public interface ViewFieldUserAbout{}
    public interface ViewFieldChatRoomType{}
    public interface ViewFieldChatIsAdmin{}




    public interface ViewFieldOther{}

    public interface ViewFieldChat extends ViewFieldUUID, ViewFieldChatName, ViewFieldDescription, ViewFieldOther, ViewFieldName {}

    public interface ViewFieldsUser extends ViewFieldUUID, ViewFieldName, ViewFieldUserLogin, ViewFieldUserAbout, ViewFieldUserEmail, ViewFieldDateOfCreated, ViewFieldUserDateLastVisit, ViewFieldUserImage{}
    public interface ViewFieldUserOnboarding extends ViewFieldName, ViewFieldUserLogin, ViewFieldOther, ViewFieldUserImage {}

    public interface ViewFieldUUIDHashtagsGroups extends ViewFieldUUID, ViewFieldName, ViewFieldOther{}
    public interface ViewFieldUserChatList extends ViewFieldUUID,ViewFieldChatRoomType, ViewFieldName, ViewFieldOther, ViewFieldDescription, ViewFieldMessageContent, ViewFieldDateOfCreated{}
    public interface ViewFieldChatRoom extends ViewFieldUUID, ViewFieldName, ViewFieldChatIsAdmin, ViewFieldOther, ViewFieldDescription, ViewFieldMessageContent, ViewFieldDateOfCreated, ViewFieldMessages{}

    public interface ViewMessage extends ViewFieldId, ViewFieldUUID, ViewFieldMessageContent, ViewFieldDateOfCreated, ViewFieldUser, ViewFieldUserImage, ViewFieldName {}

}
