package com.example.booklist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import java.util.Date;

public class DetailsAcitivity extends AppCompatActivity {
    private static final String LOG_TAG = DetailsAcitivity.class.getSimpleName();

    TextView bookName;
    TextView deliveryAddr;
    TextView bookAuthor;
    TextView contactName;
    TextView deliveryDdl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_acitivity);

        bookName =  findViewById(R.id.textViewBookNameD);
        deliveryAddr = findViewById(R.id.textViewDeliveryAddressD);
        bookAuthor = findViewById(R.id.textViewBookAuthorD);
        contactName = findViewById(R.id.textViewContactD);
        deliveryDdl = findViewById(R.id.textViewDeliveryDeadlineD);

        Intent intent = getIntent();

        String val1 = intent.getStringExtra(MainActivity.EXTRA_BOOKNAME);
        Log.i(LOG_TAG, "onCreate: Könyv cím: "+val1);
        bookName.setText(val1);

        String val2 = intent.getStringExtra(MainActivity.EXTRA_DELIVERYADDR);
        Log.i(LOG_TAG, "onCreate: Szállítási cím: "+val2);
        deliveryAddr.setText(val2);

        String val3 = intent.getStringExtra(MainActivity.EXTRA_BOOKAUTHOR);
        Log.i(LOG_TAG, "onCreate: Szerző: "+val3);
        bookAuthor.setText(val3);

        String val4 = intent.getStringExtra(MainActivity.EXTRA_CONTACTNAME);
        Log.i(LOG_TAG, "onCreate: Kapcsolattartó: "+val4);
        contactName.setText(val4);

        Date val5 = (Date) intent.getSerializableExtra(MainActivity.EXTRA_DELIVERYDDL);
        Log.i(LOG_TAG, "onCreate: Határidő: "+val5);
        deliveryDdl.setText(val5.toString());

//        String val5 = intent.getStringExtra(MainActivity.EXTRA_DELIVERYDDL);
//        Log.i(LOG_TAG, "onCreate: Szállítási határidő:"+val5);


    }
}