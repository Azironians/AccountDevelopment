package managment.actionManagement;

import heroes.abstractHero.AHero;
import managment.playerManagement.Player;

import java.util.ArrayList;
import java.util.List;

public class EventObserver {

    //Inner:
    private Player player;
    private Double attack; //Атака
    private Double treatment; //Лечение
    private Double hitPoints; //Здоровье
    private Double supplyHealth; //Запас здоровья
    private Double currentExperience; //Текущий опыт
    private int levelHero; //Уровень героя

    public EventObserver(final Player player) {
        this.player = player;
        final AHero aHero = player.getHero();
        this.attack = aHero.getAttack();
        this.treatment = aHero.getTreatment();
        this.hitPoints = aHero.getHitPoints();
        this.supplyHealth = aHero.getSupplyHealth();
        this.currentExperience = aHero.getCurrentExperience();
        this.levelHero = aHero.getLevelHero();
    }

    public final List<ActionEvent> observe(){
        final AHero aHero = player.getHero();
        final List<ActionEvent> events = new ArrayList<>();
        checkHitPointEvents(aHero, events);
        checkLevelEvents(aHero, events);
        return events;
    }

    private void checkHitPointEvents(final AHero aHero, List<ActionEvent> events){
        if (aHero.getHitPoints() < 0){
            events.add(new ActionEvent(ActionType.NEGATIVE_HP, player));
        }
        if (aHero.getHitPoints() == 0){
            events.add(new ActionEvent(ActionType.ZERO_HP, player));
        }
        if (aHero.getHitPoints() > 0){
            events.add(new ActionEvent(ActionType.POSITIVE_HP, player));
        }
    }

    private void checkLevelEvents(final AHero aHero, List<ActionEvent> events){
        int levelDelta = aHero.getLevelHero() - levelHero;
        if (levelDelta > 0){
            for (int i = 0; i < levelDelta; i++){
                events.add(new ActionEvent(ActionType.LEVEL_UP, player));
            }
        }
        if (levelDelta < 0){
            levelDelta = -levelDelta;
            for (int i = 0; i < levelDelta; i++){
                events.add(new ActionEvent(ActionType.LEVEL_DOWN, player));
            }
        }
    }



}
