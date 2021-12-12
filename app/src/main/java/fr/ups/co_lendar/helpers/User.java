package fr.ups.co_lendar.helpers;

import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.io.Serializable;
import java.lang.ref.Reference;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import fr.ups.co_lendar.FirebaseCallback;
import fr.ups.co_lendar.HomeActivity;
import fr.ups.co_lendar.MainLoginActivity;

public class User implements Serializable {

    String firstName;
    String lastName;
    String password;
    String email;
    String UID;
    List<String> usersEvents;
    String TAG = "USER";

    public User(String firstName, String lastName, String email, String password, String UID) {
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.UID = UID;
    }

    public User(String firstName, String lastName, String email, String password) {
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public User(FirebaseCallback callback, String UUID) {
        FirebaseFirestore mFirestore = FirebaseFirestore.getInstance();

        mFirestore.collection("users").document(Objects.requireNonNull(UUID))
                .get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    this.setFirstName(document.getString("firstName"));
                    this.setLastName(document.getString("lastName"));
                    this.setEmail(document.getString("email"));
                    this.setPassword(document.getString("password"));
                    this.setUID(UUID);
                    this.setUsersEvents((List<String>) document.get("usersEvents"));
                    callback.onSuccess(null);
                } else {
                    Log.d(TAG, "No such user");
                }
            } else {
                Log.d(TAG, "get failed with ", task.getException());
            }
        });
    }

    public User() {
        super();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public List<String> getUsersEvents() {
        return usersEvents;
    }

    public void setUsersEvents(List<String> usersEvents) {
        this.usersEvents = usersEvents;
    }

    public void getUserGroups(FirebaseCallback callback) {

        ArrayList<Group> groups = new ArrayList<>();
        FirebaseFirestore mFirestore = FirebaseFirestore.getInstance();
        mFirestore.collection("groups")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Group group = document.toObject(Group.class);
                            if (group.getMembers().contains(this.UID)) {
                                groups.add(group);
                            }
                        }
                        callback.onSuccess(groups);
                    } else {
                        Log.d(TAG, "Error getting groups: ", task.getException());
                    }
                });
    }

    public void getUserRequests(FirebaseCallback callback) {

        ArrayList<Request> requests = new ArrayList<>();
        FirebaseFirestore mFirestore = FirebaseFirestore.getInstance();
        mFirestore.collection("requests")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Request request = document.toObject(Request.class);
                            if (request.getReceiverID().equals(this.UID)) {
                                requests.add(request);
                            }
                        }
                        callback.onSuccess(requests);
                    } else {
                        Log.d(TAG, "Error getting requests: ", task.getException());
                    }
                });
    }
}
