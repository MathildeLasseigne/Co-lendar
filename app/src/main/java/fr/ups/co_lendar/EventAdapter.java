package fr.ups.co_lendar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import fr.ups.co_lendar.fragments.EventFragment;
import fr.ups.co_lendar.helpers.Event;

public class EventAdapter extends ArrayAdapter<EventFragment> {

    TextView name;
    TextView time;
    TextView extraInvitees;
    TextView address;
    ImageView imageView1;
    ImageView imageView2;
    ImageView imageView3;

    public EventAdapter(Context context, ArrayList<EventFragment> groupList) {
        super(context, 0, groupList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        EventFragment ef = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_event, parent, false);

        }
        initializeUI(convertView);
        name.setText(ef.getEventName());
        address.setText(ef.getEventAddress());
        time.setText(ef.getEventTime());
        return convertView;
    }

    private void initializeUI(View view) {
        name = view.findViewById(R.id.textView_eventName);
        address = view.findViewById(R.id.textView_location);
        time = view.findViewById(R.id.textView_time);
        extraInvitees = view.findViewById(R.id.textView_hiddenMembersNb);

        imageView1 = view.findViewById(R.id.imageView1);
        imageView2 = view.findViewById(R.id.imageView2);
        imageView3 = view.findViewById(R.id.imageView3);
    }

}