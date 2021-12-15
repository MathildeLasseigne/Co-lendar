package fr.ups.co_lendar.helpers;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import fr.ups.co_lendar.FirebaseCallback;

public class Group {

    private String groupID;
    private String name;
    private List<String> members = new ArrayList<>();
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

    public Group(FirebaseCallback callback, String groupID) {
        FirebaseFirestore mFirestore = FirebaseFirestore.getInstance();

        mFirestore.collection("groups").document(Objects.requireNonNull(groupID))
                .get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    this.groupID = document.getString("groupID");
                    this.name = document.getString("name");
                    this.adminUID = document.getString("adminUID");
                    this.members = (List<String>) document.get("members");
                    callback.onSuccess(this);
                } else {
                    Log.d(TAG, "No such group");
                }
            } else {
                Log.d(TAG, "get failed with ", task.getException());
            }
        });
    }


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

    public List<String> getMembers() {
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
                            event.setEventID(document.getId());
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

    public void getGroupUsers(FirebaseCallback callback) {

        ArrayList<User> users = new ArrayList<>();
        List<String> membersOfGroup = getMembers();
        FirebaseFirestore mFirestore = FirebaseFirestore.getInstance();
        mFirestore.collection("users")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            User user = document.toObject(User.class);
                            for(String uidMember : membersOfGroup) {
                                Log.d("USER UID", user.getUID());
                                Log.d("MEMBER UID", uidMember);
                                if (user.getUID().equals(uidMember)) {
                                    users.add(user);
                                }
                            }
                        }
                        callback.onSuccess(users);
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

    public void addUser(String userID, FirebaseCallback callback) {

        FirebaseFirestore mFirestore = FirebaseFirestore.getInstance();
        mFirestore.collection("groups").document(this.groupID)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        Group group = document.toObject(Group.class);
                        List<String> members = group.getMembers();
                        members.add(userID);
                        mFirestore.collection("groups")
                                .document(this.groupID)
                                .update("members", members)
                                .addOnCompleteListener(task1 -> {
                                    if (task1.isSuccessful()) {
                                        Log.d(TAG, "Member added correctly");
                                        callback.onSuccess(null);
                                    } else {
                                        Log.d(TAG, "Member could not be added");
                                        callback.onFailed(null);
                                    }
                                });
                    } else {
                        Log.d(TAG, "Error getting group: ", task.getException());
                    }
                });
    }

    public void getGroupImage(FirebaseCallback callback) {
        StorageReference storageReference;
        storageReference = FirebaseStorage.getInstance().getReference().child("groupPictures/" + groupID);
        try {
            final File localFile = File.createTempFile("groupPicture", "jpeg");
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
