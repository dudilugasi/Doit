package ac.shenkar.software.doit;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import ac.shenkar.software.doit.activities.CreateTeamActivity;
import ac.shenkar.software.doit.activities.WaitingTasksActivity;
import ac.shenkar.software.doit.bl.CreateTeamController;
import ac.shenkar.software.doit.bl.LoginListener;
import ac.shenkar.software.doit.common.Constants;
import ac.shenkar.software.doit.common.LoginController;
import ac.shenkar.software.doit.common.TeamMember;
import com.parse.FindCallback;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

//try again
public class LogInActivity extends AppCompatActivity implements LoginListener {
    private TextView nameText;
    private TextView passwordText;
    private TextView phoneText;
    private Button signUpButton;
    private LoginController loginController;
    private boolean hasAdmin = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        getSupportActionBar().hide();
        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.whereEqualTo("admin", true);
        query.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> objects, com.parse.ParseException e) {
                if (e == null) {
                    if(objects.size() > 0){
                        hasAdmin=true;
                    }
                    else{
                        ((TextView)findViewById(R.id.admin_text)).setText(R.string.admin_notice);
                            signUpButton.setText(R.string.sign_up);
                    }

                }
            }
        });

        loginController = new LoginController(this);

        if (loginController.isLoggedIn()) {
            Intent intent = new Intent(this, WaitingTasksActivity.class);
            startActivity(intent);
        }

        nameText = (TextView) findViewById(R.id.user_name_field);
        passwordText = (TextView) findViewById(R.id.password_field);
        signUpButton = (Button)findViewById(R.id.sign_up_button);

        final CreateTeamController controller = new CreateTeamController(this);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(hasAdmin)
                    loginController.login(nameText.getText().toString(), passwordText.getText().toString());
                else{
                    controller.setMember(new TeamMember(nameText.getText().toString(),"admin@gmail.com", passwordText.getText().toString()),true);
                    Intent intent = new Intent(LogInActivity.this, CreateTeamActivity.class);
                    startActivity(intent);
                }

            }
        });

        if (!isNetworkConnected()) {
            Toast.makeText(this,"not connected",Toast.LENGTH_LONG).show();
        }

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

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        if (ni == null) {
            // There are no active networks.
            return false;
        } else
            return true;
    }
}
