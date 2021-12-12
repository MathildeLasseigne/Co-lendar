package fr.ups.co_lendar.helpers;

import android.util.Log;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import fr.ups.co_lendar.FirebaseCallback;

public class Group {

    private String groupID;
    private String name;
    private ArrayList<String> members = new ArrayList<>();
    private String adminUID;

    private String TAG = "Group";

    public Group(){
        super();
    }

    public Group(String groupID, String name, ArrayList<String> members, String adminUID) {
        this.groupID = groupID;
        this.name = name;
        this.members = members;
        this.adminUID = adminUID;
    }

    public Group(FirebaseCallback callback, String groupID) {}

    /*public ArrayList<User> getMembers(){
        return members;
    }*/

    public String getGroupID() {
        return groupID;
    }

    public void setGroupID(String groupID) {
        this.groupID = groupID;
    }

    public String getName(){
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getMembers() {
        return members;
    }

    public void setMembers(ArrayList<String> members) {
        this.members = members;
    }

    public String getAdminUID() {
        return adminUID;
    }

    public void setAdminUID(String adminUID) {
        this.adminUID = adminUID;
    }

    public void getGroupEvents(FirebaseCallback callback) {

        ArrayList<Event> events = new ArrayList<>();
        FirebaseFirestore mFirestore = FirebaseFirestore.getInstance();
        mFirestore.collection("events")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Event event = document.toObject(Event.class);
                            if (event.getGroupID().equals(this.groupID)) {
                                events.add(event);
                            }
                        }
                        callback.onSuccess(events);
                    } else {
                        Log.d(TAG, "Error getting groups: ", task.getException());
                    }
                });
    }

    public void insertIntoDatabase(FirebaseCallback callback) {

        if (groupID != null && name != null && members != null && members.size() > 0
                && adminUID != null) {
            Map<String, Object> group = new HashMap<>();
            group.put("groupID", groupID);
            group.put("name", name);
            group.put("members", members);
            group.put("adminUID", adminUID);

            FirebaseFirestore mFirestore = FirebaseFirestore.getInstance();
            mFirestore.collection("groups")
                    .add(group).addOnSuccessListener(unused -> {
                callback.onSuccess(null);
            }).addOnFailureListener(e -> {
                callback.onFailed(null);
            });
        }
    }
}
