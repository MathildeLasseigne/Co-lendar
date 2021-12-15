package fr.ups.co_lendar.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Date;

import fr.ups.co_lendar.R;
import fr.ups.co_lendar.helpers.Event;
import fr.ups.co_lendar.helpers.User;

public class EventViewFragment extends Fragment {

    TextView name;
    TextView address;
    TextView date;
    TextView time;
    TextView comments;
    TextView url;
    TextView commentsLabel;
    TextView urlLabel;
    ImageView iv1;
    ImageView iv2;
    ImageView iv3;
    Event thisEvent;
    ImageButton back;

    View mView;
    User loggedInUser;

    public EventViewFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_event_view, container, false);
        return mView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        setFragmentArguments();
        initializeUI();
    }

    private void initializeUI() {
        name = mView.findViewById(R.id.placeholder_name);
        address = mView.findViewById(R.id.placeholder_address);
        date = mView.findViewById(R.id.placeholder_date);
        time = mView.findViewById(R.id.placeholder_time);
        comments = mView.findViewById(R.id.placeholder_comments);
        url = mView.findViewById(R.id.placeholder_url);

        commentsLabel = mView.findViewById(R.id.comments_label);
        urlLabel = mView.findViewById(R.id.url_label);

        iv1 = mView.findViewById(R.id.imageView4);
        iv2 = mView.findViewById(R.id.imageView5);
        iv3 = mView.findViewById(R.id.imageView6);
        back = mView.findViewById(R.id.imageButton_back);

        if (thisEvent != null) {
            name.setText(thisEvent.getName());
            address.setText(thisEvent.getLocation());

            String dateStr = "";
            Date eventDate = thisEvent.getDate();
            dateStr = dateStr + eventDate.getDate() + " " + getMonthName(eventDate.getMonth()-1);

            String timeStr = "";
            timeStr = timeStr + eventDate.getHours() + ":" + eventDate.getMinutes();

            date.setText(dateStr);
            time.setText(timeStr);
            if (thisEvent.getComments() == null) {
                comments.setVisibility(View.GONE);
                commentsLabel.setVisibility(View.GONE);
            } else {
                comments.setVisibility(View.VISIBLE);
                commentsLabel.setVisibility(View.VISIBLE);
                comments.setText(thisEvent.getComments());
            }
            if (thisEvent.getUrl() == null) {
                url.setVisibility(View.GONE);
                urlLabel.setVisibility(View.GONE);
            } else {
                url.setVisibility(View.VISIBLE);
                urlLabel.setVisibility(View.VISIBLE);
                url.setText(thisEvent.getUrl());
            }
        }

        back.setOnClickListener(view -> {
            closeFragment();
        });

        iv1.setBackgroundResource(R.drawable.img1);
        iv2.setBackgroundResource(R.drawable.img2);
        iv3.setBackgroundResource(R.drawable.img3);
    }

    private void closeFragment() {
        CalendarFragment fragment = new CalendarFragment();
        Bundle bundle = this.getArguments();
        bundle.putSerializable("user", loggedInUser);
        fragment.setArguments(bundle);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();

        transaction.replace(R.id.flFragment, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private String getMonthName(int index) {
        String[] monthNames = new String[] {"January", "February", "March", "April", "May",
                "June", "July", "August", "September", "October", "November", "December"};
        return monthNames[index];
    }

    private void setFragmentArguments() {
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            loggedInUser = (User) bundle.getSerializable("user");
        }
    }

    public void setEvent(Event event) {
        this.thisEvent = event;
    }
}