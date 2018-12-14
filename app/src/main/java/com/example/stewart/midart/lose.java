package com.example.stewart.midart;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class lose extends AppCompatActivity {

    private Button playAgain;
    public static String playerName;
    public static String playerGender;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lose);

                playAgain = (Button) findViewById(R.id.playAgain);
                playAgain.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(lose.this, MidArt.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("playerName",playerName);
                        bundle.putString("playerGender",playerGender);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                });
            }
        }
