package fr.ups.co_lendar;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import fr.ups.co_lendar.helpers.Request;
import fr.ups.co_lendar.helpers.User;

public class FollowRequestCreation extends Fragment {

    private User receiver;

    private ImageView receiverPicture;

    private TextView receiverName;

    private EditText message;

    private Button accept; //Change to ImageButton
    private Button refuse; //Change to ImageButton

    public FollowRequestCreation(User receiver) {
        this.receiver = receiver;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
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

    private void registerRequestIntoView(){

        //this.receiverPicture set to receiver.getPicture()

        this.receiverName.setText(receiver.getFirstName());



        accept.setOnClickListener(view -> {
            Request newRequest = new Request(new User(), receiver, message.getText().toString()); //todo The new user is the user who make the request
        });

        refuse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO switch to group view
            }
        });

    }
}