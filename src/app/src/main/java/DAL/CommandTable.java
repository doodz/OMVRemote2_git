package DAL;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by Ividata7 on 10/04/2017.
 */

public class CommandTable {

    public static final String TABLE_COMMANDS = "Commands";
    public static final String COLUMN_ID = "commandId";

    public static final String COLUMN_CMD_COMMAND = "command";
    public static final String COLUMN_CMD_FLAGOUTPUT = "flag_output";
    public static final String COLUMN_CMD_NAME = "name";
    public static final String COLUMN_CMD_DESC = "description";
    private static final String CREATE_TABLE = "CREATE TABLE "
            + TABLE_COMMANDS
            + " ("
            + COLUMN_ID+ " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
            + COLUMN_CMD_NAME+ " TEXT NOT NULL, "
            + COLUMN_CMD_COMMAND + " TEXT NOT NULL, "
            + COLUMN_CMD_DESC + " TEXT, "
            + COLUMN_CMD_FLAGOUTPUT + " INTEGER NOT NULL"
            +");";

    public static void onCreate(SQLiteDatabase database) {
        database.execSQL(CREATE_TABLE);
    }

    public static void onUpgrade(SQLiteDatabase database, int oldVersion,
                                 int newVersion) {


        if(oldVersion == 2 && newVersion == 3) {
            Log.w(HostsTable.class.getName(), "Upgrading database from version "
                    + oldVersion + " to " + newVersion
                    + ", we will add COMMANDS table");
            onCreate(database);
        }
    }
}
