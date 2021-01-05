package com.example.booklist;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteOpenHelper;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());

        Log.d(LOG_TAG, "onCreate: Set the main view");

        bookData = new ArrayList<>();
        faker = new Faker();

        feltolt();
        populateListView();


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

            Date ba5date = null;
            try {
                ba5date = format.parse(ba5);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            bookData.add(new BookData(String.valueOf(ba1), ba2, ba3, ba4, ba5date));
            populateListView();

        } else {
            Log.d(LOG_TAG, "onActivityResult: something went wrong");
        }
    }

    private void feltolt() {
        bookData.add(new BookData("Anything", "Should", "Do", "ForNow", new Date()));
        bookData.add(new BookData(String.valueOf(faker.book().title()), String.valueOf(faker.address().fullAddress()), String.valueOf(faker.book().author()), String.valueOf(faker.name().fullName()), faker.date().future(360, TimeUnit.DAYS)));
        bookData.add(new BookData(String.valueOf(faker.book().title()), String.valueOf(faker.address().fullAddress()), String.valueOf(faker.book().author()), String.valueOf(faker.name().fullName()), faker.date().future(360, TimeUnit.DAYS)));
        bookData.add(new BookData(String.valueOf(faker.book().title()), String.valueOf(faker.address().fullAddress()), String.valueOf(faker.book().author()), String.valueOf(faker.name().fullName()), faker.date().future(360, TimeUnit.DAYS)));
        bookData.add(new BookData(String.valueOf(faker.book().title()), String.valueOf(faker.address().fullAddress()), String.valueOf(faker.book().author()), String.valueOf(faker.name().fullName()), faker.date().future(360, TimeUnit.DAYS)));
        bookData.add(new BookData(String.valueOf(faker.book().title()), String.valueOf(faker.address().fullAddress()), String.valueOf(faker.book().author()), String.valueOf(faker.name().fullName()), faker.date().future(360, TimeUnit.DAYS)));
    }

    private void populateListView() {
        //Faker is used for quickly generating all kinds of fake data. Read more here: https://github.com/thiagokimo/Faker
        // doc: http://dius.github.io/java-faker/apidocs/index.html
        Log.d(LOG_TAG, "populateListView: data elements " + bookData.size());
        BookDataAdapter bookDataAdapter = new BookDataAdapter(getBaseContext(), R.layout.booklist_layout, bookData);

        binding.mainListView.setAdapter(bookDataAdapter);
    }




}