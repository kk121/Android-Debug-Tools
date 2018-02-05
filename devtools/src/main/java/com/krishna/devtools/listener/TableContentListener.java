package com.krishna.devtools.listener;

import android.database.Cursor;

/**
 * Created by krishna on 12/01/18.
 */

public interface TableContentListener {
    void onTableContentFetched(Cursor cursor);
}
