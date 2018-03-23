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



/**
 * Created by thiba on 11/10/2016.
 */

public class HostsDAO {


    private SQLiteDatabase database;
    private OmvDatabaseHelper dbHelper;

    public HostsDAO(Context context) {
        dbHelper = new OmvDatabaseHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Host createHost(Host host) {

        ContentValues values = HostToContentValues(host);
        long insertId = database.insert(HostsTable.TABLE_HOST, null,
                values);
        Cursor cursor = database.query(HostsTable.TABLE_HOST,
                allColumns, HostsTable.COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        Host newComment = cursorToHost(cursor);
        cursor.close();
        return newComment;
    }

    public int deleteHost(Host host) {
        long id = host.getId();
        System.out.println("Host deleted with id: " + id);
        return database.delete(HostsTable.TABLE_HOST, HostsTable.COLUMN_ID
                + " = " + id, null);
    }

    public int UpdateHost(Host host){
        ContentValues values = HostToContentValues(host);
        return database.update(HostsTable.TABLE_HOST, values,  HostsTable.COLUMN_ID + " = " +host.getId(), null);
    }


    public List<Host> getAllHosts() {
        List<Host> hosts = new ArrayList<Host>();

        Cursor cursor = database.query(HostsTable.TABLE_HOST,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Host host = cursorToHost(cursor);
            hosts.add(host);
            cursor.moveToNext();
        }

        cursor.close();
        return hosts;
    }

    /**
     * Gets a device from the device table.
     *
     * @param id ID of the device
     * @return a {@link Host}
     */
    public Host read(long id) {
        Log.i("HostsDAO","Reading device with id = " + id);

        Cursor cursor = database.query(HostsTable.TABLE_HOST, allColumns,
                HostsTable.COLUMN_ID + " = " +id, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            Host host = cursorToHost(cursor);
            cursor.close();

            return host;
        } else {
            cursor.close();

            Log.w("HostsDAO","Device with id = {"+id+"} is not in db.");
            return null;
        }
    }


    private String[] ColumnsWithoutId = { HostsTable.COLUMN_Name,HostsTable.COLUMN_ADDR,
            HostsTable.COLUMN_USER,HostsTable.COLUMN_PASS,HostsTable.COLUMN_Port,HostsTable.COLUMN_SSL,HostsTable.COLUMN_MACADDR,HostsTable.COLUMN_WOLPORT
            ,HostsTable.COLUMN_ADDR_BROADCAST};

    private String[] allColumns = { HostsTable.COLUMN_ID, HostsTable.COLUMN_Name,HostsTable.COLUMN_ADDR,
            HostsTable.COLUMN_USER,HostsTable.COLUMN_PASS,HostsTable.COLUMN_Port,HostsTable.COLUMN_SSL,HostsTable.COLUMN_MACADDR,HostsTable.COLUMN_WOLPORT
            ,HostsTable.COLUMN_ADDR_BROADCAST};

    private ContentValues HostToContentValues(Host host)
    {
        ContentValues values = new ContentValues();
        //values.put(HostsTable.COLUMN_ID, host.getId());
        values.put(HostsTable.COLUMN_Name, host.getName());
        values.put(HostsTable.COLUMN_ADDR, host.getAddr());
        values.put(HostsTable.COLUMN_USER, host.getUser());
        values.put(HostsTable.COLUMN_PASS, host.getPass());
        values.put(HostsTable.COLUMN_Port, host.getPort());
        values.put(HostsTable.COLUMN_SSL, host.getSll()?1:0);
        values.put(HostsTable.COLUMN_MACADDR, host.getMacAddr());
        values.put(HostsTable.COLUMN_WOLPORT, host.getWolport());
        values.put(HostsTable.COLUMN_ADDR_BROADCAST,host.getAddrBroadcast());
        return values;
    }

    private Host cursorToHost(Cursor cursor) {
        Host host = new Host();
        host.setId(cursor.getLong(0));
        host.setName(cursor.getString(1));
        host.setAddr(cursor.getString(2));
        host.setUser(cursor.getString(3));
        host.setPass(cursor.getString(4));
        host.setPort(cursor.getInt(5));
        host.setSll(cursor.getInt(6)==1);
        host.setMacAddr(cursor.getString(7));
        host.setWolport(cursor.getInt(8));
        host.setAddrBroadcast(cursor.getString(9));
        return host;
    }


}
