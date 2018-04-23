package com.krishna.debug_tools.adapter;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.krishna.debug_tools.R;


/**
 * Created by krishna on 14/01/18.
 */

public class TableContentViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener, PopupMenu.OnMenuItemClickListener, TextWatcher {
    private int colorWhite;
    private int colorLightGray;

    public TableContentViewHolder(View itemView, Cursor cursor) {
        super(itemView);
        colorWhite = Color.WHITE;
        colorLightGray = itemView.getResources().getColor(R.color.colorGrayLight);
        cursor.moveToFirst();
        int margin = (int) itemView.getResources().getDimension(R.dimen.margin_4dp);
        for (int i = 0; i < cursor.getColumnCount(); i++) {
            TextView editText = new EditText(itemView.getContext());
            editText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(getColumnWidth(itemView.getContext(), cursor.getType(i)), ViewGroup.LayoutParams.WRAP_CONTENT);
            lp.setMargins(margin, 0, margin, 0);
            ((ViewGroup) itemView).addView(editText, lp);
            editText.addTextChangedListener(this);
        }
    }

    public static int getColumnWidth(Context context, int columnType) {
        switch (columnType) {
            case Cursor.FIELD_TYPE_INTEGER:
            case Cursor.FIELD_TYPE_FLOAT:
                return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 90, context.getResources().getDisplayMetrics());
            case Cursor.FIELD_TYPE_STRING:
            case Cursor.FIELD_TYPE_BLOB:
            case Cursor.FIELD_TYPE_NULL:
                return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 200, context.getResources().getDisplayMetrics());
        }
        return ViewGroup.LayoutParams.WRAP_CONTENT;
    }

    public void bindView(int position, Cursor cursor) {
        if (cursor.moveToPosition(position)) {
            ViewGroup row = (ViewGroup) itemView;
            for (int i = 0; i < cursor.getColumnCount(); i++) {
                TextView editText = (TextView) row.getChildAt(i);
                editText.setText(cursor.getString(i));
                editText.setTextColor(Color.GRAY);
                editText.setBackgroundDrawable(null);
                editText.setFocusableInTouchMode(false);
                editText.setOnLongClickListener(this);
            }
            if (position % 2 == 0) {
                row.setBackgroundColor(colorWhite);
            } else {
                row.setBackgroundColor(colorLightGray);
            }
        }
    }

    @Override
    public boolean onLongClick(View v) {
        PopupMenu popupMenu = new PopupMenu(itemView.getContext(), v);
        popupMenu.getMenu().add(0, 1, 1, "Edit");
        popupMenu.getMenu().add(0, 2, 2, "Delete");
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.show();
        return true;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        if (item.getItemId() == 1) {
            ViewGroup row = (ViewGroup) itemView;
            for (int i = 0; i < row.getChildCount(); i++) {
                View column = row.getChildAt(i);
                column.setFocusableInTouchMode(true);
                column.setBackgroundResource(R.drawable.edittext_drawable);
            }
        } else if (item.getItemId() == 2) {

        }
        return true;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
