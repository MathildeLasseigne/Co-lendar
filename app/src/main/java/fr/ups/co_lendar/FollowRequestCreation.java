package fr.ups.co_lendar;

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

import java.util.Objects;

import fr.ups.co_lendar.helpers.Request;
import fr.ups.co_lendar.helpers.User;

public class FollowRequestCreation extends Fragment {

    private String receiverID;

    private ImageView receiverPicture;

    private TextView receiverName;

    private EditText message;

    private Button accept; //Change to ImageButton
    private Button refuse; //Change to ImageButton

    private FirebaseAuth mAuth;
    private FirebaseFirestore mFirestore;
    private String TAG = "FollowRequestCreation";

    Fragment replacementFragment;

    public FollowRequestCreation() {    }

    /**
     *
     * @param receiverID
     * @param replacementFragment The fragment this fragment will be exchanged with after closing
     */
    public void setParameters(String receiverID, Fragment replacementFragment){
        this.receiverID = receiverID;
        this.replacementFragment = replacementFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mFirestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_follow_request_creation, container, false);
        initialiseVar(view);
        return view;
    }

    private void initialiseVar(View view){

        this.receiverPicture = (ImageView) view.findViewById(R.id.followPictureInRequest);

        this.receiverName = (TextView)view.findViewById(R.id.receiverNameFollowCreation);

        this.message = (EditText) view.findViewById(R.id.editFollowMessage);


        this.accept = (Button) view.findViewById(R.id.acceptButton);
        this.refuse = (Button) view.findViewById(R.id.refuseButton);
    }

    //TODO: refactor
    private void registerRequestIntoView(){

        //this.receiverPicture set to receiver.getPicture()

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
            Request newRequest = new Request(Request.Object.Follow, "", mAuth.getUid(), this.receiverID, this.message.getText().toString());
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
        mFirestore.collection("users").document(Objects.requireNonNull(UUID))
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

    public void replaceFragment(Fragment someFragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();

        transaction.replace(R.id.flFragment, someFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}