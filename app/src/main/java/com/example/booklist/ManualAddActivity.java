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

    //Ilyenkor ugynazokat a valuekat megadva, ugyanahhoz az adatbázishoz kapcsolódunk(mint mikor máshol hívjuk meg,pl MainActivity-ben)
    BookDatabase bookDatabase = new BookDatabase(ManualAddActivity.this, Constants.DATABASE_NAME, null, Constants.DATABASE_VERSION);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual_add);
        Log.i(LOG_TAG, "onCreate: Manul add started...");

        EditText addBookName = findViewById(R.id.editTextBookNameM);
        EditText addDeliveryAddr = findViewById(R.id.editTextDeliveryAddressM);
        EditText addBookAuthor = findViewById(R.id.editTextBookAuthorM);
        EditText addContactName = findViewById(R.id.editTextContactM);
        EditText addDeliveryDdl = findViewById(R.id.editTextDeliveryDeadlineM);

        Button buttonAddBook = findViewById(R.id.buttonAddBookM);

        buttonAddBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intentManualAdd = new Intent(ManualAddActivity.this, MainActivity.class);

                bookDatabase.addBook(addBookName.getText().toString(), addDeliveryAddr.getText().toString(), addBookAuthor.getText().toString(), addContactName.getText().toString(), addDeliveryDdl.getText().toString());


                setResult(5, intentManualAdd);
                Log.i(LOG_TAG, "onClick: " + addBookName.getText().toString());
                finish();
            }
        });


    }


}