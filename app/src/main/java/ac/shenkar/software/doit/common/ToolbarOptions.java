package ac.shenkar.software.doit.common;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import ac.shenkar.software.doit.LogInActivity;
import ac.shenkar.software.doit.activities.AboutActivity;
import ac.shenkar.software.doit.activities.ManageTeamActivity;
import ac.shenkar.software.doit.activities.SettingsActivity;
import ac.shenkar.software.doit.activities.WaitingTasksActivity;


public abstract class ToolbarOptions extends AppCompatActivity {
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if(!LoginController.isAdmin())
            menu.findItem(ac.shenkar.software.doit.R.id.manage_action).setVisible(false);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Inflate the menu
        getMenuInflater().inflate(ac.shenkar.software.doit.R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Intent intent;
        switch(id){
            case ac.shenkar.software.doit.R.id.tasks_action:
                intent = new Intent(this,WaitingTasksActivity.class);
                startActivity(intent);
                break;
            case  ac.shenkar.software.doit.R.id.settings_action:
                intent = new Intent(this,SettingsActivity.class);
                startActivity(intent);
                break;
            case ac.shenkar.software.doit.R.id.logout_action:
                LoginController.logout();
                intent = new Intent(this, LogInActivity.class);
                startActivity(intent);
                break;
            case ac.shenkar.software.doit.R.id.manage_action:
                intent = new Intent(this, ManageTeamActivity.class);
                startActivity(intent);
                break;
            case ac.shenkar.software.doit.R.id.about_action:
                intent = new Intent(this, AboutActivity.class);
                startActivity(intent);
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
