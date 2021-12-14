package fr.ups.co_lendar.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import fr.ups.co_lendar.EventAdapter;
import fr.ups.co_lendar.FirebaseCallback;
import fr.ups.co_lendar.R;
import fr.ups.co_lendar.helpers.Event;
import fr.ups.co_lendar.helpers.User;

public class CalendarFragment extends Fragment {

    TextView selectedDateTextView;
    TextView upcomingEventsTextView;
    ListView selectedDateEventsLV;
    ListView upcomingEventsLV;
    CalendarView calendarView;

    Date selectedDate;
    User loggedInUser;
    View mView;
    String TAG = "CalendarFragment";
    List<Event> allEvents;
    String suffix = " today";

    public CalendarFragment(){
        // require a empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_calendar, container, false);
        return mView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        setFragmentArguments();
        initializeUI();
        initializeListeners();
    }

    private void setFragmentArguments() {
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            loggedInUser = (User) bundle.getSerializable("user");
        }
    }

    private void initializeUI() {
        selectedDateTextView = mView.findViewById(R.id.textView_dateSelected);
        upcomingEventsTextView = mView.findViewById(R.id.textView_allEvents);
        selectedDateEventsLV = mView.findViewById(R.id.listView_selectedDateEvents);
        upcomingEventsLV = mView.findViewById(R.id.listView_allEvents);
        calendarView = mView.findViewById(R.id.calendarView);

        selectedDate = new Date(calendarView.getDate());

        //getting user's events
        loggedInUser.getUserEvents(new FirebaseCallback() {
            @Override
            public void onStart() {}

            @Override
            public void onSuccess(Object data) {
                allEvents = new ArrayList<Event>();
                for (Event event : (List<Event>) data) {
                    if (!event.getDate().before(Calendar.getInstance().getTime())) {
                        allEvents.add(event);
                    }
                }
                showTodaysEvents();
                showAllEvents();
            }

            @Override
            public void onFailed(DatabaseError databaseError) {
                Log.d(TAG, "Error getting user's events");
            }
        });
    }

    private void initializeListeners() {
        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            Calendar c = Calendar.getInstance();
            c.set(year, month, dayOfMonth, 0, 0);
            selectedDate = c.getTime();
            Date today = Calendar.getInstance().getTime();
            if (today.getYear() == year && today.getMonth() == month
                && today.getDate() == dayOfMonth) {
                suffix = " today";
            } else {
                suffix = " on the " + dayOfMonth + "th of " + getMonthName(month);
            }
            showTodaysEvents();
        });
    }

    @SuppressLint("SetTextI18n")
    private void showTodaysEvents() {
        ArrayList<EventFragment> eventFragments = new ArrayList<>();
        for (Event event : allEvents) {
            if (event.getDate().getDate() == selectedDate.getDate()) {
                eventFragments.add(new EventFragment(event));
            }
        }
        if (eventFragments.size() == 0) {
            selectedDateTextView.setText("No events" + suffix);
            selectedDateEventsLV.setVisibility(View.GONE);
        } else {
            //setting the right size of a listview
            ViewGroup.LayoutParams params = selectedDateEventsLV.getLayoutParams();
            if (eventFragments.size() == 1) {
                params.height = dpToPx(90);
            } else {
                params.height = dpToPx(180);
            }
            selectedDateEventsLV.setLayoutParams(params);
            selectedDateTextView.setText(eventFragments.size() + " event(s)" + suffix);
            selectedDateEventsLV.setVisibility(View.VISIBLE);
            registerFragmentsToAdapter(eventFragments, selectedDateEventsLV);
        }
    }

    private void showAllEvents() {
        Collections.sort(allEvents, new sortCompare());
        ArrayList<EventFragment> eventFragments = new ArrayList<>();
        for (Event event : allEvents) {
            eventFragments.add(new EventFragment(event));
        }
        if (eventFragments.size() == 0) {
            upcomingEventsTextView.setText(R.string.no_upcoming_events);
            upcomingEventsLV.setVisibility(View.GONE);
        } else {
            upcomingEventsTextView.setText(R.string.some_upcoming_events);
            upcomingEventsLV.setVisibility(View.VISIBLE);
            registerFragmentsToAdapter(eventFragments, upcomingEventsLV);
        }
    }

    private void registerFragmentsToAdapter(ArrayList<EventFragment> fragments, ListView lv) {

        EventAdapter adapter = new EventAdapter(getContext(), fragments);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener((AdapterView.OnItemClickListener) (parent, view, position, id) -> {
            Log.d("event", "position is " + position);
        });
    }

    class sortCompare implements Comparator<Event>
    {
        @Override
        public int compare(Event a, Event b)
        {
            return a.getDate().compareTo(b.getDate());
        }
    }

    private String getMonthName(int index) {
        String[] monthNames = new String[] {"January", "February", "March", "April", "May",
                "June", "July", "August", "September", "October", "November", "December"};
        return monthNames[index];
    }

    public int dpToPx(int dp) {
        float density = mView.getContext().getResources().getDisplayMetrics().density;
        return Math.round((float) dp * density);
    }
}