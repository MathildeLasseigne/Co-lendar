package fr.ups.co_lendar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

import fr.ups.co_lendar.fragments.groupDisplayFragment;

public class GroupDisplayAdapter extends ArrayAdapter<groupDisplayFragment> {
    public GroupDisplayAdapter(Context context, ArrayList<groupDisplayFragment> groupList) {
        super(context, 0, groupList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        groupDisplayFragment gd = getItem(position);
        String groupName = gd.getGroup().getName();
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_group_display, parent, false);
        }
        TextView gName = (TextView) convertView.findViewById(R.id.GroupText);
        gName.setText(groupName);
        return convertView;
    }
}