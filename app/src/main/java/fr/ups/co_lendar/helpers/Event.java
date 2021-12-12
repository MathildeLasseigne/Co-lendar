package fr.ups.co_lendar.helpers;

import android.util.Log;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.Serializable;
import java.net.URL;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import fr.ups.co_lendar.FirebaseCallback;

public class Event implements Serializable {

    private String eventID;
    private String name;
    private String ownerID;
    private List<String> participants;
    private String location;
    private Date date;
    private String groupID;
    private String url;
    private String comments;
    private String TAG = "Event";

    public Event(String eventID, String name, String ownerID, List<String> participants, String location, Date date, String groupID, String url, String comments) {
        this.eventID = eventID;
        this.name = name;
        this.ownerID = ownerID;
        this.participants = participants;
        this.location = location;
        this.date = date;
        this.groupID = groupID;
        this.url = url;
        this.comments = comments;
    }

    public Event(FirebaseCallback callback, String eventID) {

        FirebaseFirestore mFirestore = FirebaseFirestore.getInstance();

        mFirestore.collection("events").document(Objects.requireNonNull(eventID))
                .get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    this.eventID = document.getString("eventID");
                    this.name = document.getString("name");
                    this.ownerID = document.getString("ownerID");
                    this.participants = (List<String>) document.get("participants");
                    this.location = document.getString(location);
                    this.date = document.getDate("date");
                    this.groupID = document.getString("groupID");
                    this.url = document.getString("link");
                    this.comments = document.getString("comments");
                    callback.onSuccess(null);
                } else {
                    Log.d(TAG, "No such event");
                }
            } else {
                Log.d(TAG, "get failed with ", task.getException());
            }
        });
    }

    public Event() {
        super();
    }

    public String getEventID() {
        return eventID;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
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

    public List<String> getParticipants() {
        return participants;
    }

    public void setParticipants(List<String> participants) {
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

    public String getGroupID() {
        return groupID;
    }

    public void setGroupID(String groupID) {
        this.groupID = groupID;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
