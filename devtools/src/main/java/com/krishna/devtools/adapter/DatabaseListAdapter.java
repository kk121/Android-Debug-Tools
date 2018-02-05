package com.krishna.devtools.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.krishna.devtools.data.pojo.DBPojo;
import com.krishna.devtools.R;

import java.util.List;

/**
 * Created by krishna on 12/01/18.
 */

public class DatabaseListAdapter extends BaseAdapter {
    public static final int SHOW_DATABASE_LIST = 1;
    public static final int SHOW_TABLES_LIST = 2;
    private List<DBPojo> dbPojoList;
    private int itemType;

    public DatabaseListAdapter(List<DBPojo> dbPojoList, int itemType) {
        this.dbPojoList = dbPojoList;
        this.itemType = itemType;
    }

    @Override
    public int getCount() {
        if (dbPojoList != null)
            return dbPojoList.size();
        return 0;
    }

    @Override
    public DBPojo getItem(int position) {
        return dbPojoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_database_list, parent, false);
        }
        TextView tvName = view.findViewById(R.id.tv_db_name);
        TextView tvVersion = view.findViewById(R.id.tv_db_version);
        ImageView ivIcon = view.findViewById(R.id.iv_icon);
        tvName.setText(getItem(position).getDbName());
        if (itemType == SHOW_DATABASE_LIST) {
            tvVersion.setText("version " + String.valueOf(getItem(position).getDbVersion()));
        } else {
            tvVersion.setVisibility(View.GONE);
        }
        ivIcon.setImageResource(R.drawable.ic_file);
        return view;
    }
}
