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


public class AddTeacherFragment extends Fragment {

    private Button saveButton;
    private DatabaseHelper db;
    private Fragment fragment;
    private FragmentManager fragmentManager;

    public AddTeacherFragment() {
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_add_teacher, container, false);
        setHasOptionsMenu(true);


        //add button
        saveButton = (Button) rootView.findViewById(R.id.bt_add_teacher_save);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText firstName = (EditText) rootView.findViewById(R.id.et_add_teacher_firstname);
                EditText lastName = (EditText) rootView.findViewById(R.id.et_add_teacher_lastname);
                EditText email = (EditText) rootView.findViewById(R.id.et_add_teacher_email);
                EditText phone = (EditText) rootView.findViewById(R.id.et_add_teacher_phone);
                EditText description = (EditText) rootView.findViewById(R.id.et_add_teacher_description);

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


                db = new DatabaseHelper(getActivity().getApplicationContext());
                db.insertTeacher(firstName.getText().toString(),lastName.getText().toString(),phone.getText().toString(),email.getText().toString(),description.getText().toString());

                fragmentManager = getActivity().getSupportFragmentManager();
                fragment = new TeacherFragment();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.addToBackStack(null);
                transaction.replace(R.id.main_container, fragment).commit();

            }
        });

        return  rootView;
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
