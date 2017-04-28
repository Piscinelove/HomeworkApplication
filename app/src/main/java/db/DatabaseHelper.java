package db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Rafael Peixoto on 22.04.2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Database.db";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DatabaseContract.CREATE_TABLE_TEACHERS);
        db.execSQL(DatabaseContract.CREATE_TABLE_COURSES);
        db.execSQL(DatabaseContract.CREATE_TABLE_HOMEWORKS);
        db.execSQL(DatabaseContract.CREATE_TABLE_EXAMS);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DatabaseContract.DELETE_TABLE_HOMEWORKS);
        db.execSQL(DatabaseContract.DELETE_TABLE_COURSES);
        db.execSQL(DatabaseContract.DELETE_TABLE_TEACHERS);
        db.execSQL(DatabaseContract.DELETE_TABLE_EXAMS);
        onCreate(db);

    }

    //INSERT METHODS

    public void insertTeacher (String firstName, String lastName, String phone, String email, String description)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(DatabaseContract.Teachers.TEACHER_FIRSTNAME, firstName );
        values.put(DatabaseContract.Teachers.TEACHER_LASTNAME, lastName );
        values.put(DatabaseContract.Teachers.TEACHER_PHONE, phone );
        values.put(DatabaseContract.Teachers.TEACHER_EMAIL, email );
        values.put(DatabaseContract.Teachers.TEACHER_DESCRIPTION, description );

        db.insert(DatabaseContract.Teachers.TABLE_NAME,null, values);
    }

    public void insertCourse (String name, String day, String start, String end, int color, int room, String description, int teacherId)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(DatabaseContract.Courses.COURSE_NAME, name );
        values.put(DatabaseContract.Courses.COURSE_DAY, day );
        values.put(DatabaseContract.Courses.COURSE_START, start);
        values.put(DatabaseContract.Courses.COURSE_END, end);
        values.put(DatabaseContract.Courses.COURSE_COLOR, color );
        values.put(DatabaseContract.Courses.COURSE_ROOM, room );
        values.put(DatabaseContract.Courses.COURSE_DESCRIPTION, description );
        values.put(DatabaseContract.Courses.COURSE_TEACHER_ID, teacherId );

        db.insert(DatabaseContract.Courses.TABLE_NAME,null, values);
    }

    public void insertHomework (String name, String deadline, boolean done, String description, int courseId)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(DatabaseContract.Homeworks.HOMEWORK_NAME, name );
        values.put(DatabaseContract.Homeworks.HOMEWORK_DEADLINE, deadline );
        if(done)
            values.put(DatabaseContract.Homeworks.HOMEWORK_DONE, 1 );
        else
            values.put(DatabaseContract.Homeworks.HOMEWORK_DONE, 0 );
        values.put(DatabaseContract.Homeworks.HOMEWORK_DESCRIPTION, description );
        values.put(DatabaseContract.Homeworks.HOMEWORK_COURSE_ID, courseId );

        db.insert(DatabaseContract.Homeworks.TABLE_NAME,null, values);
    }

    public void insertExam (String name, String date, String start, String end, double grade, int room, String description, int courseId)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(DatabaseContract.Exams.EXAM_NAME, name );
        values.put(DatabaseContract.Exams.EXAM_DATE, date );
        values.put(DatabaseContract.Exams.EXAM_START, start);
        values.put(DatabaseContract.Exams.EXAM_END, end);
        values.put(DatabaseContract.Exams.EXAM_GRADE, grade);
        values.put(DatabaseContract.Exams.EXAM_ROOM, room);
        values.put(DatabaseContract.Exams.EXAM_DESCRIPTION, description );
        values.put(DatabaseContract.Exams.EXAM_COURSE_ID, courseId );

        db.insert(DatabaseContract.Exams.TABLE_NAME,null, values);
    }

    //UPDATE METHODS

    public void updateTeacher (int teacherId, String firstName, String lastName, String phone, String email, String description)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();

        values.put(DatabaseContract.Teachers.TEACHER_FIRSTNAME, firstName );
        values.put(DatabaseContract.Teachers.TEACHER_LASTNAME, lastName );
        values.put(DatabaseContract.Teachers.TEACHER_PHONE, phone );
        values.put(DatabaseContract.Teachers.TEACHER_EMAIL, email );
        values.put(DatabaseContract.Teachers.TEACHER_DESCRIPTION, description );

        String selection = DatabaseContract.Teachers.TEACHER_ID + " LIKE ?";
        String[] selectionArgs = { String.valueOf(teacherId)};
        db.update(DatabaseContract.Teachers.TABLE_NAME, values, selection, selectionArgs);
    }

    public void updateCourse (int courseId, String name, String day, String start, String end, int color, int room, String description, int teacherId)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();

        values.put(DatabaseContract.Courses.COURSE_NAME, name );
        values.put(DatabaseContract.Courses.COURSE_DAY, day );
        values.put(DatabaseContract.Courses.COURSE_START, start);
        values.put(DatabaseContract.Courses.COURSE_END,end);
        values.put(DatabaseContract.Courses.COURSE_COLOR, color );
        values.put(DatabaseContract.Courses.COURSE_ROOM, room );
        values.put(DatabaseContract.Courses.COURSE_DESCRIPTION, description );
        values.put(DatabaseContract.Courses.COURSE_TEACHER_ID, teacherId );

        String selection = DatabaseContract.Courses.COURSE_ID + " LIKE ?";
        String[] selectionArgs = { String.valueOf(courseId)};
        db.update(DatabaseContract.Courses.TABLE_NAME, values, selection, selectionArgs);
    }

    public void updateHomework (int homeworkId, String name, String deadline, boolean done, String description, int courseId)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();

        values.put(DatabaseContract.Homeworks.HOMEWORK_NAME, name );
        values.put(DatabaseContract.Homeworks.HOMEWORK_DEADLINE, deadline );
        if(done)
            values.put(DatabaseContract.Homeworks.HOMEWORK_DONE, 1 );
        else
            values.put(DatabaseContract.Homeworks.HOMEWORK_DONE, 0 );
        values.put(DatabaseContract.Homeworks.HOMEWORK_DESCRIPTION, description );
        values.put(DatabaseContract.Homeworks.HOMEWORK_COURSE_ID, courseId );

        String selection = DatabaseContract.Homeworks.HOMEWORK_ID + " LIKE ?";
        String[] selectionArgs = { String.valueOf(homeworkId)};
        db.update(DatabaseContract.Homeworks.TABLE_NAME, values, selection, selectionArgs);
    }

    public void updateExam (int examId, String name, String date, String start, String end, double grade, int room, String description, int courseId)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();

        values.put(DatabaseContract.Exams.EXAM_NAME, name );
        values.put(DatabaseContract.Exams.EXAM_DATE, date );
        values.put(DatabaseContract.Exams.EXAM_START, start);
        values.put(DatabaseContract.Exams.EXAM_END,end);
        values.put(DatabaseContract.Exams.EXAM_GRADE, grade);
        values.put(DatabaseContract.Exams.EXAM_ROOM, room);
        values.put(DatabaseContract.Exams.EXAM_DESCRIPTION, description );
        values.put(DatabaseContract.Exams.EXAM_COURSE_ID, courseId );


        String selection = DatabaseContract.Exams.EXAM_ID + " LIKE ?";
        String[] selectionArgs = { String.valueOf(examId)};
        db.update(DatabaseContract.Exams.TABLE_NAME, values, selection, selectionArgs);
    }

    //DELETE METHODS
    public void deleteTeacher (int teacherId)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DatabaseContract.Teachers.TABLE_NAME, DatabaseContract.Teachers.TEACHER_ID + " = ?", new String[]{String.valueOf(teacherId)});
        db.close();
    }

    //DELETE METHODS
    public void deleteHomework (int homeworkId)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DatabaseContract.Homeworks.TABLE_NAME, DatabaseContract.Homeworks.HOMEWORK_ID + " = ?", new String[]{String.valueOf(homeworkId)});
        db.close();
    }

    //SELECT METHODS
    public ArrayList<Course> getAllCourses() {
        ArrayList<Course> listCourses = new ArrayList<Course>();

        //SELECT
        String select = "SELECT  * FROM " + DatabaseContract.Courses.TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(select, null);


        if (cursor.moveToFirst()) {
            do {
                Course course = new Course();
                course.setCourseId(Integer.parseInt(cursor.getString(0)));
                course.setName(cursor.getString(1));
                course.setDay(cursor.getString(2));
                course.setStart(cursor.getString(3));
                course.setEnd(cursor.getString(4));
                course.setColor(Integer.parseInt(cursor.getString(5)));
                course.setRoom(Integer.parseInt(cursor.getString(6)));
                course.setDescription(cursor.getString(7));
                course.setTeacherId(Integer.parseInt(cursor.getString(8)));

                //POPULATE THE ARRAY LIST
                listCourses.add(course);

            } while (cursor.moveToNext());
        }


        return listCourses;

    }

    public ArrayList<Teacher> getAllTeachers() {
        ArrayList<Teacher> listTeachers = new ArrayList<Teacher>();

        //SELECT
        String select = "SELECT  * FROM " + DatabaseContract.Teachers.TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(select, null);

        if (cursor.moveToFirst()) {
            do {
                Teacher teacher = new Teacher();
                teacher.setTeacherId(Integer.parseInt(cursor.getString(0)));
                teacher.setFirstName(cursor.getString(1));
                teacher.setLastName(cursor.getString(2));
                teacher.setPhone(cursor.getString(3));
                teacher.setEmail(cursor.getString(4));
                teacher.setDescription(cursor.getString(5));

                //POPULATE THE ARRAY LIST
                listTeachers.add(teacher);

            } while (cursor.moveToNext());
        }
        return listTeachers;
    }

    //SELECT METHODS
    public ArrayList<Homework> getAllHomeworks() {
        ArrayList<Homework> listHomeworks = new ArrayList<Homework>();

        //SELECT
        String select = "SELECT  * FROM " + DatabaseContract.Homeworks.TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(select, null);


        if (cursor.moveToFirst()) {
            do {
                Homework homework = new Homework();

                homework.setHomeworkId(Integer.parseInt(cursor.getString(0)));
                homework.setName(cursor.getString(1));
                homework.setDeadline(cursor.getString(2));
                if(Integer.parseInt(cursor.getString(3))==1){
                    homework.setDone(true);
                }
                else
                    homework.setDone(false);

                homework.setDescription(cursor.getString(4));
                homework.setCourseId(Integer.parseInt(cursor.getString(5)));

                //POPULATE THE ARRAY LIST
                listHomeworks.add(homework);

            } while (cursor.moveToNext());
        }
        return listHomeworks;
    }


    //SELECT METHODS
    public ArrayList<Exam> getAllExams() {
        ArrayList<Exam> listExams = new ArrayList<Exam>();

        //SELECT
        String select = "SELECT  * FROM " + DatabaseContract.Exams.TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(select, null);


        if (cursor.moveToFirst()) {
            do {
                Exam exam = new Exam();

                exam.setExamId(Integer.parseInt(cursor.getString(0)));
                exam.setName(cursor.getString(1));
                exam.setDate(cursor.getString(2));
                exam.setStart(cursor.getString(3));
                exam.setEnd(cursor.getString(4));
                exam.setGrade(cursor.getDouble(5));
                exam.setRoom(Integer.parseInt(cursor.getString(6)));
                exam.setDescription(cursor.getString(7));
                exam.setCourseId(Integer.parseInt(cursor.getString(8)));

                //POPULATE THE ARRAY LIST
                listExams.add(exam);

            } while (cursor.moveToNext());
        }
        return listExams;
    }

    //SELECT TEACHER FORM ID
    public Teacher getTeacherFromId(int teacherId) {
        Teacher teacher = new Teacher();

        //SELECT
        String select = "SELECT  * FROM " + DatabaseContract.Teachers.TABLE_NAME + " WHERE "+DatabaseContract.Teachers.TEACHER_ID+" = "+teacherId;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(select, null);

        if (cursor.moveToFirst()) {
            do {
                teacher.setTeacherId(Integer.parseInt(cursor.getString(0)));
                teacher.setFirstName(cursor.getString(1));
                teacher.setLastName(cursor.getString(2));
                teacher.setPhone(cursor.getString(3));
                teacher.setEmail(cursor.getString(4));
                teacher.setDescription(cursor.getString(5));
            }
            while (cursor.moveToNext());
        }


        return teacher;
    }

    //SELECT TEACHER FORM ID
    public Homework getHomeworkFromId(int homeworkId) {
        Homework homework = new Homework();

        //SELECT
        String select = "SELECT  * FROM " + DatabaseContract.Homeworks.TABLE_NAME + " WHERE "+DatabaseContract.Homeworks.HOMEWORK_ID+" = "+homeworkId;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(select, null);

        if (cursor.moveToFirst()) {
            do {

                homework.setHomeworkId(Integer.parseInt(cursor.getString(0)));
                homework.setName(cursor.getString(1));
                homework.setDeadline(cursor.getString(2));
                if(Integer.parseInt(cursor.getString(3))==1){
                    homework.setDone(true);
                }
                else
                    homework.setDone(false);

                homework.setDescription(cursor.getString(4));
                homework.setCourseId(Integer.parseInt(cursor.getString(5)));

            }
            while (cursor.moveToNext());
        }


        return homework;
    }




}
