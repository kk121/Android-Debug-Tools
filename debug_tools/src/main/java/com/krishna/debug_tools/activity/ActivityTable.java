package com.krishna.debug_tools.activity;

import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.krishna.debug_tools.R;
import com.krishna.debug_tools.adapter.TableContentRecyclerAdapter;
import com.krishna.debug_tools.adapter.TableContentViewHolder;
import com.krishna.debug_tools.async_task.TableContentTask;
import com.krishna.debug_tools.data.pojo.Table;
import com.krishna.debug_tools.listener.TableContentListener;

/**
 * Created by krishna on 14/01/18.
 */

public class ActivityTable extends AppCompatActivity implements TableContentListener {
    public static final String EXTRA_TABLE = "table";
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.table_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        fetchContentOfTable();
    }

    private void fetchContentOfTable() {
        Table table = (Table) getIntent().getSerializableExtra(EXTRA_TABLE);
        if (table != null) {
            getSupportActionBar().setTitle(table.getTableName());
            new TableContentTask(this, table, this)
                    .executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    @Override
    public void onTableContentFetched(Cursor cursor) {
        if (cursor != null) {
            addTableHeaders(cursor);
            TableContentRecyclerAdapter adapter = new TableContentRecyclerAdapter(cursor);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(adapter);
        } else {
            findViewById(R.id.tv_empty_state).setVisibility(View.VISIBLE);
        }
    }

    private void addTableHeaders(Cursor cursor) {
        cursor.moveToFirst();
        ViewGroup headerLayout = findViewById(R.id.table_header);
        int margin = (int) getResources().getDimension(R.dimen.margin_4dp);
        for (int i = 0; i < cursor.getColumnCount(); i++) {
            TextView textView = new TextView(this);
            textView.setText(cursor.getColumnName(i));
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            textView.setTextColor(Color.BLACK);
            textView.setTypeface(textView.getTypeface(), Typeface.BOLD);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(TableContentViewHolder.getColumnWidth(this, cursor.getType(i)), ViewGroup.LayoutParams.WRAP_CONTENT);
            lp.setMargins(margin, 0, margin, 0);
            headerLayout.addView(textView, lp);
        }
    }
}
