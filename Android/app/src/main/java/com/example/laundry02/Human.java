package com.example.laundry02;

import android.graphics.Bitmap;

public class Human {
    public String name;
    public String gender;
    public String age;
    public int image;

    Human(String name, String gender, String age, int image){
        this.image = image;
        this.name =name;
        this.gender = gender;
        this.age = age;
    }

    public String getName() {
        return name;
    }
    public String getAge(){
        return age;
    }
    public String getGender(){
        return gender;
    }
    public int getImage(){
        return image;
    }
}

/*
public class Human {
    public String name;
    public String gender;
    public String age;
    public int image;

    public String getName() {
        return name;
    }
    public String getAge(){
        return age;
    }
    public String getGender(){
        return gender;
    }
    public int getImage(){
        return image;
    }

    Human(String name, String gender, String age, int image){
        this.image = image;
        this.name =name;
        this.gender = gender;
        this.age = age;
    }


}*/