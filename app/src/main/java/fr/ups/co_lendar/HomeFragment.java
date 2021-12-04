package fr.ups.co_lendar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.*;

public class HomeFragment extends Fragment implements View.OnClickListener {

    private View mView;
    private TextView sun;
    private TextView mon;
    private TextView tue;
    private TextView wed;
    private TextView thu;
    private TextView fri;
    private TextView sat;
    private TextView day1;
    private TextView day2;
    private TextView day3;
    private TextView day4;
    private TextView day5;
    private TextView day6;
    private TextView day7;



    public HomeFragment(){
        // require a empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_home, container,false);
        sun = (TextView) mView.findViewById((R.id.sun));
        sun.setOnClickListener(this);
        mon = (TextView) mView.findViewById((R.id.mon));
        mon.setOnClickListener(this);
        tue = (TextView) mView.findViewById((R.id.tue));
        tue.setOnClickListener(this);
        wed = (TextView) mView.findViewById((R.id.wed));
        wed.setOnClickListener(this);
        thu = (TextView) mView.findViewById((R.id.thu));
        thu.setOnClickListener(this);
        fri = (TextView) mView.findViewById((R.id.fri));
        fri.setOnClickListener(this);
        sat = (TextView) mView.findViewById((R.id.sat));
        sat.setOnClickListener(this);
        day1 = (TextView) mView.findViewById((R.id.day1));
        day1.setOnClickListener(this);
        day2 = (TextView) mView.findViewById((R.id.day2));
        day2.setOnClickListener(this);
        day3 = (TextView) mView.findViewById((R.id.day3));
        day3.setOnClickListener(this);
        day4 = (TextView) mView.findViewById((R.id.day4));
        day4.setOnClickListener(this);
        day5 = (TextView) mView.findViewById((R.id.day5));
        day5.setOnClickListener(this);
        day6 = (TextView) mView.findViewById((R.id.day6));
        day6.setOnClickListener(this);
        day7 = (TextView) mView.findViewById((R.id.day7));
        day7.setOnClickListener(this);

        return mView;
    }


    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.sun:
            case R.id.day1:
                sun.setBackgroundColor(getResources().getColor(R.color.colorGrey));
                day1.setBackgroundColor(getResources().getColor(R.color.colorGrey));
                mon.setBackgroundColor(getResources().getColor(R.color.colorDarkBackground));
                day2.setBackgroundColor(getResources().getColor(R.color.colorDarkBackground));
                tue.setBackgroundColor(getResources().getColor(R.color.colorDarkBackground));
                day3.setBackgroundColor(getResources().getColor(R.color.colorDarkBackground));
                wed.setBackgroundColor(getResources().getColor(R.color.colorDarkBackground));
                day4.setBackgroundColor(getResources().getColor(R.color.colorDarkBackground));
                thu.setBackgroundColor(getResources().getColor(R.color.colorDarkBackground));
                day5.setBackgroundColor(getResources().getColor(R.color.colorDarkBackground));
                fri.setBackgroundColor(getResources().getColor(R.color.colorDarkBackground));
                day6.setBackgroundColor(getResources().getColor(R.color.colorDarkBackground));
                sat.setBackgroundColor(getResources().getColor(R.color.colorDarkBackground));
                day7.setBackgroundColor(getResources().getColor(R.color.colorDarkBackground));

                sun.setTextColor(getResources().getColor(R.color.black));
                day1.setTextColor(getResources().getColor(R.color.black));
                mon.setTextColor(getResources().getColor(R.color.colorGrey));
                day2.setTextColor(getResources().getColor(R.color.colorGrey));
                tue.setTextColor(getResources().getColor(R.color.colorGrey));
                day3.setTextColor(getResources().getColor(R.color.colorGrey));
                wed.setTextColor(getResources().getColor(R.color.colorGrey));
                day4.setTextColor(getResources().getColor(R.color.colorGrey));
                thu.setTextColor(getResources().getColor(R.color.colorGrey));
                day5.setTextColor(getResources().getColor(R.color.colorGrey));
                fri.setTextColor(getResources().getColor(R.color.colorGrey));
                day6.setTextColor(getResources().getColor(R.color.colorGrey));
                sat.setTextColor(getResources().getColor(R.color.colorGrey));
                day7.setTextColor(getResources().getColor(R.color.colorGrey));
                break;

