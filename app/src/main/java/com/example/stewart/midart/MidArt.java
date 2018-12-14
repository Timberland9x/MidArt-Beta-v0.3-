package com.example.stewart.midart;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;



public class MidArt extends AppCompatActivity {

    public static String playerName;
    public static String playerGender;
    private EditText inputplayerName;
    private TextView gender;
    CheckBox male;
    CheckBox female;
    private boolean genderIsChecked = false;


    public void playerName() {
        inputplayerName = findViewById(R.id.inputplayerName);
        playerName = inputplayerName.getText().toString();
        playerName = playerName.replaceAll(" ","");
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mid_art);

        //setting player Gender
        male = (CheckBox) findViewById(R.id.male);
        female = (CheckBox) findViewById(R.id.female);

        male.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (male.isChecked()) {
                    Toast.makeText(MidArt.this,
                            "Male",
                            Toast.LENGTH_SHORT)
                            .show();
                    genderIsChecked = true;
                    playerGender = "his";
                    if (female.isChecked()) {
                        female.toggle();

                    }
                } else {
                    Toast.makeText(MidArt.this,
                            "Male checkbox is unchecked",
                            Toast.LENGTH_SHORT)
                            .show();
                    genderIsChecked = false;
                }
            }
        });

        female.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (female.isChecked()) {
                    Toast.makeText(MidArt.this,
                            "Female",
                            Toast.LENGTH_SHORT)
                            .show();
                    genderIsChecked = true;
                    playerGender = "her";
                    if (male.isChecked()) {
                        male.toggle();

                    }
                } else {
                    Toast.makeText(MidArt.this,
                            "Female checkbox is unchecked",
                            Toast.LENGTH_SHORT)
                            .show();
                    genderIsChecked = false;
                }
            }
        });
        Button next1 = (Button) findViewById(R.id.next1);
        next1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                inputplayerName = findViewById(R.id.inputplayerName);
                if (TextUtils.isEmpty(inputplayerName.getText())) {
                    inputplayerName.setError("Name is required!");
                } else {
                    if (!genderIsChecked) {
                        gender = findViewById(R.id.gender);
                        gender.setError("");
                    } else {
                        playerName();
                        Intent intent = new Intent(MidArt.this, Introduction.class);
                        intent.putExtra("playerName", playerName);
                        intent.putExtra("playerGender", playerGender);
                        startActivity(intent);
                    }
                }
            }
        });

    }

    }

