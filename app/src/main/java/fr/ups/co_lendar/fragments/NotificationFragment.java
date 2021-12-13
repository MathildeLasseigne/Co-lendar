package fr.ups.co_lendar.fragments;


import android.view.View;

import androidx.fragment.app.Fragment;

import fr.ups.co_lendar.NotificationFragmentAdapter;
import fr.ups.co_lendar.helpers.Request;

public abstract class NotificationFragment extends Fragment {

    protected Request request;

    private NotificationFragmentAdapter adapter;


    public NotificationFragment(){

    }

    public void setParameters(Request request){
        this.request = request;
    }

    public Request getRequest() {
        return request;
    }


    public void setAdapter(NotificationFragmentAdapter adapter) {
        this.adapter = adapter;
    }

    public void removeFromView(){
        adapter.removeFromList(this);
    }

    public abstract void initialiseVar(View view);

    /**
     * Return the layout corresponding to the correct request.
     * Is different for each child of NotificationFragment
     * @return
     */
    public abstract int getLayoutId();

    public abstract void registerRequestIntoView();

}
