package umons.ac.be.liadventures.application.res;

public enum Difficulty {
    EASY (80, 10, 10),
    NORMAL (70, 15, 15),
    HARD (50, 25, 25),
    EXTREME (25, 40, 35);

    public final int emptyRate;
    public final int trapRate;
    public final int monsterRate;

    Difficulty(int emptyRate, int trapRate, int monsterRate){
        this.emptyRate = emptyRate;
        this.trapRate = trapRate;
        this.monsterRate = monsterRate;
    }

    public static Difficulty getFromString(String str){
        switch(str){
            case "EASY":
                return EASY;
            case "HARD":
                return HARD;
            case "EXTREME":
                return EXTREME;
            default:
                return NORMAL;
        }
    }
}
