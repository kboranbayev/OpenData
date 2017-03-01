package ca.bcit.comp3717.a00978728.opendata;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Kuanysh on 09-Feb-17.
 */

public class DatabaseHelper extends SQLiteOpenHelper{
    private static final String DATABASE_NAME = "dataset.db";
    protected static final String TABLE = "opendata";
    private static final String COL1 = "ID";
    protected static final String COL2 = "CATEGORY";
    protected static final String COL3 = "NAME";
    protected static final String COL4 = "ABOUT";
    private static DatabaseHelper instance;

    private DatabaseHelper (Context context){
        super(context, DATABASE_NAME, null, 1);
    }

    public synchronized static DatabaseHelper getInstance(final Context context)
    {
        if(instance == null)
        {
            instance = new DatabaseHelper(context.getApplicationContext());
        }
        return instance;
    }

    @Override
    public void onConfigure(final SQLiteDatabase db)
    {
        super.onConfigure(db);

        setWriteAheadLoggingEnabled(true);
        db.setForeignKeyConstraintsEnabled(true);
    }

    @Override
    public void onCreate(final SQLiteDatabase db)
    {
        final String CREATE_DATA_TABLE;

        CREATE_DATA_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE + " ( " +
                COL1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL2 + " TEXT NOT NULL, " +
                COL3 + " TEXT NOT NULL, " +
                COL4 + " TEXT NOT NULL)";

        db.execSQL(CREATE_DATA_TABLE);
    }

    @Override
    public void onUpgrade(final SQLiteDatabase db,
                          final int oldVersion,
                          final int newVersion)
    {
        db.execSQL("DROP IF TABLE EXISTS " + TABLE);
    }

    public long getNumberOfRows(final SQLiteDatabase db)
    {
        final long numEntries;

        numEntries = DatabaseUtils.queryNumEntries(db, TABLE);

        return (numEntries);
    }

    public void addData(final SQLiteDatabase db,
                        final String category,
                        final String name,
                        final String about)
    {
        final ContentValues contentValues = new ContentValues();

        contentValues.put(COL2, category);
        contentValues.put(COL3, name);
        contentValues.put(COL4, about);

        db.insert(TABLE, null, contentValues);
    }

    public Cursor getAllContents(final Context ctx, SQLiteDatabase db)
    {
        db = this.getReadableDatabase();
        final Cursor cursor;
        cursor = db.query(TABLE,
                null,
                null,
                null,
                null,
                null,
                null,
                null);
        cursor.setNotificationUri(ctx.getContentResolver(), NameContentProvider.CONTENT_URI);
        return cursor;
    }
}