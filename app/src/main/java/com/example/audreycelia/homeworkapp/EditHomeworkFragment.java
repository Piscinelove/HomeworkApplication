package com.example.audreycelia.homeworkapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import db.DatabaseHelper;
import db.Homework;


public class EditHomeworkFragment extends Fragment {
    private DatabaseHelper db;
    private Fragment fragment;
    private FragmentManager fragmentManager;


    public EditHomeworkFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.actionbar, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.action_bar_back:
                getActivity().getSupportFragmentManager().popBackStack();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_edit_teacher, container, false);
        setHasOptionsMenu(true);

        final int homeworkId = getArguments().getInt("SelectedHomeworkId");
        db = new DatabaseHelper(getActivity().getApplicationContext());
        final Homework homework = db.getHomeworkFromId(homeworkId);


        final EditText name = (EditText) rootView.findViewById(R.id.et_add_homework_name);
        final EditText date = (EditText) rootView.findViewById(R.id.et_add_homework_date);
        final Spinner course = (Spinner) rootView.findViewById(R.id.sp_add_homework_course);
        final CheckBox done = (CheckBox) rootView.findViewById(R.id.cb_add_homework_done);
        final EditText description = (EditText) rootView.findViewById(R.id.et_add_homework_description);


        final Button editButton = (Button) rootView.findViewById(R.id.bt_add_homework_save);
        final Button saveButton = (Button) rootView.findViewById(R.id.buttonSave_edit_homework);
        final Button deleteButton = (Button) rootView.findViewById(R.id.buttonDelete_edit_homework);

        name.setText(homework.getName());
        date.setText(homework.getDeadline());

        description.setText(homework.getDescription());

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveButton.setVisibility(View.VISIBLE);
                deleteButton.setVisibility(View.VISIBLE);
                editButton.setVisibility(View.INVISIBLE);
                name.setEnabled(true);
                date.setEnabled(true);
                course.setEnabled(true);
                done.setEnabled(true);
                description.setEnabled(true);

                saveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(TextUtils.isEmpty(name.getText().toString())) {
                            name.setError("Name field cannot be empty");
                            return;
                        }

                        if(TextUtils.isEmpty(date.getText().toString())) {
                            date.setError("Date field cannot be empty");
                            return;
                        }

                        if(course.getSelectedItem()==null) {
                            TextView spinnerText = (TextView) course.getSelectedView();
                            spinnerText.setError("Course field cannot be empty");
                            return;
                        }

                        db.updateHomework(homeworkId,name.getText().toString(),date.getText().toString(),true,description.getText().toString(),1);
                        saveButton.setVisibility(View.INVISIBLE);
                        deleteButton.setVisibility(View.INVISIBLE);
                        editButton.setVisibility(View.VISIBLE);

                    }
                });

                deleteButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        db.deleteHomework(homeworkId);
                        saveButton.setVisibility(View.INVISIBLE);
                        deleteButton.setVisibility(View.INVISIBLE);
                        editButton.setVisibility(View.VISIBLE);

                        fragmentManager = getActivity().getSupportFragmentManager();
                        fragment = new HomeworkFragment();
                        FragmentTransaction transaction = fragmentManager.beginTransaction();
                        transaction.addToBackStack(null);
                        transaction.replace(R.id.main_container, fragment).commit();

                    }
                });
            }
        });



        return rootView;
    }


}
