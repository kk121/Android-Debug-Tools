package com.krishna.debug_tools.data.pojo;

import java.io.Serializable;

/**
 * Created by krishna on 14/01/18.
 */

public class Table implements Serializable{
    private String tableName;
    private DBPojo dbPojo;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public DBPojo getDbPojo() {
        return dbPojo;
    }

    public void setDbPojo(DBPojo dbPojo) {
        this.dbPojo = dbPojo;
    }
}
