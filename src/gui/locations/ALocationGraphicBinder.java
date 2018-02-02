package gui.locations;

import controllers.main.matchmaking.ControllerMatchMaking;
import heroes.abstractHero.AHero;
import managment.actionManagement.ActionManager;
import managment.battleManagement.BattleManager;
import managment.playerManagement.Player;
import managment.playerManagement.ATeam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public final class ALocationGraphicBinder {
    private static final Logger log = LoggerFactory.getLogger(ALocationGraphicBinder.class);

    private ATeam leftATeam;

    private ATeam rightATeam;

    private final ALocation leftLocation;

    private final ALocation rightLocation;

    public ALocationGraphicBinder(final ControllerMatchMaking matchMaking, final ATeam leftATeam, final ATeam rightATeam){
        this.leftLocation = matchMaking.getLeftLocation();
        this.rightLocation = matchMaking.getRightLocation();
        this.leftATeam = leftATeam;
        this.rightATeam = rightATeam;
        //Start settings:
        leftLocation.setFace(leftATeam.getCurrentPlayer().getHero().getFace());
        rightLocation.setFace(rightATeam.getCurrentPlayer().getHero().getFace());
        //Time:
        leftLocation.setTime(BattleManager.getStartTime());
        rightLocation.setTime(BattleManager.getStartTime());
        //Skills:
        wireActionManagerToSkills(matchMaking.getActionManager(), leftATeam, rightATeam);
    }

    private void wireActionManagerToSkills(final ActionManager actionManager, final ATeam leftATeam, final ATeam rightATeam){
        final List<AHero> allHeroes = new ArrayList<>(){{
            add(leftATeam.getCurrentPlayer().getHero());
            add(leftATeam.getAlternativePlayer().getHero());
            add(rightATeam.getCurrentPlayer().getHero());
            add(rightATeam.getAlternativePlayer().getHero());
        }};
        for (final AHero hero: allHeroes){
            final List<AHero.Skill> skills = hero.getCollectionOfSkills();
            for (final AHero.Skill skill : skills){
                skill.setActionManager(actionManager);
                log.info("Successfully wired action manager" + skill.getName());
            }
        }
    }

    public final void bind(){
        bind(leftLocation, leftATeam);
        bind(rightLocation, rightATeam);
    }

    private void bind(final ALocation location, final ATeam aTeam){
        final Player currentPlayer = aTeam.getCurrentPlayer();
        final AHero hero = currentPlayer.getHero();
        //Profile:
        location.setName(currentPlayer.getProfile().getName());
        //Attack:
        location.setAttack((hero.getAttack().intValue()));
        //Health:
        location.setHitPoints(hero.getHitPoints().intValue());
        location.setSupplyHealth(hero.getSupplyHealth().intValue());
        location.setTreatment(hero.getTreatment().intValue());
        //Experience:
        location.setLevel(hero.getLevelHero());
        location.setExperience(hero.getCurrentExperience().intValue());
        location.setRequiredExperience(hero.getListOfRequiredExperience().get(hero.getLevelHero() - 1).intValue());
        //Skills:
        location.setupSkills(hero, hero.getCollectionOfSkills());
    }
}
