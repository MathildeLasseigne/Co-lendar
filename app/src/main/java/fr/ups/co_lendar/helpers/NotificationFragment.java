package fr.ups.co_lendar.helpers;


import androidx.fragment.app.Fragment;

public abstract class NotificationFragment extends Fragment {

    protected Request request;


    public NotificationFragment(Request request){
        this.request = request;
    }

    public void removeFromView(){
        //TODO
    }

}
