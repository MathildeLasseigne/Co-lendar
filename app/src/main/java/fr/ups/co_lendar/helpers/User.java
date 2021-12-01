package fr.ups.co_lendar.helpers;

import android.content.Intent;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.lang.ref.Reference;

import fr.ups.co_lendar.HomeActivity;

public class User {

    String firstName;
    String lastName;
    String password;
    String email;
    //image

    public User(String firstName, String lastName, String email, String password) {
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public User() {
        super();
    }

    public User (String UID) {
        DatabaseReference user = FirebaseDatabase.getInstance().getReference().child("users").child(UID);
        this.firstName = user.child("firstName").toString();
        this.lastName = user.child("lastName").toString();
        this.email = user.child("email").toString();
        this.password = user.child("password").toString();
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

    public User getUser(String UID) {
        DatabaseReference user = FirebaseDatabase.getInstance().getReference().child("users").child(UID);
        String firstName = user.child("firstName").toString();
        String lastName = user.child("lastName").toString();
        String email = user.child("email").toString();
        String password = user.child("password").toString();
        return new User(firstName, lastName, email, password);
    }

    public String getFirstNameFromUID(String UID) {
        DatabaseReference user = FirebaseDatabase.getInstance().getReference().child("users").child(UID);
        return user.child("firstName").toString();
    }
}
