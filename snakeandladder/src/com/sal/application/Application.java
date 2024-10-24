package com.sal.application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class Application {
  
  private static final Scanner in = new Scanner(System.in);

  public static void main(String[] args) {
    // TODO Auto-generated method stub
    start();

  }

  private static void start() {
    // TODO Auto-generated method stub
    System.out.println("Welcome to the game!");
    
    int i = getNumberOfPlayers();
    
    playGame(i);
    
    
  }

  private static void playGame(int i) {
    // TODO Auto-generated method stub
    if(i!=0) {
      System.out.println("Game started with "+i+" players!");
      List<String> playerNames = getPlayerNames(i);
      String winner = null;
      Map<String,Integer> playerGame = new HashMap<String,Integer>();
      Map<Integer,Integer> ladderMap = new HashMap<Integer,Integer>();
      Map<Integer,Integer> snakeMap = new HashMap<Integer,Integer>();
      ladderMap.put(3, 21);
      ladderMap.put(8, 30);
      ladderMap.put(28, 84);
      ladderMap.put(58, 77);
      ladderMap.put(75, 88);
      ladderMap.put(80, 100);
      ladderMap.put(90, 91);
      
      snakeMap.put(17, 12);
      snakeMap.put(52, 29);
      snakeMap.put(57, 40);
      snakeMap.put(62, 22);
      snakeMap.put(88, 18);
      snakeMap.put(95, 51);
      snakeMap.put(97, 79);
      
      while (winner == null) {
        for(String player: playerNames) {
          System.out.println(player+" Press Y to roll the dice!");
          String diceRoll = in.next();
          if(!diceRoll.equalsIgnoreCase("Y")) {
            System.out.println(player+" choose not to roll the dice!");
            continue;
          }
          Random rand = new Random();
          int game = rand.nextInt(1, 7);
          System.out.println(player+" rolled "+game);
          
          if(game == 6) {
            while(game == 6 && winner == null) {
              winner = play(player,game,playerGame,ladderMap,snakeMap,winner);
              System.out.println("You played a 6 earlier! Play again");
              System.out.println(player+" Press Y to roll the dice!");
              diceRoll = in.next();
              if(!diceRoll.equalsIgnoreCase("Y")) {
                System.out.println(player+" choose not to roll the dice!");
                game = 0;
                continue;
              }
              game = rand.nextInt(1, 7);
            }
            winner = play(player,game,playerGame,ladderMap,snakeMap,winner);
            if(winner!=null)
              break;
          }
          else {
            winner = play(player,game,playerGame,ladderMap,snakeMap,winner);
            if(winner!=null)
              break;
          }
          
        }
      }
      
      System.out.println("Game Ended! Want to Restart the Game???(Y/N):");
      String restart = in.next();
      if(restart.equalsIgnoreCase("Y")) {
        start();
      }
    }
    
  }

  private static String play(String player, int game, Map<String, Integer> playerGame, Map<Integer, Integer> ladderMap, Map<Integer, Integer> snakeMap, String winner) {
    // TODO Auto-generated method stub
    if(playerGame.containsKey(player)) {
      int score = playerGame.get(player);
      int newScore = score+game;
      if(newScore>100) {
        System.out.println(player+" was at "+score+" and now moved to "+newScore+".Invalid Play, Score greater than 100");
      }else if(newScore==100) {
        System.out.println(player+" won!!! Congratulations!!!");
        winner = player;
      }else {
        if(ladderMap.containsKey(newScore)) {
          System.out.println(player+" was at "+score+" and now moved to "+newScore+".Ladder at "+newScore+". Jumped to "+ladderMap.get(newScore)+".");
          playerGame.put(player, ladderMap.get(newScore));
          newScore = ladderMap.get(newScore);
        }else if(snakeMap.containsKey(newScore)){
          System.out.println(player+" was at "+score+" and now moved to "+newScore+".Snake bites at "+newScore+". Dropped to "+snakeMap.get(newScore)+".");
          playerGame.put(player, snakeMap.get(newScore));
          newScore = snakeMap.get(newScore);
        }else {
          System.out.println(player+" was at "+score+" and now moved to "+newScore);
          playerGame.put(player, newScore);
        }
      }
      
    }else {
      
      int newScore = game;
      if (ladderMap.containsKey(newScore)) {
        System.out.println(player+" Started with "+game+", Ladder at " + newScore + ". Jumped to " + ladderMap.get(newScore) + ".");
        playerGame.put(player, ladderMap.get(newScore));
        newScore = ladderMap.get(newScore);
      } else if (snakeMap.containsKey(newScore)) {
        System.out.println(player+" Started with "+game+", Snake bites at " + newScore + ". Dropped to " + snakeMap.get(newScore) + ".");
        playerGame.put(player, snakeMap.get(newScore));
        newScore = snakeMap.get(newScore);
      } else {
        playerGame.put(player, newScore);
      }
      
      
    }
    
    return winner;
    
  }

  private static List<String> getPlayerNames(int i) {
    // TODO Auto-generated method stub
    List<String> playerNames = new ArrayList<String>();
    for(int j=1;j<=i;j++) {
      System.out.println("Enter the name of player"+j);
      playerNames.add(in.next());
      
    }
    
    return playerNames;
    
  }

  private static int getNumberOfPlayers() {
    // TODO Auto-generated method stub
    String input = "N";
    int i=0;
    
    while(!input.equalsIgnoreCase("Y")) {
      System.out.println("Enter the number of players: ");
      i = in.nextInt();
      
      if(i>4 || i<=0) {
        System.out.println("Max 4 members can play!");
        continue; 
      }else {
        System.out.println(i+" members are playing. To continue press Y, To update the number of players press N(Y/N):");
        input = in.next();
      }
    }   
    return i;
  }

}
