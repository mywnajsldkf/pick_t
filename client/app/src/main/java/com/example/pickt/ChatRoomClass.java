package com.example.pickt;

public class ChatRoomClass {
    int profile;
    String nickname;
    String recent_message;

    public ChatRoomClass(int profile,String nickname, String message){
        this.profile=profile;
        this.nickname=nickname;
        this.recent_message=message;
    }

    public int getProfile() {
        return profile;
    }

    public void setProfile(int profile) {
        this.profile = profile;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getRecent_message() {
        return recent_message;
    }

    public void setRecent_message(String recent_message) {
        this.recent_message = recent_message;
    }
}
