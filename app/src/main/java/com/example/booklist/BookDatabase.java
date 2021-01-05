package com.example.booklist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;


public class BookDatabase extends SQLiteOpenHelper {
    private static final String LOG_TAG = MainActivity.class.getSimpleName();

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