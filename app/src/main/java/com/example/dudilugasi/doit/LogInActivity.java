package com.example.dudilugasi.doit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dudilugasi.doit.activities.WaitingTasksActivity;
import com.example.dudilugasi.doit.bl.LoginListener;
import com.example.dudilugasi.doit.common.Constants;
import com.example.dudilugasi.doit.common.LoginController;
import com.example.dudilugasi.doit.common.TaskItem;
import com.example.dudilugasi.doit.common.TeamMember;
import com.example.dudilugasi.doit.dal.DAO;
import com.example.dudilugasi.doit.dal.IDataAccess;

import java.util.Date;
import java.util.List;

//try again
public class LogInActivity extends AppCompatActivity implements LoginListener {
    private TextView nameText;
    private TextView passwordText;
    private TextView phoneText;
    private Button signUpButton;
    private LoginController loginController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        getSupportActionBar().hide();

        loginController = new LoginController(this);

        if (loginController.isLoggedIn()) {
            Intent intent = new Intent(this, WaitingTasksActivity.class);
            startActivity(intent);
        }

        nameText = (TextView) findViewById(R.id.user_name_field);
        passwordText = (TextView) findViewById(R.id.password_field);
        phoneText = (TextView) findViewById(R.id.phone_field);
        signUpButton = (Button)findViewById(R.id.sign_up_button);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loginController.login(nameText.getText().toString(), passwordText.getText().toString());
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

    @Override
    public void onUpdate(int code) {
        if (code == Constants.USER_LOGGED_IN) {
            Intent intent = new Intent(this, WaitingTasksActivity.class);
            startActivity(intent);
        }

        if (code == Constants.USER_LOGGED_IN_FAILED) {
            Toast.makeText(this,"log in failed",Toast.LENGTH_LONG).show();
        }
    }
}
