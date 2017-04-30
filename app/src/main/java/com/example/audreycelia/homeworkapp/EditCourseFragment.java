package com.example.audreycelia.homeworkapp;

import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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


public class EditCourseFragment extends Fragment {

    private DatabaseHelper db;
    private Fragment fragment;
    private FragmentManager fragmentManager;
    private Menu menu;
    private TimePickerDialog timePickerDialog;
    private ColorPickerDialog colorPickerDialog;
    private int hour;
    private int minute;
    private int courseId;

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

        // Recupérer les éléments de la view
        final EditText name = (EditText) getView().findViewById(R.id.et_edit_course_name);
        final EditText from = (EditText) getView().findViewById(R.id.et_edit_course_from);
        final EditText until = (EditText) getView().findViewById(R.id.et_edit_course_until);
        final Spinner teacher = (Spinner) getView().findViewById(R.id.sp_edit_course_teacher);
        final Button colorButton = (Button) getView().findViewById(R.id.bt_edit_course_color);
        final EditText room = (EditText) getView().findViewById(R.id.et_edit_course_room);
        final EditText description = (EditText) getView().findViewById(R.id.et_edit_course_description);
        final Spinner day = (Spinner) getView().findViewById(R.id.sp_edit_course_day);

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

                //Enable les fields
                name.setEnabled(true);
                from.setEnabled(true);
                until.setEnabled(true);
                teacher.setEnabled(true);
                colorButton.setEnabled(true);
                room.setEnabled(true);
                description.setEnabled(true);
                day.setEnabled(true);


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

                int color = ((ColorDrawable)colorButton.getBackground()).getColor();
                db = new DatabaseHelper(getActivity().getApplicationContext());
                db.updateCourse(courseId, name.getText().toString(),day.getSelectedItem().toString(),from.getText().toString(),until.getText().toString(), color, Integer.parseInt(room.getText().toString()),description.getText().toString(),((Teacher)teacher.getSelectedItem()).getTeacherId());

                //Disable temporaiement les fields
                name.setEnabled(false);
                from.setEnabled(false);
                until.setEnabled(false);
                teacher.setEnabled(false);
                colorButton.setEnabled(false);
                room.setEnabled(false);
                description.setEnabled(false);
                day.setEnabled(false);

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




        courseId = getArguments().getInt("SelectedCourseId");
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

        //Disable temporaiement les fields
        name.setEnabled(false);
        from.setEnabled(false);
        until.setEnabled(false);
        teacher.setEnabled(false);
        color.setEnabled(false);
        room.setEnabled(false);
        description.setEnabled(false);
        day.setEnabled(false);

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
