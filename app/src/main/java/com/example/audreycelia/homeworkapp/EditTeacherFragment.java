package com.example.audreycelia.homeworkapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import db.DatabaseHelper;
import db.Teacher;


public class EditTeacherFragment extends Fragment {

    private DatabaseHelper db;
    private Fragment fragment;
    private FragmentManager fragmentManager;


    public EditTeacherFragment() {
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

        final int teacherId = getArguments().getInt("SelectedTeacherId");
        db = new DatabaseHelper(getActivity().getApplicationContext());
        Teacher teacher = db.getTeacherFromId(teacherId);
        final EditText firstName = (EditText) rootView.findViewById(R.id.et_edit_teacher_firstname);
        final EditText lastName = (EditText) rootView.findViewById(R.id.et_edit_teacher_lastname);
        final EditText phone = (EditText) rootView.findViewById(R.id.et_edit_teacher_phone);
        final EditText email = (EditText) rootView.findViewById(R.id.et_edit_teacher_email);
        final EditText description = (EditText) rootView.findViewById(R.id.et_edit_teacher_description);
        final Button editButton = (Button) rootView.findViewById(R.id.bt_add_homework_save);
        final Button saveButton = (Button) rootView.findViewById(R.id.buttonSave_edit_teacher);
        final Button deleteButton = (Button) rootView.findViewById(R.id.buttonDelete_edit_teacher);

        firstName.setText(teacher.getFirstName());
        lastName.setText(teacher.getLastName());
        phone.setText(teacher.getPhone());
        email.setText(teacher.getEmail());
        description.setText(teacher.getDescription());

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveButton.setVisibility(View.VISIBLE);
                deleteButton.setVisibility(View.VISIBLE);
                editButton.setVisibility(View.INVISIBLE);
                firstName.setEnabled(true);
                lastName.setEnabled(true);
                email.setEnabled(true);
                phone.setEnabled(true);
                description.setEnabled(true);

                saveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(TextUtils.isEmpty(firstName.getText().toString())) {
                            firstName.setError("Firstname field cannot be empty");
                            return;
                        }

                        if(TextUtils.isEmpty(lastName.getText().toString())) {
                            lastName.setError("Lastname field cannot be empty");
                            return;
                        }


                        if(!TextUtils.isEmpty(phone.getText().toString()) && !isPhoneValid(phone.getText()))
                        {
                            phone.setError("Invalid phone number");
                            return;
                        }

                        if(!TextUtils.isEmpty(email.getText().toString()) && !isEmailValid(email.getText()))
                        {
                            email.setError("Invalid email");
                            return;
                        }

                        db.updateTeacher(teacherId, firstName.getText().toString(), lastName.getText().toString(), phone.getText().toString(), email.getText().toString(), description.getText().toString());
                        saveButton.setVisibility(View.INVISIBLE);
                        deleteButton.setVisibility(View.INVISIBLE);
                        editButton.setVisibility(View.VISIBLE);
                    }
                });

                deleteButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        db.deleteTeacher(teacherId);
                        saveButton.setVisibility(View.INVISIBLE);
                        deleteButton.setVisibility(View.INVISIBLE);
                        editButton.setVisibility(View.VISIBLE);

                        fragmentManager = getActivity().getSupportFragmentManager();
                        fragment = new TeacherFragment();
                        FragmentTransaction transaction = fragmentManager.beginTransaction();
                        transaction.addToBackStack(null);
                        transaction.replace(R.id.main_container, fragment).commit();



                    }
                });
            }
        });



        return rootView;
    }

    private boolean isEmailValid(CharSequence email)
    {
        if(!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches())
            return true;
        return false;
    }

    private boolean isPhoneValid(CharSequence phone)
    {
        if(Patterns.PHONE.matcher(phone).matches())
            return true;
        return false;
    }


}
