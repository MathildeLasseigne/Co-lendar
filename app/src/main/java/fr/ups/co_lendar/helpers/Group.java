package fr.ups.co_lendar.helpers;

import java.util.ArrayList;

public class Group {

    private int gid;

    private String name;

    private ArrayList<User> members = new ArrayList<>();

    private String adminUID;

    private ArrayList<Event> events = new ArrayList<>();

    public Group(){

    }

    public ArrayList<User> getMembers(){
        return members;
    }

    public String getName(){
        return this.name;
    }
}
