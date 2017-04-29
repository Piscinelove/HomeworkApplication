package com.example.audreycelia.homeworkapp;

import android.app.TimePickerDialog;
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
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.android.colorpicker.ColorPickerDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import db.Course;
import db.DatabaseHelper;


public class AddExamFragment extends Fragment {

    private Button saveButton;
    private DatabaseHelper db;
    private Fragment fragment;
    private FragmentManager fragmentManager;
    private TimePickerDialog timePickerDialog;
    private int hour;
    private int minute;

    public AddExamFragment() {
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
            EditText name = (EditText) getView().findViewById(R.id.et_add_exam_name);
            EditText date = (EditText) getView().findViewById(R.id.et_add_exam_date);
            EditText from = (EditText) getView().findViewById(R.id.et_add_exam_from);
            EditText until = (EditText) getView().findViewById(R.id.et_add_exam_until);
            Spinner course = (Spinner) getView().findViewById(R.id.sp_add_exam_course);
            EditText room = (EditText) getView().findViewById(R.id.et_add_exam_room);
            EditText grade = (EditText) getView().findViewById(R.id.et_add_exam_grade);
            EditText description = (EditText) getView().findViewById(R.id.et_add_exam_description);




            if(TextUtils.isEmpty(name.getText().toString())) {
                name.setError("Name field cannot be empty");
                return false;
            }

            if(TextUtils.isEmpty(date.getText().toString())) {
                date.setError("Date field cannot be empty");
                return false;
            }

            if(TextUtils.isEmpty(from.getText().toString())) {
                from.setError("From field cannot be empty");
                return false;
            }

            if(TextUtils.isEmpty(until.getText().toString())) {
                from.setError("Until field cannot be empty");
                return false;
            }

            if(course.getSelectedItem()==null) {
                TextView spinnerText = (TextView) course.getSelectedView();
                spinnerText.setError("Course field cannot be empty");
                return false;
            }

            if(TextUtils.isEmpty(room.getText().toString())) {
                room.setError("Room field cannot be empty");
                return false;
            }


            db = new DatabaseHelper(getActivity().getApplicationContext());
            db.insertExam(name.getText().toString(),date.getText().toString(),from.getText().toString(),until.getText().toString(), Double.parseDouble(grade.getText().toString()), Integer.parseInt(room.getText().toString()),description.getText().toString(),1);

            fragmentManager = getActivity().getSupportFragmentManager();
            fragment = new ExamFragment();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.addToBackStack(null);
            transaction.replace(R.id.main_container, fragment).commit();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_add_exam, container, false);
        setHasOptionsMenu(true);

        final EditText date = (EditText) rootView.findViewById(R.id.et_add_exam_date);
        final EditText from = (EditText) rootView.findViewById(R.id.et_add_exam_from);
        final EditText until = (EditText) rootView.findViewById(R.id.et_add_exam_until);
        Spinner course = (Spinner) rootView.findViewById(R.id.sp_add_exam_course);

        //Fill spinner from database
        db = new DatabaseHelper(getActivity().getApplicationContext());
        ArrayList<Course> courses = db.getAllCourses();
        ArrayAdapter<Course> dataAdapter = new ArrayAdapter<Course>(getActivity(), android.R.layout.simple_spinner_item, courses);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        course.setAdapter(dataAdapter);


        //Time picker for from time
        from.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Get current time
                final Calendar c = Calendar.getInstance();
                hour = c.get(Calendar.HOUR_OF_DAY);
                minute = c.get(Calendar.MINUTE);

                //Launch Time Picker Dialog
                timePickerDialog = new TimePickerDialog(getActivity(),new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute)
                    {

                        SimpleDateFormat hourFormatin = new SimpleDateFormat("K:mm");
                        SimpleDateFormat hourFormatout = new SimpleDateFormat("KK:mm");
                        Date date;
                        try {
                            date = hourFormatin.parse(hourOfDay+":"+minute);
                            hourFormatout.format(date);
                            String hour = hourFormatout.format(date);
                            from.setText(hour);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }



                    }
                },hour,minute,true);
                timePickerDialog.show();
            }});

        //Time picker for until time
        until.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Get current time
                final Calendar c = Calendar.getInstance();
                hour = c.get(Calendar.HOUR_OF_DAY);
                minute = c.get(Calendar.MINUTE);

                //Launch Time Picker Dialog
                timePickerDialog = new TimePickerDialog(getActivity(),new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,int minute)
                    {

                        until.setText(hourOfDay + ":" + minute);
                    }
                },hour,minute,true);
                timePickerDialog.show();
            }});

        return  rootView;
    }
}
