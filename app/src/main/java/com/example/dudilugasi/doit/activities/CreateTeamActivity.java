package com.example.dudilugasi.doit.activities;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;


import com.example.dudilugasi.doit.R;
import com.example.dudilugasi.doit.bl.CreateTeamController;
import com.example.dudilugasi.doit.bl.ICreateTeamController;
import com.example.dudilugasi.doit.common.TeamMember;

import java.util.ArrayList;

public class CreateTeamActivity extends AppCompatActivity {
    private EditText name;
    private EditText mail;
    private EditText phone;
    private String teamName;
    private Button sendButton;
    private ImageButton addButton;
    private ArrayList<TeamMember> teamMemberArray;
    private ICreateTeamController controller;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_team);
        controller = new CreateTeamController(this);
        name = (EditText)findViewById(R.id.mamber_name_input);
        mail = (EditText)findViewById(R.id.member_mail_input);
        phone = (EditText)findViewById(R.id.member_phone_input);
        teamMemberArray = new ArrayList<TeamMember>();
        sendButton = (Button)findViewById(R.id.send_button);
        addButton = (ImageButton)findViewById(R.id.plus_buttun);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addMember();
                name.setText("");
                mail.setText("");
                phone.setText("");
            }
        });
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addMember();
                String ben="";
                for (TeamMember t:teamMemberArray) {
                   controller.setMember(t);
                    ben+=t.getName();
                }
                Toast.makeText(v.getContext(),ben,Toast.LENGTH_LONG).show();

            }
        });
    }


    public void addMember(){
       // teamName = ((EditText)findViewById(R.id.team_name_input)).getText().toString();
        TeamMember teamMember = new TeamMember(name.getText().toString(),mail.getText().toString(),phone.getText().toString());
        teamMemberArray.add(teamMember);
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
