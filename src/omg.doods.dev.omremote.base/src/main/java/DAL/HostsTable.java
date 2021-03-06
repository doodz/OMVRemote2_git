package DAL;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by thiba on 11/10/2016.
 */

public class HostsTable {

    public static final String TABLE_HOST = "host";
    public static final String COLUMN_ID = "hostid";
    public static final String COLUMN_Name = "name";
    public static final String COLUMN_ADDR = "addr";
    public static final String COLUMN_USER = "user";
    public static final String COLUMN_PASS = "pass";
    public static final String COLUMN_MACADDR = "macaddr";
    public static final String COLUMN_WIFIONLY = "wifionly";
    public static final String COLUMN_SSL ="ssl";
    public static final String COLUMN_Port = "port";
    public static final String COLUMN_WOLPORT = "wolport";

    private static final String DATABASE_CREATE = "create table "
            + TABLE_HOST
            + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_Name + " TEXT NOT NULL, "
            + COLUMN_ADDR + " TEXT NOT NULL, "
            + COLUMN_USER + " TEXT NOT NULL, "
            + COLUMN_PASS + " TEXT NOT NULL, "
            + COLUMN_MACADDR + " TEXT, "
            + COLUMN_WIFIONLY +" INTEGER DEFAULT 0, "
            + COLUMN_SSL +" INTEGER DEFAULT 0, "
            + COLUMN_Port +" INTEGER DEFAULT 80, "
            + COLUMN_WOLPORT +" INTEGER DEFAULT 9 "
            + ");";

    public static void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    public static void onUpgrade(SQLiteDatabase database, int oldVersion,
                                 int newVersion) {
        Log.w(HostsTable.class.getName(), "Upgrading database from version "
                + oldVersion + " to " + newVersion
                + ", which will destroy all old data");
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_HOST);
        onCreate(database);
    }

}
