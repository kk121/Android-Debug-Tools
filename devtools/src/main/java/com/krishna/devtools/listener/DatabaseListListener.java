package com.krishna.devtools.listener;

import com.krishna.devtools.data.pojo.DBPojo;

import java.util.List;

/**
 * Created by krishna on 12/01/18.
 */

public interface DatabaseListListener {
    void onDatabaseListFetched(List<DBPojo> dbPojoList);
}
