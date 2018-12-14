package com.example.stewart.midart;

import android.animation.ObjectAnimator;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.media.MediaPlayer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;

public class Dungeon extends AppCompatActivity {

    MediaPlayer dSong;
    private String playerName;
    private String playerGender;
    private int[] maxplayerStats = {20, 10, 1, 0, 5};
    private int[] playerStat;
    private ImageView player;
    private int attack;
    private Button buttonAttack;
    private Button bag;
    private Button run;
    private TextView monsterName;
    private int hpPot;
    private int sword;
    private int[] maxExp = {25, 75, 175, 300};
    private AlertDialog dialog;
    private TextView monsterHealth;
    private String[] monsterlevel_1 = {"Fish", "Lamb", "Red Fish"};
    private String[] monsterlevel_2 = {"Rabbit", "Chicken"};
    private String[] monsterlevel_3 = {"Mad Chicken", "Hog", "Swarm of Bats"};
    private String[] monsterlevel_4 = {"Wolf", "Zombine", "Troll", "Golem"};
    private String[] monsterlevel_5 = {"Boss"};
    //The monster name, the player fights in the dungeon
    private String monster = "";
    //the stats of the monster the player is fighting.
    private int[] tempMonsterStats = {0, 0, 0, 0, 0};
    private int monsterDamage = 0;
    //Stats for MONSTERS {health,random attack,max gold,min gold,amount of experience points}
    //level 1 monsters
    private int[] redfishStats = {8, 4, 5, 3, 2};
    private int[] lambStats = {10, 0, 0, 0, 10};
    private int[] fishStats = {/*health*/6,/*attack*/2,/*max gold*/3,/*min*/1,/*experience points*/1};
    //level 2 monsters
    private int[] rabbitStats = {5, 3, 5, 0, 2};
    private int[] chickenStats = {5, 4, 5, 0, 2};
    //level 3 monsters
    private int[] madchickenStats = {20, 5, 8, 6, 5};
    private int[] hogStats = {19, 5, 8, 5, 5};
    private int[] swarmofbatsStats = {26, 2, 6, 4, 5};
    //level 4 monsters
    private int[] wolfStats = {20, 5, 0, (int) (Math.random() * 15) + 10, 10};
    private int[] zombineStats = {20, 9, 6, (int) (Math.random() * 15) + 10, 10};
    private int[] trollStats = {25, 5, 20, 15, 10};
    private int[] golemStats = {20, 8, 15, 10, 10};
    //level 5 boss
    private int[] bossStats = {60,/*8*/5, 3, (int) (Math.random() * 15) + 10};
    private String rewardScreenMessage;

    //set the text on the TextView text
    public void setText(String w) {
        TextView text = (TextView) findViewById(R.id.text);
        text.setText(w);
    }

    //set the monster name on monsterName TextView
    public void setMonsterName(String m) {
        TextView monsterName = (TextView) findViewById(R.id.monsterName);
        monsterName.setText(m);
    }

    //alert the player when they hit the back button on the android device
    @Override
    public void onBackPressed() {
        final AlertDialog.Builder bPressedScreen = new AlertDialog.Builder(Dungeon.this);
        bPressedScreen.setMessage("You are in battle!");
        bPressedScreen.setNegativeButton("Back", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        AlertDialog alertDialog = bPressedScreen.create();
        alertDialog.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dungeon);

        super.onPause();
        dSong = MediaPlayer.create(Dungeon.this, R.raw.defenseline);
        dSong.start();

        //get varible data from previous class
        final Bundle intent = getIntent().getExtras();
        if (intent != null) {
            playerName = intent.getString("playerName");
            playerGender = intent.getString("playerGender");
            playerStat = intent.getIntArray("playerStat");
        }
        TextView name = (TextView) findViewById(R.id.name);
        name.setText(playerName);


        if (playerGender.toLowerCase().equals("his")) {
            player = (ImageView) findViewById(R.id.player);
            player.setImageResource(R.drawable.playermale);
        } else {
            player = (ImageView) findViewById(R.id.player);
            player.setImageResource(R.drawable.playerfemale);
        }
        //Start the dungeon method
        dungeon();
    }

