package com.example.audreycelia.homeworkapp;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.net.Uri;
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
import android.widget.DatePicker;
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
import db.Exam;
import db.Teacher;


public class EditExamFragment extends Fragment {

    private DatabaseHelper db;
    private Fragment fragment;
    private FragmentManager fragmentManager;
    private Menu menu;

    private TimePickerDialog timePickerDialog;
    private DatePickerDialog datePickerDialog;

    private int hour;
    private int minute;

    private int month;
    private int year;
    private int day;

    public EditExamFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.editactionbar, menu);
        this.menu = menu;
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        MenuItem edit = menu.findItem(R.id.ab_edit_edit);
        MenuItem back = menu.findItem(R.id.ab_edit_back);
        MenuItem undo = menu.findItem(R.id.ab_edit_undo);
        MenuItem save = menu.findItem(R.id.ab_edit_save);

        final int examId = getArguments().getInt("SelectedExamId");

        final EditText name = (EditText) getView().findViewById(R.id.et_edit_exam_name);
        final EditText date = (EditText) getView().findViewById(R.id.et_edit_exam_date);
        final EditText from = (EditText) getView().findViewById(R.id.et_edit_exam_from);
        final EditText until = (EditText) getView().findViewById(R.id.et_edit_exam_until);
        final Spinner course = (Spinner) getView().findViewById(R.id.sp_edit_exam_course);
        final EditText room = (EditText) getView().findViewById(R.id.et_edit_exam_room);
        final EditText grade = (EditText) getView().findViewById(R.id.et_edit_exam_grade);
        final EditText description = (EditText) getView().findViewById(R.id.et_edit_exam_description);
        final Button deleteButton = (Button) getView().findViewById(R.id.bt_delete_edit_exam);

        switch (item.getItemId())
        {
            case R.id.ab_edit_back:
                getActivity().getSupportFragmentManager().popBackStack();
                return true;

            case R.id.ab_edit_edit:

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

                //Enable les fields
                deleteButton.setVisibility(View.VISIBLE);
                name.setEnabled(true);
                date.setEnabled(true);
                from.setEnabled(true);
                until.setEnabled(true);
                course.setEnabled(true);
                room.setEnabled(true);
                grade.setEnabled(true);
                description.setEnabled(true);

                //Gérer les boutons du menu
                edit.setVisible(false);
                back.setVisible(false);
                undo.setVisible(true);
                save.setVisible(true);

                return true;

            case R.id.ab_edit_undo:
                //Gérer les boutons du menu
                edit.setVisible(true);
                back.setVisible(true);
                undo.setVisible(false);
                save.setVisible(false);

                deleteButton.setVisibility(View.INVISIBLE);
                name.setEnabled(false);
                date.setEnabled(false);
                from.setEnabled(false);
                until.setEnabled(false);
                course.setEnabled(false);
                room.setEnabled(false);
                grade.setEnabled(false);
                description.setEnabled(false);
                return true;

            case R.id.ab_edit_save:

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
                db.updateExam(examId,name.getText().toString(),date.getText().toString(),from.getText().toString(),until.getText().toString(), Double.parseDouble(grade.getText().toString()), Integer.parseInt(room.getText().toString()),description.getText().toString(),1);


                //Disable temporaiement les fields
                deleteButton.setVisibility(View.INVISIBLE);

                name.setEnabled(false);
                date.setEnabled(false);
                from.setEnabled(false);
                until.setEnabled(false);
                course.setEnabled(false);
                room.setEnabled(false);
                grade.setEnabled(false);
                description.setEnabled(false);

                //Gérer les boutons du menu
                edit.setVisible(true);
                back.setVisible(true);
                undo.setVisible(false);
                save.setVisible(false);

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_edit_exam, container, false);
        setHasOptionsMenu(true);

        final int examId = getArguments().getInt("SelectedExamId");
        db = new DatabaseHelper(getActivity().getApplicationContext());

        Exam exam = db.getExamFromId(examId);

         EditText name = (EditText) rootView.findViewById(R.id.et_edit_exam_name);
         EditText date = (EditText) rootView.findViewById(R.id.et_edit_exam_date);
         EditText from = (EditText) rootView.findViewById(R.id.et_edit_exam_from);
         EditText until = (EditText) rootView.findViewById(R.id.et_edit_exam_until);
         Spinner course = (Spinner) rootView.findViewById(R.id.sp_edit_exam_course);
         EditText room = (EditText) rootView.findViewById(R.id.et_edit_exam_room);
         EditText grade = (EditText) rootView.findViewById(R.id.et_edit_exam_grade);
         EditText description = (EditText) rootView.findViewById(R.id.et_edit_exam_description);
         final Button deleteButton = (Button) rootView.findViewById(R.id.bt_delete_edit_exam);


        //Disable temporaiement les fields
        name.setEnabled(false);
        date.setEnabled(false);
        from.setEnabled(false);
        until.setEnabled(false);
        course.setEnabled(false);
        room.setEnabled(false);
        grade.setEnabled(false);
        description.setEnabled(false);

        name.setText(exam.getName());
        date.setText(exam.getDate());
        from.setText(exam.getStart());
        until.setText(exam.getEnd());

        //Fill spinner from database
        db = new DatabaseHelper(getActivity().getApplicationContext());
        ArrayList<Course> courses = db.getAllCourses();
        ArrayAdapter<Course> dataAdapter = new ArrayAdapter<Course>(getActivity(), android.R.layout.simple_spinner_item, courses);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        course.setAdapter(dataAdapter);

        // Récupérer le nom pour le mettre comme élément sélectionner dans le spinner
        Course selectedCourse = db.getCourseFromId(exam.getCourseId());
        course.setSelection(((ArrayAdapter)course.getAdapter()).getPosition(selectedCourse));


        room.setText(""+exam.getRoom());
        grade.setText(""+exam.getGrade());
        description.setText(exam.getDescription());



        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.deleteExam(examId);
                deleteButton.setVisibility(View.INVISIBLE);

                fragmentManager = getActivity().getSupportFragmentManager();
                fragment = new ExamFragment();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.addToBackStack(null);
                transaction.replace(R.id.main_container, fragment).commit();

            }
        });

        return rootView;
    }
}
