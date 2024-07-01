package com.example.chatapplication.chat;

public class ChatList {
private  String login,message,date,time;

    public ChatList(String login, String message, String date, String time) {
        this.login = login;
        this.message = message;
        this.date = date;
        this.time = time;
    }

    public String getLogin() {
        return login;
    }

    public String getMessage() {
        return message;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }
}
