package com.example.booklist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.yaml.snakeyaml.scanner.Constant;

public class OptionsActivity extends AppCompatActivity {
    private static final String LOG_TAG = OptionsActivity.class.getSimpleName();

    TextView optionsInfo;
    EditText editShow;
    Button cancelButton;
    Button okButton;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        optionsInfo = findViewById(R.id.textViewInfoLabelO);
        editShow = findViewById(R.id.editTextShowXBookO);
        cancelButton = findViewById(R.id.buttonCancelO);
        okButton = findViewById(R.id.buttonOKO);

        sharedPreferences = this.getSharedPreferences(Constants.COMMON, Context.MODE_PRIVATE);
        String newMax = String.valueOf(sharedPreferences.getInt(Constants.SHOWX, 0));


        if (sharedPreferences.contains(Constants.SHOWX)) {
            editShow.setText(newMax);
            Log.d(LOG_TAG, "onCreate: Number of books to show is: " + newMax);
        } else {
            Toast.makeText(this, "Number of books to be showed is not set, default value is 5.", Toast.LENGTH_LONG);
            Log.d(LOG_TAG, "onCreate: SHOWX is using default value: " + editShow.getText().toString());
        }


        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(LOG_TAG, "onClick: OK BUTTON CLICKED");
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt(Constants.SHOWX, Integer.valueOf(editShow.getText().toString()));
//                editor.apply();
                editor.commit();

                Log.d(LOG_TAG, "onClick: List is set to display a maximum of " + sharedPreferences.getInt(Constants.SHOWX, 0) + " book(s).");
                finish();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(LOG_TAG, "onClick: CANCEL BUTTON CLICKED");
                finish();
                Log.d(LOG_TAG, "onClick: Setting display cancelled...\n" + "Displaying a maximum of" + sharedPreferences.getInt(Constants.SHOWX, 5) + " books.");
            }
        });

    }
}