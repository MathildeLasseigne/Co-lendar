package fr.ups.co_lendar.fragments;

import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Date;
import java.util.List;

import fr.ups.co_lendar.R;
import fr.ups.co_lendar.helpers.Event;

public class EventFragment extends Fragment {

    Event event;
    TextView name;
    TextView time;
    TextView extraInvitees;
    TextView address;
    ImageView imageView1;
    ImageView imageView2;
    ImageView imageView3;

    View mView;

    public EventFragment() {
        // Required empty public constructor
    }

    public EventFragment(Event event) {
        this.event = event;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_event, container, false);
        return mView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        initializeUI();
    }

    private void initializeUI() {
        //TODO: get event
        name = mView.findViewById(R.id.textView_eventName);
        address = mView.findViewById(R.id.textView_location);
        time = mView.findViewById(R.id.textView_time);
        extraInvitees = mView.findViewById(R.id.textView_hiddenMembersNb);

        imageView1 = mView.findViewById(R.id.imageView1);
        imageView2 = mView.findViewById(R.id.imageView2);
        imageView3 = mView.findViewById(R.id.imageView3);
    }

    public String getEventName() {
        return event.getName();
    }

    public String getEventTime() {
        String eventTime = "";
        eventTime += event.getDate().getHours();
        eventTime += ":";
        eventTime += event.getDate().getMinutes();
        if (event.getDate().getMinutes() == 0) {
            eventTime += "0";
        }
        return eventTime;
    }

    public String getEventAddress() {
        return event.getLocation();
    }

    public List<String> getMembers() {
        return event.getParticipants();
    }

    public Bitmap getImageBitmap1() {
        if (event.getParticipants() != null) {
            //get first participant's pic
        } else if (event.getGroupID() != null){
            //get groupPic
        }
        return null;
    }

    public Bitmap getImageBitmap2() {
        return null;
    }

    public Bitmap getImageBitmap3() {
        return null;
    }
}
