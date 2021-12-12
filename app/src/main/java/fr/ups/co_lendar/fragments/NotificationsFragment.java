package fr.ups.co_lendar.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import fr.ups.co_lendar.NotificationFragmentAdapter;
import fr.ups.co_lendar.R;
import fr.ups.co_lendar.helpers.Request;

public class NotificationsFragment extends Fragment {

    private ArrayList<Request> requests;

    private ArrayList<NotificationFragmentAdapter> eventNotifications = new ArrayList<>();
    private ArrayList<NotificationFragmentAdapter> groupNotifications = new ArrayList<>();
    private ArrayList<NotificationFragmentAdapter> followNotifications = new ArrayList<>();
    private ArrayList<NotificationFragmentAdapter> feedbackNotifications = new ArrayList<>();

    public NotificationsFragment(){
        // require a empty public constructor
    }

    /**
     * Load the request from the database
     * @return
     */
    public ArrayList<Request> loadRequests(){
        return null;
    }

    public void updateView(){
        ArrayList<Request> r = loadRequests();
        for(Request req : r){
            boolean found = false;
            for(Request req2 : requests){
                if(req.equals(req2)){
                    break;
                }
                if(! found){
                    addRequest(req);
                }
            }
        }
    }

    private void addRequest(Request request){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_notifications, container, false);
    }
}