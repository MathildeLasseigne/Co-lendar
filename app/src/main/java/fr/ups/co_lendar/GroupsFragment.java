package fr.ups.co_lendar;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

import fr.ups.co_lendar.fragments.groupDisplayFragment;

public class GroupsFragment extends Fragment {

    DatabaseReference dataResReference;
    List<String> idOfBlock = new ArrayList<String>();

    public GroupsFragment(){
        // require a empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_groups, container, false);
        addGroupDisplay(rootView, R.layout.fragment_group_display);
        return rootView;
    }


    public void addGroupDisplay(View rootView, int optionId) {
        ListView ListGroup = (ListView) rootView.findViewById(R.id.eventList);
        ArrayList<groupDisplayFragment> groups = new ArrayList<groupDisplayFragment>();
        groupDisplayFragment g1 = new groupDisplayFragment();
        g1.setGroupId(1);
        groups.add(g1);
        /*List<Group> groupOfTheUser = new ArrayList<>();


        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String currentUid = currentFirebaseUser.getUid();
        FirebaseDatabase.getInstance().getReference().child("groups")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        List<Group> list = new ArrayList<Group>();
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            list.add(snapshot.getValue(Group.class));
                            Group g = snapshot.getValue(Group.class);
                            //List<User> members = g.getMembers();
                            for(User member : g.getMembers()) {
                                String currentEmail = FirebaseAuth.getInstance().getCurrentUser().getEmail();
                                if(member.getEmail() == currentEmail) {
                                    groupOfTheUser.add(g);
                                }
                            }
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });

        for(Group g : groupOfTheUser) {
            String name = g.getName();
            groupDisplayFragment g1 = new groupDisplayFragment();
            g1.setParameters(name);
            groups.add(g1);
        }*/

        // Create the adapter to convert the array to views
        GroupDisplayAdapter adapter = new GroupDisplayAdapter (getContext(), groups);

        // DataBind ListView with items from ArrayAdapter
        ListGroup.setAdapter(adapter);
    }
}