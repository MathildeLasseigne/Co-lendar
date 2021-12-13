package fr.ups.co_lendar;

import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import fr.ups.co_lendar.helpers.Event;
import fr.ups.co_lendar.helpers.Group;
import fr.ups.co_lendar.helpers.Request;
import fr.ups.co_lendar.helpers.User;

public class EventRequestCreation extends Fragment {

    private String eventID;
    private String receiverID;

    private ImageView eventPicture;

    private TextView eventName;
    private TextView receiverName;

    private Button accept; //Change to ImageButton
    private Button refuse; //Change to ImageButton

    private FirebaseAuth mAuth;
    private FirebaseFirestore mFirestore;
    private String TAG = "EventRequestCreation";

    Fragment replacementFragment;

    public EventRequestCreation() {    }

    /**
     *
     * @param eventID
     * @param receiverID
     * @param replacementFragment The fragment this fragment will be exchanged with after closing
     */
    public void setParameters(String eventID, String receiverID, Fragment replacementFragment){
        this.eventID = eventID;
        this.receiverID = receiverID;
        this.replacementFragment = replacementFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mFirestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_event_request_creation, container, false);
        initialiseVar(view);
        return view;
    }

    private void initialiseVar(View view){

        this.eventPicture = (ImageView) view.findViewById(R.id.eventPictureInRequest);

        this.eventName = (TextView)view.findViewById(R.id.eventNameCreation);
        this.receiverName = (TextView)view.findViewById(R.id.receiverNameEventCreation);


        this.accept = (Button) view.findViewById(R.id.acceptButton);
        this.refuse = (Button) view.findViewById(R.id.refuseButton);
    }

    //TODO: Refactor
    private void registerRequestIntoView(){

        //this.eventPicture set to event.getPicture()

        setEventNameAndPicture(this.eventID, eventName, eventPicture);

        setReceiverName(new FirebaseCallback() {
            @Override
            public void onStart() { }

            @Override
            public void onSuccess(Object data) {

            }

            @Override
            public void onFailed(DatabaseError databaseError) {
                Log.v(TAG, "Error while loading the receiver");
            }
        }, this.receiverID);


            accept.setOnClickListener(view -> {
                Request newRequest = new Request(Request.Object.Event, this.eventID, mAuth.getUid(), this.receiverID, "");
                replaceFragment(replacementFragment);
            });

            refuse.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    replaceFragment(replacementFragment);
                }
            });

    }

    public void setReceiverName(FirebaseCallback callback, String userID){
        mFirestore.collection("users").document(Objects.requireNonNull(userID))
                .get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    String name = document.getString("firstName") + " " + document.getString("lastName");
                    this.receiverName.setText(name);
                    callback.onSuccess(null);
                } else {
                    Log.d(TAG, "No such user");
                }
            } else {
                Log.d(TAG, "get failed with ", task.getException());
            }
        });
    }

    private Event tmpEvent = null;
    private void setEventNameAndPicture(String eventID, TextView name, ImageView image){
        tmpEvent = new Event(new FirebaseCallback() {
            @Override
            public void onStart() { }

            @Override
            public void onSuccess(Object data) {
                name.setText(tmpEvent.getName());
                tmpEvent.getEventImage(new FirebaseCallback() {
                    @Override
                    public void onStart() { }

                    @Override
                    public void onSuccess(Object data) {
                        image.setImageBitmap((Bitmap)data);
                    }

                    @Override
                    public void onFailed(DatabaseError databaseError) { Log.d(TAG, "Error getting profile picture"); }
                });
            }

            @Override
            public void onFailed(DatabaseError databaseError) {
                Log.v(TAG, "Error while loading the event");
            }
        }, eventID);
    }

    public void replaceFragment(Fragment someFragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();

        transaction.replace(R.id.flFragment, someFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}