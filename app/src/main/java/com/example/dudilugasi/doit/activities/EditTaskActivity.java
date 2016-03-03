package com.example.dudilugasi.doit.activities;

import android.content.Intent;
import android.database.Cursor;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.dudilugasi.doit.R;
import com.example.dudilugasi.doit.common.Constants;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EditTaskActivity extends AppCompatActivity {

    private String name;
    private String time;
    private Date date = new Date();
    private int priority;
    private String category;
    private String room;
    private String asignee;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);
        Intent intent = getIntent();
        String[] categories = {"Cleaning", "Electricity", "Computers", "General", "Other"};
        ArrayAdapter<String> stringArrayAdapter= new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, categories);
        Spinner spinner = (Spinner)  findViewById(R.id.task_category_spinner);
        spinner.setAdapter(stringArrayAdapter);

        category = intent.getStringExtra(Constants.NEW_TASK_CATEGORY);
        priority = intent.getIntExtra(Constants.NEW_TASK_PRIORITY, 1);
        room = intent.getStringExtra(Constants.NEW_TASK_LOCATION);
        asignee = intent.getStringExtra(Constants.NEW_TASK_ASSIGNEE);
        name = intent.getStringExtra(Constants.NEW_TASK_NAME);
        date = (Date) intent.getSerializableExtra(Constants.NEW_TASK_DUE_DATE);

        EditText temp = (EditText) findViewById(R.id.task_name_text);
        temp.setText(name, TextView.BufferType.EDITABLE);

        Spinner temp1 = (Spinner) findViewById(R.id.task_category_spinner);
        ArrayAdapter myAdap = (ArrayAdapter) temp1.getAdapter();
        int spinnerPosition = myAdap.getPosition(category);
        temp1.setSelection(spinnerPosition);

        RadioGroup temp2 = (RadioGroup) findViewById(R.id.radio_group);
        temp2.check(priority);

        temp = (EditText) findViewById(R.id.task_room_num);
        temp.setText(room,TextView.BufferType.EDITABLE);

        temp = (EditText) findViewById(R.id.time_text);
        temp.setText(date.getHours()+":"+date.getMinutes(),TextView.BufferType.EDITABLE);

        temp = (EditText) findViewById(R.id.date_text);
        temp.setText(date.getDay()+"/"+date.getMonth()+"/"+date.getYear());


    }






    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }
    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }








    public void onCancel(View v){
        finish();
    }
    public void onSave(View v) {

        name = (String) findViewById(R.id.task_name).toString();//task name
        RadioGroup rg1 = (RadioGroup) findViewById(R.id.radio_group);//priority
        if (rg1.getCheckedRadioButtonId() != -1) {
            int id = rg1.getCheckedRadioButtonId();
            View radioButton = rg1.findViewById(id);
            int radioId = rg1.indexOfChild(radioButton);
            RadioButton btn = (RadioButton) rg1.getChildAt(radioId);
            priority =  radioId;


        }
        Spinner sp = (Spinner) findViewById(R.id.task_category_spinner);
        category = sp.getSelectedItem().toString(); //category


        EditText nm = (EditText)  findViewById(R.id.task_room_num); //room
        room  = nm.getText().toString();

        time = (String) findViewById(R.id.time_text).toString(); // time
        String[] time1 = time.split(":");
        date.setHours(Integer.parseInt(time1[0]));
        date.setMinutes(Integer.parseInt(time1[1]));

        time = (String) findViewById(R.id.date_text).toString(); //date
        String[] date1 = time.split("/");
        date.setDate(Integer.parseInt(time1[0]));
        date.setMonth(Integer.parseInt(time1[1]));
        date.setYear(Integer.parseInt(time1[2]));




        asignee = (String) findViewById(R.id.person_name_spinner).toString();

        Intent intent = new Intent(this, WaitingTasksActivity.class);
        intent.putExtra(Constants.NEW_TASK_CATEGORY,category);
        intent.putExtra(Constants.NEW_TASK_LOCATION,room);
        intent.putExtra(Constants.NEW_TASK_PRIORITY,priority);
        intent.putExtra(Constants.NEW_TASK_DUE_DATE, date);
        intent.putExtra(Constants.NEW_TASK_ASSIGNEE,asignee);
        setResult(RESULT_OK, intent);
        finish();
    }
}
