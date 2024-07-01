package com.example.chatapplication.notes;

import java.util.List;

public class Plan {
    private int key;
public String user;
    public String content;
    public Boolean execution;
    public String nowDate;
    public String nowTime;

    public Plan(){}
    public Plan(String user, String content, Boolean execution, String nowDate,String nowTime,int key) {
        this.user=user;
        this.content = content;
        this.execution = execution;
        this.nowDate = nowDate;
        this.nowTime=nowTime;
        this.key=key;
    }
    public void setKey(int key) {
        this.key = key;
    }

    // Метод для получения ключа
    public int getKey() {
        return key;
    }
    public String getNowTime() {
        return nowTime;
    }

    public void setNowTime(String nowTime) {
        this.nowTime = nowTime;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setExecution(Boolean execution) {
        this.execution = execution;
    }

    public void setNowDate(String nowDate) {
        this.nowDate = nowDate;
    }

    public String getContent() {
        return content;
    }

    public Boolean getExecution() {
        return execution;
    }

    public String getNowDate() {
        return nowDate;
    }
}
