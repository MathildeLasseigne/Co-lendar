package fr.ups.co_lendar;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.database.DatabaseError;

import fr.ups.co_lendar.helpers.Event;
import fr.ups.co_lendar.fragments.NotificationFragment;
import fr.ups.co_lendar.helpers.Request;
/*
/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EventRequest#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class EventRequest extends NotificationFragment {

    private TextView eventName;
    private TextView schedule;
    private TextView location;

    private ImageButton requestSender;
    private Button accept; //Change to ImageButton
    private Button refuse; //Change to ImageButton
    private Button info;

    private String TAG = "EventRequest";


    public EventRequest() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(getLayoutId(), container, false);
        setUpNotification(view);
        return view;
    }

    public void initialiseVar(View view){
        this.eventName = (TextView)view.findViewById(R.id.eventName);
        this.schedule = (TextView)view.findViewById(R.id.schedule);
        this.location = (TextView)view.findViewById(R.id.location);
        this.requestSender = (ImageButton) view.findViewById(R.id.profileButton);
        this.accept = (Button) view.findViewById(R.id.acceptButton);
        this.refuse = (Button) view.findViewById(R.id.refuseButton);
        this.info = (Button) view.findViewById(R.id.infoButton);
    }


    public void registerRequestIntoView(){
        Event event = this.request.getEvent();
        if(event != null){
            this.eventName.setText(event.getName());
            String date = event.getDate().toString();
            this.schedule.setText(date);
            this.location.setText(event.getLocation());

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
        return R.layout.fragment_event_request;
    }
}