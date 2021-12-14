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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

import fr.ups.co_lendar.FirebaseCallback;
import fr.ups.co_lendar.GroupDisplayAdapter;
import fr.ups.co_lendar.R;
import fr.ups.co_lendar.helpers.Group;
import fr.ups.co_lendar.helpers.User;

public class GroupsFragment extends Fragment {

    User loggedInUser;
    private FirebaseFirestore mFirestore;
    private FirebaseAuth mAuth;

    public GroupsFragment(){
        // require a empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();
        mFirestore = FirebaseFirestore.getInstance();
        setUser();
        View rootView = inflater.inflate(R.layout.fragment_groups, container, false);
        addGroupDisplay(rootView, R.layout.fragment_group_display);
        return rootView;
    }

    private void setUser() {

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            loggedInUser = (User) bundle.getSerializable("user");
        }
    }

    public void addGroupDisplay(View rootView, int optionId) {
        ListView ListGroup = (ListView) rootView.findViewById(R.id.groupList);
        ArrayList<groupDisplayFragment> groups = new ArrayList<groupDisplayFragment>();
        /*groupDisplayFragment g1 = new groupDisplayFragment();
        g1.setParameters("Girls in deep shit");
        g1.setGid("g1");
        groups.add(g1);
        groupDisplayFragment g2 = new groupDisplayFragment();
        g2.setParameters("La Taverne");
        g2.setGid("g2");
        groups.add(g2);*/

        //List<Group> groupOfTheUser = new ArrayList<>();

        loggedInUser.getUserGroups(new FirebaseCallback() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(Object data) {
                ArrayList<Group> a = (ArrayList<Group>) data;
                for(Group g : a) {
                    String name = g.getName();
                    groupDisplayFragment g3 = new groupDisplayFragment();
                    g3.setParameters(name);
                    groups.add(g3);
                }
            }

            @Override
            public void onFailed(DatabaseError databaseError) {

            }
        });


        /*for(Group g : groupOfTheUser) {
            String name = g.getName();
            groupDisplayFragment g3 = new groupDisplayFragment();
            g1.setParameters(name);
            groups.add(g3);
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