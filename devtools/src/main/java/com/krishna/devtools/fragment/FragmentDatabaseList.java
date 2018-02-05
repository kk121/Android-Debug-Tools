package com.krishna.devtools.fragment;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.krishna.devtools.data.pojo.DBPojo;
import com.krishna.devtools.listener.DatabaseListListener;
import com.krishna.devtools.async_task.DatabaseListTask;
import com.krishna.devtools.R;
import com.krishna.devtools.adapter.DatabaseListAdapter;

import java.util.List;

/**
 * Created by krishna on 12/01/18.
 */

public class FragmentDatabaseList extends Fragment implements AdapterView.OnItemClickListener {
    private ListView listView;
    private Communicator communicator;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        listView = view.findViewById(R.id.listview);
        listView.setOnItemClickListener(this);

        DatabaseListListener databaseListListener = new DatabaseListListener() {
            @Override
            public void onDatabaseListFetched(List<DBPojo> dbPojoList) {
                DatabaseListAdapter adapter = new DatabaseListAdapter(dbPojoList, DatabaseListAdapter.SHOW_DATABASE_LIST);
                listView.setAdapter(adapter);
            }
        };

        new DatabaseListTask(getContext(), databaseListListener).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (communicator != null) {
            DBPojo dbPojo = (DBPojo) parent.getItemAtPosition(position);
            communicator.showListOfTables(dbPojo);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        communicator = (Communicator) context;
    }

    public interface Communicator {
        void showListOfTables(DBPojo dbPojo);
    }
}
