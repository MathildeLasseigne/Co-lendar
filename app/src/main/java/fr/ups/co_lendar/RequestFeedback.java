package fr.ups.co_lendar;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import fr.ups.co_lendar.helpers.NotificationFragment;
import fr.ups.co_lendar.helpers.Request;
import fr.ups.co_lendar.helpers.User;


public class RequestFeedback extends NotificationFragment {


    private TextView senderName;
    private TextView acceptanceMessage;
    private TextView topicName;

    private ImageView topicPicture;

    private Button okButton;

    public RequestFeedback(Request request) {
        super(request);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_request_feedback, container, false);
        initialiseVar(view);
        return view;
    }

    private void initialiseVar(View view){

        this.topicPicture = (ImageView) view.findViewById(R.id.topicPictureInFeedback);

        this.senderName = (TextView)view.findViewById(R.id.receiverNameEventCreation);
        this.acceptanceMessage = (TextView)view.findViewById(R.id.acceptance);
        this.topicName = (TextView)view.findViewById(R.id.topic);


        this.okButton = (Button) view.findViewById(R.id.okButton);
    }

    private void registerRequestIntoView(){

        //this.topicPicture set

        this.senderName.setText(request.getSender().getFirstName());
        if(true){ //Accepted
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


    }
}