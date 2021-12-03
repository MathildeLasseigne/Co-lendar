package fr.ups.co_lendar.helpers;

import java.io.Serializable;
import java.net.URL;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;

public class Event implements Serializable {

    private int id;
    private String name;
    private String ownerID;
    private ArrayList<String> participants;
    private String location;
    private Date date;
    private Time time;
    private String groupID;
    private URL link;
    private String comments;

    public Event(int id, String name, String ownerID, ArrayList<String> participants, String location, Date date, Time time, String groupID, URL link, String comments) {
        this.id = id;
        this.name = name;
        this.ownerID = ownerID;
        this.participants = participants;
        this.location = location;
        this.date = date;
        this.time = time;
        this.groupID = groupID;
        this.link = link;
        this.comments = comments;
    }

    private Event() {
        super();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(String ownerID) {
        this.ownerID = ownerID;
    }

    public ArrayList<String> getParticipants() {
        return participants;
    }

    public void setParticipants(ArrayList<String> participants) {
        this.participants = participants;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public String getGroupID() {
        return groupID;
    }

    public void setGroupID(String groupID) {
        this.groupID = groupID;
    }

    public URL getLink() {
        return link;
    }

    public void setLink(URL link) {
        this.link = link;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
