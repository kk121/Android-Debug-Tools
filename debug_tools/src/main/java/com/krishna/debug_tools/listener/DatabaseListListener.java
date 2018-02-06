package com.krishna.debug_tools.listener;

import com.krishna.debug_tools.data.pojo.DBPojo;

import java.util.List;

/**
 * Created by krishna on 12/01/18.
 */

public interface DatabaseListListener {
    void onDatabaseListFetched(List<DBPojo> dbPojoList);
}
