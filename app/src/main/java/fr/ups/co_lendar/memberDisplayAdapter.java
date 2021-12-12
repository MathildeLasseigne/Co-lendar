package fr.ups.co_lendar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import fr.ups.co_lendar.fragments.groupDisplayFragment;
import fr.ups.co_lendar.fragments.membersFragment;

public class memberDisplayAdapter extends ArrayAdapter<membersFragment> {
    public memberDisplayAdapter(Context context, ArrayList<membersFragment> memberList) {
        super(context, 0, memberList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        membersFragment gd = getItem(position);
        String FirstName = gd.getFirstName();
        String LastName = gd.getLastName();
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_members, parent, false);
        }
        TextView gName = (TextView) convertView.findViewById(R.id.MemberName);
        String name = FirstName + " " + LastName;
        gName.setText(name);
        //FrameLayout f = (FrameLayout) convertView.findViewById(R.id.groupContainer);
        //f.setId(gd.getGroupId());
        return convertView;
    }
}
