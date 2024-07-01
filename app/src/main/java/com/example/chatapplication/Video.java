package com.example.chatapplication;

public class Video {
    public String loginUser;
    public String urlVideo;
    public String nameVideo;
    public boolean milk;
    public boolean meat;
    public boolean cream;
    public boolean fruit;
    public boolean veget;
    public boolean yogurt;
    public boolean cheese;
    public boolean egg;
    public boolean berrie;

    public String id;
    public String way;
    public String timePost;

    public Video(String nameVideo,String timePost,String way,String loginUser, String urlVideo, boolean milk, boolean meat, boolean cream, boolean fruit, boolean veget, boolean yogurt, boolean cheese, boolean egg, boolean berrie,String id) {
       this.nameVideo=nameVideo;
        this.timePost=timePost;
        this.way=way;
        this.loginUser = loginUser;
        this.urlVideo = urlVideo;
        this.milk = milk;
        this.meat = meat;
        this.cream = cream;
        this.fruit = fruit;
        this.veget = veget;
        this.yogurt = yogurt;
        this.cheese = cheese;
        this.egg = egg;
        this.berrie = berrie;
        this.id=id;
    }

    public String getNameVideo() {
        return nameVideo;
    }

    public String getLoginUser() {
        return loginUser;
    }

    public String getUrlVideo() {
        return urlVideo;
    }

    public boolean isMilk() {
        return milk;
    }

    public boolean isMeat() {
        return meat;
    }

    public boolean isCream() {
        return cream;
    }

    public boolean isFruit() {
        return fruit;
    }

    public boolean isVeget() {
        return veget;
    }

    public boolean isYogurt() {
        return yogurt;
    }

    public boolean isCheese() {
        return cheese;
    }

    public boolean isEgg() {
        return egg;
    }

    public boolean isBerrie() {
        return berrie;
    }

    public String getId() {
        return id;
    }

    public String getWay() {
        return way;
    }

    public String getTimePost() {
        return timePost;
    }
}
