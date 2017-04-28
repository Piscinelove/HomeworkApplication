package com.example.audreycelia.homeworkapp;

import android.app.TimePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;


//import com.android.colorpicker.ColorPickerDialog;
//import com.android.colorpicker.ColorPickerSwatch;

import java.util.ArrayList;
import java.util.Calendar;

import db.DatabaseHelper;
import db.Teacher;


public class AddCourseFragment extends Fragment {
    private Button saveButton;
    private DatabaseHelper db;
    private Fragment fragment;
    private FragmentManager fragmentManager;

    private TimePickerDialog timePickerDialog;
    //private ColorPickerDialog colorPickerDialog;
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

                        from.setText(hourOfDay + ":" + minute);
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

        /*colorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                colorPickerDialog = new ColorPickerDialog();
                int[] colors = {getResources().getColor(R.color.primary1),
                        getResources().getColor(R.color.primary2),
                        getResources().getColor(R.color.primary3),
                        getResources().getColor(R.color.primary4),
                        getResources().getColor(R.color.primary5),
                        getResources().getColor(R.color.primary6),
                        getResources().getColor(R.color.primary7),
                        getResources().getColor(R.color.primary8),
                        getResources().getColor(R.color.primary9)};
                colorPickerDialog.initialize(R.string.colorChange,colors, colors[1], 3, colors.length);
                colorPickerDialog.show(getActivity().getFragmentManager(), "test");
                colorPickerDialog.setOnColorSelectedListener(new ColorPickerSwatch.OnColorSelectedListener() {
                    @Override
                    public void onColorSelected(int color) {
                        colorButton.setBackgroundColor(color);
                        colorButton.setTextColor(Color.WHITE);

                    }
                });
            }
        });*/


        //add button
        //test
        saveButton = (Button) rootView.findViewById(R.id.bt_add_exam_save);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText name = (EditText) rootView.findViewById(R.id.et_add_exam_name);
                EditText from = (EditText) rootView.findViewById(R.id.et_add_course_from);
                EditText until = (EditText) rootView.findViewById(R.id.et_add_course_until);
                Spinner teacher = (Spinner) rootView.findViewById(R.id.sp_add_course_teacher);
                Button color = (Button) rootView.findViewById(R.id.bt_add_course_color);
                EditText room = (EditText) rootView.findViewById(R.id.et_add_course_room);
                EditText description = (EditText) rootView.findViewById(R.id.et_add_course_description);

                if(TextUtils.isEmpty(name.getText().toString())) {
                    name.setError("Name field cannot be empty");
                    return;
                }

                if(TextUtils.isEmpty(from.getText().toString())) {
                    from.setError("From field cannot be empty");
                    return;
                }

                if(TextUtils.isEmpty(until.getText().toString())) {
                    from.setError("Until field cannot be empty");
                    return;
                }

                if(teacher.getSelectedItem()==null) {
                    TextView spinnerText = (TextView) teacher.getSelectedView();
                    spinnerText.setError("Teacher field cannot be empty");
                    return;
                }

                if(TextUtils.isEmpty(room.getText().toString())) {
                    room.setError("Room field cannot be empty");
                    return;
                }


                db = new DatabaseHelper(getActivity().getApplicationContext());
                db.insertCourse(name.getText().toString(),from.getText().toString(),until.getText().toString(), Integer.parseInt(color.getText().toString()), Integer.parseInt(room.getText().toString()),description.getText().toString(),1);

                fragmentManager = getActivity().getSupportFragmentManager();
                fragment = new CourseFragment();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.addToBackStack(null);
                transaction.replace(R.id.main_container, fragment).commit();
            }
        });

        return  rootView;
    }


}
