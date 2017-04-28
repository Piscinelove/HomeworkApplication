package com.example.audreycelia.homeworkapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.CalendarView;


public class MainActivity extends AppCompatActivity {

    private BottomNavigationView navigation;
    private Fragment fragment;
    private FragmentManager fragmentManager;


    CalendarView calendar ;

    @Override
    //test
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.inflateMenu(R.menu.navigation);

        fragmentManager = getSupportFragmentManager();

        fragment = new CalendarFragment();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.main_container, fragment).commit();

        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item)
            {
                switch (item.getItemId())
                {
                    case R.id.navigation_timetable:
                        fragment = new CalendarFragment();
                        break;
                    case R.id.navigation_course:
                        fragment = new CourseFragment();
                        break;
                    case R.id.navigation_exam:
                        fragment = new ExamFragment();
                        break;
                    case R.id.navigation_homework:
                        fragment = new HomeworkFragment();
                        break;
                    case R.id.navigation_teacher:
                        fragment = new TeacherFragment();
                        break;
                }

                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.main_container, fragment).commit();
                return true;
            }
        });



    }
}
