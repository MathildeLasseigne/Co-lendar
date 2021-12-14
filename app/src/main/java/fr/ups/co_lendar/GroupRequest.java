package fr.ups.co_lendar;

import android.content.Intent;
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
import fr.ups.co_lendar.helpers.User;

public class GroupRequest extends NotificationFragment {


    private TextView groupName;
    private TextView message;
    private TextView participantsLeftoverNumber;


    private ImageView participant1;
    private ImageView participant2;
    private ImageView participant3;
    private ImageView participant4;
    private ImageView participant5;
    private ImageView participant6;

    private ImageButton requestSender;
    private Button accept; //Change to ImageButton
    private Button refuse; //Change to ImageButton
    private Button info;

    private String TAG = "EventRequest";


    public GroupRequest() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(getLayoutId(), container, false);
        setUpNotification(view);
        return view;
    }

    public void initialiseVar(View view){
        this.groupName = (TextView)view.findViewById(R.id.groupName);
        this.message = (TextView)view.findViewById(R.id.message);
        this.participantsLeftoverNumber = (TextView)view.findViewById(R.id.participantsLeftoverNumber);

        this.requestSender = (ImageButton) view.findViewById(R.id.profileButton);

        this.participant1 = (ImageView) view.findViewById(R.id.participant1);
        this.participant2 = (ImageView) view.findViewById(R.id.participant2);
        this.participant3 = (ImageView) view.findViewById(R.id.participant3);
        this.participant4 = (ImageView) view.findViewById(R.id.participant4);
        this.participant5 = (ImageView) view.findViewById(R.id.participant5);
        this.participant6 = (ImageView) view.findViewById(R.id.participant6);

        this.accept = (Button) view.findViewById(R.id.acceptButton);
        this.refuse = (Button) view.findViewById(R.id.refuseButton);
        this.info = (Button) view.findViewById(R.id.infoButton);
    }

    public void registerRequestIntoView(){
       Group group = this.request.getGroup();
        if(group != null){
            this.groupName.setText(group.getName());
            this.message.setText(this.request.getMessage());
            this.participantsLeftoverNumber.setText("");
            int i = 0;
            if(group.getMembers().size() > i){
                getUserPictureInImageView(group.getMembers().get(i), participant1);
                i++;
            }
            if(group.getMembers().size() > i){
                getUserPictureInImageView(group.getMembers().get(i), participant2);
                i++;
            }
            if(group.getMembers().size() > i){
                getUserPictureInImageView(group.getMembers().get(i), participant3);
                i++;
            }
            if(group.getMembers().size() > i){
                getUserPictureInImageView(group.getMembers().get(i), participant4);
                i++;
            }
            if(group.getMembers().size() > i){
                getUserPictureInImageView(group.getMembers().get(i), participant5);
                i++;
            }
            if(group.getMembers().size() > i){
                getUserPictureInImageView(group.getMembers().get(i), participant6);
                i++;
            }
            if(group.getMembers().size() > i){
                String str = "+"+ (group.getMembers().size() - i);
                this.participantsLeftoverNumber.setText(str);
            }

            this.request.getSender().getUserImage(new FirebaseCallback() {
                @Override
                public void onStart() { }

                @Override
                public void onSuccess(Object data) {
                    requestSender.setImageBitmap((Bitmap)data);
                }

                @Override
                public void onFailed(DatabaseError databaseError) { Log.d(TAG, "Error getting profile picture"); }
            });
            this.requestSender.setOnClickListener(view -> {
                //TODO switch to user (the sender) view
            });

            accept.setOnClickListener(view -> {
                request.acceptRequest();
                removeFromView();
            });

            refuse.setOnClickListener(view -> {
                request.refuseRequest();
                removeFromView();
            });

            info.setOnClickListener(view -> {
                //TODO switch to event view
            });
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_group_request;
    }

    private User getUserPictureInImageView(String uid, ImageView img) {

        return new User(new FirebaseCallback() {
            @Override
            public void onStart() { }

            @Override
            public void onSuccess(Object data) {
                User user = (User) data;
                user.getUserImage(new FirebaseCallback() {
                    @Override
                    public void onStart() { }

                    @Override
                    public void onSuccess(Object data) {
                        img.setImageBitmap((Bitmap)data);
                    }

                    @Override
                    public void onFailed(DatabaseError databaseError) { Log.d(TAG, "Error getting profile picture"); }
                });
            }

            @Override
            public void onFailed(DatabaseError databaseError) {
                Log.v(TAG, "Error while loading the user");
            }
        }, uid);
    }
}