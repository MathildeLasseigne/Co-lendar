package fr.ups.co_lendar.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
    ArrayList<groupDisplayFragment> groups = new ArrayList<groupDisplayFragment>();
    private View rootView;
    private GroupDisplayAdapter adapter;
    private Integer count = 0;

    public GroupsFragment(){
        // require a empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(count>0) {
            adapter.clear();
        }
        setUser();
        mAuth = FirebaseAuth.getInstance();
        mFirestore = FirebaseFirestore.getInstance();
        rootView = inflater.inflate(R.layout.fragment_groups, container, false);
        setGroups();

        count++;

        return rootView;
    }


    private void setUser() {

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            loggedInUser = (User) bundle.getSerializable("user");
        }
    }

    public void addGroupDisplay(View rootView) {

        ListView ListGroup = (ListView) rootView.findViewById(R.id.groupList);


        // Create the adapter to convert the array to views
        adapter = new GroupDisplayAdapter (getContext(), groups);
        adapter.notifyDataSetChanged();

        // DataBind ListView with items from ArrayAdapter
        ListGroup.setAdapter(adapter);

        //adapter.clear();

        ListGroup.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                for(int i=0; i< groups.size(); i++) {
                    if(position == i) {
                        Fragment fragment = null;
                        GroupViewFragment gv = new GroupViewFragment();
                        gv.setGroup(groups.get(i).getGroup());
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

    public void setGroups() {
        loggedInUser.getUserGroups(new FirebaseCallback() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(Object data) {
                ArrayList<Group> a = (ArrayList<Group>) data;
                for(Group g : a) {
                    groupDisplayFragment g3 = new groupDisplayFragment();
                    g3.setGroup(g);
                    groups.add(g3);
                    addGroupDisplay(rootView);
                }
            }

            @Override
            public void onFailed(DatabaseError databaseError) {

            }
        });
    }

}