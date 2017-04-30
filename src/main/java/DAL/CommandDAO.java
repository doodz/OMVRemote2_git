package DAL;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import Client.Host;
import Models.bd.Command;

/**
 * Created by Ividata7 on 10/04/2017.
 */

public class CommandDAO {

    private SQLiteDatabase database;
    private OmvDatabaseHelper dbHelper;

    public CommandDAO(Context context) {
        dbHelper = new OmvDatabaseHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Command createCommand(Command command) {

        ContentValues values = CommandToContentValues(command);
        long insertId = database.insert(CommandTable.TABLE_COMMANDS, null,
                values);
        Cursor cursor = database.query(CommandTable.TABLE_COMMANDS,
                allColumns, CommandTable.COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        Command newCommand = cursorToCommand(cursor);
        cursor.close();
        return newCommand;
    }

    public int deleteCommand(Command command) {
        long id = command.getId();
        Log.i("CommandDAO","Command deleted with id: " + id);
        return database.delete(CommandTable.TABLE_COMMANDS, CommandTable.COLUMN_ID
                + " = " + id, null);
    }

    public int UpdateCommand(Command command){
        ContentValues values = CommandToContentValues(command);
        return database.update(CommandTable.TABLE_COMMANDS, values,  CommandTable.COLUMN_ID + " = " +command.getId(), null);
    }

    public List<Command> getAllCommands() {
        List<Command> commands = new ArrayList<Command>();

        Cursor cursor = database.query(CommandTable.TABLE_COMMANDS,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Command command = cursorToCommand(cursor);
            commands.add(command);
            cursor.moveToNext();
        }

        cursor.close();
        return commands;
    }

    /**
     * Gets a device from the device table.
     *
     * @param id ID of the device
     * @return a {@link Host}
     */
    public Command read(long id) {
        Log.i("CommandDAO","Reading device with id = " + id);

        Cursor cursor = database.query(CommandTable.TABLE_COMMANDS, allColumns,
                CommandTable.COLUMN_ID + " = " +id, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            Command command = cursorToCommand(cursor);
            cursor.close();

            return command;
        } else {
            cursor.close();

            Log.w("CommandDAO","Device with id = {"+id+"} is not in db.");
            return null;
        }
    }


    private String[] ColumnsWithoutId = {  CommandTable.COLUMN_CMD_NAME,CommandTable.COLUMN_CMD_COMMAND,CommandTable.COLUMN_CMD_DESC,CommandTable.COLUMN_CMD_FLAGOUTPUT,
           };

    private String[] allColumns = { CommandTable.COLUMN_ID,CommandTable.COLUMN_CMD_NAME, CommandTable.COLUMN_CMD_COMMAND,CommandTable.COLUMN_CMD_DESC,CommandTable.COLUMN_CMD_FLAGOUTPUT
            };

    private ContentValues CommandToContentValues(Command command)
    {
        ContentValues values = new ContentValues();
        //values.put(CommandTable.COLUMN_ID, host.getId());
        values.put(CommandTable.COLUMN_CMD_NAME, command.getName());
        values.put(CommandTable.COLUMN_CMD_COMMAND, command.getCommand());
        values.put(CommandTable.COLUMN_CMD_DESC, command.getDescription());
        values.put(CommandTable.COLUMN_CMD_FLAGOUTPUT,command.getOutput()?1:0);

        return values;
    }

    private Command cursorToCommand(Cursor cursor) {
        Command command = new Command();
        command.setId(cursor.getLong(0));
        command.setName(cursor.getString(1));
        command.setCommand(cursor.getString(2));
        command.setDescription(cursor.getString(3));
        command.setOutput(cursor.getInt(4)==1);
        return command;
    }
}
