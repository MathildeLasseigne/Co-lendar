package fr.ups.co_lendar.fragments;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Half;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import fr.ups.co_lendar.EventAdapter;
import fr.ups.co_lendar.FcmNotificationsSender;
import fr.ups.co_lendar.FirebaseCallback;
import fr.ups.co_lendar.FirebaseMessagingService;
import fr.ups.co_lendar.GroupDisplayAdapter;
import fr.ups.co_lendar.MainActivity;
import fr.ups.co_lendar.R;
import fr.ups.co_lendar.helpers.Event;
import fr.ups.co_lendar.helpers.Group;
import fr.ups.co_lendar.helpers.Request;
import fr.ups.co_lendar.helpers.User;
import fr.ups.co_lendar.memberDisplayAdapter;

public class EventCreationViewFragment extends Fragment {

    ImageView eventImageIV;
    ImageButton addParticipantIB;
    EditText nameET;
    EditText addressET;
    EditText timeET;
    EditText dateET;
    EditText commentsET;
    EditText urlET;
    ImageButton confirmButton;
    ImageButton cancelButton;
    CardView groupsCV;
    CardView participantsCV;
    RadioGroup participantGroupRG;
    RadioButton yesGroupRB;
    RadioButton noGroupRB;
    ListView userGroupsLV;
    ListView participantsLV;

    Group selectedGroup;
    groupDisplayFragment selectedGroupFragment;
    String selectedUID = "";
    List<String> selectedParticipants = new ArrayList<>();
    ArrayList<membersFragment> selectedParticipantFragments = new ArrayList<>();

    User loggedInUser;
    View mView;
    String TAG = "EventCreationViewFragment";

