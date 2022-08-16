package umons.ac.be.liadventures.application.res;

import java.util.Iterator;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class TreasureRoomTest {

    @org.junit.jupiter.api.Test
    void bestPossibleOutcome() {
        var treasureRoom = new TreasureRoom();
        //All the elements weight maximum 5 ounces, at least one element would fit in a bag. Value of an element is greater than 0.
        assertNotEquals(0, treasureRoom.bestPossibleOutcome(5));
    }

    @org.junit.jupiter.api.Test
    void bestPossibleOutcomeNotOverfill() {
        var treasureRoom = new TreasureRoom();
        assertEquals(0, treasureRoom.bestPossibleOutcome(0));
    }

    @org.junit.jupiter.api.Test
    void bestElementOrder() {
        var treasureRoom = new TreasureRoom();
        assertNotEquals(0, treasureRoom.bestElementOrder().size());
    }

    @org.junit.jupiter.api.Test
    void bestElementOrderHasAllElements(){
        var treasureRoom = new TreasureRoom();
        assertEquals(treasureRoom.totalElements, treasureRoom.bestElementOrder().size());
    }

    @org.junit.jupiter.api.Test
    void isBestElementOrderSortedByRatio(){
        var treasureRoom = new TreasureRoom();
        LinkedList<TreasureRoom.Element> sortedElements = treasureRoom.bestElementOrder();

        Iterator<TreasureRoom.Element> it = sortedElements.iterator();

        float highestRatio = 0;

        while(it.hasNext()){
            TreasureRoom.Element temp = it.next();
            float currentRatio = temp.getValue() / temp.getSizeInBag();

            //as highestRatio is a float, inserting == 0 would return false
            if(highestRatio < 0.0000001){
                highestRatio = currentRatio;
                continue;
            }
            assertTrue(currentRatio < highestRatio);
        }
    }
}