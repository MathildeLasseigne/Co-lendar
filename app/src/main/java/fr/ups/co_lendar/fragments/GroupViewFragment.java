package fr.ups.co_lendar.fragments;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.UUID;

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
    ImageButton addMembersIB;
    String selectedUID = "";
    ListView participantsLV;
    ArrayList<String> selectedParticipants = new ArrayList<>();
    ArrayList<membersFragment> selectedParticipantFragments = new ArrayList<>();
    ArrayList<EventFragment> events = new ArrayList<EventFragment>();
    String TAG = "GroupViewFragment";

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
        adapter = new memberDisplayAdapter(getContext(), selectedParticipantFragments);
        adapter.clear();
        eventAdapter = new EventAdapter(getContext(), events);
        eventAdapter.clear();
        setUser();
        rootView = inflater.inflate(R.layout.fragment_group_view, container, false);
        addMembersIB = rootView.findViewById(R.id.AddMember);
        initializeListeners();
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
                    selectedParticipantFragments.add(m);
                    addMembersDisplay(rootView);
                }
            }

            @Override
            public void onFailed(DatabaseError databaseError) {

            }
        });
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initializeListeners() {

        addMembersIB.setOnClickListener(view -> {
            showSearchDialog();
        });
    }

    private void addMembersDisplay(View rootView) {
        TextView t = (TextView) rootView.findViewById(R.id.ViewGroupName);
        t.setText(group.getName());
        participantsLV = (ListView) rootView.findViewById(R.id.groupsMembers);


        // Create the adapter to convert the array to views
        adapter = new memberDisplayAdapter(getContext(), selectedParticipantFragments);
        adapter.notifyDataSetChanged();

        // DataBind ListView with items from ArrayAdapter
        participantsLV.setAdapter(adapter);
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

    private void showSearchDialog() {
        Dialog searchDialog = new Dialog(getContext());
        searchDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        searchDialog.setCancelable(true);
        searchDialog.setContentView(R.layout.dialog_user_search);

        EditText emailET = searchDialog.findViewById(R.id.editText_search_email);
        ListView usersLV = searchDialog.findViewById(R.id.listView_searchUsers);
        Button searchButton = searchDialog.findViewById(R.id.button_searchUser);
        ImageButton cancelIB = searchDialog.findViewById(R.id.sv_imageButton_cancel);
        ImageButton confirmIB = searchDialog.findViewById(R.id.sv_imageButton_confirm);

        searchButton.setOnClickListener(view -> {
            if (emailET.getText().toString().equals("")) {
                emailET.setError("Please enter an email address");
            } else {
                findUserByEmail(emailET.getText().toString(), new FirebaseCallback() {
                    @Override
                    public void onStart() {}

                    @Override
                    public void onSuccess(Object data) {
                        User foundUser = (User) data;
                        ArrayList<membersFragment> fragments = new ArrayList<>();
                        membersFragment fragment = new membersFragment();
                        fragment.setFirstName(foundUser.getFirstName());
                        fragment.setLastName(foundUser.getLastName());
                        fragment.setUid(foundUser.getUID());
                        fragments.add(fragment);
                        memberDisplayAdapter adapter =
                                new memberDisplayAdapter(getContext(), fragments);
                        usersLV.setAdapter(adapter);
                        usersLV.setOnItemClickListener((parent, view, position, id) -> {
                            if (selectedUID.equals(fragments.get(position).getUid())) {
                                selectedUID = "";
                                selectedParticipantFragments.remove(fragments.get(position));
                                adapter.unselectFragment();
                            } else {
                                adapter.selectFragment();
                                selectedUID = fragments.get(position).getUid();
                                selectedParticipantFragments.add(fragments.get(position));
                            }
                        });
                    }

                    @Override
                    public void onFailed(DatabaseError databaseError) {
                        Log.d(TAG, "User not found");
                        emailET.setError("User not found");
                    }
                });
            }
        });

        cancelIB.setOnClickListener(view -> {
            searchDialog.dismiss();
            selectedUID = "";
        });

        confirmIB.setOnClickListener(view -> {
            if (!selectedUID.equals("")) {
                selectedParticipants.add(selectedUID);
                registerParticipantsToAdapter();
            }
            searchDialog.dismiss();
        });

        searchDialog.show();
    }

    private void registerParticipantsToAdapter() {
        memberDisplayAdapter adapter = new memberDisplayAdapter(getContext()
                , selectedParticipantFragments);
        participantsLV.setAdapter(adapter);
    }

    private void findUserByEmail(String email, FirebaseCallback callback) {
        FirebaseFirestore mFirestore = FirebaseFirestore.getInstance();
        mFirestore.collection("users")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            User user = document.toObject(User.class);
                            if (user.getEmail().equals(email)) {
                                callback.onSuccess(user);
                            }
                        }
                    } else {
                        Log.d(TAG, "Error getting users: ", task.getException());
                    }
                });
    }

}