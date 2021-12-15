package fr.ups.co_lendar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import java.util.ArrayList;

import fr.ups.co_lendar.fragments.groupDisplayFragment;

public class GroupDisplayAdapter extends ArrayAdapter<groupDisplayFragment> {
    public GroupDisplayAdapter(Context context, ArrayList<groupDisplayFragment> groupList) {
        super(context, 0, groupList);
    }

    View mView;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        groupDisplayFragment gd = getItem(position);
        String groupName = gd.getGroup().getName();
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_group_display, parent, false);
            mView = convertView;
        }
        TextView gName = (TextView) convertView.findViewById(R.id.GroupText);
        gName.setText(groupName);
        return convertView;
    }

    public void selectFragment() {
        CardView cardView = mView.findViewById(R.id.cardView_group);
        cardView.setCardBackgroundColor(getContext().getResources().getColor(R.color.colorTeal));
    }

    public void unselectFragment() {
        CardView cardView = mView.findViewById(R.id.cardView_group);
        cardView.setCardBackgroundColor(getContext().getResources().getColor(R.color.lightBlue));
    }
}