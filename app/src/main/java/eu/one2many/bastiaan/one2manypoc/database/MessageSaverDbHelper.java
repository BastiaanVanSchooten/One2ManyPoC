package eu.one2many.bastiaan.one2manypoc.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Helper class for the database. Used to get create the db when it is not yet created, or getting
 * a handle to the database from the application.
 */
public class MessageSaverDbHelper extends SQLiteOpenHelper {

    private static final String SQL_CREATE_MESSAGE_TABLE =
            "CREATE TABLE " + MessageSaverContract.MessageEntry.TABLE_NAME + " (" +
                    MessageSaverContract.MessageEntry._ID + " INTEGER PRIMARY KEY," +
                    MessageSaverContract.MessageEntry.COLUMN_NAME_TITLE + " TEXT," +
                    MessageSaverContract.MessageEntry.COLUMN_NAME_MESSAGE + " TEXT," +
                    MessageSaverContract.MessageEntry.COLUMN_NAME_DATE + " INTEGER)";

    public MessageSaverDbHelper(Context context) {
        super(context, MessageSaverContract.DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_MESSAGE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        // For now this app is only a PoC, so we don't need concern ourselves with
        // an upgrade policy for now.
    }
}