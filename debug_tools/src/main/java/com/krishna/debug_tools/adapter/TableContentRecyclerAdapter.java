package com.krishna.debug_tools.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.krishna.debug_tools.R;

/**
 * Created by krishna on 14/01/18.
 */

public class TableContentRecyclerAdapter extends RecyclerView.Adapter<TableContentViewHolder> {
    private Cursor cursor;
    private Context context;

    public TableContentRecyclerAdapter(Context context, Cursor cursor) {
        this.context = context;
        this.cursor = cursor;
    }

    @Override
    public TableContentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_table_content, parent, false);
        return new TableContentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TableContentViewHolder holder, int position) {
        holder.bindView(position, cursor);
    }

    @Override
    public int getItemCount() {
        if (cursor != null)
            return cursor.getCount();
        return 0;
    }
}
