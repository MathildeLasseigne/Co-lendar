package fr.ups.co_lendar;

import android.graphics.Bitmap;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseError;

import fr.ups.co_lendar.fragments.NotificationFragment;
import fr.ups.co_lendar.helpers.Request;


public class RequestFeedback extends NotificationFragment {


    private TextView senderName;
    private TextView acceptanceMessage;
    private TextView topicName;

    private ImageView topicPicture;

    private Button okButton;

    private String TAG = "FeedbackRequest";

    public RequestFeedback() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(getLayoutId(), container, false);
        initialiseVar(view);
        return view;
    }

    public void initialiseVar(View view){

        this.topicPicture = (ImageView) view.findViewById(R.id.topicPictureInFeedback);

        this.senderName = (TextView)view.findViewById(R.id.receiverNameEventCreation);
        this.acceptanceMessage = (TextView)view.findViewById(R.id.acceptance);
        this.topicName = (TextView)view.findViewById(R.id.topic);


        this.okButton = (Button) view.findViewById(R.id.okButton);
    }


    @Override
    public int getLayoutId() {
        return R.layout.fragment_request_feedback;
    }

    public void registerRequestIntoView(){
        //TODO: URGENT FIX - BREAKS THE CODE
        /*
        if(request.getObject() == Request.Object.Event){
            request.getEvent().getEventImage(new FirebaseCallback() {
                @Override
                public void onStart() { }

                @Override
                public void onSuccess(Object data) {
                    topicPicture.setImageBitmap((Bitmap)data);
                }

                @Override
                public void onFailed(DatabaseError databaseError) { Log.d(TAG, "Error getting event profile picture"); }
            });
        } else if (request.getObject() == Request.Object.Group){
            request.getGroup().getGroupImage(new FirebaseCallback() {
                @Override
                public void onStart() { }

                @Override
                public void onSuccess(Object data) {
                    topicPicture.setImageBitmap((Bitmap)data);
                }

                @Override
                public void onFailed(DatabaseError databaseError) { Log.d(TAG, "Error getting group profile picture"); }
            });
        } else {
            request.getSender().getUserImage(new FirebaseCallback() {
                @Override
                public void onStart() { }

                @Override
                public void onSuccess(Object data) {
                    topicPicture.setImageBitmap((Bitmap)data);
                }

                @Override
                public void onFailed(DatabaseError databaseError) { Log.d(TAG, "Error getting user profile picture"); }
            });
        }


        this.senderName.setText(request.getSender().getFirstName());
        if(request.isAccepted()){
            String str = "accepted your request !";
            this.acceptanceMessage.setText(str);
        } else {
            String str = "did not accept your request";
            this.acceptanceMessage.setText(str);
        }
        this.topicName.setText(request.getTopicName());


        okButton.setOnClickListener(view -> {
            request.removeFromDatabase();
            removeFromView();
        });
        */

    }
}