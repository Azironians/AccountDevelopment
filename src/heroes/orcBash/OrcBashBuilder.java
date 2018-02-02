package heroes.orcBash;

import bonusDirectory.Bonus;
import heroes.abstractHero.AHeroBuilder;
import heroes.abstractHero.AHero;
import heroes.abstractHero.HeroResourceSupplier;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import managment.actionManagement.ActionEvent;
import managment.actionManagement.ActionType;
import managment.battleManagement.BattleManager;
import managment.playerManagement.Player;
import managment.playerManagement.PlayerManager;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class OrcBashBuilder implements AHeroBuilder {

    private final HeroResourceSupplier resourceSupplier = new OrcBashResourceSupplier();

    @NotNull
    @Override
    public final AHero buildHero(final List<Bonus> deck) {
        //Start position:
        final double START_ATTACK = 30.0;
        final double START_TREATMENT = 125.0;
        final double START_HIT_POINTS = 500.0;
        final double START_SUPPLY_HEALTH = 500.0;
        final double START_EXPERIENCE = 0.0;
        final int START_LEVEL = 1;

        //Суперспособность 1:
        final int BASH_RELOAD = 7;
        final int BASH_REQUIRED_LEVEL = 3;
        final HeroResourceSupplier.GetSkills BASH_RESOURCE = resourceSupplier.getSkillInstances().get(0);
        final AHero.Skill BASH = new AHero.Skill("Bash", BASH_RELOAD, BASH_REQUIRED_LEVEL
                , BASH_RESOURCE.getSprite()
                , BASH_RESOURCE.getDescription()
                , new ArrayList<>()
//                , new Media("file:///D:/NoWayAlexMilkevich/videosForTutorial/menuVideo.mp4")
        ) {
            @Override
            public final void use(final BattleManager battleManager, final PlayerManager playerManager) {
                final double SKILL_COEFFICIENT = 5.5;
                final double DAMAGE = getParent().getAttack() * SKILL_COEFFICIENT;

                final Player currentPlayer = playerManager.getCurrentATeam().getCurrentPlayer();
                final Player opponentPlayer = playerManager.getOpponentATeam().getCurrentPlayer();
                final AHero opponentHero = opponentPlayer.getHero();
                if (opponentHero.getDamage(opponentPlayer, DAMAGE)) {
                    actionEvents.add(new ActionEvent(ActionType.DEAL_DAMAGE, currentPlayer));
                    actionEvents.add(new ActionEvent(ActionType.GET_DAMAGE, opponentPlayer));
                }
                battleManager.setSkipTurn(true);
            }

            @Override
            public void showAnimation() {

            }
        };

        // Суперспособность 2:
        final int FAVOURITE_BEATER_RELOAD = 8;
        final int FAVOURITE_BEATER_REQUIRED_LEVEL = 5;
        final HeroResourceSupplier.GetSkills FAVOURITE_BEATER_RESOURCE = resourceSupplier.getSkillInstances().get(1);
        final AHero.Skill FAVOURITE_BEATER = new AHero.Skill("FavouriteBeater", FAVOURITE_BEATER_RELOAD, FAVOURITE_BEATER_REQUIRED_LEVEL
                , FAVOURITE_BEATER_RESOURCE.getSprite()
                , FAVOURITE_BEATER_RESOURCE.getDescription()
                , new ArrayList<>()
//                , new Media("file:///D:/NoWayAlexMilkevich/videosForTutorial/menuVideo.mp4")
        ) {
            @Override
            public final void use(final BattleManager battleManager, final PlayerManager playerManager) {
                final double SKILL_COEFFICIENT = 2.5;
                final double DAMAGE = getParent().getAttack() * SKILL_COEFFICIENT;

                final Player currentPlayer = playerManager.getCurrentATeam().getCurrentPlayer();
                final Player opponentPlayer = playerManager.getOpponentATeam().getCurrentPlayer();
                final AHero opponentHero = opponentPlayer.getHero();
                if (opponentHero.getDamage(opponentPlayer, DAMAGE)) {
                    actionEvents.add(new ActionEvent(ActionType.DEAL_DAMAGE, currentPlayer));
                    actionEvents.add(new ActionEvent(ActionType.GET_DAMAGE, opponentPlayer));
                }
                opponentHero.setSupplyHealth(opponentHero.getSupplyHealth() - DAMAGE);
            }

            @Override
            public final void showAnimation() {

            }
        };

        //Суперспособность 3:
        final int RUSH_RELOAD = 10;
        final int RUSH_REQUIRED_LEVEL = 9;
        final HeroResourceSupplier.GetSkills RUSH_RESOURCE = resourceSupplier.getSkillInstances().get(2);
        final AHero.Skill RUSH = new AHero.Skill("Rush", RUSH_RELOAD, RUSH_REQUIRED_LEVEL
                , RUSH_RESOURCE.getSprite()
                , RUSH_RESOURCE.getDescription()
                , new ArrayList<>()
//                , new Media("file:///D:/NoWayAlexMilkevich/videosForTutorial/menuVideo.mp4")
        ) {
            @Override
            public final void use(final BattleManager battleManager, final PlayerManager playerManager) {
                final double DAMAGE = getParent().getSupplyHealth();

                final Player currentPlayer = playerManager.getCurrentATeam().getCurrentPlayer();
                final Player opponentPlayer = playerManager.getOpponentATeam().getCurrentPlayer();
                final AHero opponentHero = opponentPlayer.getHero();
                if (opponentHero.getDamage(opponentPlayer, DAMAGE)) {
                    actionEvents.add(new ActionEvent(ActionType.DEAL_DAMAGE, currentPlayer));
                    actionEvents.add(new ActionEvent(ActionType.GET_DAMAGE, opponentPlayer));
                }
            }

            @Override
            public final void showAnimation() {

            }
        };

        //Лист, в который ты потом положишь суперспособности:
        final List<AHero.Skill> SKILL_LIST = Arrays.asList(BASH, FAVOURITE_BEATER, RUSH);

        //Лист перехода по опыту:
        final double EXP_LEVEL_2 = 150.0;
        final double EXP_LEVEL_3 = 366.0;
        final double EXP_LEVEL_4 = 667.0;
        final double EXP_LEVEL_5 = 1083.0;
        final double EXP_LEVEL_6 = 1641.0;
        final double EXP_LEVEL_7 = 2381.0;
        final double EXP_LEVEL_8 = 3371.0;
        final double EXP_LEVEL_9 = 4667.0;
        final double EXP_LEVEL_10 = 6357.0;

        List<Double> REQUIRED_EXPERIENCE_LIST = Arrays.asList(EXP_LEVEL_2, EXP_LEVEL_3, EXP_LEVEL_4, EXP_LEVEL_5
                , EXP_LEVEL_6, EXP_LEVEL_7, EXP_LEVEL_8, EXP_LEVEL_9, EXP_LEVEL_10);

        //Лист переходов со здоровьем:
        final double HP_LEVEL_2 = 100.0;
        final double HP_LEVEL_3 = 120.0;
        final double HP_LEVEL_4 = 145.0;
        final double HP_LEVEL_5 = 170.0;
        final double HP_LEVEL_6 = 210.0;
        final double HP_LEVEL_7 = 245.0;
        final double HP_LEVEL_8 = 300.0;
        final double HP_LEVEL_9 = 360.0;
        final double HP_LEVEL_10 = 410.0;

        final List<Double> SUPPLY_HEATH_LIST = Arrays.asList(HP_LEVEL_2, HP_LEVEL_3, HP_LEVEL_4, HP_LEVEL_5, HP_LEVEL_6
                , HP_LEVEL_7, HP_LEVEL_8, HP_LEVEL_9, HP_LEVEL_10);

        //Лист звуков с атакой:
        List<Media> ATTACK_MEDIA_LIST = Arrays.asList(
//                new Media("src\\Sounds\\SoundOrcBasher\\BhrAttack-1.wav"),
//                new Media("src\\Sounds\\SoundOrcBasher\\BhrAttack-2.wav"),
//                new Media("src\\Sounds\\SoundOrcBasher\\BhrAttack-3.wav"),
//                new Media("src\\Sounds\\SoundOrcBasher\\BhrAttack-4.wav")
        );

        //Лист звуков с лечением:
        List<Media> TREATMENT_MEDIA_LIST = Arrays.asList(
//                new Media("src\\Sounds\\SoundOrcBasher\\BhrTreatment-1.wav"),
//                new Media("src\\Sounds\\SoundOrcBasher\\BhrTreatment-2.wav"),
//                new Media("src\\Sounds\\SoundOrcBasher\\BhrTreatment-3.wav"),
//                new Media("src\\Sounds\\SoundOrcBasher\\BhrTreatment-4.wav")
        );

        //Лист урона:
        final double DAMAGE_LEVEL_2 = 6.0;
        final double DAMAGE_LEVEL_3 = 7.0;
        final double DAMAGE_LEVEL_4 = 9.0;
        final double DAMAGE_LEVEL_5 = 10.0;
        final double DAMAGE_LEVEL_6 = 12.0;
        final double DAMAGE_LEVEL_7 = 16.0;
        final double DAMAGE_LEVEL_8 = 18.0;
        final double DAMAGE_LEVEL_9 = 22.0;
        final double DAMAGE_LEVEL_10 = 25.0;

        final List<Double> DAMAGE_LIST = Arrays.asList(DAMAGE_LEVEL_2, DAMAGE_LEVEL_3, DAMAGE_LEVEL_4, DAMAGE_LEVEL_5
                , DAMAGE_LEVEL_6, DAMAGE_LEVEL_7, DAMAGE_LEVEL_8, DAMAGE_LEVEL_9, DAMAGE_LEVEL_10);

        //Лист Лечения:
        final double TREAT_LEVEL_2 = 25.0;
        final double TREAT_LEVEL_3 = 30.0;
        final double TREAT_LEVEL_4 = 36.0;
        final double TREAT_LEVEL_5 = 43.0;
        final double TREAT_LEVEL_6 = 52.0;
        final double TREAT_LEVEL_7 = 62.0;
        final double TREAT_LEVEL_8 = 75.0;
        final double TREAT_LEVEL_9 = 89.0;
        final double TREAT_LEVEL_10 = 108.0;

        final List<Double> TREATMENT_LIST = Arrays.asList(TREAT_LEVEL_2, TREAT_LEVEL_3, TREAT_LEVEL_4, TREAT_LEVEL_5
                , TREAT_LEVEL_6, TREAT_LEVEL_7, TREAT_LEVEL_8, TREAT_LEVEL_9, TREAT_LEVEL_10);

        //Картинка героя:
        ImageView FACE = resourceSupplier.getFaceImageInstance();

        //Презентация:
        List<Media> PRESENTATION_MEDIA_LIST = Arrays.asList(
//                new Media("src\\Sounds\\SoundOrcBasher\\BhrGreetings-1.wav"),
//                new Media("src\\Sounds\\SoundOrcBasher\\BhrGreetings-2.wav")
        );

        AHero.Presentation PRESENTATION = new AHero.Presentation(
                new ImageView(new Image("file:src\\resources\\heroes\\lordVampire\\face\\lordVampire.png")),
                PRESENTATION_MEDIA_LIST, new Pane());

        //Возврат собранного героя:
        return new OrcBash(START_ATTACK, START_TREATMENT, START_HIT_POINTS, START_SUPPLY_HEALTH, START_EXPERIENCE
                , START_LEVEL, REQUIRED_EXPERIENCE_LIST, DAMAGE_LIST, TREATMENT_LIST, SUPPLY_HEATH_LIST, SKILL_LIST
                , FACE, ATTACK_MEDIA_LIST, TREATMENT_MEDIA_LIST, PRESENTATION, deck);
    }
}