package com.example.booklist;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.widget.ListView;

import com.example.booklist.databinding.ActivityMainBinding;
import com.github.javafaker.Faker;
import com.mifmif.common.regex.Main;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());

        populateListView();
    }

    private void populateListView() {

        ArrayList<BookData> bookData = new ArrayList<>();
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

    }

}