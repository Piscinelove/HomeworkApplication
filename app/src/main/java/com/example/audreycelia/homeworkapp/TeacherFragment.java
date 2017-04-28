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
import db.Teacher;
import db.TeachersListAdapter;

public class TeacherFragment extends Fragment {
    private ListView listView;
    private DatabaseHelper db;
    private ImageButton addButton;
    private Fragment fragment;
    private FragmentManager fragmentManager;

    public  TeacherFragment()
    {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.fragment_teacher, container, false);
        //fill the list view with the db
        db = new DatabaseHelper(getActivity().getApplicationContext());
        ArrayList<Teacher> listTeachers = db.getAllTeachers();
        listView = (ListView) rootView.findViewById(R.id.listTeachers);
        listView.setAdapter(new TeachersListAdapter(getActivity().getApplicationContext(), listTeachers));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int teacherId = ((Teacher) listView.getItemAtPosition(position)).getTeacherId();
                System.out.println("ID TEACHER = "+teacherId);
                Bundle bundle = new Bundle();
                bundle.putInt("SelectedTeacherId", teacherId);
                fragmentManager = getActivity().getSupportFragmentManager();
                fragment = new EditTeacherFragment();
                fragment.setArguments(bundle);
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.addToBackStack(null);
                transaction.replace(R.id.main_container, fragment).commit();

            }
        });


        //add button
        addButton = (ImageButton) rootView.findViewById(R.id.ib_plus_teacher);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager = getActivity().getSupportFragmentManager();
                fragment = new AddTeacherFragment();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.addToBackStack(null);
                transaction.replace(R.id.main_container, fragment).commit();
            }
        });

        return  rootView;
    }

}


