package com.example.booklist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.ListView;
import android.widget.TextView;

import com.example.booklist.databinding.ActivityMainBinding;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class DetailsAcitivity extends AppCompatActivity {
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
        bookName.setText(val1);
        String val2 = intent.getStringExtra(MainActivity.EXTRA_DELIVERYADDR);
        deliveryAddr.setText(val2);
        String val3 = intent.getStringExtra(MainActivity.EXTRA_BOOKAUTHOR);
        bookAuthor.setText(val3);
        String val4 = intent.getStringExtra(MainActivity.EXTRA_CONTACTNAME);
        contactName.setText(val4);
        String val5 = intent.getStringExtra(MainActivity.EXTRA_DELIVERYDDL);
        deliveryDdl.setText(val5);



    }
}