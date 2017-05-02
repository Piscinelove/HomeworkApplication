package com.example.audreycelia.homeworkapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;


public class CalendarFragment extends Fragment {

    private CalendarView calendar ;

    public CalendarFragment(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //set the title on the app
        getActivity().setTitle(R.string.app_name);
        return inflater.inflate(R.layout.fragment_calendar, container, false);


    }
}
