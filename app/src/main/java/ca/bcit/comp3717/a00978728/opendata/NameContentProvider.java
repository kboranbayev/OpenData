package ca.bcit.comp3717.a00978728.opendata;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

/**
 * Created by Kuanysh on 25-Feb-17.
 */

public class NameContentProvider
        extends ContentProvider
{
    private static final UriMatcher uriMatcher;
    private static final int NAMES_URI = 1;
    private DatabaseHelper namesOpenHelper;
    public static final Uri CONTENT_URI;

    static
    {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI("ca.bcit.comp3717.a00978728.opendata", "dataset", NAMES_URI);
    }

    static
    {
        CONTENT_URI = Uri.parse("content://ca.bcit.comp3717.a00978728.opendata/dataset" );
    }

    @Override
    public boolean onCreate()
    {
        namesOpenHelper = DatabaseHelper.getInstance(getContext());

        return true;
    }

    @Override
    public Cursor query(final Uri      uri,
                        final String[] projection,
                        final String   selection,
                        final String[] selectionArgs,
                        final String   sortOrder)
    {
        final Cursor cursor;

        switch (uriMatcher.match(uri))
        {
            case NAMES_URI:
            {
                final SQLiteDatabase db;

                db     = namesOpenHelper.getWritableDatabase();
                cursor = namesOpenHelper.getAllContents(getContext(), db);
                break;
            }
            default:
            {
                throw new IllegalArgumentException("Unsupported URI: " + uri);
            }
        }
        return (cursor);
    }

    @Override
    public String getType(final Uri uri)
    {
        final String type;

        switch(uriMatcher.match(uri))
        {
            case NAMES_URI:
                type = "vnd.android.cursor.dir/dataset";
                break;
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }

        return (type);
    }

    @Override
    public int delete(final Uri      uri,
                      final String   selection,
                      final String[] selectionArgs)
    {
        // Implement this to handle requests to delete one or more rows.
        //throw new UnsupportedOperationException("Not yet implemented");
        final SQLiteDatabase db = namesOpenHelper.getWritableDatabase();
        int rows; // Number of rows effected

        switch(uriMatcher.match(uri)){
            case NAMES_URI:
                rows = db.delete(DatabaseHelper.TABLE, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        // Because null could delete all rows:
        if(selection == null || rows != 0){
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return 0;
    }

    @Override
    public Uri insert(final Uri           uri,
                      final ContentValues values)
    {
        // TODO: Implement this to handle requests to insert a new row.
        //throw new UnsupportedOperationException("Not yet implemented");

        final SQLiteDatabase db = namesOpenHelper.getWritableDatabase();

        switch(uriMatcher.match(uri)){
            case NAMES_URI:
                db.insert(namesOpenHelper.TABLE, null, values);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        // Use this on the URI passed into the function to notify any observers that the uri has
        // changed.
        getContext().getContentResolver().notifyChange(uri, null);
        return uri;
    }

    @Override
    public int update(final Uri           uri,
                      final ContentValues values,
                      final String        selection,
                      final String[]      selectionArgs)
    {
        // TODO: Implement this to handle requests to update one or more rows.
        //throw new UnsupportedOperationException("Not yet implemented");
        final SQLiteDatabase db = namesOpenHelper.getWritableDatabase();
        int rows;

        switch(uriMatcher.match(uri)){
            case NAMES_URI:
                rows = db.update(namesOpenHelper.TABLE, values, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        if(rows != 0){
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return 0;
    }
}