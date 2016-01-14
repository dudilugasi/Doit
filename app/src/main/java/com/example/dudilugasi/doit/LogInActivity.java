package com.example.dudilugasi.doit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.dudilugasi.doit.common.TaskItem;
import com.example.dudilugasi.doit.dal.DAO;
import com.example.dudilugasi.doit.dal.IDataAccess;

import java.util.Date;
import java.util.List;

//try again
public class LogInActivity extends AppCompatActivity {
    private TextView nameText;
    private TextView passwordText;
    private TextView phoneText;
    private Button signUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        getSupportActionBar().hide();

        nameText = (TextView) findViewById(R.id.user_name_field);
        passwordText = (TextView) findViewById(R.id.password_field);
        phoneText = (TextView) findViewById(R.id.phone_field);
        signUpButton = (Button)findViewById(R.id.sign_up_button);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //*********call for authentication**********
                //   Intent intent = new Intent(LogInActivity.this,***next activity***)
                //   String text1 = nameText.getText().toString();
                //   intent.putExtra("name",text1);
                //   String text2 = passwordText.getText().toString();
                //   intent.putExtra("pass",text2);
                //   String text3 = phoneText.getText().toString();
                //   intent.putExtra("phone", text3);
                //   startActivity(intent);
            }
        });
    }

}
