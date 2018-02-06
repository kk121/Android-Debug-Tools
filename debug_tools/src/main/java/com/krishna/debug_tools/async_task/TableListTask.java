package com.krishna.debug_tools.async_task;

import android.content.Context;
import android.os.AsyncTask;

import com.krishna.debug_tools.listener.DatabaseListListener;
import com.krishna.debug_tools.data.pojo.DBPojo;
import com.krishna.debug_tools.utils.DBUtils;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Created by krishna on 12/01/18.
 */
public class TableListTask extends AsyncTask<Void, Void, List<DBPojo>> {
    private static final String TAG = "DatabaseListTask";
    private WeakReference<Context> contextWeakReference;
    private DatabaseListListener databaseListListener;
    private DBPojo dbPojo;

    public TableListTask(Context context, DBPojo dbPojo, DatabaseListListener databaseListListener) {
        contextWeakReference = new WeakReference<>(context);
        this.databaseListListener = databaseListListener;
        this.dbPojo = dbPojo;
    }

    @Override
    protected List<DBPojo> doInBackground(Void... voids) {
        return DBUtils.getAllTablesFromDb(contextWeakReference.get(), dbPojo);
    }

    @Override
    protected void onPostExecute(List<DBPojo> dbPojos) {
        super.onPostExecute(dbPojos);
        if (databaseListListener != null)
            databaseListListener.onDatabaseListFetched(dbPojos);
    }
}
