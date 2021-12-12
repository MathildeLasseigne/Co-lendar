package fr.ups.co_lendar.helpers;

import android.util.Log;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import java.util.ArrayList;
import fr.ups.co_lendar.FirebaseCallback;

public class Group {

    private String GID;
    private String name;
    private ArrayList<String> members = new ArrayList<>();
    private String adminUID;

    private String TAG = "Group";

    public Group(){
        super();
    }

    public Group(String GID, String name, ArrayList<String> members, String adminUID) {
        this.GID = GID;
        this.name = name;
        this.members = members;
        this.adminUID = adminUID;
    }

    public Group(FirebaseCallback callback, String groupID) {}

    /*public ArrayList<User> getMembers(){
        return members;
    }*/

    public String getGID() {
        return GID;
    }

    public void setGID(String GID) {
        this.GID = GID;
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
                            if (event.getGroupID().equals(this.GID)) {
                                events.add(event);
                            }
                        }
                        callback.onSuccess(events);
                    } else {
                        Log.d(TAG, "Error getting groups: ", task.getException());
                    }
                });
    }
}
