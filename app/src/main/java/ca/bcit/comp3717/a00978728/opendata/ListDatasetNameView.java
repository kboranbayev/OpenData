package ca.bcit.comp3717.a00978728.opendata;

import android.app.ListActivity;
import android.os.Bundle;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.view.View;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import java.util.ArrayList;

import static ca.bcit.comp3717.a00978728.opendata.DatabaseHelper.COL3;
import static ca.bcit.comp3717.a00978728.opendata.NameContentProvider.CONTENT_URI;

public class ListDatasetNameView extends ListActivity {
    protected DatabaseHelper helper;
    private ArrayList<String> listValues;
    private ContentResolver resolver;

    @Override
    protected void onCreate(final Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_dataset_view);
        String selectedCategory = getIntent().getStringExtra("Category");
        resolver = getContentResolver();
        helper = DatabaseHelper.getInstance(getApplicationContext());
        display(selectedCategory);
    }

    public void display(final String category)
    {
        final Cursor cursor;
        final String[] projection = new String[]{COL3};

        listValues = new ArrayList<>();
        cursor = resolver.query(CONTENT_URI, projection, null, null, null);

        while (cursor.moveToNext())
        {
            if (cursor.getString(1).compareTo(category) == 0)
                listValues.add(cursor.getString(2));

        }

        ArrayAdapter<String> myAdapter = new ArrayAdapter <String>(this,
                R.layout.dataset_names_layout, R.id.listDatasetName, listValues);

        setListAdapter(myAdapter);
    }

    @Override
    protected void onListItemClick(final ListView list,
                                   final View view,
                                   final int position,
                                   final long id)
    {
        super.onListItemClick(list, view, position, id);
        String selectedItem = (String) getListView().getItemAtPosition(position);
        Intent intent = new Intent(ListDatasetNameView.this, About.class);
        intent.putExtra("Dataset", selectedItem);
        startActivity(intent);
    }
}