package eu.one2many.bastiaan.one2manypoc.database;

import android.provider.BaseColumns;

/**
 * Contract class defining the database used for storing the received messages.
 */
public final class MessageSaverContract {

    public static final String DB_NAME = "message_db";

    // The class has a private constructor to prevent instantiation.
    private MessageSaverContract(){}

    // Inner class defining the table containing the saved messages.
    public static class MessageEntry implements BaseColumns {

        public static final String TABLE_NAME = "messages";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_MESSAGE = "text";
        public static final String COLUMN_NAME_DATE = "date";

    }

}