            case R.id.mon:
            case R.id.day2:
                sun.setBackgroundColor(getResources().getColor(R.color.colorDarkBackground));
                day1.setBackgroundColor(getResources().getColor(R.color.colorDarkBackground));
                mon.setBackgroundColor(getResources().getColor(R.color.colorGrey));
                day2.setBackgroundColor(getResources().getColor(R.color.colorGrey));
                tue.setBackgroundColor(getResources().getColor(R.color.colorDarkBackground));
                day3.setBackgroundColor(getResources().getColor(R.color.colorDarkBackground));
                wed.setBackgroundColor(getResources().getColor(R.color.colorDarkBackground));
                day4.setBackgroundColor(getResources().getColor(R.color.colorDarkBackground));
                thu.setBackgroundColor(getResources().getColor(R.color.colorDarkBackground));
                day5.setBackgroundColor(getResources().getColor(R.color.colorDarkBackground));
                fri.setBackgroundColor(getResources().getColor(R.color.colorDarkBackground));
                day6.setBackgroundColor(getResources().getColor(R.color.colorDarkBackground));
                sat.setBackgroundColor(getResources().getColor(R.color.colorDarkBackground));
                day7.setBackgroundColor(getResources().getColor(R.color.colorDarkBackground));

                sun.setTextColor(getResources().getColor(R.color.colorGrey));
                day1.setTextColor(getResources().getColor(R.color.colorGrey));
                mon.setTextColor(getResources().getColor(R.color.black));
                day2.setTextColor(getResources().getColor(R.color.black));
                tue.setTextColor(getResources().getColor(R.color.colorGrey));
                day3.setTextColor(getResources().getColor(R.color.colorGrey));
                wed.setTextColor(getResources().getColor(R.color.colorGrey));
                day4.setTextColor(getResources().getColor(R.color.colorGrey));
                thu.setTextColor(getResources().getColor(R.color.colorGrey));
                day5.setTextColor(getResources().getColor(R.color.colorGrey));
                fri.setTextColor(getResources().getColor(R.color.colorGrey));
                day6.setTextColor(getResources().getColor(R.color.colorGrey));
                sat.setTextColor(getResources().getColor(R.color.colorGrey));
                day7.setTextColor(getResources().getColor(R.color.colorGrey));
                break;

            case R.id.tue:
            case R.id.day3:
                sun.setBackgroundColor(getResources().getColor(R.color.colorDarkBackground));
                day1.setBackgroundColor(getResources().getColor(R.color.colorDarkBackground));
                mon.setBackgroundColor(getResources().getColor(R.color.colorDarkBackground));
                day2.setBackgroundColor(getResources().getColor(R.color.colorDarkBackground));
                tue.setBackgroundColor(getResources().getColor(R.color.colorGrey));
                day3.setBackgroundColor(getResources().getColor(R.color.colorGrey));
                wed.setBackgroundColor(getResources().getColor(R.color.colorDarkBackground));
                day4.setBackgroundColor(getResources().getColor(R.color.colorDarkBackground));
                thu.setBackgroundColor(getResources().getColor(R.color.colorDarkBackground));
                day5.setBackgroundColor(getResources().getColor(R.color.colorDarkBackground));
                fri.setBackgroundColor(getResources().getColor(R.color.colorDarkBackground));
                day6.setBackgroundColor(getResources().getColor(R.color.colorDarkBackground));
                sat.setBackgroundColor(getResources().getColor(R.color.colorDarkBackground));
                day7.setBackgroundColor(getResources().getColor(R.color.colorDarkBackground));

                sun.setTextColor(getResources().getColor(R.color.colorGrey));
                day1.setTextColor(getResources().getColor(R.color.colorGrey));
                mon.setTextColor(getResources().getColor(R.color.colorGrey));
                day2.setTextColor(getResources().getColor(R.color.colorGrey));
                tue.setTextColor(getResources().getColor(R.color.black));
                day3.setTextColor(getResources().getColor(R.color.black));
                wed.setTextColor(getResources().getColor(R.color.colorGrey));
                day4.setTextColor(getResources().getColor(R.color.colorGrey));
                thu.setTextColor(getResources().getColor(R.color.colorGrey));
                day5.setTextColor(getResources().getColor(R.color.colorGrey));
                fri.setTextColor(getResources().getColor(R.color.colorGrey));
                day6.setTextColor(getResources().getColor(R.color.colorGrey));
                sat.setTextColor(getResources().getColor(R.color.colorGrey));
                day7.setTextColor(getResources().getColor(R.color.colorGrey));
                break;

