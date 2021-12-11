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

import java.io.Serializable;
import java.lang.ref.Reference;
import java.util.ArrayList;
import java.util.List;

import fr.ups.co_lendar.HomeActivity;
import fr.ups.co_lendar.MainLoginActivity;

public class User implements Serializable {

    String firstName;
    String lastName;
    String password;
    String email;
    String UID;
    List<String> usersEvents;

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

    public User() {
        super();
    }
    
    public User (String UID, MainLoginActivity mla) {
        DatabaseReference user = FirebaseDatabase.getInstance().getReference("users");

        Query userQuery = user.child(UID);
        userQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    setFirstName(snapshot.child("firstName").getValue(String.class));
                    setLastName(snapshot.child("lastName").getValue(String.class));
                    setEmail(snapshot.child("email").getValue(String.class));
                    setPassword(snapshot.child("password").getValue(String.class));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println(error.getMessage());
            }
        });
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

    public User getUser(String UID) {
        DatabaseReference user = FirebaseDatabase.getInstance().getReference().child("users").child(UID);
        String firstName = user.child("firstName").toString();
        String lastName = user.child("lastName").toString();
        String email = user.child("email").toString();
        String password = user.child("password").toString();
        return new User(firstName, lastName, email, password, UID);
    }

    public boolean equals(User other){
        return false;
    }
}
