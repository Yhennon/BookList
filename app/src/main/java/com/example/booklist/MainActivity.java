package com.example.booklist;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteCursorDriver;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQuery;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;

import com.example.booklist.databinding.ActivityMainBinding;
import com.github.javafaker.Faker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    public static final String EXTRA_BOOKNAME = "com.example.android.booklist.extra.BOOKNAME";
    public static final String EXTRA_DELIVERYADDR = "com.example.android.booklist.extra.DELIVERYADDR";
    public static final String EXTRA_BOOKAUTHOR = "com.example.android.booklist.extra.BOOKAUTHOR";
    public static final String EXTRA_CONTACTNAME = "com.example.android.booklist.extra.CONTACTNAME";
    public static final String EXTRA_DELIVERYDDL = "com.example.android.booklist.extra.DELVIERYDDL";

    ActivityMainBinding binding;
    ArrayList<BookData> bookData;
    Faker faker;

    private static final String DATABASE_NAME = "books_db";
    private static final int DATABASE_VERSION = 1;
    private static final String CREATE_TABLE = "create table if not exists ";
    private static final String COMMA_SEPARATOR = ", ";
    private static final String SELECT_ALL = "select * from ";
    private static final String STRING_TYPE_50 = " varchar(50)";


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

    SQLiteDatabase.CursorFactory cursorFactory = new SQLiteDatabase.CursorFactory() {
        @Override
        public Cursor newCursor(SQLiteDatabase sqLiteDatabase, SQLiteCursorDriver sqLiteCursorDriver, String s, SQLiteQuery sqLiteQuery) {
            return null;
        }
    };

    BookDatabase bookDatabase = new BookDatabase(MainActivity.this, DATABASE_NAME, null, DATABASE_VERSION);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());

        Log.d(LOG_TAG, "onCreate: Set the main view");

        bookData = new ArrayList<>();
        faker = new Faker();

//        Log.d(LOG_TAG, "onCreate: TEST BEFORE FELTOLT");
//        feltoltDB();
//        Log.d(LOG_TAG, "onCreate: FELTOLTDB SUCCESFUL");
        populateListView();

//        Log.d(LOG_TAG, "onCreate: GETALLBOOKS SUCCESFUL");


        /* LISTENERS */
        binding.mainListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i(LOG_TAG, "onItemClick: Clicked book at position: " + position);

                Intent intentLaunchDetails = new Intent(MainActivity.this, DetailsAcitivity.class);
                intentLaunchDetails.putExtra(EXTRA_BOOKNAME, bookData.get(position).getBookName());
                intentLaunchDetails.putExtra(EXTRA_DELIVERYADDR, bookData.get(position).getDeliveryAddress());
                intentLaunchDetails.putExtra(EXTRA_BOOKAUTHOR, bookData.get(position).getBookAuthor());
                intentLaunchDetails.putExtra(EXTRA_CONTACTNAME, bookData.get(position).getContactName());
                intentLaunchDetails.putExtra(EXTRA_DELIVERYDDL, bookData.get(position).getDeliveryDeadline());

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
            Log.i(LOG_TAG, "onActivityResult: " + resultCode);
            Log.i(LOG_TAG, "onActivityResult: started");
            super.onActivityResult(requestCode, resultCode, data);

            SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
            // TODO:
            // TALÁN LEGFONTOSABB: EZEKET,MEG PL NULLRA ELLENŐRZÉST  ROSSZ HELYEZN VÉGZEM MOST,NEM?
            // MANUALADDACTIVITYBEN KELLENE, ÉS HA ROSSZ, EL SE ENGEDNI IDE A MAINACTIVITYHEZ
            // Should also accpet formats like:
            // yyyy/m/dd
            // yyyy/mm/d
            // yyyy/m/d
            // yyyy.mm.dd
            // yyyy.mm.d
            // yyyy.m.dd
            // yyyy.m.d
            // maybe use 2 formats, one with /, one with .
            // where there are only 1 m or 1 d, use a leading zero

            String ba1 = data.getStringExtra(ManualAddActivity.EXTRA_ADD_BOOKNAME);
            String ba2 = data.getStringExtra(ManualAddActivity.EXTRA_ADD_DELIVERYADDR);
            String ba3 = data.getStringExtra(ManualAddActivity.EXTRA_ADD_BOOKAUTHOR);
            String ba4 = data.getStringExtra(ManualAddActivity.EXTRA_ADD_CONTACTNAME);
            String ba5 = data.getStringExtra(ManualAddActivity.EXTRA_ADD_DELIVERYDDL);

            bookDatabase.addBook(ba1, ba2, ba3, ba4, ba5);

