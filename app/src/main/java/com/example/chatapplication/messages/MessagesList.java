package com.example.chatapplication.messages;

public class MessagesList {
    private String login,password,lastMessage,profilePic,chatKey;
    private int unseenMessages;

    public MessagesList(String login, String password, String lastMessage,String profilePic, int unseenMessages,String chatKey) {
        this.login = login;
        this.password = password;
        this.lastMessage = lastMessage;
        this.profilePic=profilePic;
        this.unseenMessages = unseenMessages;
        this.chatKey=chatKey;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public int getUnseenMessages() {
        return unseenMessages;
    }

    public String getChatKey() {
        return chatKey;
    }
}
