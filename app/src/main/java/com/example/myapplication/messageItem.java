package com.example.myapplication;

public class messageItem {
     String photoURL;
    String message;
     String Uid;
     Long sentAt;

    public messageItem(){

    }


    public messageItem(String photoURL, String message, String uid) {
        this.photoURL = photoURL;
        this.message = message;
        Uid = uid;
    }

    public String getphotoURL() {
        return photoURL;
    }

    public String getmessage() {
        return message;
    }

    public String getUid() {
        return Uid;
    }

    public Long getsentAt() {
        return sentAt;
    }
}
