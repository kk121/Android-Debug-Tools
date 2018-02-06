package com.krishna.debug_tools.async_task;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;

import com.krishna.debug_tools.listener.TableContentListener;
import com.krishna.debug_tools.data.pojo.Table;
import com.krishna.debug_tools.utils.DBUtils;

import java.lang.ref.WeakReference;

/**
 * Created by krishna on 12/01/18.
 */
public class TableContentTask extends AsyncTask<Void, Void, Cursor> {
    private static final String TAG = "DatabaseListTask";
    private WeakReference<Context> contextWeakReference;
    private TableContentListener tableContentListener;
    private Table table;

    public TableContentTask(Context context, Table table, TableContentListener tableContentListener) {
        contextWeakReference = new WeakReference<>(context);
        this.tableContentListener = tableContentListener;
        this.table = table;
    }

    @Override
    protected Cursor doInBackground(Void... voids) {
        return DBUtils.getContentsOfTable(contextWeakReference.get(), table);
    }

    @Override
    protected void onPostExecute(Cursor cursor) {
        super.onPostExecute(cursor);
        if (tableContentListener != null)
            tableContentListener.onTableContentFetched(cursor);
    }
}
