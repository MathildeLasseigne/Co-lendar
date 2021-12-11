package fr.ups.co_lendar.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import fr.ups.co_lendar.R;

public class groupDisplayFragment extends Fragment implements View.OnClickListener{
    ArrayList<String> listItems=new ArrayList<String>();
    String groupName = "GroupName";
    Integer id = 0;
    private View mView;

    public groupDisplayFragment() {
        // Required empty public constructor
    }

    public void setGroupId(Integer newId) {
        id = newId;
    }

    public Integer getGroupId() {
        return id;
    }

    public void setParameters(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupName() {
        return groupName;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_group_display, container, false);
        mView.setOnClickListener(this);
        return mView;
    }

    @Override
    public void onClick(View v) {
        Intent i = new Intent(getActivity(), GroupViewFragment.class);
        startActivity(i);
    }
}