    public void dungeon() {

        buttonAttack = (Button) findViewById(R.id.buttonAttack);
        buttonAttack.setText("Attack");

        monsterBattle();
        if (monster.toLowerCase().equals("lamb")) {
            setText("You see a harmless Lamb...");
        } else {
            setText(playerName + " walk and encounter a " + monster);
        }
        setMonsterHealth();
        setPlayerHealth();
        setPlayerLevel();

        buttonAttack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (monster.toLowerCase().equals("lamb")) {
                    setText(playerName + " pets the Lamb and it walks away happy...");
                    playerStat[3] += tempMonsterStats[4];
                    rewardScreen();
                } else {
                    String addText = (playerName + " hits the " + monster + " for " + playerAttack() + " Hp");
                    tempMonsterStats[0] = tempMonsterStats[0] - attack;
                    setMonsterHealth();

                    ObjectAnimator attack = ObjectAnimator.ofFloat(player, "translationY", -100f);
                    attack.setDuration(500);
                    attack.start();

                    monsterDamage = (int) monsterAttack();
                    setText(addText + "\n" + playerName + " gets damaged for " + monsterDamage + "Hp");
                    playerStat[0] = playerStat[0] - monsterDamage;
                    setPlayerHealth();

                    if (playerStat[0] <= 0) {
                        Intent lose = new Intent(Dungeon.this, lose.class);
                        startActivity(lose);
                    }

                    if (tempMonsterStats[0] <= 0) {
                        playerStat[3] += tempMonsterStats[4];
                        rewardScreen();
                    }
                }
            }
        });




               /* if (playerStat[0] == maxplayerStats[0]) {
                    System.out.println(playerName + " is at max health.");
                    System.out.println("(Did not use HpPot)");
                }
                else {
                    if (hpPot > 0) {
                        int stop = 0;
                        while (playerStat[0] != maxplayerStats[0] || stop == 5) {
                            playerStat[0]++;
                            stop++;
                        }
                        hpPot--;
                        setText(playerName + " used hpPot (healed for 5 hp)");
                    }
                    else {
                        System.out.println("You dont have any HpPots");
                    }
                }
            }
                */
        run = (Button) findViewById(R.id.run);
        run.setText("Run");
        run.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (escape() <= 7) {
                    runAwayScreen();

                } else {
                    String text1 = "You could not run away";

                    monsterDamage = (int) monsterAttack();
                    setText(text1 + "\n" + playerName + " gets damaged for " + monsterDamage);
                    playerStat[0] = playerStat[0] - monsterDamage;
                    if (playerStat[0] <= 0) {
                        Intent lose = new Intent(Dungeon.this, lose.class);
                        startActivity(lose);
                    }
                }
            }
        });

        Button playerstats = (Button) findViewById(R.id.playerstats);
        playerstats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showplayerStats();
            }
        });

        Button bag = (Button) findViewById(R.id.bag);
        bag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder openbag = new AlertDialog.Builder(Dungeon.this);
                View bag = getLayoutInflater().inflate(R.layout.bag, null);
                openbag.setView(bag);
                dialog = openbag.create();
                dialog.show();

            }
        });
    }

    public void rewardScreen() {
        final AlertDialog.Builder rScreen = new AlertDialog.Builder(Dungeon.this);

        levelUp();
        rewardScreenMessage = "YOU WIN!\n" + playerName + " got " + tempMonsterStats[4] + " experience and " + gold() + " gold.";
        rScreen.setMessage(rewardScreenMessage);

        rScreen.setNegativeButton("Continue", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
                dungeon();
            }
        });
        rScreen.setPositiveButton("Town", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                enterTown();
            }
        });
        AlertDialog alertDialog = rScreen.create();
        alertDialog.show();

    }

    public void runAwayScreen() {
        final AlertDialog.Builder raScreen = new AlertDialog.Builder(Dungeon.this);
        raScreen.setCancelable(true);
        raScreen.setMessage("You have escaped");
        raScreen.setNegativeButton("Continue", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
                dungeon();
            }
        });
        raScreen.setPositiveButton("Town", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                enterTown();
            }
        });
        AlertDialog alertDialog = raScreen.create();
        alertDialog.show();


    }

    public void monsterBattle() {
        if (playerStat[2] == 1) {
            monster = monsterlevel_1[(int) (Math.random() * 3)];
            if (monster.toLowerCase().equals("fish")) {
                for (int i = 0; i < fishStats.length; i++) {
                    tempMonsterStats[i] = fishStats[i];
                    ImageView monsterPic = (ImageView) findViewById(R.id.monsterPic);
                    monsterPic.setImageResource(R.drawable.fish);
                    setMonsterName(monster);
                    setText("You see a " + monster);
                }
            } else if (monster.toLowerCase().equals("red fish")) {
                for (int i = 0; i < redfishStats.length; i++) {
                    tempMonsterStats[i] = redfishStats[i];
                    ImageView monsterPic = (ImageView) findViewById(R.id.monsterPic);
                    monsterPic.setImageResource(R.drawable.redfish);
                    setMonsterName(monster);
                    setText("You see a " + monster);
                }
            } else if (monster.toLowerCase().equals("lamb")) {
                for (int i = 0; i < lambStats.length; i++) {
                    tempMonsterStats[i] = lambStats[i];
                    ImageView monsterPic = (ImageView) findViewById(R.id.monsterPic);
                    monsterPic.setImageResource(R.drawable.lamb);
                    setMonsterName(monster);

                    Button buttonAttack = (Button) findViewById(R.id.buttonAttack);
                    buttonAttack.setText("Pet");
                }
            }
        } else if (playerStat[2] == 2) {
            monster = monsterlevel_2[(int) (Math.random() * 0)];

            if (monster.toLowerCase().equals("rabbit")) {
                for (int i = 0; i < rabbitStats.length; i++) {
                    tempMonsterStats[i] = rabbitStats[i];
                    setMonsterName(monster);

                    setText("You see a " + monster);
                }
            }
            if (monster.toLowerCase().equals("chicken")) {
                for (int i = 0; i < rabbitStats.length; i++) {
                    tempMonsterStats[i] = chickenStats[i];
                    setMonsterName(monster);

                    setText("You see a " + monster);
                }
            }
        } else if (playerStat[2] == 3) {
            monster = monsterlevel_3[(int) (Math.random() * 2)];

            if (monster.toLowerCase().equals("mad chicken")) {
                for (int i = 0; i < madchickenStats.length; i++) {
                    tempMonsterStats[i] = madchickenStats[i];
                    setMonsterName(monster);
                    setText("You see a " + monster);
                }

            } else if (monster.toLowerCase().equals("hog")) {
                for (int i = 0; i < hogStats.length; i++) {
                    tempMonsterStats[i] = hogStats[i];
                    setMonsterName(monster);
                    setText("You see a " + monster);
                }

            } else if (monster.toLowerCase().equals("swarm of bats")) {
                for (int i = 0; i < swarmofbatsStats.length; i++) {
                    tempMonsterStats[i] = swarmofbatsStats[i];
                    setMonsterName(monster);
                    setText("You see a " + monster);

                }
            }
        } else if (playerStat[2] == 4) {
            monster = monsterlevel_4[(int) (Math.random() * 3)];

            if (monster.toLowerCase().equals("wolf")) {
                for (int i = 0; i < wolfStats.length; i++) {
                    tempMonsterStats[i] = wolfStats[i];
                    setMonsterName(monster);
                    setText("You see a " + monster);
                }

            } else if (monster.toLowerCase().equals("zombine")) {
                for (int i = 0; i < zombineStats.length; i++) {
                    tempMonsterStats[i] = zombineStats[i];
                    setMonsterName(monster);
                    setText("You see a " + monster);
                }

            } else if (monster.toLowerCase().equals("troll")) {
                for (int i = 0; i < trollStats.length; i++) {
                    tempMonsterStats[i] = trollStats[i];
                    setMonsterName(monster);
                    setText("You see a " + monster);
                }

            }
        }
    /*else if(playerStats [2] == 5){
     monster = monsterlevel_3[(int)(Math.random()*3)];
     return monster
     }
     */


    }

    public void setMonsterHealth() {
        TextView monsterHealth = (TextView) findViewById(R.id.monsterHealth);
        String mH = "Health: " + tempMonsterStats[0];
        monsterHealth.setText(mH);
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

    public void enterTown() {
        onPause();
        Intent dungeon = new Intent(Dungeon.this, Town.class);
        dungeon.putExtra("playerName", playerName);
        dungeon.putExtra("playerGender", playerGender);
        dungeon.putExtra("maxplayerStats", maxplayerStats);
        dungeon.putExtra("playerStat", playerStat);
        dungeon.putExtra("sword", sword);
        dungeon.putExtra("hpPot", hpPot);
        startActivity(dungeon);
    }

    public void levelUp() {
        if (playerStat[2] == 1 && playerStat[3] >= maxExp[0]) {
            playerStat[2] = 2;
            playerStat[0] = +10;
            rewardScreenMessage = "YOU LEVELED UP TO LEVEL " + playerStat[2];
        } else if (playerStat[2] == 2 && playerStat[3] >= maxExp[1]) {
            playerStat[2] = 3;
            playerStat[0] = +10;
            rewardScreenMessage = "YOU LEVELED UP TO LEVEL " + playerStat[2];
        } else if (playerStat[2] == 3 && playerStat[3] >= maxExp[2]) {
            playerStat[2] = 4;
            playerStat[0] = +10;
            rewardScreenMessage = "YOU LEVELED UP TO LEVEL " + playerStat[2];
        } else if (playerStat[2] == 4 && playerStat[3] >= maxExp[3]) {
            playerStat[2] = 5;
            playerStat[0] = +10;
            rewardScreenMessage = "YOU LEVELED UP TO LEVEL " + playerStat[2];
        }
        setPlayerLevel();
    }

    public int playerAttack() {
        attack = (int) (Math.random() * (playerStat[1] - (playerStat[1] - 2) + 1)) + (playerStat[1] - 2);
        return attack;
    }

    public int escape() {
        return (int) (Math.random() * 10) + 1;
    }


    //the amount of gold depending on the monster
    public int gold() {
        int amount = (int) (Math.random() * (tempMonsterStats[2] - tempMonsterStats[3])) + tempMonsterStats[3];
        playerStat[4] += amount;
        return amount;
    }

    //the amount of damage the mpnster attack depending on monster
    public double monsterAttack() {
        if (tempMonsterStats[1] == 0) {
            return 0;
        } else {
            double minAttack = (tempMonsterStats[1] * .75);
            return (int) (Math.random() * (tempMonsterStats[1] - minAttack) + 1) + minAttack;
        }
    }

    public void showplayerStats() {
        AlertDialog.Builder stats = new AlertDialog.Builder(Dungeon.this);
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

    @Override
    protected void onPause() {
        super.onPause();
        dSong.release();
        finish();
    }
}
