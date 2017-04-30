package com.example.audreycelia.homeworkapp;

import android.app.DatePickerDialog;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import db.Course;
import db.DatabaseHelper;


public class AddHomeworkFragment extends Fragment {

    private Button saveButton;
    private DatabaseHelper db;
    private Fragment fragment;
    private FragmentManager fragmentManager;
    private DatePickerDialog datePickerDialog;
    private int hour;
    private int minute;
    private int month;
    private int year;
    private int day;


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
                //transform date format for correct handling in db
                SimpleDateFormat dateFormatin = new SimpleDateFormat("dd.MM.yyyy");
                SimpleDateFormat dateFormatout = new SimpleDateFormat("yyyyMMdd");
                String examDate ="";
                Date dateTime;
                try
                {
                    dateTime = dateFormatin.parse(date.getText().toString());
                    examDate = dateFormatout.format(dateTime);
                } catch (ParseException e) {
                    e.printStackTrace();
                }


                db = new DatabaseHelper(getActivity().getApplicationContext());
                db.insertHomework(name.getText().toString(),examDate,true,description.getText().toString(),1);

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

        final EditText date = (EditText) rootView.findViewById(R.id.et_add_homework_date);
        Spinner course = (Spinner) rootView.findViewById(R.id.sp_add_homework_course);

        //Fill spinner from database
        db = new DatabaseHelper(getActivity().getApplicationContext());
        ArrayList<Course> courses = db.getAllCourses();
        ArrayAdapter<Course> dataAdapter = new ArrayAdapter<Course>(getActivity(), android.R.layout.simple_spinner_item, courses);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        course.setAdapter(dataAdapter);

        //Date picker for date
        date.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                //Get current time
                final Calendar c = Calendar.getInstance();
                month = c.get(Calendar.MONTH);
                year = c.get(Calendar.YEAR);
                day = c.get(Calendar.DAY_OF_MONTH);

                //Launch date Picker Dialog
                datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
                        Date dateTime;
                        try {
                            month++;
                            dateTime = dateFormat.parse(dayOfMonth+"."+month+"."+year);
                            dateFormat.format(dateTime);
                            String courseDate = dateFormat.format(dateTime);

                            date.setText(courseDate);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                    }
                },year,month, day);
                datePickerDialog.show();


            }
        });

        return  rootView;
    }
}
