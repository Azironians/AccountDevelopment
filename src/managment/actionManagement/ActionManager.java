package managment.actionManagement;

import bonusDirectory.Bonus;
import bonusDirectory.BonusEventHandler;
import com.google.inject.Inject;
import heroes.abstractHero.AHero;
import managment.battleManagement.BattleManager;
import managment.playerManagement.ATeam;
import managment.playerManagement.Player;
import managment.playerManagement.PlayerManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public final class ActionManager {
    private static final Logger log = LoggerFactory.getLogger(ActionManager.class);

    @Inject
    private BattleManager battleManager;

    @Inject
    private PlayerManager playerManager;

    private final BonusEventHandler bonusEventHandler = new BonusEventHandler();

    private final ActionEventHandler actionEventHandler = new ActionEventHandler();

    public final void setHeroRequest(final ATeam aTeam) {
        final ATeam currentATeam = playerManager.getCurrentATeam();
        final AHero currentHero = currentATeam.getCurrentPlayer().getHero();
        if (aTeam.equals(currentATeam)) {
            if (currentHero.isHealingAccess()) {
                healingProcess(aTeam);
            }
        } else {
            if (currentHero.isAttackAccess()) {
                attackProcess(aTeam, currentATeam);
            }
        }
    }

    private void healingProcess(final ATeam aTeam) {
        final Player currentPlayer = aTeam.getCurrentPlayer();
        final AHero currentHero = aTeam.getCurrentPlayer().getHero();
        if (currentHero.isTreatmentAccess()){
            final List<ActionEvent> treatmentEvents = new ArrayList<>() {{
                add(new ActionEvent(ActionType.TREATMENT, currentPlayer));
            }};
            final double treatmentValue = currentHero.getTreatment();
            if (currentHero.getHealing(currentPlayer, treatmentValue)){
                treatmentEvents.add(currentHero.getHealingEvent());
            }
            bonusEventHandler.handle(treatmentEvents);
            if (battleManager.isEndTurn()) {
                bonusEventHandler.handle(new ActionEvent(ActionType.END_TURN, aTeam.getCurrentPlayer()));
                battleManager.nextTurn();
            }
        }
    }

    private void attackProcess(final ATeam victim, final ATeam attack) {
        final Player currentPlayer = attack.getCurrentPlayer();
        final AHero attackHero = currentPlayer.getHero();
        if (attackHero.isAttackAccess()) {
            final List<ActionEvent> attackEvents = new ArrayList<>() {{
                add(new ActionEvent(ActionType.ATTACK, attack.getCurrentPlayer()));
            }};
            final double attackValue = attackHero.getAttack();
            attackHero.addExperience(attackValue);
            if (victim.getCurrentPlayer().getHero().getDamage(currentPlayer, attackValue)) {
                attackEvents.add(new ActionEvent(ActionType.DEAL_DAMAGE, attack.getCurrentPlayer()));
                attackEvents.add(victim.getCurrentPlayer().getHero().getDamageEvent());
            }
            bonusEventHandler.handle(attackEvents);
            if (battleManager.isEndTurn()) {
                bonusEventHandler.handle(new ActionEvent(ActionType.END_TURN, attack.getCurrentPlayer()));
                battleManager.nextTurn();
            }
        }
    }

    public final void setSkillRequest(final AHero hero, final AHero.Skill skill) {
        final ATeam currentATeam = playerManager.getCurrentATeam();
        final boolean heroAuthentication = hero.equals(currentATeam.getCurrentPlayer().getHero());
        log.info("hero authentication: " + heroAuthentication);
        if (heroAuthentication) {
            final boolean access = skill.isSkillAccess();
            log.info("skill access:" + access);
            if (access) {
                skillProcess(currentATeam, skill);
            }
        }
    }

    private void skillProcess(final ATeam currentATeam, AHero.Skill skill) {
        final Player currentPlayer = currentATeam.getCurrentPlayer();
        final AHero currentHero = currentPlayer.getHero();
        final boolean ready = skill.isReady();
        log.info("Ready: " + ready + " temp: " + skill.getTemp());
        final boolean levelReached = currentHero.getLevelHero() >= skill.getRequiredLevel();
        log.info("Level reached: " +  levelReached);
        if (ready && levelReached) {
            skill.getActionEvents().clear();
            skill.use(battleManager, playerManager);
            skill.reset();
            skill.showAnimation();
            bonusEventHandler.handle(skill.getActionEvents());
            bonusEventHandler.handle(new ActionEvent(ActionType.USED_SKILL, currentPlayer, skill.getName()));
            if (battleManager.isEndTurn()) {
                bonusEventHandler.handle(new ActionEvent(ActionType.END_TURN, currentATeam.getCurrentPlayer()));
                battleManager.nextTurn();
            }
        }
    }

    public final void setBonusRequest(final ATeam aTeam, final Bonus bonus) {

    }

    public final void setPlayerSwapRequest(final ATeam aTeam) {
        final ATeam currentTeam = playerManager.getCurrentATeam();
        if (aTeam.equals(currentTeam)) {
            if (aTeam.swapPlayers()) {
                final AHero currentHero = currentTeam.getCurrentPlayer().getHero();
                final AHero.Skill swapSkill = currentHero.getSwapSkill();
                if (swapSkill.isSkillAccess()){
                    skillProcess(currentTeam, swapSkill);
                }
                final ActionEvent actionEvent = new ActionEvent(ActionType.SWAP_HEROES, aTeam.getCurrentPlayer());
                actionEventHandler.handle(actionEvent);
                if (battleManager.isEndTurn()) {
                    battleManager.nextTurn();
                }
            }
        }
    }
}
