package umons.ac.be.liadventures.application.res;

import java.util.ArrayList;
import java.util.Random;

public class TreasureRoom extends Cell{
    private final String pathToTexture = "file:src/main/resources/textures/sprites/chest.png";

    protected final int totalElements;
    protected final ArrayList<Element> elements;

    public TreasureRoom(){
        Random random = new Random();
        totalElements = 4 + random.nextInt(9);
        elements = new ArrayList<>(totalElements);

        for(int i = 0 ; i < totalElements; i++){
            elements.add(new Element());
        }
    }
    @Override
    public void reveal(){

        this.setStyle("-fx-background-color: #ffae00; -fx-border-color: black; -fx-background-image: url(" + pathToTexture +")");

    }

    public ArrayList<Element> bestPossibleOutcome(int bagSize){
        ArrayList<Element> L = new ArrayList<>(totalElements);
        //faire un ratio de valeur par unité de poids
        //trier les elements par ce ratio dans une liste L
        //tant qu'on a suffisamment de place pour l'élément au meilleur ratio le prendre, sinon prendre celui d'apres dans L
        return L;
    }

    public ArrayList<Element> getElements() {
        return elements;
    }

    public void looting(){


    }

    public static class Element {
        public final int value;
        public final int sizeInBag;
        public final String description;

        protected Element(){
            Random rand = new Random();
            value = 1 + rand.nextInt(10);
            sizeInBag = 1 + rand.nextInt(5);

            int select = rand.nextInt(5);

            switch (select){
                case 0:
                    description = "A gold bar";
                    break;
                case 1:
                    description = "A jewelery";
                    break;
                case 2:
                    description = "An ancient artifact";
                    break;
                case 3:
                    description = "A demon soul";
                    break;
                case 4:
                    description = "A bottled dragon fart";
                    break;
                default:
                    description = "The stick of truth";
                    break;
            }

        }

        public int getSizeInBag() {
            return sizeInBag;
        }

        public int getValue() {
            return value;
        }

        public String getDescription() {
            return description;
        }
    }
}
