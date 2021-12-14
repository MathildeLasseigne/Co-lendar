package fr.ups.co_lendar.fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.StorageReference;

import fr.ups.co_lendar.FirebaseCallback;
import fr.ups.co_lendar.R;
import fr.ups.co_lendar.helpers.User;

public class HomeFragment extends Fragment {

    TextView greeting;
    View mView;
    User loggedInUser;
    ImageView profilePicture;
    String TAG = "HomeFragment";

    public HomeFragment(){
        // require a empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_home, container,false);
        return mView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        initializeUI();
    }

    private void initializeUI() {
        greeting = mView.findViewById(R.id.textView_greeting);
        profilePicture = mView.findViewById(R.id.imageView_profilePic);
        setName();
        loggedInUser.getUserImage(new FirebaseCallback() {
            @Override
            public void onStart() { }

            @Override
            public void onSuccess(Object data) {
                profilePicture.setImageBitmap((Bitmap)data);
            }

            @Override
            public void onFailed(DatabaseError databaseError) { Log.d(TAG, "Error getting profile picture"); }
        });
    }

    private void setName() {

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            loggedInUser = (User) bundle.getSerializable("user");
            String newGreeting = greeting.getText() + " " + loggedInUser.getFirstName();
            greeting.setText(newGreeting);
        }
    }

}