package umons.ac.be.liadventures.application.res;

import java.util.Random;

//extends cell ?????
public class Player extends Cell {
     private final String pathToTexture = "src/main/resources/textures/Player.jpg";

     //can be static because only 1 player at a time
     private static int ability, endurance, luck, bagCapacity;

     public Player(){
          Random rand = new Random();

          ability = 7 + rand.nextInt(6);
          endurance = 14 + rand.nextInt(11);
          luck = 7 + rand.nextInt(6);
          bagCapacity = 8 + rand.nextInt(11);
     }

     public boolean fightMonster(Monster monster){
          Random rand = new Random();

          //jeter 2 dés pour lia et le monstre
          int liaCombatScore = ability + 2 + rand.nextInt(11);
          int monsterCombatScore = monster.getAbility() + 2 + rand.nextInt(11);

          if(liaCombatScore < monsterCombatScore)
               setAbility(ability - 2);
          else if (monsterCombatScore < liaCombatScore)
               monster.setAbility(monster.getAbility() - 2);

          if(monster.getAbility() <= 0 || ability <= 0) //if someone dead return true
               return true;

          return fightMonster(monster);
     }

     public boolean trapped(int bound){
          return true;
     }

     public static int getEndurance() {
          return endurance;
     }

     public static int getAbility() {
          return ability;
     }

     public static int getBagCapacity() {
          return bagCapacity;
     }

     public static int getLuck() {
          return luck;
     }

     public static void setAbility(int ability) {
          Player.ability = ability;
     }
}
