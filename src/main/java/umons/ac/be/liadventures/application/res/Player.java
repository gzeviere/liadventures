package umons.ac.be.liadventures.application.res;

import java.util.Random;

//extends cell ?????
public class Player {
     private final String pathToTexture = "src/main/resources/textures/sprites/player.png";

     //made static because only 1 player at a time
     private static int ability, endurance, luck, bagCapacity;

     private int posX, posY;

     public Player(){
          Random rand = new Random();

          //dices rolls, maybe make this visible at game creation ??
          ability = 7 + rand.nextInt(6);
          endurance = 14 + rand.nextInt(11);
          luck = 7 + rand.nextInt(6);
          bagCapacity = 8 + rand.nextInt(11);

          posX = 0;
          posY = 0;
     }

     public int getPosX() {
          return posX;
     }

     public int getPosY() {
          return posY;
     }

     public void setPosX(int posX) {
          this.posX = posX;
     }

     public void setPosY(int posY) {
          this.posY = posY;
     }

     /**
      * Needs to be called when the player enters a room with a monster.
      * Player's and monster's endurance will be updated and one will be 0, handle both scenarios.
      *
      * @param monster the monster the player is fighting
      * @return always true
      */
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

     public boolean triggeredTrap(Trap trap){
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
