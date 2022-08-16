package umons.ac.be.liadventures.application.res;

import java.util.Random;

public class Player {
     //private final String pathToTexture = "src/main/resources/textures/sprites/player.png"; unused

     private final int ability;
     private int endurance;
     private int luck;
     private final int bagCapacity;


     private int posX, posY;

     /**
      * Generates a new player and sets randoms stats for it matching the probability of dice rolls :
      *   Ability = 6 + one rolled die,
      *   Endurance = 12 + two rolled dice,
      *   Luck = 6 + one rolled die,
      *   Bag capacity = 6 + two rolled dice.
      */
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
      * Recursive function that needs to be called when the player enters a room with a monster.
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
               setEndurance(endurance - 2);
          else if (monsterCombatScore < liaCombatScore)
               monster.setEndurance(monster.getEndurance() - 2);

          if(monster.getEndurance() <= 0 || endurance <= 0) { //if someone dead return true
               if (monster.getEndurance() < 1)
                    monster.isDead = true;
               return true;
          }
          return fightMonster(monster);
     }

     /**
      * Needs to be called when the player enters a room with a trap.
      * Player's luck or endurance will be updated.
      *
      * @return true if the player avoids the trap (and lose 1 luck),
      * false if the player falls in the trap (and lose 2 endurance)
      */
     public boolean triggeredTrap(){
          Random rand = new Random();
          int trapLuck = 2 + rand.nextInt(11);

          if(trapLuck <= getLuck()){
               this.luck--;
               return true;
          }
          endurance = endurance-2;
          return false;
     }

     public int getEndurance() {
          return endurance;
     }

     public int getAbility() {
          return ability;
     }

     public int getBagCapacity() {
          return bagCapacity;
     }

     public int getLuck() {
          return luck;
     }

     public void setEndurance(int endurance) {
          this.endurance = endurance;
     }
}
