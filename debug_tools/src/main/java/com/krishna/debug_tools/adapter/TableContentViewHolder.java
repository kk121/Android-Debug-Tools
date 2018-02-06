package com.krishna.debug_tools.adapter;

import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.krishna.debug_tools.R;

/**
 * Created by krishna on 14/01/18.
 */

public class TableContentViewHolder extends RecyclerView.ViewHolder {
    private TextView tvText;

    public TableContentViewHolder(View itemView) {
        super(itemView);
        tvText = itemView.findViewById(R.id.tv_text);
    }

    public void bindView(int position, Cursor cursor) {
        if (cursor != null) {
            int columnCount = cursor.getColumnCount();
            int row = position / columnCount;
            int column = position % columnCount;
            cursor.moveToPosition(row);
            int columnType = cursor.getType(column);
            setWidthOfView(columnType);
            if (row == 0) {
                tvText.setText(cursor.getColumnName(column));
            } else {
                tvText.setText(cursor.getString(column));
            }
        }
    }

    private void setWidthOfView(int columnType) {
        int width;
        switch (columnType) {
            case Cursor.FIELD_TYPE_BLOB:
            case Cursor.FIELD_TYPE_STRING:
                width = (int) tvText.getContext().getResources().getDimension(R.dimen.col_string_width);
                break;
            default:
                width = (int) tvText.getContext().getResources().getDimension(R.dimen.col_default_width);
                break;
        }
        /*View parent = (View) tvText.getParent();
        ViewGroup.LayoutParams params = parent.getLayoutParams();
        params.width = width;
        parent.setLayoutParams(params);*/
        tvText.setWidth(width);
    }
}
