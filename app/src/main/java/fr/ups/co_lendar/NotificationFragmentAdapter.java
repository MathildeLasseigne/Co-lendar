package fr.ups.co_lendar;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.ArrayList;

import android.widget.ArrayAdapter;

import fr.ups.co_lendar.fragments.NotificationFragment;

public class NotificationFragmentAdapter extends ArrayAdapter<NotificationFragment> {

    private ArrayList<NotificationFragment> requestList;

    public NotificationFragmentAdapter(Context context, ArrayList<NotificationFragment> requestList) {
        super(context, 0, requestList);
        this.requestList = requestList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        NotificationFragment nf = getItem(position);
        nf.setAdapter(this);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(nf.getLayoutId(), parent, false);
        }
        nf.initialiseVar(convertView);
        nf.registerRequestIntoView();

        return convertView;
    }

    public void removeFromList(NotificationFragment nf){
        remove(nf);
        //notifyDataSetChanged();
    }
}
