package fr.ups.co_lendar.fragments;


import androidx.fragment.app.Fragment;

import fr.ups.co_lendar.helpers.Request;

public abstract class NotificationFragment extends Fragment {

    protected Request request;


    public NotificationFragment(Request request){
        this.request = request;
    }

    public void removeFromView(){
        //TODO
    }

}
