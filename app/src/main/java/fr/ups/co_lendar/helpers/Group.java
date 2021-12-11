package fr.ups.co_lendar.helpers;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Group {

    private int gid;

    private String name;

    private ArrayList<User> members = new ArrayList<>();

    private String adminUID;

    private ArrayList<Event> events = new ArrayList<>();

    public Group(){

    }

    /*public ArrayList<User> getMembers(){
        return members;
    }*/

    public String getName(){
        return this.name;
    }
}
