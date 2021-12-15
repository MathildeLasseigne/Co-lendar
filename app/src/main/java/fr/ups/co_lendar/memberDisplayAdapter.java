package fr.ups.co_lendar;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import java.util.ArrayList;

import fr.ups.co_lendar.fragments.groupDisplayFragment;
import fr.ups.co_lendar.fragments.membersFragment;

public class memberDisplayAdapter extends ArrayAdapter<membersFragment> {

    View mView;

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
            mView = convertView;
        }
        TextView gName = convertView.findViewById(R.id.MemberName);
        String name = FirstName + " " + LastName;
        gName.setText(name);
        return convertView;
    }

    public void selectFragment() {
        CardView layout = mView.findViewById(R.id.cardView_member);
        layout.setCardBackgroundColor(getContext().getResources().getColor(R.color.colorTeal));
    }

    public void unselectFragment() {
        CardView layout = mView.findViewById(R.id.cardView_member);
        layout.setCardBackgroundColor(getContext().getResources().getColor(R.color.colorGrey));
    }
}
