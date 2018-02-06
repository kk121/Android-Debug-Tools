package com.krishna.debug_tools.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.krishna.debug_tools.R;

import java.util.List;
import java.util.Map;

/**
 * Created by krishna on 12/01/18.
 */

public class SharedPreferenceAdapter extends BaseAdapter {
    private List<Map.Entry<String, String>> data;

    public SharedPreferenceAdapter(List<Map.Entry<String, String>> data) {
        this.data = data;
    }

    @Override
    public int getCount() {
        return data != null ? data.size() : 0;
    }

    @Override
    public Map.Entry<String, String> getItem(int position) {
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
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_shared_preference, parent, false);
        }
        TextView tvKey = view.findViewById(R.id.tv_key);
        TextView tvValue = view.findViewById(R.id.tv_value);
        tvKey.setText(getItem(position).getKey());
        tvValue.setText(getItem(position).getValue());
        return view;
    }
}
