package fr.ups.co_lendar.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import fr.ups.co_lendar.GroupDisplayAdapter;
import fr.ups.co_lendar.R;
import fr.ups.co_lendar.memberDisplayAdapter;

public class GroupViewFragment extends Fragment {
    private String gid;
    private String groupName;

    public GroupViewFragment() {
        // Required empty public constructor
    }

    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_group_view, container, false);
        addMembersDisplay(rootView);
        return rootView;
    }

    private void addMembersDisplay(View rootView) {
        TextView t = (TextView) rootView.findViewById(R.id.ViewGroupName);
        t.setText(groupName);
        ListView ListMembers = (ListView) rootView.findViewById(R.id.groupsMembers);
        ArrayList<membersFragment> members = new ArrayList<membersFragment>();
        membersFragment m1 = new membersFragment();
        m1.setFirstName("Floriana");
        m1.setLastName("Pieprzyk");
        members.add(m1);
        membersFragment m2 = new membersFragment();
        m2.setFirstName("Julia");
        m2.setLastName("Biesiada");
        members.add(m2);
        membersFragment m3 = new membersFragment();
        m3.setFirstName("Mathilde");
        m3.setLastName("Lasseigne");
        members.add(m3);

        // Create the adapter to convert the array to views
        memberDisplayAdapter adapter = new memberDisplayAdapter(getContext(), members);

        // DataBind ListView with items from ArrayAdapter
        ListMembers.setAdapter(adapter);
    }
}