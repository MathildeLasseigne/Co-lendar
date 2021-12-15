package fr.ups.co_lendar.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;

import fr.ups.co_lendar.EventAdapter;
import fr.ups.co_lendar.FirebaseCallback;
import fr.ups.co_lendar.GroupDisplayAdapter;
import fr.ups.co_lendar.R;
import fr.ups.co_lendar.helpers.Event;
import fr.ups.co_lendar.helpers.Group;
import fr.ups.co_lendar.helpers.User;
import fr.ups.co_lendar.memberDisplayAdapter;

public class GroupViewFragment extends Fragment {
    private Group group;
    private View rootView;
    User loggedInUser;
    private memberDisplayAdapter adapter;
    private EventAdapter eventAdapter;
    ArrayList<membersFragment> members = new ArrayList<membersFragment>();
    ArrayList<EventFragment> events = new ArrayList<EventFragment>();

    public GroupViewFragment() {
        // Required empty public constructor
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    private void setUser() {

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            loggedInUser = (User) bundle.getSerializable("user");
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        adapter = new memberDisplayAdapter(getContext(), members);
        adapter.clear();
        eventAdapter = new EventAdapter(getContext(), events);
        eventAdapter.clear();
        setUser();
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_group_view, container, false);
        setMembers();
        setEvents();
        return rootView;
    }

    public void setMembers() {
        group.getGroupUsers(new FirebaseCallback() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(Object data) {
                ArrayList<User> users = (ArrayList<User>) data;
                for(User user : users) {
                    membersFragment m = new membersFragment();
                    m.setFirstName(user.getFirstName());
                    m.setLastName(user.getLastName());
                    members.add(m);
                    addMembersDisplay(rootView);
                }
            }

            @Override
            public void onFailed(DatabaseError databaseError) {

            }
        });
    }

    private void addMembersDisplay(View rootView) {
        TextView t = (TextView) rootView.findViewById(R.id.ViewGroupName);
        t.setText(group.getName());
        ListView ListMembers = (ListView) rootView.findViewById(R.id.groupsMembers);


        // Create the adapter to convert the array to views
        adapter = new memberDisplayAdapter(getContext(), members);
        adapter.notifyDataSetChanged();

        // DataBind ListView with items from ArrayAdapter
        ListMembers.setAdapter(adapter);
    }

    public void setEvents() {
        group.getGroupEvents(new FirebaseCallback() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(Object data) {
                ArrayList<Event> eventsOfGroup = (ArrayList<Event>) data;
                for(Event event : eventsOfGroup) {

                    EventFragment e = new EventFragment(event);
                    events.add(e);
                    addEventsDisplay(rootView);
                }
            }

            @Override
            public void onFailed(DatabaseError databaseError) {

            }
        });
    }

    private void addEventsDisplay(View rootView) {
        ListView ListEvents = (ListView) rootView.findViewById(R.id.groupsEvents);

        // Create the adapter to convert the array to views
        eventAdapter = new EventAdapter(getContext(), events);
        eventAdapter.notifyDataSetChanged();

        // DataBind ListView with items from ArrayAdapter
        ListEvents.setAdapter(eventAdapter);
    }

    public void replaceFragment(Fragment someFragment) {
        Bundle bundle = this.getArguments();
        bundle.putSerializable("user", loggedInUser);
        someFragment.setArguments(bundle);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();

        transaction.replace(R.id.flFragment, someFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}