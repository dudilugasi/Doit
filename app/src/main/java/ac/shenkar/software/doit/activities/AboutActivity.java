package ac.shenkar.software.doit.activities;
import android.os.Bundle;


import ac.shenkar.software.doit.common.ToolbarOptions;

public class AboutActivity extends ToolbarOptions {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(ac.shenkar.software.doit.R.layout.activity_about);
    }
}
