package com.krishna.debug_tools.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.krishna.debug_tools.utils.DBUtils;
import com.krishna.debug_tools.fragment.FragmentFilesList;
import com.krishna.debug_tools.R;

import java.io.File;
import java.util.List;

/**
 * Created by krishna on 12/01/18.
 */

public class SimpleListAdapter extends BaseAdapter {
    private List<File> data;
    private int fileType;

    public SimpleListAdapter(int fileType, List<File> data) {
        this.fileType = fileType;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data != null ? data.size() : 0;
    }

    @Override
    public File getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_simple_list, parent, false);
        }
        TextView tvName = view.findViewById(R.id.tv_name);
        ImageView ivIcon = view.findViewById(R.id.iv_icon);
        String fileName = getItem(position).getName();
        if (fileType == FragmentFilesList.TYPE_SHARED_PREF) {
            fileName = DBUtils.removePackageFromFileName(fileName);
        }
        tvName.setText(fileName);
        //set icon
        if (getItem(position).isDirectory()) {
            ivIcon.setImageResource(R.drawable.ic_folder);
        } else {
            ivIcon.setImageResource(R.drawable.ic_file);
        }
        return view;
    }
}
