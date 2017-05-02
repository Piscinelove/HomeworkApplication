package com.example.audreycelia.homeworkapp;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CalendarView;
import android.widget.Toast;

import java.util.Locale;


public class MainActivity extends AppCompatActivity {

    private BottomNavigationView navigation;
    private Fragment fragment;
    private FragmentManager fragmentManager;


    CalendarView calendar ;



    @Override
    public void onBackPressed() {
        if(fragmentManager.getBackStackEntryCount() > 0) {
                Fragment currentFragment = fragmentManager.findFragmentById(R.id.main_container);
                if(currentFragment instanceof CalendarFragment ){
                    navigation.getMenu().getItem(0).setChecked(true);
                }
                else if(currentFragment instanceof CourseFragment){
                    navigation.getMenu().getItem(1).setChecked(true);
                }
                else if(currentFragment instanceof ExamFragment){
                    navigation.getMenu().getItem(2).setChecked(true);
                }
                else if(currentFragment instanceof HomeworkFragment){
                    navigation.getMenu().getItem(3).setChecked(true);
                }
                else if(currentFragment instanceof TeacherFragment){
                    navigation.getMenu().getItem(4).setChecked(true);
                }
                else
                    super.onBackPressed();
            }
        }



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
        transaction.addToBackStack(null);
        navigation.getMenu().getItem(0).setChecked(true);


        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item)
            {
                Fragment currentFragment = fragmentManager.findFragmentById(R.id.main_container);
                switch (item.getItemId())

                {
                    case R.id.navigation_timetable:
                        if (!(currentFragment instanceof CalendarFragment)) {
                            fragment = new CalendarFragment();
                        }
                        break;
                    case R.id.navigation_course:
                        if (!(currentFragment instanceof CourseFragment)) {
                            fragment = new CourseFragment();
                        }

                        break;
                    case R.id.navigation_exam:
                        if (!(currentFragment instanceof ExamFragment)) {
                            fragment = new ExamFragment();
                        }
                        break;
                    case R.id.navigation_homework:
                        if (!(currentFragment instanceof HomeworkFragment)) {
                            fragment = new HomeworkFragment();
                        }
                        break;
                    case R.id.navigation_teacher:
                        if (!(currentFragment instanceof TeacherFragment)) {
                            fragment = new TeacherFragment();
                        }
                        break;

                }

                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.main_container, fragment).commit();
                transaction.addToBackStack(null);
                return true;
            }
        });



    }

    public void changeLanguage (String toLoad) {
        Locale locale = new Locale(toLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale= locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        Intent intent = new Intent(MainActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    //Méthode qui se déclenchera au clic sur un item
    public boolean onOptionsItemSelected(MenuItem item) {
        String languageToLoad;
        //On regarde quel item a été cliqué grâce à son id et on déclenche une action
        switch (item.getItemId()) {
            case R.id.action_bar_settings:
                //Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.french:
                languageToLoad = "fr";
                changeLanguage(languageToLoad);
                return true;
            case R.id.german:
                languageToLoad = "de";
                changeLanguage(languageToLoad);
                return true;
            case R.id.english:
                languageToLoad = "en";
                changeLanguage(languageToLoad);
                return true;

        }
        return false;
    }


}

