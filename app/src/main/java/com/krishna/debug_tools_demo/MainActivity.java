package com.krishna.debug_tools_demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //create a dummy db
        DBHelper.getInstance(this).getReadableDatabase();

        startActivity(new Intent("com.krishna.debug_tools"));
    }
}
