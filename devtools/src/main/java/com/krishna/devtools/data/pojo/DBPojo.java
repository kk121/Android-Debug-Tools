package com.krishna.devtools.data.pojo;

import java.io.Serializable;

/**
 * Created by krishna on 12/01/18.
 */

public class DBPojo implements Serializable {
    private int dbVersion;
    private String dbName;

    public DBPojo() {
    }

    public DBPojo(int dbVersion, String dbName) {
        this.dbVersion = dbVersion;
        this.dbName = dbName;
    }

    public int getDbVersion() {
        return dbVersion;
    }

    public void setDbVersion(int dbVersion) {
        this.dbVersion = dbVersion;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    @Override
    public String toString() {
        return "DBPojo{" +
                "dbVersion=" + dbVersion +
                ", dbName='" + dbName + '\'' +
                '}';
    }
}
