package fr.ups.co_lendar.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;
import java.util.UUID;

import fr.ups.co_lendar.FirebaseCallback;
import fr.ups.co_lendar.R;
import fr.ups.co_lendar.helpers.Event;
import fr.ups.co_lendar.helpers.Group;
import fr.ups.co_lendar.helpers.User;

public class AddingGroupViewFragment extends Fragment {
    private View view;
    private User loggedInUser;
    private ArrayList<User> members = new ArrayList<>();

    public AddingGroupViewFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setUser();
        view = inflater.inflate(R.layout.fragment_adding_group_view, container, false);
        setButtonAddGroup();
        return view;
    }

    private void setUser() {

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            loggedInUser = (User) bundle.getSerializable("user");
        }
    }

    private void setButtonAddGroup() {
        ImageButton confirm = (ImageButton) view.findViewById(R.id.confirm);


        confirm.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                EditText groupName = (EditText) view.findViewById(R.id.GroupAddedName);
                ArrayList<String> test = new ArrayList<String>();
                test.add("BLABLAAAAAAA");
                Group g = new Group();
                g.setName(groupName.getText().toString());
                g.setGroupID(UUID.randomUUID().toString());
                g.setMembers(test);
                g.setAdminUID(loggedInUser.getUID());
                g.insertIntoDatabase(new FirebaseCallback() {
                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onSuccess(Object data) {
                    }

                    @Override
                    public void onFailed(DatabaseError databaseError) {
                        Log.d("ERROR", "Problem adding the Group.");
                    }
                });

                Fragment fragment = null;
                GroupsFragment gf = new GroupsFragment();
                fragment = gf;
                replaceFragment(fragment);
            }
        });
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