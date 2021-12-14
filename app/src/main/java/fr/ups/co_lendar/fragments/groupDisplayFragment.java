package fr.ups.co_lendar.fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import fr.ups.co_lendar.R;
import fr.ups.co_lendar.helpers.Group;

public class groupDisplayFragment extends Fragment {
    private Group group;

    private View mView;

    public groupDisplayFragment() {
        // Required empty public constructor
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group g) {
        this.group = g;
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

        return mView;
    }

}