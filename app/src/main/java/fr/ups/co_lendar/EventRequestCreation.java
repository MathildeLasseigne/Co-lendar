package fr.ups.co_lendar;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import fr.ups.co_lendar.helpers.Event;
import fr.ups.co_lendar.helpers.Group;
import fr.ups.co_lendar.helpers.Request;
import fr.ups.co_lendar.helpers.User;

public class EventRequestCreation extends Fragment {

    private Event event;
    private User receiver;

    private ImageView eventPicture;

    private TextView eventName;
    private TextView receiverName;

    private Button accept; //Change to ImageButton
    private Button refuse; //Change to ImageButton


    public EventRequestCreation(Event event, User receiver) {
        this.event = event;
        this.receiver = receiver;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
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

    private void registerRequestIntoView(){

        //this.eventPicture set to event.getPicture()

        this.eventName.setText(event.getName());
        this.receiverName.setText(receiver.getFirstName());



            accept.setOnClickListener(view -> {
                Request newRequest = new Request(event, new User(), receiver); //todo The new user is the user who make the request
            });

            refuse.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //TODO switch to event view
                }
            });

    }

}