package com.example.dudilugasi.doit.activities;

import android.content.Intent;
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
import android.widget.TimePicker;

import com.example.dudilugasi.doit.R;
import com.example.dudilugasi.doit.common.Constants;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EditTaskActivity extends AppCompatActivity {

    private String time;
    private String date;
    private int priority;
    private String category;
    private String room;
    private String asignee;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);
        String[] categories = {"Cleaning", "Electricity", "Computers", "General", "Other"};
        ArrayAdapter<String> stringArrayAdapter= new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, categories);
        Spinner spinner = (Spinner)  findViewById(R.id.task_category_spinner);
        spinner.setAdapter(stringArrayAdapter);

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

        time = (String) findViewById(R.id.time_text).toString(); //time
        date = (String) findViewById(R.id.date_text).toString(); //date



        asignee = (String) findViewById(R.id.person_name_spinner).toString();

        Intent intent = new Intent(this, WaitingTasksActivity.class);
        intent.putExtra(Constants.NEW_TASK_CATEGORY,category);
        intent.putExtra(Constants.NEW_TASK_LOCATION,room);
        intent.putExtra(Constants.NEW_TASK_PRIORITY,priority);
        intent.putExtra(Constants.NEW_TASK_DUE_DATE,time);
        intent.putExtra(Constants.NEW_TASK_DUE_DATE , new Date());
        intent.putExtra(Constants.NEW_TASK_ASSIGNEE,asignee);
        setResult(RESULT_OK, intent);
        finish();
    }
}
