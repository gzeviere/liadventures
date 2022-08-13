package umons.ac.be.liadventures.application.res;

public class Cell {
    //protected String pathToTexture; don't need as doesn't have texture
    public Cell(){}
    public void reveal(){return;} //reveals true texture instead of blank grey default hidden texture

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
