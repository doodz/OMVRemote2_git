package DAL;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by thiba on 11/10/2016.
 */

public class DAOBase {

    protected final static int VERSION = 1;
    // Le nom du fichier qui représente ma base
    protected final static String NOM = "database.db";

    protected SQLiteDatabase mDb = null;
    protected OmvDatabaseHelper dbHelper  = null;

    public DAOBase(Context pContext) {
        this.dbHelper  = new OmvDatabaseHelper(pContext);
    }

    public SQLiteDatabase open() {
        // Pas besoin de fermer la dernière base puisque getWritableDatabase s'en charge
        mDb = dbHelper.getWritableDatabase();
        return mDb;
    }

    public void close() {
        mDb.close();
    }

    public SQLiteDatabase getDb() {
        return mDb;
    }
}
