package com.example.audreycelia.homeworkapp;

import android.app.TimePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
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
import android.widget.Toast;


import com.android.colorpicker.ColorPickerDialog;
import com.android.colorpicker.ColorPickerSwatch;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import db.Course;
import db.DatabaseHelper;
import db.Teacher;


public class AddCourseFragment extends Fragment {
    private Button saveButton;
    private DatabaseHelper db;
    private Fragment fragment;
    private FragmentManager fragmentManager;

    private TimePickerDialog timePickerDialog;
    private ColorPickerDialog colorPickerDialog;
    private int hour;
    private int minute;
    //



    public AddCourseFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_add_course, container, false);
        setHasOptionsMenu(true);
        final EditText from = (EditText) rootView.findViewById(R.id.et_add_course_from);
        final EditText until = (EditText) rootView.findViewById(R.id.et_add_course_until);
        final Button colorButton = (Button) rootView.findViewById(R.id.bt_add_course_color);
        Spinner teacher = (Spinner) rootView.findViewById(R.id.sp_add_course_teacher);



        //Fill spinner from database
        db = new DatabaseHelper(getActivity().getApplicationContext());
        ArrayList<Teacher> teachers = db.getAllTeachers();
        ArrayAdapter<Teacher> dataAdapter = new ArrayAdapter<Teacher>(getActivity(), android.R.layout.simple_spinner_item, teachers);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        teacher.setAdapter(dataAdapter);

        //Color Picker
        colorPickerDialog = new ColorPickerDialog();
        int[] colors = {ContextCompat.getColor(getActivity(),R.color.primary1),
                ContextCompat.getColor(getActivity(),R.color.primary2),
                ContextCompat.getColor(getActivity(),R.color.primary3),
                ContextCompat.getColor(getActivity(),R.color.primary4),
                ContextCompat.getColor(getActivity(),R.color.primary5),
                ContextCompat.getColor(getActivity(),R.color.primary6),
                ContextCompat.getColor(getActivity(),R.color.primary7),
                ContextCompat.getColor(getActivity(),R.color.primary8),
                ContextCompat.getColor(getActivity(),R.color.primary9)};
        colorPickerDialog.initialize(R.string.colorChange,colors, colors[1], 3, colors.length);


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
                    public void onTimeSet(TimePicker view, int hourOfDay,int minute)
                    {

                        String hour = formatTimeString(hourOfDay+":"+minute);
                        from.setText(hour);

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

                        String hour = formatTimeString(hourOfDay+":"+minute);
                        until.setText(hour);
                    }
                },hour,minute,true);
                timePickerDialog.show();
            }});

        //Color picker when click on color button
        colorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                colorPickerDialog.show(getActivity().getFragmentManager(), "test");
                colorPickerDialog.setOnColorSelectedListener(new ColorPickerSwatch.OnColorSelectedListener() {
                    @Override
                    public void onColorSelected(int color) {
                        colorButton.setBackgroundColor(color);
                        colorButton.setTextColor(Color.WHITE);
                        //change la couleur actuellement sélectionnée
                        colorPickerDialog.setSelectedColor(color);

                    }
                });
            }
        });

        return  rootView;
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
            //When user click button save on toolbar
            case R.id.ab_save:
                EditText name = (EditText) getView().findViewById(R.id.et_add_course_name);
                EditText from = (EditText) getView().findViewById(R.id.et_add_course_from);
                EditText until = (EditText) getView().findViewById(R.id.et_add_course_until);
                Spinner teacher = (Spinner) getView().findViewById(R.id.sp_add_course_teacher);
                Button color = (Button) getView().findViewById(R.id.bt_add_course_color);
                EditText room = (EditText) getView().findViewById(R.id.et_add_course_room);
                EditText description = (EditText) getView().findViewById(R.id.et_add_course_description);
                Spinner day = (Spinner) getView().findViewById(R.id.sp_add_course_day);

                if(TextUtils.isEmpty(name.getText().toString())) {
                    name.setError("Name field cannot be empty");
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

                if(teacher.getSelectedItem()==null) {
                    TextView spinnerText = (TextView) teacher.getSelectedView();
                    spinnerText.setError("Teacher field cannot be empty");
                    return false;
                }

                if(TextUtils.isEmpty(room.getText().toString())) {
                    room.setError("Room field cannot be empty");
                    return false;
                }

                //CHECK OVERLAPS
                if(checkTimeOverlap(from.getText().toString(),until.getText().toString(), day.getSelectedItem().toString()))
                {
                    Toast toast = Toast.makeText(getActivity(), R.string.overlap, Toast.LENGTH_SHORT);
                    toast.show();
                    return false;
                }



                db.insertCourse(name.getText().toString(),day.getSelectedItem().toString(),from.getText().toString(),until.getText().toString(), colorPickerDialog.getSelectedColor(), Integer.parseInt(room.getText().toString()),description.getText().toString(),((Teacher)teacher.getSelectedItem()).getTeacherId());

                fragmentManager = getActivity().getSupportFragmentManager();
                fragment = new CourseFragment();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.addToBackStack(null);
                transaction.replace(R.id.main_container, fragment).commit();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //CHECK IF START TIME AND END TIME OF THE COURSE
    //OVERLAPS WITH AN EXISTING COURSE IN DB

    public boolean checkTimeOverlap(String start, String end, String day)
    {

        //FORMAT THE START TIME AND END TIME
        SimpleDateFormat hourFormat = new SimpleDateFormat("HH:mm");

        //SHIFT OF USER
        Date startA = formatTime(start);
        Date endA = formatTime(end);

        //EXISTING SHIFT IN DB
        Date startB;
        Date endB;

        //OPEN DATABASE HELPER
        db = new DatabaseHelper(getActivity().getApplicationContext());
        ArrayList<Course> existingCourses = db.getAllCourses();

        //CHECKING IN ALL THE DB
        for (Course existingCourse : existingCourses)
        {

            if(day.equals(existingCourse.getDay()))
            {
                startB = formatTime(existingCourse.getStart());
                endB = formatTime(existingCourse.getEnd());

                if ((startA.before(endB) || startA.equals(endB)) && (startB.before(endA) || startB.equals(endA))
                        && (startA.before(endB) || startA.equals(endB)) && (startB.before(endB) || startB.equals(endB)))
                    //overlaps
                    return true;
            }
        }

        //doesn't overlap
        return false;

    }

    //FORMAT TIME FROM A STRING
    public Date formatTime(String time)
    {
        //FORMAT THE START TIME NAD
        SimpleDateFormat hourFormat = new SimpleDateFormat("HH:mm");
        Date date = new Date();

        try
        {
            date = hourFormat.parse(time);

        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }

        return date;
    }

    //FORMAT TIME FROM A STRING AND RETURN THE STRING
    public String formatTimeString(String time)
    {
        //FORMAT THE START TIME NAD
        SimpleDateFormat hourFormat = new SimpleDateFormat("HH:mm");
        Date date = new Date();

        try
        {
            date = hourFormat.parse(time);
            time = hourFormat.format(date);

        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }

        return time;
    }


}
