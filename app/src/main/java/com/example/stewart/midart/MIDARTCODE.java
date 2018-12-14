package com.example.stewart.midart;

import java.util.Scanner;
import java.lang.Math;

//*Alpha 1.0 Java*
public class MIDARTCODE{
    // Name of player
    private static String playerName;
    private static String playerGender;

    //Stats of player {health,damage,level,exp,gold}
    private static int attack = 0;

    private static int [] playerStats = {20,10,1,0,5};
    private static int playerHealth = playerStats[0];
    private static int [] maxExp = {25,75,175,300};
    // amount of health pots player has
    private static int hpPots = 1;

    //items
    private static int sword = 0;


    //monsters in the game

    //depending on level of player = different more difficult MONSTERS
    private static String [] monsterlevel_1 = {"Fish","Lamb","Red Fish"};
    private static String [] monsterlevel_2 = {"Rabbit, Chicken"};
    private static String [] monsterlevel_3 = {"Mad Chicken","Hog","Swarm of Bats"};
    private static String [] monsterlevel_4 = {"Wolf","Zombine","Troll","Golem"};
    private static String [] monsterlevel_5 = {"Boss"};

    //The monster name, the player fights in the dungeon
    private static String monster = " ";

    //the stats of the monster the player is fighting.
    private static int [] tempMonsterStats = {0,0,0,0,0};
    private static int monsterDamage = 0;
    //Stats for MONSTERS {health,random attack,max gold,min gold,amount of experience points}
    //level 1 monsters
    private static int [] redfishStats = {8,4,5,3,2};

    private static int [] lambStats = {10,0,0,0,10};

    private static int [] fishStats = {/*health*/6,/*attack*/2,/*max gold*/3,/*min*/1,/*experience points*/1};
    //level 2 monsters
    private static int [] rabbitStats = {5,3,5,0,2};

    private static int [] chickenStats = {5,4,5,0,2};
    //level 3 monsters
    private static int [] madchickenStats = {20,5,8,6,5};

    private static int [] hogStats = {19,5,8,5,5};

    private static int [] swarmofbatsStats = {26,2,6,4,5};
    //level 4 monsters
    private static int [] wolfStats = {20,5,0,(int)(Math.random()*15)+10,10};

    private static int [] zombineStats = {20,9,6,(int)(Math.random()*15)+10,10};

    private static int [] trollStats = {25,5,20,15,10};

    private static int [] golemStats = {20,8,15,10,10};
    //level 5 boss
    private static int [] bossStats = {60,/*8*/5,3,(int)(Math.random()*15)+10};

    private static int stopFight = 0;

    //Player enters into the town, and gets to picks where they want to go, the shop or dungeon.
    public static void enterTown () {
        System.out.println("\n\n" + playerName+" enters into the town"
                +"\n---------------------------------------------------------     "
                +"\n|  __      __       .__                                 |      "
                +"\n| /  \\    /  \\ ____ |  |   ____  ____   _____   ____    |"
                +"\n| \\   \\/\\/   // __ \\|  | _/ ___\\/  _ \\ /     \\_/ __ \\   |"
                +"\n|  \\        /\\  ___/|  |_\\  \\__(  <_> )  Y Y  \\  ___/   |"
                +"\n|   \\__/\\  /  \\___  >____/\\___  >____/|__|_|  /\\___  >  |"
                +"\n|        \\/       \\/          \\/            \\/     \\/   |"
                +"\n---------------------------------------------------------     ");

        Scanner scan = new Scanner(System.in);

        int townStop = 0;

        while(townStop == 0){
            System.out.println("\n***********************************************************"
                    +"\nWhat do you want to do?"
                    +"\n\n1. Dungeon"
                    +"\n2. Shop"
                    +"\n3. Stats");


            String town = scan.nextLine();
            if (town.toLowerCase().equals("dungeon") || town.equals("1") || town.toLowerCase().equals("one")){
                System.out.println("\n\nYou have entered the dungeon");
                dungeon();
                townStop = 1;

            }
            else if (town.toLowerCase().equals("shop") || town.equals("2")  || town.toLowerCase().equals("two"))
            {
                shop();
                townStop = 1;
            }
            else if (town.toLowerCase().equals("stats") || town.equals("3")  || town.toLowerCase().equals("three"))
            {
                displayplayerStats();
            }

            else{
                System.out.println("Sorry, but thats not a choice");
            }
        }

    }
    //the dungeon the player fights in
    //fights a monster

    //leveling system

    //player gets exp, gold

