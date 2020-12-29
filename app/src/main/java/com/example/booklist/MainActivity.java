package com.example.booklist;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.example.booklist.databinding.ActivityMainBinding;
import com.github.javafaker.Faker;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

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
                // Fontos tanulság! A különbség ez a két sor között.
                //Toast.makeText(MainActivity.this, String.valueOf(bookData.get((position))), Toast.LENGTH_SHORT).show();
                Toast.makeText(MainActivity.this, bookData.get(position).getBookName(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}