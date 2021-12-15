package fr.ups.co_lendar.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import fr.ups.co_lendar.EventAdapter;
import fr.ups.co_lendar.FirebaseCallback;
import fr.ups.co_lendar.GroupDisplayAdapter;
import fr.ups.co_lendar.R;
import fr.ups.co_lendar.helpers.Event;
import fr.ups.co_lendar.helpers.User;

public class WeekCalendarFragment extends Fragment implements View.OnClickListener {

    private View mView;
    User loggedInUser;

    ArrayList<Event> events;
    private TextView sun;
    private TextView mon;
    private TextView tue;
    private TextView wed;
    private TextView thu;
    private TextView fri;
    private TextView sat;
    private TextView day1;
    private TextView day2;
    private TextView day3;
    private TextView day4;
    private TextView day5;
    private TextView day6;
    private TextView day7;

    private int firstDay;
    private TextView selectedDayText;
    private TextView selectedDayNumber;
    private FirebaseAuth mAuth;

    private FirebaseFirestore mFirestore;
    private String TAG = "WeekCalendarFragment";

    private ArrayList<TextView> daysText = new ArrayList<>();
    private ArrayList<TextView> daysNumbers = new ArrayList<>();

    public WeekCalendarFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mFirestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_week_calendar, container, false);
        return mView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        getUser();
        initializeUI();
    }

    private void getUser() {
        User user = new User(new FirebaseCallback() {
            @Override
            public void onStart() {}

            @Override
            public void onSuccess(Object data) {
                loggedInUser = (User) data;
                setEvents();
            }

            @Override
            public void onFailed(DatabaseError databaseError) {

            }
        }, mAuth.getCurrentUser().getUid());
    }

    private void initializeUI() {
        sun = (TextView) mView.findViewById((R.id.sun));
        sun.setOnClickListener(this);
        mon = (TextView) mView.findViewById((R.id.mon));
        mon.setOnClickListener(this);
        tue = (TextView) mView.findViewById((R.id.tue));
        tue.setOnClickListener(this);
        wed = (TextView) mView.findViewById((R.id.wed));
        wed.setOnClickListener(this);
        thu = (TextView) mView.findViewById((R.id.thu));
        thu.setOnClickListener(this);
        fri = (TextView) mView.findViewById((R.id.fri));
        fri.setOnClickListener(this);
        sat = (TextView) mView.findViewById((R.id.sat));
        sat.setOnClickListener(this);
        day1 = (TextView) mView.findViewById((R.id.day1));
        day1.setOnClickListener(this);
        day2 = (TextView) mView.findViewById((R.id.day2));
        day2.setOnClickListener(this);
        day3 = (TextView) mView.findViewById((R.id.day3));
        day3.setOnClickListener(this);
        day4 = (TextView) mView.findViewById((R.id.day4));
        day4.setOnClickListener(this);
        day5 = (TextView) mView.findViewById((R.id.day5));
        day5.setOnClickListener(this);
        day6 = (TextView) mView.findViewById((R.id.day6));
        day6.setOnClickListener(this);
        day7 = (TextView) mView.findViewById((R.id.day7));
        day7.setOnClickListener(this);

        daysText.add(sun);
        daysText.add(mon);
        daysText.add(tue);
        daysText.add(wed);
        daysText.add(thu);
        daysText.add(fri);
        daysText.add(sat);

        daysNumbers.add(day1);
        daysNumbers.add(day2);
        daysNumbers.add(day3);
        daysNumbers.add(day4);
        daysNumbers.add(day5);
        daysNumbers.add(day6);
        daysNumbers.add(day7);

        setDates();
    }

    @SuppressLint("SimpleDateFormat")
    private void setDates() {
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE", Locale.ENGLISH);
        Date d = new Date();
        String dayOfTheWeek = sdf.format(d).toLowerCase(Locale.ROOT).substring(0,3);

        Calendar cal = Calendar.getInstance();
        int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);

        switch (dayOfTheWeek) {
            case "sun":
                day1.setText(String.valueOf(dayOfMonth));
                day2.setText(String.valueOf(dayOfMonth+1));
                day3.setText(String.valueOf(dayOfMonth+2));
                day4.setText(String.valueOf(dayOfMonth+3));
                day5.setText(String.valueOf(dayOfMonth+4));
                day6.setText(String.valueOf(dayOfMonth+5));
                day7.setText(String.valueOf(dayOfMonth+6));
                selectDayText(mView.findViewById(R.id.sun));
                selectDayNumber(mView.findViewById(R.id.day1));
                selectedDayText = sun;
                selectedDayNumber = day1;
                firstDay = dayOfMonth;
                break;
            case "mon":
                day1.setText(String.valueOf(dayOfMonth-1));
                day2.setText(String.valueOf(dayOfMonth));
                day3.setText(String.valueOf(dayOfMonth+1));
                day4.setText(String.valueOf(dayOfMonth+2));
                day5.setText(String.valueOf(dayOfMonth+3));
                day6.setText(String.valueOf(dayOfMonth+4));
                day7.setText(String.valueOf(dayOfMonth+5));
                selectDayText(mView.findViewById(R.id.mon));
                selectDayNumber(mView.findViewById(R.id.day2));
                selectedDayText = mon;
                selectedDayNumber = day2;
                firstDay = dayOfMonth - 1;
                break;
            case "tue":
                day1.setText(String.valueOf(dayOfMonth-2));
                day2.setText(String.valueOf(dayOfMonth-1));
                day3.setText(String.valueOf(dayOfMonth));
                day4.setText(String.valueOf(dayOfMonth+1));
                day5.setText(String.valueOf(dayOfMonth+2));
                day6.setText(String.valueOf(dayOfMonth+3));
                day7.setText(String.valueOf(dayOfMonth+4));
                selectDayText(mView.findViewById(R.id.tue));
                selectDayNumber(mView.findViewById(R.id.day3));
                selectedDayText = tue;
                selectedDayNumber = day3;
                firstDay = dayOfMonth - 2;
                break;
            case "wed":
                day1.setText(String.valueOf(dayOfMonth-3));
                day2.setText(String.valueOf(dayOfMonth-2));
                day3.setText(String.valueOf(dayOfMonth-1));
                day4.setText(String.valueOf(dayOfMonth));
                day5.setText(String.valueOf(dayOfMonth+1));
                day6.setText(String.valueOf(dayOfMonth+2));
                day7.setText(String.valueOf(dayOfMonth+3));
                selectDayText(mView.findViewById(R.id.wed));
                selectDayNumber(mView.findViewById(R.id.day4));
                selectedDayText = wed;
                selectedDayNumber = day4;
                firstDay = dayOfMonth - 3;
                break;
            case "thu":
                day1.setText(String.valueOf(dayOfMonth-4));
                day2.setText(String.valueOf(dayOfMonth-3));
                day3.setText(String.valueOf(dayOfMonth-2));
                day4.setText(String.valueOf(dayOfMonth-1));
                day5.setText(String.valueOf(dayOfMonth));
                day6.setText(String.valueOf(dayOfMonth+1));
                day7.setText(String.valueOf(dayOfMonth+2));
                selectDayText(mView.findViewById(R.id.thu));
                selectDayNumber(mView.findViewById(R.id.day5));
                selectedDayText = thu;
                selectedDayNumber = day5;
                firstDay = dayOfMonth - 4;
                break;
            case "fri":
                day1.setText(String.valueOf(dayOfMonth-5));
                day2.setText(String.valueOf(dayOfMonth-4));
                day3.setText(String.valueOf(dayOfMonth-3));
                day4.setText(String.valueOf(dayOfMonth-2));
                day5.setText(String.valueOf(dayOfMonth-1));
                day6.setText(String.valueOf(dayOfMonth));
                day7.setText(String.valueOf(dayOfMonth+1));
                selectDayText(mView.findViewById(R.id.fri));
                selectDayNumber(mView.findViewById(R.id.day6));
                selectedDayText = fri;
                selectedDayNumber = day6;
                firstDay = dayOfMonth - 5;
                break;
            case "sat":
                day1.setText(String.valueOf(dayOfMonth-6));
                day2.setText(String.valueOf(dayOfMonth-5));
                day3.setText(String.valueOf(dayOfMonth-4));
                day4.setText(String.valueOf(dayOfMonth-3));
                day5.setText(String.valueOf(dayOfMonth-2));
                day6.setText(String.valueOf(dayOfMonth-1));
                day7.setText(String.valueOf(dayOfMonth));
                selectDayText(mView.findViewById(R.id.sat));
                selectDayNumber(mView.findViewById(R.id.day7));
                selectedDayText = sat;
                selectedDayNumber = day7;
                firstDay = dayOfMonth - 6;
                break;
            default:
                break;
        }
    }

    private void selectDayText(TextView textView) {
        textView.setBackgroundResource(R.drawable.calendar_selection_bg);
    }

    private void unselectDay() {
        selectedDayNumber.setBackgroundResource(0);
        selectedDayText.setBackgroundResource(0);
    }

    private void selectDayNumber(TextView textView) {
        textView.setBackgroundResource(R.color.colorTeal);
    }

    @Override
    public void onClick(View view) {
        unselectDay();
        int index = -1;
        if (daysNumbers.contains(view)) {
            index = daysNumbers.indexOf(view);
        } else if (daysText.contains(view)) {
            index = daysText.indexOf(view);
        }
        if (index != -1) {
            selectDayText(daysText.get(index));
            selectDayNumber(daysNumbers.get(index));
            selectedDayNumber = daysNumbers.get(index);
            selectedDayText = daysText.get(index);
            showEvents();
        }
    }

    private void setEvents() {

        events = new ArrayList<>();
        if (loggedInUser != null) {
            loggedInUser.getUserEvents(new FirebaseCallback() {
                @Override
                public void onStart() {}

                @Override
                public void onSuccess(Object data) {
                    for (Event event : (List<Event>) data) {
                        if (event.getDate().getDate() >= firstDay
                                && event.getDate().getDate() <= firstDay + 6) {
                            events.add(event);
                        }
                    }
                    showEvents();
                }

                @Override
                public void onFailed(DatabaseError databaseError) {Log.d(TAG, "Error getting events: ");}
            });
        }
    }

    private void showEvents() {
        ArrayList<EventFragment> eventFragments = new ArrayList<>();
        ArrayList<Event> dayEvents = new ArrayList<>();
        for (Event event : events) {
            if (event.getDate().getDate() == Integer.parseInt(selectedDayNumber.getText().toString())) {
                eventFragments.add(new EventFragment(event));
                dayEvents.add(event);
            }
        }
        EventAdapter adapter = new EventAdapter(getContext(), eventFragments);

        // DataBind ListView with items from ArrayAdapter
        ListView eventList = (ListView) mView.findViewById(R.id.eventListView);
        eventList.setAdapter(adapter);
        eventList.setOnItemClickListener((AdapterView.OnItemClickListener) (parent, view, position, id) -> {
            for(int i=0; i< events.size(); i++) {
                if(position == i) {
                    //EventViewFragment ev = new EventViewFragment();
                    //ev.setEvent(events.get(i));
                    //replaceFragment(ev);
                }
            }
        });
    }
}