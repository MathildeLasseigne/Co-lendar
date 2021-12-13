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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

import fr.ups.co_lendar.helpers.Event;
import fr.ups.co_lendar.helpers.Group;
import fr.ups.co_lendar.helpers.Request;

public class GroupRequestCreation extends Fragment {

    private String groupID;
    private String receiverID;


    private ImageView groupPicture;

    private TextView groupName;
    private TextView receiverName;

    private EditText message;

    private Button accept; //Change to ImageButton
    private Button refuse; //Change to ImageButton

    private FirebaseAuth mAuth;
    private FirebaseFirestore mFirestore;
    private String TAG = "GroupRequestCreation";

    Fragment replacementFragment;

    public GroupRequestCreation() {}

    /**
     *
     * @param groupID
     * @param receiverID
     * @param replacementFragment The fragment this fragment will be exchanged with after closing
     */
    public void setParameters(String groupID, String receiverID, Fragment replacementFragment){
        this.groupID = groupID;
        this.receiverID = receiverID;
        this.replacementFragment = replacementFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mFirestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_group_request_creation, container, false);
        initialiseVar(view);
        return view;
    }

    private void initialiseVar(View view){

        this.groupPicture = (ImageView) view.findViewById(R.id.groupPictureInRequest);

        this.groupName = (TextView)view.findViewById(R.id.groupNameCreation);
        this.receiverName = (TextView)view.findViewById(R.id.receiverNameGroupCreation);

        this.message = (EditText) view.findViewById(R.id.editGroupMessage);


        this.accept = (Button) view.findViewById(R.id.acceptButton);
        this.refuse = (Button) view.findViewById(R.id.refuseButton);
    }

    private void registerRequestIntoView(){

        setGroupNameAndPicture(this.groupID, this.groupName, this.groupPicture);

        /*setGroupName(new FirebaseCallback() {
            @Override
            public void onStart() { }

            @Override
            public void onSuccess(Object data) {

            }

            @Override
            public void onFailed(DatabaseError databaseError) {
                Log.v(TAG, "Error while loading the event");
            }
        }, this.groupID);
         */

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
            Request newRequest = new Request(Request.Object.Group, this.groupID, mAuth.getUid(), this.receiverID, this.message.getText().toString());
            replaceFragment(replacementFragment);
        });

        refuse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceFragment(replacementFragment);
            }
        });
    }

    public void setGroupName(FirebaseCallback callback, String groupID){
        mFirestore.collection("groups").document(Objects.requireNonNull(groupID))
                .get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    this.groupName.setText(document.getString("name"));

                    callback.onSuccess(null);
                } else {
                    Log.d(TAG, "No such group");
                }
            } else {
                Log.d(TAG, "get failed with ", task.getException());
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

    private Group tmpGroup = null;
    private void setGroupNameAndPicture(String groupID, TextView name, ImageView image){
        tmpGroup = new Group(new FirebaseCallback() {
            @Override
            public void onStart() { }

            @Override
            public void onSuccess(Object data) {
                name.setText(tmpGroup.getName());
                tmpGroup.getGroupImage(new FirebaseCallback() {
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
                Log.v(TAG, "Error while loading the group");
            }
        }, groupID);
    }

    public void replaceFragment(Fragment someFragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();

        transaction.replace(R.id.flFragment, someFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}