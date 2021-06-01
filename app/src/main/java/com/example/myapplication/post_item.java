package com.example.myapplication;

import android.widget.Toast;


public class post_item {

    String firstName;
    String photoURL;
    long createdAt;
    String mainPost;


    public post_item(){

    }
    public post_item(String mainPost1,String firstName1,String photoURL1,int createdAt1){
        photoURL=photoURL1;
        firstName=firstName1;
        createdAt=createdAt1;
        mainPost=mainPost1;
    }
    public String getfirstName(){
        return firstName;
    }
    public String getphotoURL(){
        return photoURL;
    }

    public long getcreatedAt() {
        return createdAt;
    }

    public String getmainPost() {
        return mainPost;
    }


}
