package com.example.dudilugasi.doit.activities;

import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.example.dudilugasi.doit.R;
import com.example.dudilugasi.doit.common.Constants;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class EditTaskActivity extends AppCompatActivity {

    private String name;
    private Date date = new Date();
    private Calendar calendar = Calendar.getInstance();
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


        ParseQuery<ParseUser> query = ParseUser.getQuery();

        //query.whereEqualTo("Boy", tf3.getText().toString());   //This is to filter things!
        //query.selectKeys(Arrays.asList("username"));
        query.findInBackground(new FindCallback<ParseUser>() {
            public void done(List<ParseUser> objects, ParseException e) {
                if (e == null) {

                    int len = objects.size();
                    String[] list1Strings22 = new String[len];
                    // list1Strings22[0] = "test";

                    for (int i = 0; i < len; i++) {
                        list1Strings22[i] = objects.get(i).getString("username");
                    }

                    Toast toast = Toast.makeText(getApplicationContext(), "test", Toast.LENGTH_LONG);
                    toast.show();
                    ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(EditTaskActivity.this, android.R.layout.simple_spinner_dropdown_item, list1Strings22);
                    users.setAdapter(stringArrayAdapter);

                } else {
                    Toast toast = Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_LONG);
                    toast.show();
                }
            }

        });



        category = intent.getStringExtra(Constants.NEW_TASK_CATEGORY);
        priority = intent.getIntExtra(Constants.NEW_TASK_PRIORITY, 1);
        room = intent.getStringExtra(Constants.NEW_TASK_LOCATION);
        asignee = intent.getStringExtra(Constants.NEW_TASK_ASSIGNEE);
        name = intent.getStringExtra(Constants.NEW_TASK_NAME);
            
        taskId = intent.getStringExtra(Constants.EDIT_TASK_ID);

            if(name != null){ date = (Date) intent.getSerializableExtra(Constants.NEW_TASK_DUE_DATE);}
        else{date.setTime(0);}

        EditText temp = (EditText) findViewById(R.id.task_name_text);
        temp.setText(name, TextView.BufferType.EDITABLE);

        Spinner temp1 = (Spinner) findViewById(R.id.task_category_spinner);
        ArrayAdapter myAdap = (ArrayAdapter) temp1.getAdapter();
        int spinnerPosition = myAdap.getPosition(category);
        temp1.setSelection(spinnerPosition);


        RadioGroup temp2 = (RadioGroup) findViewById(R.id.radio_group);
        temp2.check(priority);

        temp = (EditText) findViewById(R.id.task_room_num);
        temp.setText(room, TextView.BufferType.EDITABLE);

        calendar.setTime(date);
        temp = (EditText) findViewById(R.id.time_text);
        temp.setText(calendar.get(calendar.HOUR_OF_DAY)+":"+calendar.get(calendar.HOUR_OF_DAY),TextView.BufferType.EDITABLE);

        temp = (EditText) findViewById(R.id.date_text);
        temp.setText(calendar.get(calendar.DAY_OF_WEEK)+"/"+calendar.get(calendar.MONTH)+"/"+(calendar.get(calendar.YEAR)));


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
        this.calendar.set(calendar.HOUR_OF_DAY, Integer.parseInt(time1[0]));
        this.calendar.set(calendar.MINUTE , Integer.parseInt(time1[1]));

        time = (EditText) findViewById(R.id.date_text); //date
        String[] date1 = time.getText().toString().split("/");
        this.calendar.set(calendar.DATE, Integer.parseInt(date1[0]));
        this.calendar.set(calendar.MONTH, Integer.parseInt(date1[1]));
        this.calendar.set(calendar.YEAR, Integer.parseInt(date1[2]));
        date = calendar.getTime();





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
