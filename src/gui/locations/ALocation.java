package gui.locations;

import heroes.abstractHero.AHero;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import org.jetbrains.annotations.Contract;

import java.util.ArrayList;
import java.util.List;

public final class ALocation {

    private AnchorPane location;

    private final boolean invert;

    //Hero:
    private ImageView face;

    private AnchorPane skillPane;

    private final List<Pane> skills = new ArrayList<>();

    //Characteristics:
    private AnchorPane characteristics;

    private Text level;

    private Text attack;

    private Text treatment;

    private Text time;

    private Text supplyHealth;

    private Text hitPoints;

    private Text experience;

    private Text requiredExperience;

    //Swap heroes:
    private AnchorPane heroes;

    private Button currentHero;

    private Button backHero;

    //Profile:
    private Text name;

    public ALocation(final AnchorPane location, final boolean invert) {
        final int HEROES_CONTROL_INDEX = 0;
        final int BONUS_STACK_INDEX = 1;
        final int SKILL_INDEX = 3;
        final int FACE_INDEX = 4;
        final int CHARACTERISTIC_INDEX = 2;

        //inversion:
        this.invert = invert;
        //root:
        this.location = location;
        //heroes control:
        this.heroes = (AnchorPane) location.getChildren().get(HEROES_CONTROL_INDEX);
        this.currentHero = (Button) this.heroes.getChildren().get(0);
        this.backHero = (Button) this.heroes.getChildren().get(1);
        //bonus stack:
        //...........
        //skill pane:
        this.skillPane = (AnchorPane) location.getChildren().get(SKILL_INDEX);
        //face:
        this.face = (ImageView) location.getChildren().get(FACE_INDEX);
        //characteristics:
        this.characteristics = (AnchorPane) location.getChildren().get(CHARACTERISTIC_INDEX);
        this.attack = (Text) this.characteristics.getChildren().get(0);
        this.hitPoints = (Text) this.characteristics.getChildren().get(1);
        this.supplyHealth = (Text) this.characteristics.getChildren().get(2);
        this.treatment = (Text) this.characteristics.getChildren().get(3);
        this.level = (Text) this.characteristics.getChildren().get(4);
        this.experience = (Text) this.characteristics.getChildren().get(5);
        this.requiredExperience = (Text) this.characteristics.getChildren().get(6);
        this.time = (Text) this.characteristics.getChildren().get(7);
        //name:
        this.name = (Text) this.characteristics.getChildren().get(8);
    }

    public void setFace(ImageView face) {
        this.face.setImage(face.getImage());
    }

    @Contract(pure = true)
    public AnchorPane getLocation() {
        return location;
    }

    @Contract(pure = true)
    public ImageView getFace() {
        return face;
    }

    @Contract(pure = true)
    public AnchorPane getSkillPane() {
        return skillPane;
    }

    public final List<Pane> getSkills() {
        return skills;
    }

    final void setupSkills(final AHero parentHero, final List<AHero.Skill> skills){
        final int startY = 0;
        int startX = invert ? 0 : 150;
        final int shiftX = invert ? + 75 : -75;
        for (final AHero.Skill skill : skills){
            skill.install(skillPane, parentHero, startX, startY, invert);
            startX += shiftX;
        }
    }

    @Contract(pure = true)
    public AnchorPane getCharacteristics() {
        return characteristics;
    }

    @Contract(pure = true)
    public Text getLevel() {
        return level;
    }

    public void changeLevel(final int delta) {
        int level = Integer.parseInt(this.level.getText());
        level += delta;
        this.level.setText(level + "");
    }

    public void setLevel(final int level) {
        this.level.setText(level + "");
    }

    @Contract(pure = true)
    public Text getAttack() {
        return attack;
    }

    public void changeAttack(final int delta) {
        int attack = Integer.parseInt(this.attack.getText());
        attack += delta;
        this.attack.setText(attack + "");
    }

    public void setAttack(int attack) {
        this.attack.setText(attack + "");
    }

    @Contract(pure = true)
    public Text getTreatment() {
        return treatment;
    }

    public void changeTreatment(final int delta) {
        int treatment = Integer.parseInt(this.treatment.getText());
        treatment += delta;
        this.treatment.setText(treatment + "");
    }

    public void setTreatment(int treatment) {
        this.treatment.setText(treatment + "");
    }

    @Contract(pure = true)
    public Text getHitPoints() {
        return hitPoints;
    }

    public void changeHitPoints(final int delta) {
        int hitPoints = Integer.parseInt(this.hitPoints.getText());
        hitPoints += delta;
        this.hitPoints.setText(hitPoints + "");
    }

    public void setHitPoints(final int hitPoints) {
        this.hitPoints.setText(hitPoints + "");
    }

    @Contract(pure = true)
    public Text getSupplyHealth() {
        return supplyHealth;
    }

    public void changeSupplyHealth(final int delta) {
        int supplyHealth = Integer.parseInt(this.supplyHealth.getText());
        supplyHealth += delta;
        this.supplyHealth.setText(supplyHealth + "");
    }

    public void setSupplyHealth(int supplyHealth) {
        this.supplyHealth.setText(supplyHealth + "");
    }

    @Contract(pure = true)
    public Text getExperience() {
        return experience;
    }

    public void changeExperience(final int delta) {
        double experience = Double.parseDouble(this.experience.getText());
        experience += delta;
        this.experience.setText(experience + "");
    }

    public void setExperience(int experience) {
        this.experience.setText(experience + "");
    }

    @Contract(pure = true)
    public Text getRequiredExperience() {
        return requiredExperience;
    }

    public void changeRequiredExperience(final int delta) {
        int requiredExperience = Integer.parseInt(this.requiredExperience.getText());
        requiredExperience += delta;
        this.requiredExperience.setText(requiredExperience + "");
    }

    public void setRequiredExperience(final int requiredExperience) {
        this.requiredExperience.setText(requiredExperience + "");
    }

    @Contract(pure = true)
    public Text getTime() {
        return time;
    }

    public void changeTime(int delta) {
        int time = Integer.parseInt(this.time.getText());
        time += delta;
        this.time.setText(time + "");
    }

    public void setTime(final int time) {
        this.time.setText(time + "");
    }

    @Contract(pure = true)
    public AnchorPane getHeroes() {
        return heroes;
    }

    @Contract(pure = true)
    public Button getCurrentHero() {
        return currentHero;
    }

    @Contract(pure = true)
    public Button getBackHero() {
        return backHero;
    }

    public void setName(final String name) {
        this.name.setText(name);
    }

    public Text getName() {
        return name;
    }

    public boolean isInvert() {
        return invert;
    }
}