//            Date ba5date = null;
//            try {
//                ba5date = format.parse(ba5);
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }

//            bookData.add(new BookData(String.valueOf(ba1), ba2, ba3, ba4, ba5));
            populateListView();

        } else {
            Log.d(LOG_TAG, "onActivityResult: something went wrong");
        }
    }

    private void feltoltDB() {
//        bookData.add(new BookData(String.valueOf(faker.book().title()), String.valueOf(faker.address().fullAddress()), String.valueOf(faker.book().author()), String.valueOf(faker.name().fullName()), faker.date().future(360, TimeUnit.DAYS)));
//        bookData.add(new BookData(String.valueOf(faker.book().title()), String.valueOf(faker.address().fullAddress()), String.valueOf(faker.book().author()), String.valueOf(faker.name().fullName()), faker.date().future(360, TimeUnit.DAYS)));
//        bookData.add(new BookData(String.valueOf(faker.book().title()), String.valueOf(faker.address().fullAddress()), String.valueOf(faker.book().author()), String.valueOf(faker.name().fullName()), faker.date().future(360, TimeUnit.DAYS)));
//        bookData.add(new BookData(String.valueOf(faker.book().title()), String.valueOf(faker.address().fullAddress()), String.valueOf(faker.book().author()), String.valueOf(faker.name().fullName()), faker.date().future(360, TimeUnit.DAYS)));
//        bookData.add(new BookData(String.valueOf(faker.book().title()), String.valueOf(faker.address().fullAddress()), String.valueOf(faker.book().author()), String.valueOf(faker.name().fullName()), faker.date().future(360, TimeUnit.DAYS)));
//        BookData dbBookData1 = new BookData(String.valueOf(faker.book().title()), String.valueOf(faker.address().fullAddress()), String.valueOf(faker.book().author()), String.valueOf(faker.name().fullName()), faker.date().future(360, TimeUnit.DAYS));
//        Log.d(LOG_TAG, "feltoltDB: dbBookData1 created");
//        bookDatabase.addBook(dbBookData1.getBookName(),dbBookData1.getDeliveryAddress(),dbBookData1.getBookAuthor(),dbBookData1.getContactName(),dbBookData1.getDeliveryDeadline().toString());
//        Log.d(LOG_TAG, "feltoltDB: dbBookData1 added to database");
    }

    private void populateListView() {
        Log.d(LOG_TAG, "populateListView: data elements " + bookData.size());

        ArrayList<BookData> bookDataPopulate = new ArrayList<>();
        Cursor cur = bookDatabase.getAllBooks();

        while (cur.moveToNext()) {

            bookDataPopulate.add(new BookData(cur.getString(0),

                    cur.getString(1),

                    cur.getString(2),

                    cur.getString(3),

                    cur.getString(4)

            ));

        }

        bookData.clear();
        bookData.addAll(bookDataPopulate);


        BookDataAdapter bookDataAdapter = new BookDataAdapter(MainActivity.this, R.layout.booklist_layout, bookData);// bookDataPopulate?

        binding.mainListView.setAdapter(bookDataAdapter);

    }

    /* MYDATABASE */

    public class BookDatabase extends SQLiteOpenHelper {


        public BookDatabase(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        public BookDatabase(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version, @Nullable DatabaseErrorHandler errorHandler) {
            super(context, name, factory, version, errorHandler);
        }

//        public BookDatabase(@Nullable Context context, @Nullable String name, int version, @NonNull SQLiteDatabase.OpenParams openParams) {
//            super(context, name, version, openParams);
//        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            sqLiteDatabase.execSQL(CREATE_BOOKS_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        }

        //Feladat: Készíts egy SQLite adatbázist a könyvekhez. A könyvek listázását és a hozzáadást oldd meg adatbázissal.

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
            Log.d(LOG_TAG, "addBook: got the DB");
            ContentValues cv = new ContentValues();

            cv.put(BOOKS_TABLE_BOOKNAME, bookname);
            cv.put(BOOKS_TABLE_DELIVERYADDR, deliveryaddress);
            cv.put(BOOKS_TABLE_BOOKAUTHOR, bookauthor);
            cv.put(BOOKS_TABLE_CONTACT, contactname);
            cv.put(BOOKS_TABLE_DELIVERYDDL, deliverydeadline);
            Log.d(LOG_TAG, "addBook: put the contentvalues ");
            long result = db.insert(BOOKS_TABLE, null, cv); // returns the id of the newly inserted row, or -1 if an error occured

            Log.d(LOG_TAG, "addBook: adding book result: " + result);
            //  db.close();

            if (result == -1) {
                return false;
            } else {
                return true;
            }

        }


    }

}