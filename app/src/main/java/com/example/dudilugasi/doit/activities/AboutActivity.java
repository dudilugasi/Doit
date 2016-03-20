package com.example.dudilugasi.doit.activities;
import android.os.Bundle;
import com.example.dudilugasi.doit.R;
import com.example.dudilugasi.doit.common.ToolbarOptions;

public class AboutActivity extends ToolbarOptions {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
    }
}
