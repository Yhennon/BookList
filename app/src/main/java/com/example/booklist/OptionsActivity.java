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

        sharedPreferences = getPreferences(Context.MODE_PRIVATE);

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(LOG_TAG, "onClick: OK BUTTON CLICKED");
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("SHOWX",Integer.valueOf(editShow.getText().toString()));
                editor.apply();
                Log.d(LOG_TAG, "onClick: List is set to display " +sharedPreferences.getInt("SHOWX",Integer.valueOf(editShow.getText().toString())) + " books." );
                finish();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(LOG_TAG, "onClick: CANCEL BUTTON CLICKED");
                finish();
                Log.d(LOG_TAG, "onClick: Setting display cancelled...\n" + "Displaying " + sharedPreferences.getInt("SHOWX",5) +" books.");
            }
        });

    }
}