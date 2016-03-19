package com.example.dudilugasi.doit.common;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.example.dudilugasi.doit.LogInActivity;
import com.example.dudilugasi.doit.R;
import com.example.dudilugasi.doit.activities.AboutActivity;
import com.example.dudilugasi.doit.activities.CreateTeamActivity;
import com.example.dudilugasi.doit.activities.ManageTeamActivity;
import com.example.dudilugasi.doit.activities.SettingsActivity;
import com.example.dudilugasi.doit.activities.WaitingTasksActivity;


public abstract class ToolbarOptions extends AppCompatActivity {


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Inflate the menu
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Intent intent;
        switch(id){
            case R.id.tasks_action:
                intent = new Intent(this,WaitingTasksActivity.class);
                startActivity(intent);
                break;
            case  R.id.settings_action:
                intent = new Intent(this,SettingsActivity.class);
                startActivity(intent);
                break;
            case R.id.logout_action:
                LoginController.logout();
                intent = new Intent(this, LogInActivity.class);
                startActivity(intent);
                break;
            case R.id.manage_action:
                intent = new Intent(this, ManageTeamActivity.class);
                startActivity(intent);
                break;
            case R.id.about_action:
                intent = new Intent(this, AboutActivity.class);
                startActivity(intent);
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
