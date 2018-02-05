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
import com.krishna.devtools.R;
import com.krishna.devtools.data.pojo.Table;
import com.krishna.devtools.async_task.TableListTask;
import com.krishna.devtools.adapter.DatabaseListAdapter;

import java.util.List;

/**
 * Created by krishna on 12/01/18.
 */

public class FragmentTablesList extends Fragment implements AdapterView.OnItemClickListener {
    public static final String TAG = "FragmentTablesList";
    private static final String ARG_NAME = "name";
    private ListView listView;
    private Communicator communicator;
    private DBPojo dbPojo;

    public static FragmentTablesList newInstance(DBPojo dbPojo) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_NAME, dbPojo);
        FragmentTablesList fragment = new FragmentTablesList();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        listView = view.findViewById(R.id.listview);
        listView.setOnItemClickListener(this);
        listView.setEmptyView(view.findViewById(R.id.tv_empty_state));

        DatabaseListListener databaseListListener = new DatabaseListListener() {
            @Override
            public void onDatabaseListFetched(List<DBPojo> dbPojoList) {
                DatabaseListAdapter adapter = new DatabaseListAdapter(dbPojoList, DatabaseListAdapter.SHOW_TABLES_LIST);
                listView.setAdapter(adapter);
            }
        };
        dbPojo = (DBPojo) getArguments().getSerializable(ARG_NAME);
        if (dbPojo != null) {
            new TableListTask(getContext(), dbPojo, databaseListListener).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        }
        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (communicator != null) {
            DBPojo tablePojo = (DBPojo) parent.getItemAtPosition(position);
            Table table = new Table();
            table.setDbPojo(dbPojo);
            table.setTableName(tablePojo.getDbName());
            communicator.onTableSelected(table);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        communicator = (Communicator) context;
    }

    public interface Communicator {
        void onTableSelected(Table table);
    }
}
