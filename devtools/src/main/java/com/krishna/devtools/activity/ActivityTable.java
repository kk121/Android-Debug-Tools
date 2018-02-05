package com.krishna.devtools.activity;

import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.krishna.devtools.utils.FixedGridLayoutManager;
import com.krishna.devtools.R;
import com.krishna.devtools.data.pojo.Table;
import com.krishna.devtools.listener.TableContentListener;
import com.krishna.devtools.adapter.TableContentRecyclerAdapter;
import com.krishna.devtools.async_task.TableContentTask;

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
            FixedGridLayoutManager gridLayoutManager = new FixedGridLayoutManager();
            gridLayoutManager.setTotalColumnCount(cursor.getColumnCount());
            gridLayoutManager.setAutoMeasureEnabled(true);
            TableContentRecyclerAdapter adapter = new TableContentRecyclerAdapter(this, cursor);
            recyclerView.setLayoutManager(gridLayoutManager);
            recyclerView.setAdapter(adapter);
        } else {
            findViewById(R.id.tv_empty_state).setVisibility(View.VISIBLE);
        }
    }
}
