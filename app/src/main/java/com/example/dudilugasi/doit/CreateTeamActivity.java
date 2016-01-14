package com.example.dudilugasi.doit;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CreateTeamActivity extends AppCompatActivity {

    private String teamName;
    private String memberName;
    private String memberMail;
    private String memberPhone;
    private Button sendButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_team);

        sendButton = (Button)findViewById(R.id.send_button);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get the inputs the user inserted
                teamName = ((EditText)findViewById(R.id.team_name_input)).getText().toString();
                memberName = ((EditText)findViewById(R.id.mamber_name_input)).getText().toString();
                memberMail = ((EditText)findViewById(R.id.member_mail_input)).getText().toString();
                memberPhone = ((EditText)findViewById(R.id.member_phone_input)).getText().toString();

                Toast.makeText(v.getContext(),teamName,Toast.LENGTH_LONG).show();

            }
        });
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Inflate the menu
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.settings_action) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
