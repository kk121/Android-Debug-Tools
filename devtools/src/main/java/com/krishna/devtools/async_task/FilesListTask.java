package com.krishna.devtools.async_task;

import android.content.Context;
import android.os.AsyncTask;

import com.krishna.devtools.listener.FilesListListener;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Created by krishna on 12/01/18.
 */
public class FilesListTask extends AsyncTask<Void, Void, List<File>> {
    private WeakReference<Context> contextWeakReference;
    private FilesListListener filesListListener;

    public FilesListTask(Context context, FilesListListener filesListListener) {
        contextWeakReference = new WeakReference<>(context);
        this.filesListListener = filesListListener;
    }

    @Override
    protected List<File> doInBackground(Void... voids) {
//        return DBUtils.getAllSharedPreferences(contextWeakReference.get());
        return null;
    }

    @Override
    protected void onPostExecute(List<File> dbPojos) {
        super.onPostExecute(dbPojos);
        if (filesListListener != null)
            filesListListener.onFilesListFetched(dbPojos);
    }
}
