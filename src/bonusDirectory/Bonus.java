package bonusDirectory;

import javax.swing.text.html.ImageView;

public abstract class Bonus {
    private final String name;
    private final int id;
    private int numberOfTurns;
    private ImageView sprite;
    private ImageView label;

    public Bonus(String name, int id) {
        this.name = name;
        this.id = id;
    }


    public int getId() {
        return id;
    }

    public abstract void run();

    public abstract void onStartTurn();

    public abstract void inEndTurn();

    public abstract boolean condition();

    public abstract void decreaseOfTurns(); //уменьшение срабатывания бонусов на ходах

    public abstract void controllingBattleProcess();

    public abstract int getCountOfActivity();

    @Override
    public String toString() {
        return "Bonus{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", numberOfTurns=" + numberOfTurns +
                ", sprite=" + sprite +
                ", label=" + label +
                '}';
    }
}
