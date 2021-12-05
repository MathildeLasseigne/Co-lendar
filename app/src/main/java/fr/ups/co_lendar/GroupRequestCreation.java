package fr.ups.co_lendar;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import fr.ups.co_lendar.helpers.Group;
import fr.ups.co_lendar.helpers.Request;
import fr.ups.co_lendar.helpers.User;

public class GroupRequestCreation extends Fragment {

    private Group group;
    private User receiver;


    private ImageView groupPicture;

    private TextView groupName;
    private TextView receiverName;

    private EditText message;

    private Button accept; //Change to ImageButton
    private Button refuse; //Change to ImageButton

    public GroupRequestCreation(Group group, User receiver) {
        this.group = group;
        this.receiver = receiver;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_group_request_creation, container, false);
        initialiseVar(view);
        return view;
    }

    private void initialiseVar(View view){

        this.groupPicture = (ImageView) view.findViewById(R.id.groupPictureInRequest);

        this.groupName = (TextView)view.findViewById(R.id.groupNameCreation);
        this.receiverName = (TextView)view.findViewById(R.id.receiverNameGroupCreation);

        this.message = (EditText) view.findViewById(R.id.editGroupMessage);


        this.accept = (Button) view.findViewById(R.id.acceptButton);
        this.refuse = (Button) view.findViewById(R.id.refuseButton);
    }

    private void registerRequestIntoView(){

        //this.eventPicture set to event.getPicture()

        this.groupName.setText(group.getName());
        this.receiverName.setText(receiver.getFirstName());



        accept.setOnClickListener(view -> {
            Request newRequest = new Request(group, new User(), receiver, message.getText().toString()); //todo The new user is the user who make the request
        });

        refuse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO switch to group view
            }
        });

    }
}