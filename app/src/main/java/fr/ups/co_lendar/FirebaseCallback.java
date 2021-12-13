package fr.ups.co_lendar;

import androidx.annotation.Nullable;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

public interface FirebaseCallback {
    public void onStart();
    public void onSuccess(Object data);
    public void onFailed(DatabaseError databaseError);
}
