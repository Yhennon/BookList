package com.example.booklist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ManualAddActivity extends AppCompatActivity {

    private static final String LOG_TAG = ManualAddActivity.class.getSimpleName();

    public static final String EXTRA_ADD_BOOKNAME = "com.example.android.booklist.extra.BOOKNAME";
    public static final String EXTRA_ADD_DELIVERYADDR = "com.example.android.booklist.extra.DELIVERYADDR";
    public static final String EXTRA_ADD_BOOKAUTHOR = "com.example.android.booklist.extra.BOOKAUTHOR";
    public static final String EXTRA_ADD_CONTACTNAME = "com.example.android.booklist.extra.CONTACTNAME";
    public static final String EXTRA_ADD_DELIVERYDDL = "com.example.android.booklist.extra.DELVIERYDDL";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual_add);
        Log.i(LOG_TAG, "onCreate: we are here");

        EditText addBookName = findViewById(R.id.editTextBookNameM);
        EditText addDeliveryAddr = findViewById(R.id.editTextDeliveryAddressM);
        EditText addBookAuthor = findViewById(R.id.editTextBookAuthorM);
        EditText addContactName = findViewById(R.id.editTextContactM);
        EditText addDeliveryDdl = findViewById(R.id.editTextDeliveryDeadlineM);

        Button buttonAddBook = findViewById(R.id.buttonAddBookM);

        buttonAddBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(LOG_TAG, "onClick: now we are here");
                Intent intentManualAdd = new Intent(ManualAddActivity.this,MainActivity.class);

                intentManualAdd.putExtra(EXTRA_ADD_BOOKNAME,addBookName.getText().toString());
                intentManualAdd.putExtra(EXTRA_ADD_DELIVERYADDR, addDeliveryAddr.getText().toString());
                intentManualAdd.putExtra(EXTRA_ADD_BOOKAUTHOR,addBookAuthor.getText().toString());
                intentManualAdd.putExtra(EXTRA_ADD_CONTACTNAME,addContactName.getText().toString());
                intentManualAdd.putExtra(EXTRA_ADD_DELIVERYDDL,addDeliveryDdl.getText().toString());


                setResult(5,intentManualAdd);
                Log.i(LOG_TAG, "onClick: "+addBookName.getText().toString());
                finish();
            }
        });


    }




}