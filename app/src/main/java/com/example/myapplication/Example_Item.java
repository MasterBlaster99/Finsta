package com.example.myapplication;
//this class contains  getters and setters for the texts and image in card view

public class Example_Item {
    private String photoURL;
    private String firstName;

    public Example_Item(){

    }
    //constructors
    public Example_Item(String ImageResource, String Text1) {
        photoURL = ImageResource;
        firstName = Text1;
    }


    public String getphotoURL() {
        return photoURL;
    }

    public String getfirstName() {
        return firstName;
    }






}
