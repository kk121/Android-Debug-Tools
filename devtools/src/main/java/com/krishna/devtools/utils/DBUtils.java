package com.krishna.devtools.utils;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.support.annotation.NonNull;
import android.webkit.MimeTypeMap;

import com.krishna.devtools.data.DBOpenHelper;
import com.krishna.devtools.data.pojo.DBPojo;
import com.krishna.devtools.data.pojo.Table;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by krishna on 12/01/18.
 */

public class DBUtils {
    private static final String PATH_DATABASE = "/databases/";
    private static final String PATH_SHARED_PREFS = "/shared_prefs/";
    private static final String PATH_FILES = "/files/";

    public static List<File> getAllSharedPreferences(Context context) {
        List<File> sharedPrefList = new ArrayList<>();
        String sharedPrefsPath = getSharedPrefsPath(context);
        List<File> sharedPrefsFileList = getAllFilesInDirectory(sharedPrefsPath);
        sharedPrefList.addAll(sharedPrefsFileList);
        return sharedPrefList;
    }

    public static List<Map.Entry<String, String>> getSharedPreferences(String sharedPrefFilePath) {
        File prefFile = new File(sharedPrefFilePath);
        List<Map.Entry<String, String>> sharedPrefList = new ArrayList<>();
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(prefFile);
            doc.getDocumentElement().normalize();

            NodeList prefList = doc.getChildNodes().item(0).getChildNodes();

            for (int i = 0; i < prefList.getLength(); i++) {
                Node node = prefList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    String tagName = element.getTagName();
                    String key = element.getAttribute("name");
                    String value = null;
                    if (tagName.equals("string"))
                        value = element.getTextContent();
                    else
                        value = element.getAttribute("value");
                    Map.Entry<String, String> entry = new AbstractMap.SimpleEntry<>(key, value);
                    sharedPrefList.add(entry);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sharedPrefList;
    }

    public static List<DBPojo> getAllDatabases(Context context) {
        List<DBPojo> databaseList = new ArrayList<>();
        String databasePath = getDatabasePath(context);
        List<File> dbFileList = getAllFilesInDirectory(databasePath);
        for (File dbFile : dbFileList) {
            try {
                int dbPragmaVersion = getPragmaVersionOfSqliteDb(dbFile);
                DBPojo dbPojo = new DBPojo(dbPragmaVersion, dbFile.getName());
                databaseList.add(dbPojo);
            } catch (IOException e) {
                //ignore this db
            }
        }
        return databaseList;
    }

    public static List<DBPojo> getAllTablesFromDb(Context context, DBPojo dbPojo) {
        List<DBPojo> tablesList = new ArrayList<>();
        if (dbPojo.getDbVersion() <= 0) return tablesList;

        DBOpenHelper dbOpenHelper = new DBOpenHelper(context, dbPojo.getDbName(), null, dbPojo.getDbVersion());
        SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
        String columnTableName = "name";
        Cursor cursor = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                String tableName = cursor.getString(cursor.getColumnIndex(columnTableName));
                tablesList.add(new DBPojo(-1, tableName));
            } while (cursor.moveToNext());
            cursor.close();
        }
        return tablesList;
    }

    public static Cursor getContentsOfTable(Context context, Table table) {
        Cursor cursor = null;
        if (table.getDbPojo().getDbVersion() > 0) {
            DBOpenHelper dbOpenHelper = new DBOpenHelper(context, table.getDbPojo().getDbName(), null, table.getDbPojo().getDbVersion());
            SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
            cursor = db.rawQuery("SELECT * FROM " + table.getTableName(), null);
        }
        return cursor;
    }

    private static String getDatabasePath(Context context) {
        return getAppPath(context) + PATH_DATABASE;
    }

    public static String getSharedPrefsPath(Context context) {
        return getAppPath(context) + PATH_SHARED_PREFS;
    }

    public static String getFilesPath(Context context) {
        return getAppPath(context) + PATH_FILES;
    }

    public static String getAppPath(Context context) {
        if (Build.VERSION.SDK_INT >= 17) {
            return context.getApplicationInfo().dataDir;
        } else {
            return context.getFilesDir().getPath() + context.getPackageName();
        }
    }

    public static List<File> getAllFilesInDirectory(String dirPath) {
        List<File> dbFileList = new ArrayList<>();
        File dir = new File(dirPath);
        if (dir.exists()) {
            File[] files = dir.listFiles();
            if (files != null && files.length > 0) {
                dbFileList.addAll(Arrays.asList(files));
            }
        }
        return dbFileList;
    }

    private static int getPragmaVersionOfSqliteDb(File dbFile) throws IOException {
        RandomAccessFile fp = new RandomAccessFile(dbFile, "r");
        fp.seek(60);
        byte[] buff = new byte[4];
        fp.read(buff, 0, 4);
        return ByteBuffer.wrap(buff).getInt();
    }

    @NonNull
    public static String removePackageFromFileName(String fileName) {
        int lastDot = fileName.lastIndexOf('.');
        if (lastDot != -1) {
            int secondLastDot = fileName.substring(0, lastDot).lastIndexOf('.');
            if (secondLastDot != -1)
                fileName = fileName.substring(secondLastDot + 1);
        }
        return fileName;
    }

    public static String getMimeType(String path) {
        String type = null;
        String extension = MimeTypeMap.getFileExtensionFromUrl(path);
        if (extension != null) {
            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
        }
        if (type == null) {
            type = "*/*";
        }
        return type;
    }
}
