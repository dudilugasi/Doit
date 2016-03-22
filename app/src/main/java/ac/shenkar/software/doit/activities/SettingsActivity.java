package ac.shenkar.software.doit.activities;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import ac.shenkar.software.doit.dal.Constants;
import ac.shenkar.software.doit.common.ToolbarOptions;

public class SettingsActivity extends ToolbarOptions {
    /*
    this activity is irrelevant to our app, because the tasks
    are up to date all the time from the parse server due to activities switching.
    the code below is how we should of done it.
     */
    EditText seconds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(ac.shenkar.software.doit.R.layout.activity_settings);
        seconds = (EditText) findViewById(ac.shenkar.software.doit.R.id.settings_update_every_input);
        SharedPreferences settings = getSharedPreferences(Constants.APP_PREF, 0);
        int currentUpdateEvery = settings.getInt(Constants.UPDATE_EVERY,5);
        seconds.setText(String.valueOf(currentUpdateEvery));
    }

    public void onSaveSettings(View view) {
        int update_every = Integer.parseInt(seconds.getText().toString());
        SharedPreferences settings = getSharedPreferences(Constants.APP_PREF, 0);
        SharedPreferences.Editor settingsEditor = settings.edit();
        settingsEditor.putInt(Constants.UPDATE_EVERY,update_every);
        settingsEditor.apply();
        Toast.makeText(this,"settings saved",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this,WaitingTasksActivity.class);
        startActivity(intent);
    }
}
