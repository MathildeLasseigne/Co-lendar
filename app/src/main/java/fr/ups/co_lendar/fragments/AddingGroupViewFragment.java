package fr.ups.co_lendar.fragments;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import fr.ups.co_lendar.FirebaseCallback;
import fr.ups.co_lendar.R;
import fr.ups.co_lendar.helpers.Event;
import fr.ups.co_lendar.helpers.Group;
import fr.ups.co_lendar.helpers.Request;
import fr.ups.co_lendar.helpers.User;
import fr.ups.co_lendar.memberDisplayAdapter;

public class AddingGroupViewFragment extends Fragment {
    private View mView;
    private User loggedInUser;
    String TAG = "AddingGroupViewFragment";
    String groupID;

    ImageButton addMembersIB;
    EditText groupName;
    ImageButton confirmButton;
    ImageButton cancelButton;
    ListView participantsLV;
    String selectedUID = "";
    ArrayList<String> selectedParticipants = new ArrayList<>();
    ArrayList<membersFragment> selectedParticipantFragments = new ArrayList<>();

    public AddingGroupViewFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_adding_group_view, container, false);
        initializeUI();
        return mView;
    }

    private void setUser() {

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            loggedInUser = (User) bundle.getSerializable("user");
        }
    }

    private void setMembers() {
        selectedParticipants.add(loggedInUser.getUID());
        registerParticipantsToAdapter();

    }

    private void initializeUI() {
        setUser();
        addMembersIB = mView.findViewById(R.id.AddMember);
        groupName = mView.findViewById(R.id.GroupAddedName);
        confirmButton = mView.findViewById(R.id.confirm);
        cancelButton = mView.findViewById(R.id.cancel);
        participantsLV = mView.findViewById(R.id.listView_AddUserGroup);
        setMembers();
        initializeListeners();
    }


    @SuppressLint("ClickableViewAccessibility")
    private void initializeListeners() {

        cancelButton.setOnClickListener(view -> {
            Fragment fragment = null;
            GroupsFragment gf = new GroupsFragment();
            fragment = gf;
            replaceFragment(fragment);
        });

        confirmButton.setOnClickListener(view -> {
            Group g = new Group();
            g.setName(groupName.getText().toString());
            g.setMembers(selectedParticipants);
            groupID = UUID.randomUUID().toString();
            g.setGroupID(groupID);
            g.setAdminUID(loggedInUser.getUID());
            g.insertIntoDatabase(new FirebaseCallback() {
                @Override
                public void onStart() {

                }

                @Override
                public void onSuccess(Object data) {
                    Map<String, Object> group = new HashMap<>();
                    group.put("groupID", groupID);
                    group.put("name", groupName);
                    group.put("members", selectedParticipants);
                    group.put("adminUID", loggedInUser);
                    sendRequest(group);
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
        });

        addMembersIB.setOnClickListener(view -> {
            showSearchDialog();
        });
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

    private void sendRequest(Map<String, Object> group) {

        for (String participant : selectedParticipants) {
            if (!loggedInUser.getUID().equals(participant)) {
                new Request(Request.Object.Group, (String) group.get("groupID"), loggedInUser.getUID(), participant, "", false, false);
            }
        }

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