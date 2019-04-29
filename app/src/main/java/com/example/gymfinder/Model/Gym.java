package com.example.gymfinder.Model;

import com.google.firebase.database.Exclude;

public class Gym {
    private String name;
    private String key;
    private String description;
    private int position;

    public Gym() {
        //empty constructor needed
    }
    public Gym (int position){
        this.position = position;
    }
    public Gym(String name,String Des) {
        if (name.trim().equals("")) {
            name = "No Name";
        }
        this.name = name;
        this.description = Des;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    @Exclude
    public String getKey() {
        return key;
    }
    @Exclude
    public void setKey(String key) {
        this.key = key;
    }
}
