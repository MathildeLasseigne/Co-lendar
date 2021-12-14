package fr.ups.co_lendar.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

import fr.ups.co_lendar.EventRequestCreation;
import fr.ups.co_lendar.FirebaseCallback;
import fr.ups.co_lendar.MainActivity;
import fr.ups.co_lendar.NotificationFragmentAdapter;
import fr.ups.co_lendar.R;
import fr.ups.co_lendar.helpers.Request;
import fr.ups.co_lendar.helpers.User;

public class NotificationsFragment extends Fragment {

    User loggedInUser;

    private FirebaseFirestore mFirestore;

    private ArrayList<Request> requests = new ArrayList<>();

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

    View mView;

    String TAG = "NotificationsFragment";

    public NotificationsFragment(){
        // require a empty public constructor
    }

    public void updateView(){
        eventAdapter.clear();
        groupAdapter.clear();
        followAdapter.clear();
        feedbackAdapter.clear();
        FirebaseCallback fc = new FirebaseCallback() {
            @Override
            public void onStart() { }

            @Override
            public void onSuccess(Object data) {
                requests = (ArrayList<Request>) data;
                for(Request r : requests){
                    addRequest(r);
                }
            }

            @Override
            public void onFailed(DatabaseError databaseError) {
                Log.v(TAG, "Error while loading the requests");
            }
        };
        this.loggedInUser.getUserRequests(fc);
        /*ArrayList<Request> r = loadRequests();
        if (r != null) {
            for(Request req : r){
                boolean found = false;
                for(Request req2 : requests){
                    if(req.equals(req2)){
                        break;
                    }
                }
                if(! found){
                    addRequest(req);
                }
            }
        }*/
    }

    private void setUser() {

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            loggedInUser = (User) bundle.getSerializable("user");
        }
    }

    private void addRequest(Request request){
        NotificationFragment nf = request.createFragment();
        if(request.isFeedback()){
            feedbackAdapter.add(nf);
        } else if(request.getObject() == Request.Object.Event){
            eventAdapter.add(nf);
        } else if(request.getObject() == Request.Object.Group){
            groupAdapter.add(nf);
        } else if(request.getObject() == Request.Object.Follow){
            followAdapter.add(nf);
        }
    }

    private void initializeUI(){
        setUser();
        this.eventList = mView.findViewById(R.id.eventList);
        this.eventAdapter = new NotificationFragmentAdapter(getContext(), this.eventNotifications);
        this.eventList.setAdapter(eventAdapter);
        this.groupList = mView.findViewById(R.id.groupList);
        this.groupAdapter = new NotificationFragmentAdapter(getContext(), groupNotifications);
        this.groupList.setAdapter(groupAdapter);
        this.followList = mView.findViewById(R.id.followList);
        this.followAdapter = new NotificationFragmentAdapter(getContext(), followNotifications);
        this.followList.setAdapter(followAdapter);
        this.feedbackList = mView.findViewById(R.id.feedbackList);
        this.feedbackAdapter = new NotificationFragmentAdapter(getContext(), feedbackNotifications);
        this.feedbackList.setAdapter(feedbackAdapter);
        updateView();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mFirestore = FirebaseFirestore.getInstance();
        if(!test){
            mView = inflater.inflate(R.layout.fragment_notifications, container, false);
        } else {
            mView = inflater.inflate(R.layout.test_fragment_notification, container, false);
        }
        return mView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        initializeUI();
        if(test){ testUISetUp();}
    }

    private boolean test = false;
    //LinearLayout placeholder;

    private void testUISetUp(){
        //placeholder = mView.findViewById(R.id.placeholder);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        EventRequestCreation rc = new EventRequestCreation();
        rc.setParameters("2", "tbJ9XxC7x7YihT4J8aHCCqVH4nq1", new Fragment());
        transaction.replace(R.id.placeholder, rc);
        transaction.addToBackStack(null);
        transaction.commit();
        /*FragmentTransaction F_T =getSupportFragmentManager().beginTransaction();
        F_T.replace(R.id.df_placeholder, new DataFlairFragment());
        F_T.commit();*/
    }
}