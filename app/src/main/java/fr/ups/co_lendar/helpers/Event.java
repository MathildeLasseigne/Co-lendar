package fr.ups.co_lendar.helpers;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
                    this.location = document.getString("location");
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

    public void insertIntoDatabase(FirebaseCallback callback) {

        if (eventID != null && name != null && ownerID != null && location != null && date != null) {
            Map<String, Object> event = new HashMap<>();
            event.put("eventID", eventID);
            event.put("name", name);
            event.put("ownerID", ownerID);
            event.put("location", location);
            event.put("date", date);
            if (participants != null && participants.size() > 0) {
                event.put("participants", participants);
            }
            if (groupID != null) {
                event.put("groupID", groupID);
            }
            if (url != null) {
                event.put("url", url);
            }
            if (comments != null) {
                event.put("comments", comments);
            }

            FirebaseFirestore mFirestore = FirebaseFirestore.getInstance();
            mFirestore.collection("events")
                    .add(event).addOnSuccessListener(unused -> {
                callback.onSuccess(null);
            }).addOnFailureListener(e -> {
                callback.onFailed(null);
            });
        }
    }

    public void addUser(String userID, FirebaseCallback callback) {

        FirebaseFirestore mFirestore = FirebaseFirestore.getInstance();
        mFirestore.collection("events").document(this.eventID)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        Event event = document.toObject(Event.class);
                        List<String> participants = event.getParticipants();
                        participants.add(userID);
                        mFirestore.collection("events")
                                .document(this.eventID)
                                .update("participants", participants)
                                .addOnCompleteListener(task1 -> {
                                    if (task1.isSuccessful()) {
                                        Log.d(TAG, "Participant added correctly");
                                        callback.onSuccess(null);
                                    } else {
                                        Log.d(TAG, "Participant could not be added");
                                        callback.onFailed(null);
                                    }
                                });
                    } else {
                        Log.d(TAG, "Error getting event: ", task.getException());
                    }
                });
    }

    public void getEventImage(FirebaseCallback callback) {
        StorageReference storageReference;
        storageReference = FirebaseStorage.getInstance().getReference().child("eventPictures/" + eventID);
        try {
            final File localFile = File.createTempFile("eventPicture", "jpeg");
            storageReference.getFile(localFile)
                    .addOnSuccessListener(taskSnapshot -> {
                        Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                        callback.onSuccess(bitmap);
                    })
                    .addOnFailureListener(e -> {
                        callback.onFailed(null);
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
