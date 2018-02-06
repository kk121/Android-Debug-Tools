package com.krishna.debug_tools.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.krishna.debug_tools.utils.DBUtils;
import com.krishna.debug_tools.R;
import com.krishna.debug_tools.adapter.SharedPreferenceAdapter;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * Created by krishna on 04/02/18.
 */

public class ActivitySharedPreference extends AppCompatActivity implements AdapterView.OnItemClickListener {
    public static final String EXTRA_PATH = "file_path";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shared_preference);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ListView listView = findViewById(R.id.listview);
        listView.setOnItemClickListener(this);
        listView.setEmptyView(findViewById(R.id.tv_empty_state));

        String sharedPrefFilePath = getIntent().getStringExtra(EXTRA_PATH);
        getSupportActionBar().setTitle(new File(sharedPrefFilePath).getName());

        List<Map.Entry<String, String>> data = DBUtils.getSharedPreferences(sharedPrefFilePath);
        SharedPreferenceAdapter adapter = new SharedPreferenceAdapter(data);
        listView.setAdapter(adapter);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        Toast.makeText(this, "Position: " + position, Toast.LENGTH_SHORT).show();
    }
}
