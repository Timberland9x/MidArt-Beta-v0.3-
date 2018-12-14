package com.example.stewart.midart;

import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class Town extends AppCompatActivity {
    private String playerName;
    private int attack = 0;
    private String playerGender;
    //{health, attack, level, exp, gold}
    private int[] maxplayerStats = {20, 10, 1, 0, 5};
    private int[] playerStat;
    private int[] maxExp = {25, 75, 175, 300};
    private int sword;
    private int hpPot;
    private Button button1;
    private Button button2;
    private Button button3;
    private Button button4;
    private AlertDialog dialog;

    public void onBackPressed() {
        final AlertDialog.Builder bPressedScreen = new AlertDialog.Builder(Town.this);
        bPressedScreen.setMessage("You can't go back home");
        bPressedScreen.setNegativeButton("Back", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        AlertDialog alertDialog = bPressedScreen.create();
        alertDialog.show();
    }

    public void setText(String t1) {
        TextView text = (TextView) findViewById(R.id.text);
        text.setText(t1);
    }

    public void buttonReset() {
        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
        button4 = (Button) findViewById(R.id.button4);

        button1.setText("");
        button2.setText("");
        button3.setText("");
        button4.setText("");
    }

    public void buttonSet(String b1, String b2, String b3, String b4) {
        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
        button4 = (Button) findViewById(R.id.button4);

        button1.setText(b1);
        button2.setText(b2);
        button3.setText(b3);
        button4.setText(b4);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_town);


        final Bundle intent = getIntent().getExtras();
        if (intent != null) {
            playerName = intent.getString("playerName");
            playerGender = intent.getString("playerGender");
            playerStat = intent.getIntArray("playerStat");
        }
        TextView name = (TextView) findViewById(R.id.name);
        name.setText(playerName);

        setPlayerHealth();

        setPlayerLevel();

        setGold();


        if (playerGender.toLowerCase().equals("his")) {
            ImageView player = (ImageView) findViewById(R.id.player);
            player.setImageResource(R.drawable.playermale);
        } else {
            ImageView player = (ImageView) findViewById(R.id.player);
            player.setImageResource(R.drawable.playerfemale);
        }

        setText(playerName + " enters into the town");
        buttonSet("Dungeon", "Shop", "Stats", "Max Heal");

        button1 = (Button) findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dungeon();
            }
        });

        button2 = (Button) findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shop();
            }
        });

        button3 = (Button) findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showplayerStats();
            }
        });

        button4 = (Button) findViewById(R.id.button4);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                healPlayer();
            }
        });
    }

    public void dungeon() {
        Intent dungeon = new Intent(Town.this, Dungeon.class);
        dungeon.putExtra("playerName", playerName);
        dungeon.putExtra("playerGender", playerGender);
        dungeon.putExtra("maxplayerStats", maxplayerStats);
        dungeon.putExtra("playerStat", playerStat);
        dungeon.putExtra("sword", sword);
        dungeon.putExtra("hpPot", hpPot);
        startActivity(dungeon);
    }

    public void shop() {
        Intent shop = new Intent(Town.this, Shop.class);
        shop.putExtra("playerName", playerName);
        shop.putExtra("playerGender", playerGender);
        shop.putExtra("maxplayerStats", maxplayerStats);
        shop.putExtra("playerStat", playerStat);
        shop.putExtra("sword", sword);
        shop.putExtra("hpPot", hpPot);
        startActivity(shop);
    }

    public void showplayerStats() {
        AlertDialog.Builder stats = new AlertDialog.Builder(Town.this);
        View statsView = getLayoutInflater().inflate(R.layout.statspopup, null);
        stats.setView(statsView);
        dialog = stats.create();
        dialog.show();
        Button statsXbutton = (Button) statsView.findViewById(R.id.statsXbutton);
        statsXbutton.setOnClickListener(new  View.OnClickListener() {
            @Override
            public void onClick(View view) {
            dialog.dismiss();
            }
        });

        TextView name = (TextView) statsView.findViewById(R.id.name);
        name.setText(playerName);

        String h = "Health: " + playerStat[0] + "/" + maxplayerStats[0];
        TextView health = (TextView) statsView.findViewById(R.id.health);
        health.setText(h);

        String l = "Level " + playerStat[2];
        TextView level = (TextView) statsView.findViewById(R.id.level);
        level.setText(l);

        String g = "Gold " + playerStat[4];
        TextView gold = (TextView) statsView.findViewById(R.id.gold);
        gold.setText(g);

        String e = "Exp " + playerStat[3] + "/" + maxExp[playerStat[2]-1];
        TextView playerStats = (TextView) statsView.findViewById(R.id.playerStats);
        playerStats.setText(e);

        if (playerGender.toLowerCase().equals("his")) {
            ImageView player = (ImageView) statsView.findViewById(R.id.player);
            player.setImageResource(R.drawable.playermale);
        } else {
            ImageView player = (ImageView) statsView.findViewById(R.id.player);
            player.setImageResource(R.drawable.playerfemale);
        }

        statsXbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });
    }
    private int[] costForHeal = {10,15,20,25,30};
    public void healPlayer() {

        final AlertDialog.Builder hPlayerScreen = new AlertDialog.Builder(Town.this);
        hPlayerScreen.setCancelable(true);
        hPlayerScreen.setMessage("Do you want to heal up to max health for " + costForHeal[playerStat[2]-1] +  " gold?");
        hPlayerScreen.setNegativeButton("Continue", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();

                if (playerStat[0] == maxplayerStats[0]) {
                    Toast.makeText(Town.this,
                            "max health already",
                            Toast.LENGTH_SHORT)
                            .show();
                    setText(playerName
                            + " is at max health already.");

                }
                else if (playerStat[4] >= costForHeal[playerStat[2]-1]) {
                    Toast.makeText(Town.this,
                            "HEALED",
                            Toast.LENGTH_SHORT)
                            .show();
                    setText(playerName
                            + " is Healed to max health");
                    playerStat[4] = playerStat[4] - 10;
                    setGold();
                    int stop = 0;
                    while (playerStat[0] != maxplayerStats[0]) {
                        playerStat[0]++;
                        stop++;
                    }
                    setPlayerHealth();
                    setGold();
                }
                else {
                    Toast.makeText(Town.this,
                            "Not enough money",
                            Toast.LENGTH_SHORT)
                            .show();
                    setText(playerName
                            + " does not have enough gold");
                }
            }
        });
        hPlayerScreen.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        AlertDialog alertDialog = hPlayerScreen.create();
        alertDialog.show();
    }
    public void setPlayerHealth() {
        String h = "Health: " + playerStat[0] + "/" + maxplayerStats[0];
        TextView health = (TextView) findViewById(R.id.health);
        health.setText(h);
    }
    public void setPlayerLevel(){
        String l = "Level " + playerStat[2];
        TextView level = (TextView) findViewById(R.id.level);
        level.setText(l);
    }
    public void setGold(){
        String g = "Gold " + playerStat[4];
        TextView gold = (TextView) findViewById(R.id.gold);
        gold.setText(g);
    }
}