    public static void dungeon (){

        Scanner scan = new Scanner(System.in);

        System.out.println("\n\n\n\nYou walk and encounter a "+ monsterBattle());
        stopFight = 0;
        while(stopFight == 0){
            if(tempMonsterStats[0] <= 0){
                System.out.println("\n\n\n\n\n\n"+ monster +" Hp: " +tempMonsterStats[0]
                        +"\n" +playerName + " HP: " +playerHealth);
                System.out.println("\n***YOU WIN!***");
                System.out.println(playerName+" got " +tempMonsterStats[4]+ " experience and " + gold() + " gold.");
                playerStats[3] += tempMonsterStats[4];

                levelUp();

            }

            else{
                System.out.println("\n\n" + monster + " Hp: " +tempMonsterStats[0]
                        +"\n\n" +playerName + " HP: " +playerHealth
                        +"\n\nWhat do you do"
                        +"\n1.Attack"
                        +"\n2.Use hpPot"
                        +"\n3.Run (70% CHANCE)"
                        +"\n4.Stats");

                String doWhat = scan.nextLine();

                if(doWhat.toLowerCase().equals("attack") || doWhat.equals("1")){
                    System.out.println("\n\n\n" +playerName+ " hit the "+ monster +" for " + playerAttack() + "Hp" );
                    tempMonsterStats[0] = tempMonsterStats[0] - attack;

                    monsterDamage = (int)monsterAttack();
                    System.out.println("\n"+ playerName +" gets damaged for " + monsterDamage + "Hp");
                    playerHealth = playerHealth - monsterDamage;
                    if (playerHealth <= 0){
                        System.out.println("You Lose");
                        System.exit(0);
                    }
                    stopFight = 0;

                }

                else if(doWhat.toLowerCase().equals("use hpot") || doWhat.equals("2")){
                    if (playerHealth == playerStats[0]){
                        System.out.println("\n\n"+ playerName+ " is at max health.");
                        System.out.println("(Did not use HpPot)");
                    }
                    else{
                        if (hpPots > 0){
                            int stop = 0;
                            while (playerHealth != playerStats[0] || stop == 5){
                                playerHealth ++;
                                stop++;
                            }
                            hpPots --;
                            System.out.println(playerName + " used hpPot (healed for 5 hp)");
                        }
                        else {
                            System.out.println("You dont have any HpPots");
                        }
                    }
                }

                else if(doWhat.toLowerCase().equals("run") || doWhat.equals("3")){
                    if (escape() <= 7){
                        stopFight = 1;
                        System.out.println("You Ran"
                                +"\n\nYou escaped");

                        int stop = 0;
                        while(stop == 0){
                            System.out.println("\n\nDo you want to continue or go back to town"
                                    +"\n1.Contine"
                                    +"\n2.Town"
                                    +"\n3.Stats");

                            String whatNow = scan.nextLine();

                            if(whatNow.toLowerCase().equals("continue") || whatNow.equals("1")){
                                stop = 1;
                                dungeon();
                            }
                            else if(whatNow.toLowerCase().equals("town") || whatNow.equals("2")){
                                stop = 1;
                                enterTown();
                            }
                            else if (whatNow.toLowerCase().equals("stats") || whatNow.equals("3")){
                                displayplayerStats();
                                stopFight = 0;

                            }
                            else{
                                System.out.println("Sorry, but thats not a choice");
                                stop = 0;
                            }
                        }
                    }
                    else {
                        System.out.println("\ncould not run away");

                        monsterDamage = (int)monsterAttack();
                        System.out.println("\n\n"+ playerName +" gets damaged for " + monsterDamage);
                        playerHealth = playerHealth - monsterDamage;

                        if (playerHealth <= 0){
                            System.out.println("You Lose");
                            System.exit(0);
                        }
                        stopFight = 0;
                    }
                }
                else if (doWhat.toLowerCase().equals("stats") || doWhat.equals("4")){
                    displayplayerStats();
                    stopFight = 0;

                }
                else{
                    System.out.println("Sorry, but thats not a choice");
                }
            }
        }
    }

