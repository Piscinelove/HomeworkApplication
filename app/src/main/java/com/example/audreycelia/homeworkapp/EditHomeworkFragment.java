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
import db.Homework;



public class EditHomeworkFragment extends Fragment {
    private DatabaseHelper db;
    private Fragment fragment;
    private FragmentManager fragmentManager;
    private Menu menu;

    private DatePickerDialog datePickerDialog;
    private int month;
    private int year;
    private int day;

    public EditHomeworkFragment() {
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

        final int homeworkId = getArguments().getInt("SelectedHomeworkId");

        final EditText name = (EditText) getView().findViewById(R.id.et_edit_homework_name);
        final EditText date = (EditText) getView().findViewById(R.id.et_edit_homework_date);
        final Spinner course = (Spinner) getView().findViewById(R.id.sp_edit_homework_course);
        final CheckBox done = (CheckBox) getView().findViewById(R.id.cb_edit_homework_done);
        final EditText description = (EditText) getView().findViewById(R.id.et_edit_homework_description);
        final Button deleteButton = (Button) getView().findViewById(R.id.buttonDelete_edit_homework);


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

                //Enable les fields
                deleteButton.setVisibility(View.VISIBLE);
                name.setEnabled(true);
                date.setEnabled(true);
                course.setEnabled(true);
                done.setEnabled(true);
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
                course.setEnabled(false);
                done.setEnabled(false);
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

                if(course.getSelectedItem()==null) {
                    TextView spinnerText = (TextView) course.getSelectedView();
                    spinnerText.setError("Course field cannot be empty");
                    return false;
                }

                db = new DatabaseHelper(getActivity().getApplicationContext());
                db.updateHomework(homeworkId,name.getText().toString(),date.getText().toString(),true,description.getText().toString(),1);

                //Disable temporaiement les fields
                deleteButton.setVisibility(View.INVISIBLE);
                name.setEnabled(false);
                date.setEnabled(false);
                course.setEnabled(false);
                done.setEnabled(false);
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
        View rootView = inflater.inflate(R.layout.fragment_edit_homework, container, false);
        setHasOptionsMenu(true);

        final int homeworkId = getArguments().getInt("SelectedHomeworkId");
        db = new DatabaseHelper(getActivity().getApplicationContext());

        Homework homework = db.getHomeworkFromId(homeworkId);


         EditText name = (EditText) rootView.findViewById(R.id.et_edit_homework_name);
         final EditText date = (EditText) rootView.findViewById(R.id.et_edit_homework_date);
         Spinner course = (Spinner) rootView.findViewById(R.id.sp_edit_homework_course);
         CheckBox done = (CheckBox) rootView.findViewById(R.id.cb_edit_homework_done);
         EditText description = (EditText) rootView.findViewById(R.id.et_edit_homework_description);
        final Button deleteButton = (Button) rootView.findViewById(R.id.buttonDelete_edit_homework);

        name.setText(homework.getName());
        date.setText(homework.getDeadline());
        description.setText(homework.getDescription());

        //Disable temporaiement les fields
        name.setEnabled(false);
        date.setEnabled(false);
        course.setEnabled(false);
        done.setEnabled(false);
        description.setEnabled(false);


        //Fill spinner from database
        db = new DatabaseHelper(getActivity().getApplicationContext());
        ArrayList<Course> courses = db.getAllCourses();
        ArrayAdapter<Course> dataAdapter = new ArrayAdapter<Course>(getActivity(), android.R.layout.simple_spinner_item, courses);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        course.setAdapter(dataAdapter);

        // Récupérer le nom pour le mettre comme élément sélectionner dans le spinner
        Course selectedCourse = db.getCourseFromId(homework.getCourseId());
        course.setSelection(((ArrayAdapter)course.getAdapter()).getPosition(selectedCourse));


        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                        db.deleteHomework(homeworkId);
                        deleteButton.setVisibility(View.INVISIBLE);

                        fragmentManager = getActivity().getSupportFragmentManager();
                        fragment = new HomeworkFragment();
                        FragmentTransaction transaction = fragmentManager.beginTransaction();
                        transaction.addToBackStack(null);
                        transaction.replace(R.id.main_container, fragment).commit();
                    }
        });

        return rootView;
    }


}
