package fr.ups.co_lendar.fragments;


import android.util.Log;
import android.view.View;

import androidx.fragment.app.Fragment;

import com.google.firebase.database.DatabaseError;

import fr.ups.co_lendar.FirebaseCallback;
import fr.ups.co_lendar.NotificationFragmentAdapter;
import fr.ups.co_lendar.helpers.Request;

public abstract class NotificationFragment extends Fragment {

    protected Request request;

    private NotificationFragmentAdapter adapter;

    private String TAG = "NotificationFragment";


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

    protected abstract void initialiseVar(View view);

    /**
     * Return the layout corresponding to the correct request.
     * Is different for each child of NotificationFragment
     * @return
     */
    public abstract int getLayoutId();

    protected abstract void registerRequestIntoView();

    /**
     * Call {@link NotificationFragment#initialiseVar(View)} and {@link NotificationFragment#registerRequestIntoView()}
     * after the callback from {@link Request#mapIdToObject(FirebaseCallback)}
     * @param view the view in which the fragment exist
     */
    public void setUpNotification(View view){
        if(this.request == null){
            Log.d(TAG, "Try to instantiate notification while request is null");
        } else {
            request.mapIdToObject(new FirebaseCallback() {
                @Override
                public void onStart() {

                }

                @Override
                public void onSuccess(Object data) {
                    initialiseVar(view);
                    registerRequestIntoView();
                }

                @Override
                public void onFailed(DatabaseError databaseError) {

                }
            });
        }
    }

}
