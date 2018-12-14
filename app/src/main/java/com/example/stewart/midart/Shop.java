package com.example.stewart.midart;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class Shop extends AppCompatActivity {

    private String playerName;
    private String playerGender;
    private int[] maxplayerStats = {0,0,0,0};
    //{health, attack, level, exp, gold}
    private int[] playerStat = {0,0,0,0,0};
    private int[] maxExp = {25, 75, 175, 300};
    private int sword;
    private int hpPot;
    private Button buy1;
    private Button buy2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        final Bundle intent = getIntent().getExtras();
        if (intent != null) {
            playerName = intent.getString("playerName");
            playerGender = intent.getString("playerGender");
            maxplayerStats = intent.getIntArray("maxplayerStats");
            playerStat = intent.getIntArray(    "playerStat");
            sword = intent.getInt("sword");
            hpPot = intent.getInt("hpPot");
        }
        TextView name = (TextView) findViewById(R.id.name);
        name.setText(playerName);

        String h = "Health " + playerStat[0] + "/" + maxplayerStats[0];
        TextView health = (TextView) findViewById(R.id.health);
        health.setText(h);
        String l = "Level " + playerStat[2];
        TextView level = (TextView) findViewById(R.id.level);
        level.setText(l);
        setGold();


        if (playerGender.toLowerCase().equals("his")) {
            ImageView player = (ImageView) findViewById(R.id.player);
            player.setImageResource(R.drawable.playermale);
        } else {
            ImageView player = (ImageView) findViewById(R.id.player);
            player.setImageResource(R.drawable.playerfemale);
        }

        setText(playerName + " enters into the shop");


        buy1 = (Button) findViewById(R.id.buy1);
        buy1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (playerStat[4] >= 5) {
                    Toast.makeText(Shop.this,
                            "+1 Potion",
                            Toast.LENGTH_SHORT)
                            .show();
                    hpPot += 1;
                    playerStat[4] = playerStat[4] - 5;
                    setGold();

                } else {
                    Toast.makeText(Shop.this,
                            "Not enough money",
                            Toast.LENGTH_SHORT)
                            .show();
                }
            }
        });

        buy2 = (Button) findViewById(R.id.buy2);
        buy2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (playerStat[4] >= 25 && sword == 0) {
                    sword = 1;
                    playerStat[1] += 10;
                    playerStat[4] = playerStat[4] - 25;
                    setGold();
                    setText(playerName + " attack as been rasied (\n+ 10 attack)\n" + playerName + "attack is now " + playerStat[1]);
                } else if (sword == 1) {
                    Toast.makeText(Shop.this,
                            "you already brought a sword",
                            Toast.LENGTH_SHORT)
                            .show();
                } else {
                    Toast.makeText(Shop.this,
                            "Not enough money",
                            Toast.LENGTH_SHORT)
                            .show();
                }


            }
        });
    }
    public void setText(String t1){
        TextView text = (TextView) findViewById(R.id.text);
        text.setText(t1);
    }
    public void setGold(){
        String g = "Gold " + playerStat[4];
        TextView gold = (TextView) findViewById(R.id.gold);
        gold.setText(g);
    }
}
