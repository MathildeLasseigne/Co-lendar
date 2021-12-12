package fr.ups.co_lendar.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

import fr.ups.co_lendar.GroupDisplayAdapter;
import fr.ups.co_lendar.R;
import fr.ups.co_lendar.helpers.Group;

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
        ListView ListGroup = (ListView) rootView.findViewById(R.id.groupList);
        ArrayList<groupDisplayFragment> groups = new ArrayList<groupDisplayFragment>();
        groupDisplayFragment g1 = new groupDisplayFragment();
        g1.setParameters("Girls in deep shit");
        g1.setGid("g1");
        groups.add(g1);
        groupDisplayFragment g2 = new groupDisplayFragment();
        g2.setParameters("La Taverne");
        g2.setGid("g2");
        groups.add(g2);

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

        ListGroup.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                for(int i=0; i< groups.size(); i++) {
                    if(position == i) {
                        Fragment fragment = null;
                        GroupViewFragment gv = new GroupViewFragment();
                        gv.setGid(groups.get(i).getGid());
                        gv.setGroupName(groups.get(i).getGroupName());
                        fragment = gv;
                        replaceFragment(fragment);
                    }
                }

            }
        });
    }

    public void replaceFragment(Fragment someFragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();

        transaction.replace(R.id.flFragment, someFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}