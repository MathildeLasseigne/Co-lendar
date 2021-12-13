package fr.ups.co_lendar;

import android.graphics.Bitmap;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseError;

import fr.ups.co_lendar.helpers.Group;
import fr.ups.co_lendar.fragments.NotificationFragment;
import fr.ups.co_lendar.helpers.Request;


public class FollowRequest extends NotificationFragment {

    private TextView senderName;
    private TextView message;


    private ImageButton requestSender;
    private Button accept; //Change to ImageButton
    private Button refuse; //Change to ImageButton

    private String TAG = "FollowRequest";

    public FollowRequest() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(getLayoutId(), container, false);
        initialiseVar(view);
        return view;
    }

    public void initialiseVar(View view){
        this.senderName = (TextView)view.findViewById(R.id.senderName);
        this.message = (TextView)view.findViewById(R.id.messageFollow);

        this.requestSender = (ImageButton) view.findViewById(R.id.profileButton);

        this.accept = (Button) view.findViewById(R.id.acceptButton);
        this.refuse = (Button) view.findViewById(R.id.refuseButton);
    }

    public void registerRequestIntoView(){


        this.senderName.setText(request.getSender().getFirstName());
        this.message.setText(request.getMessage());

        request.getSender().getUserImage(new FirebaseCallback() {
            @Override
            public void onStart() { }

            @Override
            public void onSuccess(Object data) {
                requestSender.setImageBitmap((Bitmap)data);
            }

            @Override
            public void onFailed(DatabaseError databaseError) { Log.d(TAG, "Error getting profile picture"); }
        });

        this.requestSender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO switch to user (the sender) view
            }
        });

        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                request.acceptRequest();
                removeFromView();
            }
        });

        refuse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                request.refuseRequest();
                removeFromView();
            }
        });

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_follow_request;
    }

}