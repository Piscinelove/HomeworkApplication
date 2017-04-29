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


public class AddHomeworkFragment extends Fragment {

    private Button saveButton;
    private DatabaseHelper db;
    private Fragment fragment;
    private FragmentManager fragmentManager;


    public AddHomeworkFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.addactionbar, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.action_bar_back:
                getActivity().getSupportFragmentManager().popBackStack();
                return true;

            case R.id.ab_save:

                EditText name = (EditText) getView().findViewById(R.id.et_add_homework_name);
                EditText date = (EditText) getView().findViewById(R.id.et_add_homework_date);
                Spinner course = (Spinner) getView().findViewById(R.id.sp_add_homework_course);
                CheckBox done = (CheckBox) getView().findViewById(R.id.cb_add_homework_done);
                EditText description = (EditText) getView().findViewById(R.id.et_add_homework_description);



                if(TextUtils.isEmpty(name.getText().toString())) {
                    name.setError("Name field cannot be empty");
                    return false;
                }

                if(TextUtils.isEmpty(date.getText().toString())) {
                    date.setError("Date field cannot be empty");
                    return false;
                }

                if(course.getSelectedItem()==null) {
                    TextView spinnerText = (TextView) course.getSelectedView();
                    spinnerText.setError("Course field cannot be empty");
                    return false;
                }


                db = new DatabaseHelper(getActivity().getApplicationContext());
                db.insertHomework(name.getText().toString(),date.getText().toString(),true,description.getText().toString(),1);

                fragmentManager = getActivity().getSupportFragmentManager();
                fragment = new HomeworkFragment();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.addToBackStack(null);
                transaction.replace(R.id.main_container, fragment).commit();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_add_homework, container, false);
        setHasOptionsMenu(true);

        return  rootView;
    }
}
