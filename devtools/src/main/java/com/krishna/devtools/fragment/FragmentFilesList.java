package com.krishna.devtools.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.krishna.devtools.utils.DBUtils;
import com.krishna.devtools.R;
import com.krishna.devtools.adapter.SimpleListAdapter;

import java.io.File;

/**
 * Created by krishna on 12/01/18.
 */

public class FragmentFilesList extends Fragment implements AdapterView.OnItemClickListener {
    public static final String TAG = "FragmentFilesList";
    private static final String ARG_TYPE = "type";
    private static final String ARG_FILE_PATH = "file_path";
    public static final int TYPE_SHARED_PREF = 1;
    public static final int TYPE_FILES = 2;
    private int fileType;
    private Communicator communicator;

    public static FragmentFilesList newInstance(int fileType, String filePath) {
        Bundle args = new Bundle();
        args.putInt(ARG_TYPE, fileType);
        args.putString(ARG_FILE_PATH, filePath);
        FragmentFilesList fragment = new FragmentFilesList();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        ListView listView = view.findViewById(R.id.listview);
        listView.setOnItemClickListener(this);
        listView.setEmptyView(view.findViewById(R.id.tv_empty_state));

        fileType = getArguments().getInt(ARG_TYPE);
        String filePath = getArguments().getString(ARG_FILE_PATH);
        SimpleListAdapter adapter = new SimpleListAdapter(fileType, DBUtils.getAllFilesInDirectory(filePath));
        listView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (communicator != null) {
            File file = (File) parent.getItemAtPosition(position);
            communicator.onFileClick(fileType, file);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        communicator = (Communicator) context;
    }

    public interface Communicator {
        void onFileClick(int fileType, File file);
    }
}