    public EventCreationViewFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_event_creation_view, container, false);
        return mView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        setFragmentArguments();
        initializeUI();
        initializeListeners();
    }

    private void setFragmentArguments() {
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            loggedInUser = (User) bundle.getSerializable("user");
        }
    }

    private void initializeUI() {
        eventImageIV = mView.findViewById(R.id.imageView_eventImage);
        addParticipantIB = mView.findViewById(R.id.imageButton_addParticipants);
        addressET = mView.findViewById(R.id.editText_address);
        timeET = mView.findViewById(R.id.editText_time);
        dateET = mView.findViewById(R.id.editText_date);
        commentsET = mView.findViewById(R.id.editText_comments);
        urlET = mView.findViewById(R.id.editText_url);
        nameET = mView.findViewById(R.id.editText_eventName);
        confirmButton = mView.findViewById(R.id.imageButton_confirm);
        cancelButton = mView.findViewById(R.id.imageButton_cancel);
        groupsCV = mView.findViewById(R.id.cardView_groups);
        participantsCV = mView.findViewById(R.id.cardView_participants);
        yesGroupRB = mView.findViewById(R.id.yesGroupRB);
        noGroupRB = mView.findViewById(R.id.noGroupRB);
        participantGroupRG = mView.findViewById(R.id.participantGroupRG);
        userGroupsLV = mView.findViewById(R.id.listView_userGroups);
        participantsLV = mView.findViewById(R.id.listView_participants);
        showUserGroups();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initializeListeners() {
        participantGroupRG.setOnCheckedChangeListener((radioGroup, i) -> {
            if (yesGroupRB.isChecked()) {
                groupsCV.setVisibility(View.VISIBLE);
                participantsCV.setVisibility(View.GONE);
            } else {
                groupsCV.setVisibility(View.GONE);
                participantsCV.setVisibility(View.VISIBLE);
            }
        });

        cancelButton.setOnClickListener(view -> {
            goBack();
        });

        confirmButton.setOnClickListener(view -> {
            addEventToDatabase();
        });

        addParticipantIB.setOnClickListener(view -> {
            showSearchDialog();
        });
    }

    private void addEventToDatabase() {
        Map<String, Object> event = new HashMap<>();
        String ID = UUID.randomUUID().toString();
        String name = nameET.getText().toString().trim();
        String address = addressET.getText().toString().trim();
        String dateStr = dateET.getText().toString().trim();
        String[] dateArr = dateStr.split("[.]");
        String hourStr = timeET.getText().toString().trim();
        String[] hourArr = hourStr.split(":");
        String comments = commentsET.getText().toString().trim();
        String url = urlET.getText().toString().trim();
        if (name.equals("")) {
            nameET.setError("Name cannot be empty");
            return;
        }
        if (address.equals("")) {
            addressET.setError("Address cannot be empty");
            return;
        }
        if (dateStr.equals("")) {
            dateET.setError("Date cannot be empty");
        }
        if (dateArr.length != 3 || Integer.parseInt(dateArr[0]) > 31
            || Integer.parseInt(dateArr[1]) > 12) {
            dateET.setError("Incorrect date format");
            return;
        }
        if (hourStr.equals("")) {
            timeET.setError("Time cannot be empty");
        }
        if (hourArr.length != 2 || Integer.parseInt(hourArr[0]) > 24
                || Integer.parseInt(hourArr[1]) > 59) {
            timeET.setError("Incorrect time format");
            return;
        }

        if (yesGroupRB.isChecked()) {
            if (selectedGroup  == null) {
                Toast.makeText(getContext(), "Please select a group", Toast.LENGTH_SHORT).show();
                return;
            } else {
                event.put("groupID", selectedGroup.getGroupID());
            }
        } else {
            event.put("groupID", "");
            if (selectedParticipants.size() == 0) {
                Toast.makeText(getContext(), "Please select at least one user", Toast.LENGTH_SHORT).show();
                return;
            } else {
                selectedParticipants.add(loggedInUser.getUID());
                event.put("participants", selectedParticipants);
            }
        }


        Calendar c = Calendar.getInstance();
        c.set(Integer.parseInt(dateArr[2]), Integer.parseInt(dateArr[1])-1,
                Integer.parseInt(dateArr[0]), Integer.parseInt(hourArr[1])
                , Integer.parseInt(hourArr[0]));
        Date date = c.getTime();

        event.put("name", name);
        event.put("date", date);
        event.put("comments", comments);
        event.put("url", url);
        event.put("eventID", ID);
        event.put("location", address);
        event.put("ownerID", loggedInUser.getUID());

        insertIntoDatabase(new FirebaseCallback() {
            @Override
            public void onStart() {}

            @Override
            public void onSuccess(Object data) {}

            @Override
            public void onFailed(DatabaseError databaseError) {}
        }, event);

        //create a request
        sendRequest(event);

        Toast.makeText(getContext(), "Event added successfully", Toast.LENGTH_LONG).show();
        goBack();
    }

    private void showUserGroups() {
        loggedInUser.getUserGroups(new FirebaseCallback() {
            @Override
            public void onStart() { }

            @Override
            public void onSuccess(Object data) {
                List<Group> groups = (List<Group>) data;
                registerGroupsToAdapter(groups);
            }

            @Override
            public void onFailed(DatabaseError databaseError) {
                Log.d(TAG, "Could not load user's groups");
            }
        });
    }

    private void registerGroupsToAdapter(List<Group> groups) {
        ArrayList<groupDisplayFragment> groupFragments = new ArrayList<>();
        for (Group group : groups) {
            groupDisplayFragment fragment = new groupDisplayFragment();
            fragment.setGroup(group);
            groupFragments.add(fragment);
        }
        GroupDisplayAdapter adapter = new GroupDisplayAdapter(getContext(), groupFragments);
        userGroupsLV.setAdapter(adapter);
        userGroupsLV.setOnItemClickListener((parent, view, position, id) -> {
            selectGroup(adapter, groups.get(position), groupFragments.get(position));
        });
    }

    private void selectGroup(GroupDisplayAdapter adapter, Group selectedGroup,
                             groupDisplayFragment groupFragment) {
        this.selectedGroup = selectedGroup;
        if (this.selectedGroupFragment != null) {
            adapter.unselectFragment();
        }
        adapter.selectFragment();
        this.selectedGroupFragment = groupFragment;
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

    public void insertIntoDatabase(FirebaseCallback callback, Map<String, Object> event) {

        FirebaseFirestore mFirestore = FirebaseFirestore.getInstance();
        mFirestore.collection("events")
                .add(event).addOnSuccessListener(unused -> {
            callback.onSuccess(null);
        }).addOnFailureListener(e -> {
            callback.onFailed(null);
        });

    }

    private void sendRequest(Map<String, Object> event) {
        if (!event.get("groupID").equals("")) {
            Group group = new Group(new FirebaseCallback() {
                @Override
                public void onStart() {}

                @Override
                public void onSuccess(Object data) {
                    List<String> members = ((Group) data).getMembers();
                    for (String member : members) {
                        new Request(Request.Object.Event, (String)event.get("eventID"),
                                loggedInUser.getUID(), member, "", false, false);
                    }
                    //not to be modified - faking the notification feature
                    sendNotificationToUser("New event", "Floriana invited you to an event");
                }

                @Override
                public void onFailed(DatabaseError databaseError) {
                    Log.d(TAG, "Could not load group");
                }
            }, (String)event.get("groupID"));
        } else {
            for (String participant : selectedParticipants) {
                if (!loggedInUser.getUID().equals(participant)) {
                    new Request(Request.Object.Event, (String)event.get("eventID"),
                            loggedInUser.getUID(), participant, "", false, false);
                }
            }
        }

    }

    private void registerParticipantsToAdapter() {
        memberDisplayAdapter adapter = new memberDisplayAdapter(getContext()
                , selectedParticipantFragments);
        participantsLV.setAdapter(adapter);
    }

    public void sendNotificationToUser(String title, String message) {
        FcmNotificationsSender notificationsSender = new FcmNotificationsSender(FirebaseMessagingService.getToken(mView.getContext()),
                title, message, mView.getContext(), getActivity());
        notificationsSender.sendNotifications();
    }

    private void goBack() {
        CalendarFragment fragment = new CalendarFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("user", loggedInUser);
        fragment.setArguments(bundle);
        getActivity().getSupportFragmentManager()
                .beginTransaction().replace(R.id.flFragment, fragment)
                .commit();
    }
}