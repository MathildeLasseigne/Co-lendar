package fr.ups.co_lendar;

import android.os.Bundle;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import fr.ups.co_lendar.helpers.Group;
import fr.ups.co_lendar.fragments.NotificationFragment;
import fr.ups.co_lendar.helpers.Request;

public class GroupRequest extends NotificationFragment {


    private TextView groupName;
    private TextView message;
    private TextView participantsLeftoverNumber;


    private ImageView participant1;
    private ImageView participant2;
    private ImageView participant3;
    private ImageView participant4;
    private ImageView participant5;
    private ImageView participant6;

    private ImageButton requestSender;
    private Button accept; //Change to ImageButton
    private Button refuse; //Change to ImageButton
    private Button info;


    public GroupRequest(Request request) {
        super(request);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_group_request, container, false);
        initialiseVar(view);
        return view;
    }

    private void initialiseVar(View view){
        this.groupName = (TextView)view.findViewById(R.id.groupName);
        this.message = (TextView)view.findViewById(R.id.message);
        this.participantsLeftoverNumber = (TextView)view.findViewById(R.id.participantsLeftoverNumber);

        this.requestSender = (ImageButton) view.findViewById(R.id.profileButton);

        this.participant1 = (ImageView) view.findViewById(R.id.participant1);
        this.participant2 = (ImageView) view.findViewById(R.id.participant2);
        this.participant3 = (ImageView) view.findViewById(R.id.participant3);
        this.participant4 = (ImageView) view.findViewById(R.id.participant4);
        this.participant5 = (ImageView) view.findViewById(R.id.participant5);
        this.participant6 = (ImageView) view.findViewById(R.id.participant6);

        this.accept = (Button) view.findViewById(R.id.acceptButton);
        this.refuse = (Button) view.findViewById(R.id.refuseButton);
        this.info = (Button) view.findViewById(R.id.infoButton);
    }

    private void registerRequestIntoView(){
       Group group = this.request.getGroup();
        if(group != null){
            this.groupName.setText(group.getName());
            this.message.setText(request.getMessage());
            this.participantsLeftoverNumber.setText("");
            int i = 0;
            /*if(group.getMembers().size() > i){
                //this.participant1 = group.getMembers().get(i); setImage
                i++;
            }
            if(group.getMembers().size() > i){
                //this.participant2 = group.getMembers().get(i); setImage
                i++;
            }
            if(group.getMembers().size() > i){
                //this.participant3 = group.getMembers().get(i); setImage
                i++;
            }
            if(group.getMembers().size() > i){
                //this.participant4 = group.getMembers().get(i); setImage
                i++;
            }
            if(group.getMembers().size() > i){
                //this.participant5 = group.getMembers().get(i); setImage
                i++;
            }
            if(group.getMembers().size() > i){
                //this.participant6 = group.getMembers().get(i); setImage
                i++;
            }
            if(group.getMembers().size() > i){
                this.participantsLeftoverNumber.setText("+"+ (group.getMembers().size() - i));
            }*/


            //this.requestSender. //set profile picture of request.getSender()
            this.requestSender.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //TODO switch to user (the sender) view
                }
            });

            accept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    request.acceptRequest();
                    removeFromView();
                }
            });

            refuse.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    request.refuseRequest();
                    removeFromView();
                }
            });

            info.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //TODO switch to event view
                }
            });
        }
    }
}