package com.example.booklist;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import com.example.booklist.databinding.ActivityMainBinding;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    public static final String EXTRA_BOOKNAME = "com.example.android.booklist.extra.BOOKNAME";
    public static final String EXTRA_DELIVERYADDR = "com.example.android.booklist.extra.DELIVERYADDR";
    public static final String EXTRA_BOOKAUTHOR = "com.example.android.booklist.extra.BOOKAUTHOR";
    public static final String EXTRA_CONTACTNAME = "com.example.android.booklist.extra.CONTACTNAME";
    public static final String EXTRA_DELIVERYDDL = "com.example.android.booklist.extra.DELVIERYDDL";


    ActivityMainBinding binding;
    ArrayList<BookData> bookDataList;
    BookDatabase bookDatabase = new BookDatabase(MainActivity.this, Constants.DATABASE_NAME, null, Constants.DATABASE_VERSION);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());

        Log.d(LOG_TAG, "onCreate: Set the main view");

        bookDataList = new ArrayList<>();

//      bookDatabase.deleteAllBooks();

        /* LISTENERS */
        binding.mainListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i(LOG_TAG, "onItemClick: Clicked book at position: " + position);

                Intent intentLaunchDetails = new Intent(MainActivity.this, DetailsAcitivity.class);
                intentLaunchDetails.putExtra(EXTRA_BOOKNAME, bookDataList.get(position).getBookName());
                intentLaunchDetails.putExtra(EXTRA_DELIVERYADDR, bookDataList.get(position).getDeliveryAddress());
                intentLaunchDetails.putExtra(EXTRA_BOOKAUTHOR, bookDataList.get(position).getBookAuthor());
                intentLaunchDetails.putExtra(EXTRA_CONTACTNAME, bookDataList.get(position).getContactName());
                intentLaunchDetails.putExtra(EXTRA_DELIVERYDDL, bookDataList.get(position).getDeliveryDeadline());

                startActivity(intentLaunchDetails);
                Log.i(LOG_TAG, "onItemClick: Detailed view of the book launched");
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        populateListView();
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        if (resultCode == 5) {
//            super.onActivityResult(requestCode, resultCode, data);
//            Log.d(LOG_TAG, "onActivityResult: started");
//
//            String ba1 = data.getStringExtra(ManualAddActivity.EXTRA_ADD_BOOKNAME);
//            String ba2 = data.getStringExtra(ManualAddActivity.EXTRA_ADD_DELIVERYADDR);
//            String ba3 = data.getStringExtra(ManualAddActivity.EXTRA_ADD_BOOKAUTHOR);
//            String ba4 = data.getStringExtra(ManualAddActivity.EXTRA_ADD_CONTACTNAME);
//            String ba5 = data.getStringExtra(ManualAddActivity.EXTRA_ADD_DELIVERYDDL);
//
//            bookDatabase.addBook(ba1, ba2, ba3, ba4, ba5);
//
//        } else {
//            Log.d(LOG_TAG, "onActivityResult: something went wrong");
//        }
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.open_settings:
                Intent intentOptions = new Intent(MainActivity.this, OptionsActivity.class);
                startActivity(intentOptions);
                Log.d(LOG_TAG, "onOptionsItemSelected: Launched OptionsActivity");
                return true;
            case R.id.add_book:
                Log.i(LOG_TAG, "onClick: FloatingButton clicked");
                Intent intentLaunchManualAdd = new Intent(MainActivity.this, ManualAddActivity.class);
                startActivityForResult(intentLaunchManualAdd, 1);
        }
        return false;
    }

    private void populateListView() {
        ArrayList<BookData> bookDataPopulateList = new ArrayList<>();
        Cursor cur = bookDatabase.getAllBooks();

        while (cur.moveToNext()) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
            String convert = cur.getString(4);

            Date convertAsDate = null;
            try {
                convertAsDate = format.parse(convert);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            bookDataPopulateList.add(new BookData(cur.getString(0), cur.getString(1), cur.getString(2), cur.getString(3), convertAsDate));
        }

        bookDataList.clear(); // nagoyn fontos, mivel a populateListView-ot meghíjuk az elején is, h lássuk mi vna a dbben,
        // de mivel akkor is meghívjuk,mikor egy új elemet adunk hozzá, nem akarjuk h a már benne lévőket újra beleszúrja a dbbe.Ehhez kell a clear()
        bookDataList.addAll(bookDataPopulateList);

        BookDataAdapter bookDataAdapter = new BookDataAdapter(MainActivity.this, R.layout.booklist_layout, bookDataList);// bookDataPopulateList?
        binding.mainListView.setAdapter(bookDataAdapter);
        Log.d(LOG_TAG, "populateListView: There are " + bookDataList.size() + " elements in the list/db");
    }

}

