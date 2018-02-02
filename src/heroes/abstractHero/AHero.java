package heroes.abstractHero;

import bonusDirectory.Bonus;
import javafx.animation.FadeTransition;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.util.Duration;
import managment.actionManagement.ActionEvent;
import managment.actionManagement.ActionManager;
import managment.actionManagement.ActionType;
import managment.battleManagement.BattleManager;
import managment.playerManagement.Player;
import managment.playerManagement.PlayerManager;
import org.jetbrains.annotations.Contract;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public abstract class AHero {
    private static final Logger log = LoggerFactory.getLogger(AHero.class);

    private final double START_EXPERIENCE = 0.0;

    //Inner:
    private Double attack; //Атака
    private Double treatment; //Лечение
    private Double hitPoints; //Здоровье
    private Double supplyHealth; //Запас здоровья
    private Double currentExperience = START_EXPERIENCE; //Текущий опыт
    private int levelHero; //Уровень героя
    private final List<Double> listOfRequiredExperience;
    private final List<Double> listOfSupplyHealth;
    private final List<Double> listOfDamage;
    private final List<Double> listOfTreatment;
    private final List<Skill> collectionOfSkills; //Коллекция суперспособностей
    private Skill swapSkill; //Способность при выходе на поле боя
    private final List<Bonus> currentCollection;

    //Outer:
    private final ImageView face; //Картинка героя
    private final List<Media> listOfAttackVoices;
    private final List<Media> listOfTreatmentVoices;
    private final Presentation presentation;

    //Presentation clazz:
    public final static class Presentation {
        private final int WIDTH = 1280;
        private final int HEIGHT = 720;
        private final int START_OPACITY = 0;
        private final int ANIMATION_TIME = 2;

        private final ImageView backGround;
        private final List<Media> listOfPresentationMedia;
        private final Pane pane;

        public Presentation(final ImageView backGround, final List<Media> listOfPresentationMedia, final Pane pane) {
            backGround.setFitWidth(WIDTH);
            backGround.setFitHeight(HEIGHT);
            pane.getChildren().add(backGround);
            pane.setVisible(false);
            pane.setOpacity(START_OPACITY);
            this.backGround = backGround;
            this.listOfPresentationMedia = listOfPresentationMedia;
            this.pane = pane;
        }

        public final void showPresentation() {
            pane.setVisible(true);
            final FadeTransition fadeTransition = new FadeTransition(Duration.seconds(ANIMATION_TIME), pane);
            fadeTransition.setToValue(1);
            fadeTransition.play();
        }

        public final void hidePresentation() {
            final FadeTransition fadeTransition = new FadeTransition(Duration.seconds(ANIMATION_TIME), pane);
            fadeTransition.setToValue(0);
            fadeTransition.setOnFinished(end -> pane.setVisible(false));
            fadeTransition.play();
        }

        @Contract(pure = true)
        public final ImageView getBackGround() {
            return backGround;
        }

        @Contract(pure = true)
        public final List<Media> getPresentationMediaList() {
            return listOfPresentationMedia;
        }


        @Contract(pure = true)
        public final Pane getPane() {
            return pane;
        }
    }

    //Skill clazz:
    public abstract static class Skill {

        private final String name;

        private int temp = 1;
        private int reload;
        private int requiredLevel;

        private final int START_OPACITY = 0;

        private ImageView pictureOfDescription;
        private ImageView sprite;
        private Media animationSound;
        private final List<Media> listOfVoices;

        //Parent:
        protected AHero parent;

        //ActionManager:
        private ActionManager actionManager;

        protected Skill(final String name, final int reload, final int requiredLevel
                , final ImageView sprite, final ImageView pictureOfDescription,  final List<Media> listOfVoices) {
            this.name = name;
            this.reload = reload;
            this.requiredLevel = requiredLevel;

            sprite.setOnMouseEntered(event -> showDescription());
            sprite.setOnMouseExited(event -> hideDescription());
            sprite.setOnMouseClicked(event -> sendRequest());
            this.pictureOfDescription = pictureOfDescription;
            this.sprite = sprite;
            this.listOfVoices = listOfVoices;
        }

        //Swap skill:
        protected Skill(final int reload, final List<Media> voiceList){
            this.name = "swap";
            this.reload = reload;
            this.requiredLevel = 1;
            this.listOfVoices = voiceList;
        }

        private void sendRequest() {
            if (parent != null) {
                log.info(" skill request");
                actionManager.setSkillRequest(parent, this);

            }
        }

        public abstract void use(final BattleManager battleManager, final PlayerManager playerManager);

        protected final List<ActionEvent> actionEvents = new ArrayList<>();

        public final List<ActionEvent> getActionEvents() {
            return actionEvents;
        }

        public final boolean isReady() {
            return temp == reload;
        }

        void reload() {
            if (temp + 1 <= reload) {
                temp++;
            } else {
                sprite.setVisible(true);
            }
        }

        public final void reset() {
            temp = 0;
        }

        final void showDescription() {
            log.info("show description");
            final FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1), pictureOfDescription);
            fadeTransition.setToValue(1);
            fadeTransition.play();
        }

        final void hideDescription() {
            log.info("hide description");
            final FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1), pictureOfDescription);
            fadeTransition.setToValue(0);
            fadeTransition.play();
        }

        public final void install(final Pane parentPane, final AHero parent
                , final double x, final  double y, final boolean invert) {
            //the skill must match the parent!
            this.parent = parent;
            //init description:
            pictureOfDescription.setLayoutX(x);
            pictureOfDescription.setLayoutY(-127);
            pictureOfDescription.setOpacity(START_OPACITY);
            parentPane.getChildren().add(pictureOfDescription);
            //init sprite:
            final int inversion = invert ? -1 : 1;
            sprite.setLayoutX(x);
            sprite.setLayoutY(y);
            sprite.setScaleX(inversion);
            parentPane.getChildren().add(sprite);
        }

        public abstract void showAnimation();

        private boolean skillAccess = true;

        public String getName() {
            return name;
        }

        @Contract(pure = true)
        public final List<Media> getListOfVoices() {
            return listOfVoices;
        }

        @Contract(pure = true)
        public final Media getAnimationSound() {
            return animationSound;
        }

        public boolean isSkillAccess() {
            return skillAccess;
        }

        public void setSkillAccess(boolean skillAccess) {
            this.skillAccess = skillAccess;
        }

        public int getTemp() {
            return temp;
        }

        public int getReload() {
            return reload;
        }

        public void setReload(int reload) {
            this.reload = reload;
        }

        public AHero getParent() {
            return parent;
        }

        public void setActionManager(final ActionManager actionManager) {
            this.actionManager = actionManager;
        }

        public final int getRequiredLevel() {
            return requiredLevel;
        }

        public final void setRequiredLevel(final int requiredLevel) {
            this.requiredLevel = requiredLevel;
        }
    }

    public final void reloadSkills() {
        collectionOfSkills.forEach(AHero.Skill::reload);
    }

    //AHero constructor:
    public AHero(final Double attack, final Double treatment, final Double hitPoints, final Double supplyHealth
            , final Double currentExperience, final int levelHero, final List<Double> listOfRequiredExperience
            , final List<Double> listOfDamage, final List<Double> listOfTreatment, final List<Double> listOfSupplyHealth
            , final List<Skill> collectionOfSkills
                 //Outer:
            , final ImageView face, final List<Media> listOfAttackVoices, final List<Media> listOfTreatmentVoices
            , final Presentation presentation, final List<Bonus> currentCollection) {

        this.attack = attack;
        this.treatment = treatment;
        this.hitPoints = hitPoints;
        this.supplyHealth = supplyHealth;
        this.currentExperience = currentExperience;
        this.levelHero = levelHero;
        this.listOfDamage = listOfDamage;
        this.listOfTreatment = listOfTreatment;
        this.listOfRequiredExperience = listOfRequiredExperience;
        this.listOfSupplyHealth = listOfSupplyHealth;
        this.collectionOfSkills = collectionOfSkills;
        this.face = face;
        this.listOfAttackVoices = listOfAttackVoices;
        this.listOfTreatmentVoices = listOfTreatmentVoices;
        this.presentation = presentation;
        this.currentCollection = currentCollection;
    }

    //ActionAccess:
    private boolean healingAccess = true;

    private boolean treatmentAccess = true;

    private boolean attackAccess = true;

    private boolean damageAccess = true;

    //ActionEvents:
    private ActionEvent damageEvent;

    private ActionEvent healingEvent;

    public final ActionEvent getDamageEvent() {
        return damageEvent;
    }

    public final ActionEvent getHealingEvent() {
        return healingEvent;
    }

    public final boolean getHealing(final Player currentPlayer, final double healing) {
        if (hitPoints < supplyHealth) {
            if (hitPoints + healing > supplyHealth) {
                hitPoints = supplyHealth;
            } else {
                hitPoints += healing;
            }
            healingEvent = new ActionEvent(ActionType.GET_HEALING, currentPlayer);
            log.info(hitPoints.toString());
            return true;
        }
        return false;
    }

    public final boolean getDamage(final Player currentPlayer, final double damage) {
        if (damageAccess && damage > 0) {
            hitPoints -= damage;
            damageEvent = new ActionEvent(ActionType.GET_DAMAGE, currentPlayer);
            log.info(hitPoints.toString());
            return true;
        }
        return false;
    }

    //Animation:
    public abstract void showAttackAnimation();

    public abstract void showTreatmentAnimation();

    //Getters:
    public Presentation getPresentation() {
        return presentation;
    }

    public ImageView getFace() {
        return face;
    }

    public List<Bonus> getCurentCollection() {
        return currentCollection;
    }

    public Double getAttack() {
        return attack;
    }

    public final void setAttack(final double attack){
        this.attack = attack;
    }

    public Double getTreatment() {
        return treatment;
    }

    public Double getHitPoints() {
        return hitPoints;
    }

    public final void setHitPoints(Double hitPoints) {
        this.hitPoints = hitPoints;
    }

    public Double getSupplyHealth() {
        return supplyHealth;
    }

    public void setSupplyHealth(final double supplyHealth) {
        this.supplyHealth = supplyHealth;
    }

    public Double getCurrentExperience() {
        return currentExperience;
    }

    public final void addExperience(final double delta) {
        final double experience = currentExperience + delta;
        this.currentExperience = experience;
        this.levelHero = 1;
        for (int i = 1; i < listOfRequiredExperience.size(); i++) {
            if (experience >= listOfRequiredExperience.get(i - 1) && experience < listOfRequiredExperience.get(i)) {
                this.levelHero = i + 1;
            }
        }
        if (experience > listOfRequiredExperience.get(8)) {
            this.levelHero = 10;
        }
    }

    public int getLevelHero() {
        return levelHero;
    }

    public List<Double> getListOfRequiredExperience() {
        return listOfRequiredExperience;
    }

    public List<Double> getListOfSupplyHealth() {
        return listOfSupplyHealth;
    }

    public List<Double> getListOfDamage() {
        return listOfDamage;
    }

    public List<Double> getListOfTreatment() {
        return listOfTreatment;
    }

    public List<Skill> getCollectionOfSkills() {
        return collectionOfSkills;
    }

    public Skill getSwapSkill() {
        return swapSkill;
    }

    public void setSwapSkill(Skill swapSkill) {
        this.swapSkill = swapSkill;
    }

    public List<Bonus> getCurrentCollection() {
        return currentCollection;
    }

    public List<Media> getListOfAttackVoices() {
        return listOfAttackVoices;
    }

    public List<Media> getListOfTreatmentVoices() {
        return listOfTreatmentVoices;
    }

    //Access:
    public boolean isHealingAccess() {
        return healingAccess;
    }

    public void setHealingAccess(boolean healingAccess) {
        this.healingAccess = healingAccess;
    }

    public boolean isAttackAccess() {
        return attackAccess;
    }

    public void setAttackAccess(boolean attackAccess) {
        this.attackAccess = attackAccess;
    }

    public boolean isDamageAccess() {
        return damageAccess;
    }

    public void setDamageAccess(boolean damageAccess) {
        this.damageAccess = damageAccess;
    }

    public boolean isTreatmentAccess() {
        return treatmentAccess;
    }

    public void setTreatmentAccess(boolean treatmentAccess) {
        this.treatmentAccess = treatmentAccess;
    }
}