    //the different monster the player battles in the dungeon depending on player level
    public static String monsterBattle(){

        if(playerStats [2] == 1){
            monster = monsterlevel_1[(int)(Math.random()*3)];
            if (monster.toLowerCase().equals("fish")){
                for(int i = 0; i < fishStats.length; i++)
                {
                    tempMonsterStats[i] = fishStats[i];
                }
                return monster;
            }
            else if (monster.toLowerCase().equals("red fish")){
                for(int i = 0; i < redfishStats.length; i++)
                {
                    tempMonsterStats[i] = redfishStats[i];
                }
                return monster;
            }
            else if (monster.toLowerCase().equals("lamb")){
                for(int i = 0; i < lambStats.length; i++)
                {
                    tempMonsterStats[i] = lambStats[i];
                }
                return monster;
            }
        }
        else if(playerStats [2] == 2){
            monster = monsterlevel_2[(int)(Math.random()*0)];

            if (monster.toLowerCase().equals("rabbit")){
                for(int i = 0; i < rabbitStats.length; i++){
                    tempMonsterStats[i] = rabbitStats[i];
                }
                return monster;
            }
        }
        else if(playerStats [2] == 3){
            monster = monsterlevel_3[(int)(Math.random()*2)];

            if (monster.toLowerCase().equals("mad chicken")){
                for(int i = 0; i < madchickenStats.length; i++)
                {
                    tempMonsterStats[i] = madchickenStats[i];
                }
                return monster;
            }
            else if (monster.toLowerCase().equals("hog")){
                for(int i = 0; i < hogStats.length; i++)
                {
                    tempMonsterStats[i] = hogStats[i];
                }
                return monster;
            }
            else if (monster.toLowerCase().equals("swarm of bats")){
                for(int i = 0; i < swarmofbatsStats.length; i++)
                {
                    tempMonsterStats[i] = swarmofbatsStats[i];
                    return monster;
                }
            }
        }

        else if(playerStats [2] == 4){
            monster = monsterlevel_4[(int)(Math.random()*3)];

            if (monster.toLowerCase().equals("wolf")){
                for(int i = 0; i < wolfStats.length; i++)
                {
                    tempMonsterStats[i] = wolfStats[i];
                }
                return monster;
            }

            else if (monster.toLowerCase().equals("zombine")){
                for(int i = 0; i < zombineStats.length; i++)
                {
                    tempMonsterStats[i] = zombineStats[i];
                }
                return monster;
            }

            else if (monster.toLowerCase().equals("troll")){
                for(int i = 0; i < trollStats.length; i++)
                {
                    tempMonsterStats[i] = trollStats[i];
                }
                return monster;
            }
        }
    /*else if(playerStats [2] == 5){
     monster = monsterlevel_3[(int)(Math.random()*3)];
     return monster
     }
     */

        return "wrong";

    }




    //amount of damge the player can deal

    public static int playerAttack (){
        attack = (int)(Math.random()*(playerStats[1] -(playerStats[1]-2)+1))+(playerStats[1]-2);
        return attack;
    }



    //the shop
    public static void shop (){
        Scanner scan = new Scanner(System.in);
        System.out.println("\n\n\nWelcome to the shop"
                +"\n\nPlease type what you wanna buy");

        int stopShop = 0;



        while (stopShop == 0){
            System.out.println("\nyou have < "+playerStats[4] + " > gold");
            System.out.println("*******************************");
            System.out.println("Item     Cost     Inventory");
            System.out.println("\nHpPot    (10)        (" + hpPots +")");
            System.out.println("Sword    (50)       ");
            System.out.println("Leave");

            String pick = scan.nextLine();

            if(pick.toLowerCase().equals("sword") && playerStats[4] >= 50){
                if( sword == 0){
                    sword = 1;
                    playerStats[1] += 10;
                    System.out.println(playerName + " attack as been rasied (\n+ 10 attack)\n" +playerName+ "attack is now " +playerStats[1]);
                }
                else {
                    System.out.println("you already brought a sword");
                }


            }
            else if (pick.toLowerCase().equals("hppot") && playerStats[4] >= 10){
                System.out.println(playerName + " brought a potion");
                hpPots += 1;
                playerStats[4] = playerStats[4]-10;
            }
            else if (pick.toLowerCase().equals("leave")){
                enterTown();

            }
            else
                System.out.println("Not enough money or thats not a item");
        }
        //the chance to escape
    }
    public static int escape (){

        return (int)(Math.random()*10)+1;
    }


    //the amount of gold depending on the monster
    public static int gold(){
        int amount = (int)(Math.random()*(tempMonsterStats[2]-tempMonsterStats[3]))+tempMonsterStats[3];
        playerStats[4] += amount;
        return amount;
    }

