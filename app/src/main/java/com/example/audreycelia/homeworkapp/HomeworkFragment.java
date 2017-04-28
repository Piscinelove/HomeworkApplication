package com.example.audreycelia.homeworkapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;

import db.DatabaseHelper;
import db.Homework;
import db.HomeworksListAdapter;


public class HomeworkFragment extends Fragment {

    private ListView listView;
    private DatabaseHelper db;
    private ImageButton addButton;
    private Fragment fragment;
    private FragmentManager fragmentManager;

    public  HomeworkFragment()
    {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.fragment_homework, container, false);

        db = new DatabaseHelper(getActivity().getApplicationContext());

        ArrayList<Homework> listHomeworks = db.getAllHomeworks();
        listView = (ListView) rootView.findViewById(R.id.listHomeworks);
        listView.setAdapter(new HomeworksListAdapter(getActivity().getApplicationContext(), listHomeworks));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int homeworkId = ((Homework) listView.getItemAtPosition(position)).getHomeworkId();
                System.out.println("ID HOMEWORK = "+homeworkId);
                Bundle bundle = new Bundle();
                bundle.putInt("SelectedHomeworkId", homeworkId);
                fragmentManager = getActivity().getSupportFragmentManager();
                fragment = new EditHomeworkFragment();
                fragment.setArguments(bundle);
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.addToBackStack(null);
                transaction.replace(R.id.main_container, fragment).commit();

            }
        });

        //add button
        addButton = (ImageButton) rootView.findViewById(R.id.ib_plus_homework);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager = getActivity().getSupportFragmentManager();
                fragment = new AddHomeworkFragment();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.addToBackStack(null);
                transaction.replace(R.id.main_container, fragment).commit();
            }
        });



        return  rootView;
    }




}
