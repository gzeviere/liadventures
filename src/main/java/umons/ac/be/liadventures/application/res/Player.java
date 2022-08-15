package umons.ac.be.liadventures.application.res;

import javafx.scene.control.Button;

import java.util.Random;

//extends cell ?????
public class Player {
     private final String pathToTexture = "src/main/resources/textures/sprites/player.png";

     //made static because only 1 player at a time
     private int ability, endurance, luck, bagCapacity;

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
          if(monster.getEndurance() < 1)
               return true;
          Random rand = new Random();

          //jeter 2 dés pour lia et le monstre
          int liaCombatScore = ability + 2 + rand.nextInt(11);
          int monsterCombatScore = monster.getAbility() + 2 + rand.nextInt(11);

          if(liaCombatScore < monsterCombatScore)
               setEndurance(endurance - 2);
          else if (monsterCombatScore < liaCombatScore)
               monster.setEndurance(monster.getEndurance() - 2);

          if(monster.getEndurance() <= 0 || endurance <= 0) //if someone dead return true
               return true;

          return fightMonster(monster);
     }

     public void triggeredTrap(){
          Random rand = new Random();
          int trapLuck = 2 + rand.nextInt(11);

          if(trapLuck <= getLuck()){
               this.luck--;
               return;
          }
          endurance = endurance-2;
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