    //the amount of damage the mpnster attack depending on monster
    public static double monsterAttack(){
        if (tempMonsterStats[1] == 0){
            return 0;
        }
        else{
            double minAttack = (tempMonsterStats[1]*.75);
            return (int)(Math.random()*(tempMonsterStats[1]- minAttack) +1)+minAttack;
        }
    }
    public static void displayplayerStats() {
        System.out.println("***************");
        System.out.println("\nHealth: "+ playerHealth + "/"+ playerStats[0] +
                "\nAttack: "+ playerStats[1] +
                "\nLevel: " + playerStats[2] +
                "\nExp: "+ playerStats[3] +
                "\nGold: " + playerStats[4] +
                "\nHpPot:" + hpPots +"\n");


    }
    public static void letDelay(String text, int time)
    {
        int activate = 1;
        for(int i=0; i<=text.length()-1;i++){
            System.out.print(text.charAt(i));
            try {
                Thread.sleep(time);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void levelUp(){
        if (playerStats[2] == 1 && playerStats[3] >= maxExp[0]){
            playerStats[2] = 2;
            playerStats[0] =+ 10;
            System.out.println("\n\n***YOU LEVELED UP TO LEVEL " + playerStats[2] +"***");
        }

        else if (playerStats[2] == 2 && playerStats[3] >= maxExp[1]){
            playerStats[2] = 3;
            playerStats[0] =+ 10;
            System.out.println("\n\n***YOU LEVELED UP TO LEVEL " + playerStats[2] +"***");
        }
        else if (playerStats[2] == 3 && playerStats[3] >= maxExp[2]){
            playerStats[2] = 4;
            playerStats[0] =+ 10;
            System.out.println("\n\n***YOU LEVELED UP TO LEVEL " + playerStats[2] +"***");
        }
        else if (playerStats[2] == 4 && playerStats[3] >= maxExp[3]){
            playerStats[2] = 5;
            playerStats[0] =+ 10;
            System.out.println("\n\n***YOU LEVELED UP TO LEVEL " + playerStats[2] +"***");
        }
        else{
            System.out.println("\n\nDo you want to continue or go back to town"
                    +"\n1.Contine"
                    +"\n2.Town"
                    +"\n3.Stats");
        }
        Scanner scan = new Scanner(System.in);
        String whatNow = scan.nextLine();

        if(whatNow.toLowerCase().equals("continue") || whatNow.equals("1")){

            stopFight = 0;
            dungeon();

        }
        else if(whatNow.toLowerCase().equals("town") || whatNow.equals("2")){
            stopFight = 1;
            enterTown();
        }
        else if (whatNow.toLowerCase().equals("stats") || whatNow.equals("3")){
            displayplayerStats();
            stopFight = 0;

        }
        else{
            System.out.println("Sorry, but thats not a choice");
            stopFight = 0;
        }
    }

    public static void main(String[] args)
    {
        Scanner scan = new Scanner(System.in);

        //ask for player name

        //the start of the game


        int readyStop = 0;
        while(readyStop == 0){
            System.out.println("Enter your name");
            playerName = scan.nextLine();

            System.out.println("\n" + playerName + " is your  name?"
                    +"\n\nType 1 or 2"
                    +"\n\n1. Yes"
                    +"\n2. No");
            String ready = scan.nextLine();
            if (ready.toLowerCase().equals("yes") || ready.equals("1")){
                readyStop = 1;

            }

            else if (ready.toLowerCase().equals("no") || ready.equals("2")){
                readyStop = 0;
            }

            else
                System.out.println("Sorry, but thats not a choice");

        }

        int genderStop = 0;
        while(genderStop == 0){
            System.out.println("\nAre you a male or female?");
            playerGender = scan.nextLine();
            if (playerGender.toLowerCase().equals("male") || playerGender.equals("1")){
                playerGender = "his";
                genderStop = 1;
            }

            else if (playerGender.toLowerCase().equals("female") || playerGender.equals("2")){
                playerGender = "her";
                genderStop = 1;
            }
            else{
                System.out.println("Sorry, but thats not a choice");
                genderStop = 0;
            }
        }


        letDelay("\n" + playerName + " you are about to embark on an adventure, are you ready?"
                +"\n\nType 1 or 2"
                +"\n\n1. Yes"
                +"\n2. No\n",18);

        int readyStop2 = 0;

        while(readyStop2 == 0){
            String ready = scan.nextLine();
            if (ready.toLowerCase().equals("yes") || ready.equals("1")){
                System.out.println("\n\nOkay lets go!\n\n"
                        + playerName + " takes "+ playerGender +" life savings (5 gold) and "
                        + "leaves cozy little home and walk to the town\n\n");
                System.out.println("Press enter to continue");
                scan.nextLine();

                enterTown();
                readyStop2 = 1;

            }

            else if (ready.toLowerCase().equals("no") || ready.equals("2")){
                System.out.println("maybe you should just turn around! (you turn around and go home)"
                        +"\n\nYOU LOSE"
                        +"PLAY AGAIN!");
                break;
            }

            else
                System.out.println("Sorry, but thats not a choice");



        }

    }
}