            case R.id.wed:
            case R.id.day4:
                sun.setBackgroundColor(getResources().getColor(R.color.colorDarkBackground));
                day1.setBackgroundColor(getResources().getColor(R.color.colorDarkBackground));
                mon.setBackgroundColor(getResources().getColor(R.color.colorDarkBackground));
                day2.setBackgroundColor(getResources().getColor(R.color.colorDarkBackground));
                tue.setBackgroundColor(getResources().getColor(R.color.colorDarkBackground));
                day3.setBackgroundColor(getResources().getColor(R.color.colorDarkBackground));
                wed.setBackgroundColor(getResources().getColor(R.color.colorGrey));
                day4.setBackgroundColor(getResources().getColor(R.color.colorGrey));
                thu.setBackgroundColor(getResources().getColor(R.color.colorDarkBackground));
                day5.setBackgroundColor(getResources().getColor(R.color.colorDarkBackground));
                fri.setBackgroundColor(getResources().getColor(R.color.colorDarkBackground));
                day6.setBackgroundColor(getResources().getColor(R.color.colorDarkBackground));
                sat.setBackgroundColor(getResources().getColor(R.color.colorDarkBackground));
                day7.setBackgroundColor(getResources().getColor(R.color.colorDarkBackground));

                sun.setTextColor(getResources().getColor(R.color.colorGrey));
                day1.setTextColor(getResources().getColor(R.color.colorGrey));
                mon.setTextColor(getResources().getColor(R.color.colorGrey));
                day2.setTextColor(getResources().getColor(R.color.colorGrey));
                tue.setTextColor(getResources().getColor(R.color.colorGrey));
                day3.setTextColor(getResources().getColor(R.color.colorGrey));
                wed.setTextColor(getResources().getColor(R.color.black));
                day4.setTextColor(getResources().getColor(R.color.black));
                thu.setTextColor(getResources().getColor(R.color.colorGrey));
                day5.setTextColor(getResources().getColor(R.color.colorGrey));
                fri.setTextColor(getResources().getColor(R.color.colorGrey));
                day6.setTextColor(getResources().getColor(R.color.colorGrey));
                sat.setTextColor(getResources().getColor(R.color.colorGrey));
                day7.setTextColor(getResources().getColor(R.color.colorGrey));
                break;

            case R.id.thu:
            case R.id.day5:
                sun.setBackgroundColor(getResources().getColor(R.color.colorDarkBackground));
                day1.setBackgroundColor(getResources().getColor(R.color.colorDarkBackground));
                mon.setBackgroundColor(getResources().getColor(R.color.colorDarkBackground));
                day2.setBackgroundColor(getResources().getColor(R.color.colorDarkBackground));
                tue.setBackgroundColor(getResources().getColor(R.color.colorDarkBackground));
                day3.setBackgroundColor(getResources().getColor(R.color.colorDarkBackground));
                wed.setBackgroundColor(getResources().getColor(R.color.colorDarkBackground));
                day4.setBackgroundColor(getResources().getColor(R.color.colorDarkBackground));
                thu.setBackgroundColor(getResources().getColor(R.color.colorGrey));
                day5.setBackgroundColor(getResources().getColor(R.color.colorGrey));
                fri.setBackgroundColor(getResources().getColor(R.color.colorDarkBackground));
                day6.setBackgroundColor(getResources().getColor(R.color.colorDarkBackground));
                sat.setBackgroundColor(getResources().getColor(R.color.colorDarkBackground));
                day7.setBackgroundColor(getResources().getColor(R.color.colorDarkBackground));

                sun.setTextColor(getResources().getColor(R.color.colorGrey));
                day1.setTextColor(getResources().getColor(R.color.colorGrey));
                mon.setTextColor(getResources().getColor(R.color.colorGrey));
                day2.setTextColor(getResources().getColor(R.color.colorGrey));
                tue.setTextColor(getResources().getColor(R.color.colorGrey));
                day3.setTextColor(getResources().getColor(R.color.colorGrey));
                wed.setTextColor(getResources().getColor(R.color.colorGrey));
                day4.setTextColor(getResources().getColor(R.color.colorGrey));
                thu.setTextColor(getResources().getColor(R.color.black));
                day5.setTextColor(getResources().getColor(R.color.black));
                fri.setTextColor(getResources().getColor(R.color.colorGrey));
                day6.setTextColor(getResources().getColor(R.color.colorGrey));
                sat.setTextColor(getResources().getColor(R.color.colorGrey));
                day7.setTextColor(getResources().getColor(R.color.colorGrey));
                break;

