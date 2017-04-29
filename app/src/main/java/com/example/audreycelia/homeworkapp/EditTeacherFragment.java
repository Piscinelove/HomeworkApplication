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
    private Menu menu;

    public EditTeacherFragment() {
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

        final int teacherId = getArguments().getInt("SelectedTeacherId");

        final EditText firstName = (EditText) getView().findViewById(R.id.et_edit_teacher_firstname);
        final EditText lastName = (EditText) getView().findViewById(R.id.et_edit_teacher_lastname);
        final EditText phone = (EditText) getView().findViewById(R.id.et_edit_teacher_phone);
        final EditText email = (EditText) getView().findViewById(R.id.et_edit_teacher_email);
        final EditText description = (EditText) getView().findViewById(R.id.et_edit_teacher_description);
        final Button deleteButton = (Button) getView().findViewById(R.id.buttonDelete_edit_teacher);

        switch (item.getItemId())
        {
            case R.id.ab_edit_back:
                getActivity().getSupportFragmentManager().popBackStack();
                return true;
            case R.id.ab_edit_edit:

                deleteButton.setVisibility(View.VISIBLE);
                firstName.setEnabled(true);
                lastName.setEnabled(true);
                email.setEnabled(true);
                phone.setEnabled(true);
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
                return true;

            case R.id.ab_edit_save:

                if(TextUtils.isEmpty(firstName.getText().toString())) {
                    firstName.setError("Firstname field cannot be empty");
                    return false;
                }

                if(TextUtils.isEmpty(lastName.getText().toString())) {
                    lastName.setError("Lastname field cannot be empty");
                    return false;
                }


                if(!TextUtils.isEmpty(phone.getText().toString()) && !isPhoneValid(phone.getText()))
                {
                    phone.setError("Invalid phone number");
                    return false;
                }

                if(!TextUtils.isEmpty(email.getText().toString()) && !isEmailValid(email.getText()))
                {
                    email.setError("Invalid email");
                    return false;
                }

                db.updateTeacher(teacherId,firstName.getText().toString().substring(0,1).toUpperCase() +firstName.getText().toString().substring(1).toLowerCase(),lastName.getText().toString().substring(0,1).toUpperCase() +lastName.getText().toString().substring(1).toLowerCase(),phone.getText().toString(),email.getText().toString(),description.getText().toString());

                deleteButton.setVisibility(View.INVISIBLE);



                //Gérer les fields

                firstName.setEnabled(false);
                lastName.setEnabled(false);
                email.setEnabled(false);
                phone.setEnabled(false);
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
        final Button deleteButton = (Button) rootView.findViewById(R.id.buttonDelete_edit_teacher);

        firstName.setText(teacher.getFirstName());
        lastName.setText(teacher.getLastName());
        phone.setText(teacher.getPhone());
        email.setText(teacher.getEmail());
        description.setText(teacher.getDescription());


                deleteButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        db.deleteTeacher(teacherId);
                        deleteButton.setVisibility(View.INVISIBLE);
                        editButton.setVisibility(View.VISIBLE);

                        fragmentManager = getActivity().getSupportFragmentManager();
                        fragment = new TeacherFragment();
                        FragmentTransaction transaction = fragmentManager.beginTransaction();
                        transaction.addToBackStack(null);
                        transaction.replace(R.id.main_container, fragment).commit();



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
