package com.example.audreycelia.homeworkapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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

import java.util.ArrayList;

import db.Course;
import db.DatabaseHelper;
import db.Teacher;


public class EditCourseFragment extends Fragment {

    private DatabaseHelper db;
    private Fragment fragment;
    private FragmentManager fragmentManager;
    private Menu menu;

    public EditCourseFragment() {
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

        switch (item.getItemId())
        {
            case R.id.ab_edit_back:
                getActivity().getSupportFragmentManager().popBackStack();
                return true;
            case R.id.ab_edit_edit:
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
                return true;

            case R.id.ab_edit_save:
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
        View rootView = inflater.inflate(R.layout.fragment_edit_course, container, false);
        setHasOptionsMenu(true);

        final int courseId = getArguments().getInt("SelectedCourseId");
        db = new DatabaseHelper(getActivity().getApplicationContext());
        Course course = db.getCourseFromId(courseId);

        // Recupérer les éléments de la view
        EditText name = (EditText) rootView.findViewById(R.id.et_edit_course_name);
        EditText from = (EditText) rootView.findViewById(R.id.et_edit_course_from);
        EditText until = (EditText) rootView.findViewById(R.id.et_edit_course_until);
        Spinner teacher = (Spinner) rootView.findViewById(R.id.sp_edit_course_teacher);
        Button color = (Button) rootView.findViewById(R.id.bt_edit_course_color);
        EditText room = (EditText) rootView.findViewById(R.id.et_edit_course_room);
        EditText description = (EditText) rootView.findViewById(R.id.et_edit_course_description);
        Spinner day = (Spinner) rootView.findViewById(R.id.sp_edit_course_day);

        name.setText(course.getName());
        from.setText(course.getStart());
        until.setText(course.getEnd());

        //Fill spinner from database
        db = new DatabaseHelper(getActivity().getApplicationContext());
        ArrayList<Teacher> teachers = db.getAllTeachers();
        ArrayAdapter<Teacher> dataAdapter = new ArrayAdapter<Teacher>(getActivity(), android.R.layout.simple_spinner_item, teachers);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        teacher.setAdapter(dataAdapter);
        // Récupérer le nom et prénom du teacher pour le mettre comme élément sélectionner dans le spinner
        Teacher selectedTeacher = db.getTeacherFromId(course.getTeacherId());
        teacher.setSelection(((ArrayAdapter)teacher.getAdapter()).getPosition(selectedTeacher));
        color.setBackgroundColor(course.getColor());
        room.setText(""+course.getRoom());
        description.setText(course.getDescription());
        day.setSelection(((ArrayAdapter)day.getAdapter()).getPosition(course.getDay()));
        return  rootView;
    }

}
