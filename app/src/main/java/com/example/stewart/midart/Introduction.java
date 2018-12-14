package com.example.stewart.midart;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Scanner;

public class Introduction extends AppCompatActivity {

    MediaPlayer song;
    private Button button1;
    private Button button2;


    private String playerName;
    private String playerGender;
    //private int[] maxplayerStats = {20, 10, 1, 0, 5};
    //{health, attack, level, exp, gold}
    private int[] playerStat = {20, 10, 1, 0, 5};


    public void buttonReset() {
        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);

        button1.setText("");
        button2.setText("");
    }

    public void buttonSet(String b1, String b2, String b3) {
        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);

        button1.setText(b1);
        button2.setText(b2);
    }

    public void setText1(String t1) {
        TextView text1 = (TextView) findViewById(R.id.text1);
        text1.setText(t1);

    }


    //Player enters into the town, and gets to picks where they want to go, the shop or dungeon.
    public void enterTown() {
        Intent intent = new Intent(Introduction.this, Town.class);
        intent.putExtra("playerName", playerName);
        intent.putExtra("playerGender", playerGender);
        intent.putExtra("playerStat", playerStat);
        startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduction);
        song = MediaPlayer.create(Introduction.this, R.raw.fantasygameloop);
        song.start();

        Bundle intent = getIntent().getExtras();
        if (intent != null) {
            playerName = intent.getString("playerName");
            playerGender = intent.getString("playerGender");
        }

        setText1(playerName + " you are about to embark on an adventure, are you ready?");

        buttonSet("Yes", "No", "");

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                button2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                    }
                });
                setText1("Okay lets go! " + playerName +
                        " takes " + playerGender +
                        " life savings (5 gold) and leaves " + playerGender +
                        " cozy little home and walks to the town");

                buttonSet("Continue", "", "");

                button1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onPause();
                        enterTown();
                    }
                });
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent lose = new Intent(Introduction.this, lose.class);
                startActivity(lose);
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        song.release();
        finish();
    }
}


//*Alpha 1.0 Java*




