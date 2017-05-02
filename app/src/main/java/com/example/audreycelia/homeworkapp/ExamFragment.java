package com.example.audreycelia.homeworkapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;

import db.DatabaseHelper;
import db.Exam;
import db.ExamsListAdapter;


public class ExamFragment extends Fragment {

    private ListView listView;
    private DatabaseHelper db;
    private ImageButton addButton;
    private Fragment fragment;
    private FragmentManager fragmentManager;


    public ExamFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.settings, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_exam, container, false);

        //to see the menu on the top
        setHasOptionsMenu(true);

        //set the title on the app
        getActivity().setTitle(R.string.title_exam);

        db = new DatabaseHelper(getActivity().getApplicationContext());


        ArrayList<Exam> listExams = db.getAllExams();
        listView = (ListView) rootView.findViewById(R.id.listExams);
        listView.setAdapter(new ExamsListAdapter(getActivity().getApplicationContext(), listExams));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int examId = ((Exam) listView.getItemAtPosition(position)).getExamId();
                System.out.println("ID EXAM = "+examId);
                Bundle bundle = new Bundle();
                bundle.putInt("SelectedExamId", examId);
                fragmentManager = getActivity().getSupportFragmentManager();
                fragment = new EditExamFragment();
                fragment.setArguments(bundle);
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.addToBackStack(null);
                transaction.replace(R.id.main_container, fragment).commit();
            }
        });

        //add button
        addButton = (ImageButton) rootView.findViewById(R.id.ib_plus_exam);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager = getActivity().getSupportFragmentManager();
                fragment = new AddExamFragment();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.addToBackStack(null);
                transaction.replace(R.id.main_container, fragment).commit();
            }
        });


        return  rootView;
    }



}
