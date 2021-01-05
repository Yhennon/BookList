package com.example.booklist;

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

    private static final String DATABASE_NAME = "books_db";
    private static final int DATABASE_VERSION = 1;
    private static final String CREATE_TABLE = "create table if not exists ";
    private static final String COMMA_SEPARATOR = ", ";
    private static final String SELECT_ALL = "select * from ";
    private static final String STRING_TYPE_50 = " varchar(50)";
    private static final String DELETE_FROM = "delete from ";

    private static final String BOOKS_TABLE = "books";
    private static final String BOOKS_TABLE_BOOKNAME = "bookname";
    private static final String BOOKS_TABLE_DELIVERYADDR = "deliveryaddress";
    private static final String BOOKS_TABLE_BOOKAUTHOR = "bookauthor";
    private static final String BOOKS_TABLE_CONTACT = "contactname";
    private static final String BOOKS_TABLE_DELIVERYDDL = "deliverydeadline";

    private static final String CREATE_BOOKS_TABLE = CREATE_TABLE + BOOKS_TABLE + " ("
            + BOOKS_TABLE_BOOKNAME + STRING_TYPE_50 + COMMA_SEPARATOR
            + BOOKS_TABLE_DELIVERYADDR + STRING_TYPE_50 + COMMA_SEPARATOR
            + BOOKS_TABLE_BOOKAUTHOR + STRING_TYPE_50 + COMMA_SEPARATOR
            + BOOKS_TABLE_CONTACT + STRING_TYPE_50 + COMMA_SEPARATOR
            + BOOKS_TABLE_DELIVERYDDL + STRING_TYPE_50 + ");";

    ActivityMainBinding binding;
    ArrayList<BookData> bookDataList;
    BookDatabase bookDatabase = new BookDatabase(MainActivity.this, DATABASE_NAME, null, DATABASE_VERSION);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());

        Log.d(LOG_TAG, "onCreate: Set the main view");

        bookDataList = new ArrayList<>();

//      bookDatabase.deleteAllBooks();
        populateListView();

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

        binding.floatingActionButtonAddBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(LOG_TAG, "onClick: Floatingbutton clicked");
                Intent intentLaunchManualAdd = new Intent(MainActivity.this, ManualAddActivity.class);
                startActivityForResult(intentLaunchManualAdd, 1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == 5) {
            super.onActivityResult(requestCode, resultCode, data);
            Log.d(LOG_TAG, "onActivityResult: started");

            String ba1 = data.getStringExtra(ManualAddActivity.EXTRA_ADD_BOOKNAME);
            String ba2 = data.getStringExtra(ManualAddActivity.EXTRA_ADD_DELIVERYADDR);
            String ba3 = data.getStringExtra(ManualAddActivity.EXTRA_ADD_BOOKAUTHOR);
            String ba4 = data.getStringExtra(ManualAddActivity.EXTRA_ADD_CONTACTNAME);
            String ba5 = data.getStringExtra(ManualAddActivity.EXTRA_ADD_DELIVERYDDL);

            bookDatabase.addBook(ba1, ba2, ba3, ba4, ba5);
            populateListView();

        } else {
            Log.d(LOG_TAG, "onActivityResult: something went wrong");
        }
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

            bookDataPopulateList.add(new BookData(cur.getString(0),

                    cur.getString(1),

                    cur.getString(2),

                    cur.getString(3),

                    convertAsDate
            ));
        }

        bookDataList.clear();
        bookDataList.addAll(bookDataPopulateList);

        BookDataAdapter bookDataAdapter = new BookDataAdapter(MainActivity.this, R.layout.booklist_layout, bookDataList);// bookDataPopulateList?
        binding.mainListView.setAdapter(bookDataAdapter);
        Log.d(LOG_TAG, "populateListView: There are " + bookDataList.size() +" elements in the list/db" );
    }

    /* MYDATABASE */

    public class BookDatabase extends SQLiteOpenHelper {

        public BookDatabase(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            sqLiteDatabase.execSQL(CREATE_BOOKS_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        }

        /* Összes könyv listázása */
        public Cursor getAllBooks() {
            SQLiteDatabase db = getReadableDatabase();
            String query = SELECT_ALL + BOOKS_TABLE + ";";
            Log.d(LOG_TAG, "getAllBooks: query: " + query);
            Cursor data = db.rawQuery(query, null); // rawQuery : Runs the provided SQL and returns a Cursor over the result set.
            Log.d(LOG_TAG, "getAllUsers: query was succesful");
            // data.close();
            //db.close();
            return data;
        }

        /* 1 könyv hozzáadása */
        public boolean addBook(String bookname, String deliveryaddress, String bookauthor, String contactname, String deliverydeadline) {
            SQLiteDatabase db = getWritableDatabase();
            ContentValues cv = new ContentValues();

            cv.put(BOOKS_TABLE_BOOKNAME, bookname);
            cv.put(BOOKS_TABLE_DELIVERYADDR, deliveryaddress);
            cv.put(BOOKS_TABLE_BOOKAUTHOR, bookauthor);
            cv.put(BOOKS_TABLE_CONTACT, contactname);
            cv.put(BOOKS_TABLE_DELIVERYDDL, deliverydeadline);

            long result = db.insert(BOOKS_TABLE, null, cv); // returns the id of the newly inserted row, or -1 if an error occured

            Log.d(LOG_TAG, "addBook: adding book result: " + result);
            //  db.close();

            if (result == -1) {
                return false;
            } else {
                return true;
            }

        }

        /* Deleting all books from the database */
        public void deleteAllBooks() {
            SQLiteDatabase db = getWritableDatabase();
            db.execSQL(DELETE_FROM + BOOKS_TABLE);
            Log.d(LOG_TAG, "deleteAllBooks: deleting all rows was succesful ");
        }

    }

}