            case R.id.fri:
            case R.id.day6:
                sun.setBackgroundColor(getResources().getColor(R.color.colorDarkBackground));
                day1.setBackgroundColor(getResources().getColor(R.color.colorDarkBackground));
                mon.setBackgroundColor(getResources().getColor(R.color.colorDarkBackground));
                day2.setBackgroundColor(getResources().getColor(R.color.colorDarkBackground));
                tue.setBackgroundColor(getResources().getColor(R.color.colorDarkBackground));
                day3.setBackgroundColor(getResources().getColor(R.color.colorDarkBackground));
                wed.setBackgroundColor(getResources().getColor(R.color.colorDarkBackground));
                day4.setBackgroundColor(getResources().getColor(R.color.colorDarkBackground));
                thu.setBackgroundColor(getResources().getColor(R.color.colorDarkBackground));
                day5.setBackgroundColor(getResources().getColor(R.color.colorDarkBackground));
                fri.setBackgroundColor(getResources().getColor(R.color.colorGrey));
                day6.setBackgroundColor(getResources().getColor(R.color.colorGrey));
                sat.setBackgroundColor(getResources().getColor(R.color.colorDarkBackground));
                day7.setBackgroundColor(getResources().getColor(R.color.colorDarkBackground));

                sun.setTextColor(getResources().getColor(R.color.colorGrey));
                day1.setTextColor(getResources().getColor(R.color.colorGrey));
                mon.setTextColor(getResources().getColor(R.color.colorGrey));
                day2.setTextColor(getResources().getColor(R.color.colorGrey));
                tue.setTextColor(getResources().getColor(R.color.colorGrey));
                day3.setTextColor(getResources().getColor(R.color.colorGrey));
                wed.setTextColor(getResources().getColor(R.color.colorGrey));
                day4.setTextColor(getResources().getColor(R.color.colorGrey));
                thu.setTextColor(getResources().getColor(R.color.colorGrey));
                day5.setTextColor(getResources().getColor(R.color.colorGrey));
                fri.setTextColor(getResources().getColor(R.color.black));
                day6.setTextColor(getResources().getColor(R.color.black));
                sat.setTextColor(getResources().getColor(R.color.colorGrey));
                day7.setTextColor(getResources().getColor(R.color.colorGrey));
                break;

            case R.id.sat:
            case R.id.day7:
                sun.setBackgroundColor(getResources().getColor(R.color.colorDarkBackground));
                day1.setBackgroundColor(getResources().getColor(R.color.colorDarkBackground));
                mon.setBackgroundColor(getResources().getColor(R.color.colorDarkBackground));
                day2.setBackgroundColor(getResources().getColor(R.color.colorDarkBackground));
                tue.setBackgroundColor(getResources().getColor(R.color.colorDarkBackground));
                day3.setBackgroundColor(getResources().getColor(R.color.colorDarkBackground));
                wed.setBackgroundColor(getResources().getColor(R.color.colorDarkBackground));
                day4.setBackgroundColor(getResources().getColor(R.color.colorDarkBackground));
                thu.setBackgroundColor(getResources().getColor(R.color.colorDarkBackground));
                day5.setBackgroundColor(getResources().getColor(R.color.colorDarkBackground));
                fri.setBackgroundColor(getResources().getColor(R.color.colorDarkBackground));
                day6.setBackgroundColor(getResources().getColor(R.color.colorDarkBackground));
                sat.setBackgroundColor(getResources().getColor(R.color.colorGrey));
                day7.setBackgroundColor(getResources().getColor(R.color.colorGrey));

                sun.setTextColor(getResources().getColor(R.color.colorGrey));
                day1.setTextColor(getResources().getColor(R.color.colorGrey));
                mon.setTextColor(getResources().getColor(R.color.colorGrey));
                day2.setTextColor(getResources().getColor(R.color.colorGrey));
                tue.setTextColor(getResources().getColor(R.color.colorGrey));
                day3.setTextColor(getResources().getColor(R.color.colorGrey));
                wed.setTextColor(getResources().getColor(R.color.colorGrey));
                day4.setTextColor(getResources().getColor(R.color.colorGrey));
                thu.setTextColor(getResources().getColor(R.color.colorGrey));
                day5.setTextColor(getResources().getColor(R.color.colorGrey));
                fri.setTextColor(getResources().getColor(R.color.colorGrey));
                day6.setTextColor(getResources().getColor(R.color.colorGrey));
                sat.setTextColor(getResources().getColor(R.color.black));
                day7.setTextColor(getResources().getColor(R.color.black));
                break;

        }
    }
}