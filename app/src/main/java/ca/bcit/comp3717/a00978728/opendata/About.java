package ca.bcit.comp3717.a00978728.opendata;

import android.content.ContentResolver;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import static ca.bcit.comp3717.a00978728.opendata.DatabaseHelper.COL4;
import static ca.bcit.comp3717.a00978728.opendata.NameContentProvider.CONTENT_URI;


public class About extends AppCompatActivity {
    private DatabaseHelper helper;
    private TextView text;
    private ContentResolver resolver;
    private String selectedDataset;

    @Override
    protected void onCreate(final Bundle savedInstanceState)
    {
        final Cursor cursor;
        final String[] projection = new String[]{COL4};

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_view);

        text = (TextView) findViewById(R.id.aboutText);
        selectedDataset = getIntent().getStringExtra("Dataset");
        resolver = getContentResolver();
        helper = DatabaseHelper.getInstance(getApplicationContext());

        cursor = resolver.query(CONTENT_URI, projection, null, null, null);
        while (cursor.moveToNext()) {
            if (cursor.getString(2).compareTo(selectedDataset) == 0)
                text.setText("\nAbout\n\n\t" + cursor.getString(3));
        }
    }
}