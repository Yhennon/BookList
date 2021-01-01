package com.example.booklist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;

import com.example.booklist.databinding.ActivityMainBinding;
import com.github.javafaker.Faker;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());

        populateListView();
    }

    private void populateListView() {

        bookData = new ArrayList<>();
        //Faker is used for quickly generating all kinds of fake data. Read more here: https://github.com/thiagokimo/Faker
        // doc: http://dius.github.io/java-faker/apidocs/index.html
        Faker faker = new Faker();
        bookData.add(new BookData(String.valueOf(faker.book().title()), String.valueOf(faker.address().fullAddress()), String.valueOf(faker.book().author()), String.valueOf(faker.name().fullName()), faker.date().future(360, TimeUnit.DAYS)));
        bookData.add(new BookData(String.valueOf(faker.book().title()), String.valueOf(faker.address().fullAddress()), String.valueOf(faker.book().author()), String.valueOf(faker.name().fullName()), faker.date().future(360, TimeUnit.DAYS)));
        bookData.add(new BookData(String.valueOf(faker.book().title()), String.valueOf(faker.address().fullAddress()), String.valueOf(faker.book().author()), String.valueOf(faker.name().fullName()), faker.date().future(360, TimeUnit.DAYS)));
        bookData.add(new BookData(String.valueOf(faker.book().title()), String.valueOf(faker.address().fullAddress()), String.valueOf(faker.book().author()), String.valueOf(faker.name().fullName()), faker.date().future(360, TimeUnit.DAYS)));
        bookData.add(new BookData(String.valueOf(faker.book().title()), String.valueOf(faker.address().fullAddress()), String.valueOf(faker.book().author()), String.valueOf(faker.name().fullName()), faker.date().future(360, TimeUnit.DAYS)));

        BookDataAdapter bookDataAdapter = new BookDataAdapter(getBaseContext(), R.layout.booklist_layout, bookData);

        binding.mainListView.setAdapter(bookDataAdapter);


        binding.mainListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // Fontos tanulság! A különbség ez a két sor között,az első az még csak az egész objektet adja vissza,utána még le kell kérni valamelyik értékét.
                //Toast.makeText(MainActivity.this, String.valueOf(bookData.get((position))), Toast.LENGTH_SHORT).show();
                // Toast.makeText(MainActivity.this, bookData.get(position).getBookName(), Toast.LENGTH_SHORT).show();

                Log.d(LOG_TAG, "launchDetailsActivity: initiated");

                Intent intentLaunchDetails = new Intent(MainActivity.this, DetailsAcitivity.class);
                intentLaunchDetails.putExtra(EXTRA_BOOKNAME, bookData.get(position).getBookName());
                intentLaunchDetails.putExtra(EXTRA_DELIVERYADDR, bookData.get(position).getDeliveryAddress());
                intentLaunchDetails.putExtra(EXTRA_BOOKAUTHOR, bookData.get(position).getBookAuthor());
                intentLaunchDetails.putExtra(EXTRA_CONTACTNAME, bookData.get(position).getContactName());
                intentLaunchDetails.putExtra(EXTRA_DELIVERYDDL, bookData.get(position).getDeliveryDeadline());

                startActivity(intentLaunchDetails);
            }
        });
    }

}