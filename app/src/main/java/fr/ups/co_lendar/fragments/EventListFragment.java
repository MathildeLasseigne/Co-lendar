package fr.ups.co_lendar.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;
import java.util.List;

import fr.ups.co_lendar.EventRequest;
import fr.ups.co_lendar.FirebaseCallback;
import fr.ups.co_lendar.GroupRequest;
import fr.ups.co_lendar.NotificationFragmentAdapter;
import fr.ups.co_lendar.R;
import fr.ups.co_lendar.RequestFeedback;
import fr.ups.co_lendar.helpers.Request;
import fr.ups.co_lendar.helpers.User;

public class EventListFragment extends Fragment {

    User loggedInUser;
    ArrayList<Request> newRequests = new ArrayList<>();
    View mView;
    FirebaseAuth mAuth;
    String TAG = "EventListFragment";

    private ListView eventList;
    private ArrayList<NotificationFragment> eventNotifications = new ArrayList<>();
    private NotificationFragmentAdapter eventAdapter;
    private ListView groupList;
    private ArrayList<NotificationFragment> groupNotifications = new ArrayList<>();
    private NotificationFragmentAdapter groupAdapter;
    private ListView followList;
    private ArrayList<NotificationFragment> followNotifications = new ArrayList<>();
    private NotificationFragmentAdapter followAdapter;
    private ListView feedbackList;
    private ArrayList<NotificationFragment> feedbackNotifications = new ArrayList<>();
    private NotificationFragmentAdapter feedbackAdapter;

    public EventListFragment() {
        // Required empty public constructor
    }

    public void setFragmentParameters() {
        User user = new User(new FirebaseCallback() {
            @Override
            public void onStart() {}

            @Override
            public void onSuccess(Object data) {
                loggedInUser = (User) data;
                getRequests();
            }

            @Override
            public void onFailed(DatabaseError databaseError) {

            }
        }, mAuth.getCurrentUser().getUid());

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_request_list, container, false);
        return mView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        this.eventList = mView.findViewById(R.id.home_eventList);
        this.eventAdapter = new NotificationFragmentAdapter(getContext(), this.eventNotifications);
        this.eventList.setAdapter(this.eventAdapter);
        this.groupList = mView.findViewById(R.id.home_groupList);
        this.groupAdapter = new NotificationFragmentAdapter(getContext(), groupNotifications);
        this.groupList.setAdapter(this.groupAdapter);
        this.feedbackList = mView.findViewById(R.id.home_feedbackList);
        this.feedbackAdapter = new NotificationFragmentAdapter(getContext(), feedbackNotifications);
        this.feedbackList.setAdapter(this.feedbackAdapter);

        mAuth = FirebaseAuth.getInstance();
        setFragmentParameters();
    }

    private void getRequests() {
        loggedInUser.getUserRequests(new FirebaseCallback() {
            @Override
            public void onStart() {}

            @Override
            public void onSuccess(Object data) {
                List<Request> requests = (List<Request>) data;
                for (Request request : requests) {
                    if (loggedInUser.getLastLoginTimestamp() != null
                    && request.getTimestampAdded() != null
                    && loggedInUser.getLastLoginTimestamp().compareTo(request.getTimestampAdded()) < 0) {
                        newRequests.add(request);
                    }
                }
                mapRequests();
            }

            @Override
            public void onFailed(DatabaseError databaseError) {
                Log.d(TAG, "Failed loading user's requests");
            }
        });
    }

    private void mapRequests() {

        for (int i = 0; i < newRequests.size(); i++) {
            newRequests.get(i).mapIdToObject(new FirebaseCallback() {
                @Override
                public void onStart() {}

                @Override
                public void onSuccess(Object data) {
                    registerRequestsInFragment();
                }

                @Override
                public void onFailed(DatabaseError databaseError) {
                    Log.d(TAG, "mapping failed");
                }
            });
        }
        if (newRequests.size() == 0) hideEmptyViews();
    }

    private void registerRequestsInFragment() {

        for (Request request : newRequests) {
            if (request.getObject() == Request.Object.Event) {
                EventRequest er = new EventRequest();
                er.setParameters(request);
                eventNotifications.add(er);
            }
            else if (request.getObject() == Request.Object.Group) {
                GroupRequest gr = new GroupRequest();
                gr.setParameters(request);
                eventNotifications.add(gr);
            }
            else {
                RequestFeedback fr = new RequestFeedback();
                fr.setParameters(request);
                eventNotifications.add(fr);
            }
        }

        hideEmptyViews();
    }

    private void hideEmptyViews() {
        if (eventNotifications.isEmpty()) {
            eventList.setVisibility(View.GONE);
            mView.findViewById(R.id.tv_er).setVisibility(View.GONE);
        } else {
            eventList.setVisibility(View.VISIBLE);
            mView.findViewById(R.id.tv_er).setVisibility(View.VISIBLE);
        }
        if (feedbackNotifications.isEmpty()) {
            feedbackList.setVisibility(View.GONE);
            mView.findViewById(R.id.tv_fr).setVisibility(View.GONE);
        } else {
            feedbackList.setVisibility(View.VISIBLE);
            mView.findViewById(R.id.tv_fr).setVisibility(View.VISIBLE);
        }
        if (groupNotifications.isEmpty()) {
            groupList.setVisibility(View.GONE);
            mView.findViewById(R.id.tv_gr).setVisibility(View.GONE);
        } else {
            groupList.setVisibility(View.VISIBLE);
            mView.findViewById(R.id.tv_gr).setVisibility(View.VISIBLE);
        }
    }
}