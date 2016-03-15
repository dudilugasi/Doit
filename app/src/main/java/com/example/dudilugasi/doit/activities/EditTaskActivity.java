package com.example.dudilugasi.doit.activities;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.dudilugasi.doit.R;
import com.example.dudilugasi.doit.bl.ITaskController;
import com.example.dudilugasi.doit.bl.TaskController;
import com.example.dudilugasi.doit.common.Constants;
import com.example.dudilugasi.doit.dal.IDataAccess;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.io.Console;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class EditTaskActivity extends AppCompatActivity {

    private String name;
    private Calendar date;
    private int priority;
    private String category;
    private String room;
    private String asignee;
    private String taskId;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);
        Intent intent = getIntent();
        final String[] categories = {"Cleaning", "Electricity", "Computers", "General", "Other"};
        ArrayAdapter<String> stringArrayAdapter= new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, categories);
        Spinner spinner = (Spinner)  findViewById(R.id.task_category_spinner);
        spinner.setAdapter(stringArrayAdapter);


        final Spinner users = (Spinner) findViewById(R.id.person_name_spinner);

        ParseQuery<ParseObject> query = ParseQuery.getQuery("User");
        query.selectKeys(Arrays.asList("username"));
        query.findInBackground(new FindCallback<ParseObject>() {

            @Override
            public void done(List<ParseObject> posts, ParseException e) {

                if (e == null) {
                    List<String> postTexts = new ArrayList<String>();
                    for (ParseObject post : posts) {
                        postTexts.add(post.getString("username"));
                    }
                    String[] names = new String[postTexts.size()];
                    names = postTexts.toArray(names);
                    ArrayAdapter<String> stringArrayAdapter = new  ArrayAdapter<String>(EditTaskActivity.this, android.R.layout.simple_spinner_dropdown_item, names);
                    users.setAdapter(stringArrayAdapter);
                    Toast.makeText(EditTaskActivity.this, postTexts.toString(), Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(EditTaskActivity.this, "query error: " + e, Toast.LENGTH_LONG).show();

                }

            }
        });



        category = intent.getStringExtra(Constants.NEW_TASK_CATEGORY);
        priority = intent.getIntExtra(Constants.NEW_TASK_PRIORITY, 1);
        room = intent.getStringExtra(Constants.NEW_TASK_LOCATION);
        asignee = intent.getStringExtra(Constants.NEW_TASK_ASSIGNEE);
        name = intent.getStringExtra(Constants.NEW_TASK_NAME);
        taskId = intent.getStringExtra(Constants.EDIT_TASK_ID);
        if(name == null){date = Calendar.getInstance();}
        else{date = (Calendar) intent.getSerializableExtra(Constants.NEW_TASK_DUE_DATE);}

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
        temp.setText(date.get(date.HOUR_OF_DAY)+":"+date.get(date.HOUR_OF_DAY),TextView.BufferType.EDITABLE);

        temp = (EditText) findViewById(R.id.date_text);
        temp.setText(date.get(date.DAY_OF_WEEK)+"/"+date.get(date.MONTH)+"/"+(date.get(date.YEAR)));


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

        EditText tmp = (EditText) findViewById(R.id.task_name_text);//task name
        name = tmp.getText().toString();
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

        EditText time = (EditText) findViewById(R.id.time_text); // time
        String[] time1 = time.getText().toString().split(":");
        this.date.set(date.HOUR_OF_DAY, Integer.parseInt(time1[0]));
        this.date.set(date.MINUTE , Integer.parseInt(time1[1]));

        time = (EditText) findViewById(R.id.date_text); //date
        String[] date1 = time.getText().toString().split("/");
        this.date.set(date.DATE, Integer.parseInt(date1[0]));
        this.date.set(date.MONTH, Integer.parseInt(date1[1]));
        this.date.set(date.YEAR, Integer.parseInt(date1[2]));






        Spinner spp = (Spinner) findViewById(R.id.person_name_spinner);
       // asignee = spp.getSelectedItem().toString();

        Intent intent = new Intent(this, WaitingTasksActivity.class);
        intent.putExtra(Constants.NEW_TASK_NAME,name);
        intent.putExtra(Constants.NEW_TASK_CATEGORY,category);
        intent.putExtra(Constants.NEW_TASK_LOCATION,room);
        intent.putExtra(Constants.NEW_TASK_PRIORITY,priority);
        intent.putExtra(Constants.NEW_TASK_DUE_DATE, date);
        intent.putExtra(Constants.NEW_TASK_ASSIGNEE,asignee);
        intent.putExtra(Constants.EDIT_TASK_ID,taskId);
        setResult(RESULT_OK, intent);
        finish();
    }
}
