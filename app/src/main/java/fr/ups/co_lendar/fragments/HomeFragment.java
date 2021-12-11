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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Objects;

import fr.ups.co_lendar.R;
import fr.ups.co_lendar.helpers.Event;
import fr.ups.co_lendar.helpers.User;

public class HomeFragment extends Fragment {

    TextView greeting;
    View mView;
    User loggedInUser;
    ImageView profilePicture;
    StorageReference storageReference;
    private FirebaseFirestore mFirestore;
    String TAG = "HomeFragment";

    public HomeFragment(){
        // require a empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mFirestore = FirebaseFirestore.getInstance();
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
        setProfilePic();
    }

    private void setName() {

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            loggedInUser = (User) bundle.getSerializable("user");
            String newGreeting = greeting.getText() + " " + loggedInUser.getFirstName();
            greeting.setText(newGreeting);
        }
    }

    private void setProfilePic() {
        storageReference = FirebaseStorage.getInstance().getReference().child("profilePictures/" + loggedInUser.getUID());
        try {
            final File localFile = File.createTempFile("profilePicture", "jpeg");
            storageReference.getFile(localFile)
                    .addOnSuccessListener(taskSnapshot -> {
                        Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                        profilePicture.setImageBitmap(bitmap);
                    })
                    .addOnFailureListener(e -> {
                        e.printStackTrace();
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}