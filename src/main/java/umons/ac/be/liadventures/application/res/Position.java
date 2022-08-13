package umons.ac.be.liadventures.application.res;

import javafx.geometry.Pos;

//useful ???
public class Position {
    private int x,y;

    public Position(int x ,int y){
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void modifyPos(Position newPos){
        x = newPos.getX();
        y = newPos.getY();
    }

    @Override
    public String toString() {
        return x +", "+y;
    }
}
