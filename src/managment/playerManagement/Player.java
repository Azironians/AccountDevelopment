package managment.playerManagement;

import bonusDirectory.Bonus;
import heroes.abstractHero.AHero;
import managment.profileManage.Profile;
import org.jetbrains.annotations.Contract;

public final class Player {
    private final Profile profile;
    private AHero hero;

    private double dealDamage = 0;
    private double restoredHitPoints = 0;
    private int reachedLevel = 1;
    private int usedSkills = 0;
    private Bonus favouriteBonus;
    private int remainingTime;
    private Boolean winner = null;


    public Player(final Profile profile, final AHero hero) {
        this.profile = profile;
        this.hero = hero;
    }

    @Contract(pure = true)
    public Profile getProfile() {
        return profile;
    }

    @Contract(pure = true)
    public AHero getHero() {
        return hero;
    }

    public void setHero(AHero hero) {
        this.hero = hero;
    }

    @Contract(pure = true)
    public double getDealDamage() {
        return dealDamage;
    }

    void setDealDamage(int dealDamage) {
        this.dealDamage = dealDamage;
    }

    @Contract(pure = true)
    public double getRestoredHitPoints() {
        return restoredHitPoints;
    }

    void setRestoredHitPoints(int restoredHitPoints) {
        this.restoredHitPoints = restoredHitPoints;
    }

    @Contract(pure = true)
    public int getReachedLevel() {
        return reachedLevel;
    }

    void setReachedLevel(byte reachedLevel) {
        this.reachedLevel = reachedLevel;
    }

    @Contract(pure = true)
    public int getUsedSkills() {
        return usedSkills;
    }

    void setUsedSkills(byte usedSkills) {
        this.usedSkills = usedSkills;
    }

    @Contract(pure = true)
    public Bonus getFavouriteBonus() {
        return favouriteBonus;
    }

    public void setFavouriteBonus(Bonus favouriteBonus) {
        this.favouriteBonus = favouriteBonus;
    }

    @Contract(pure = true)
    public int getRemainingTime() {
        return remainingTime;
    }

    void setRemainingTime(int remainingTime) {
        this.remainingTime = remainingTime;
    }

    @Contract(pure = true)
    public boolean isWinner() {
        return winner;
    }

    void setWinner(boolean winner) {
        this.winner = winner;
    }
}




