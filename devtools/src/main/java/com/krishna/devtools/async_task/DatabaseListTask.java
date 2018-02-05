package com.krishna.devtools.async_task;

import android.content.Context;
import android.os.AsyncTask;

import com.krishna.devtools.listener.DatabaseListListener;
import com.krishna.devtools.data.pojo.DBPojo;
import com.krishna.devtools.utils.DBUtils;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Created by krishna on 12/01/18.
 */
public class DatabaseListTask extends AsyncTask<Void, Void, List<DBPojo>> {
    private static final String TAG = "DatabaseListTask";
    private WeakReference<Context> contextWeakReference;
    private DatabaseListListener databaseListListener;

    public DatabaseListTask(Context context, DatabaseListListener databaseListListener) {
        contextWeakReference = new WeakReference<>(context);
        this.databaseListListener = databaseListListener;
    }

    @Override
    protected List<DBPojo> doInBackground(Void... voids) {
        return DBUtils.getAllDatabases(contextWeakReference.get());
    }

    @Override
    protected void onPostExecute(List<DBPojo> dbPojos) {
        super.onPostExecute(dbPojos);
        if (databaseListListener != null)
            databaseListListener.onDatabaseListFetched(dbPojos);
    }
}
