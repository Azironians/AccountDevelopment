package managment.battleManagement;

import bonusDirectory.BonusEventHandler;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import heroes.abstractHero.AHero;
import managment.actionManagement.ActionEvent;
import managment.actionManagement.ActionType;
import managment.playerManagement.ATeam;
import managment.playerManagement.Player;
import managment.playerManagement.PlayerManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public final class BattleManager {
    private final static Logger logger = LoggerFactory.getLogger(BattleManager.class);

    @Inject
    @Named("start time")
    private static int startTime;

    @Inject
    private PlayerManager playerManager;

    @Inject
    private BonusEventHandler bonusEventHandler;

    @Inject
    @Named("turn")
    private int turn;

    //End turn condition:
    @Inject
    @Named("end turn condition")
    private boolean endTurn;

    @Inject
    @Named("skip turn condition")
    private boolean skipTurn;

    //Next turn:
    public final void nextTurn() {
        if (endGame()) {
            logger.info("GAME_OVER");
        } else {
            changeTurn();
            if (skipTurn) {
                skipTurn = false;
                changeTurn();
            }
        }
    }

    //Defines turn:
    private void changeTurn() {
        final ATeam left = playerManager.getLeftATeam();
        final ATeam right = playerManager.getRightATeam();
        final Player currentPlayer = playerManager.getCurrentATeam().getCurrentPlayer();
        turn = (turn + 1) % 2;
        if (turn == left.getTurn()) {
            playerManager.setCurrentATeam(left);
            playerManager.setOpponentATeam(right);
        }
        if (turn == right.getTurn()) {
            playerManager.setCurrentATeam(right);
            playerManager.setOpponentATeam(left);
        }
        playerManager.getCurrentATeam().getCurrentPlayer().getHero().reloadSkills();
        final ActionEvent start = new ActionEvent(ActionType.START_TURN, currentPlayer);
        bonusEventHandler.handle(start);
    }

    private boolean endGame() {
        final AHero currentHero = playerManager.getCurrentATeam().getCurrentPlayer().getHero();
        return currentHero.getHitPoints() <= 0;
    }

    public static int getStartTime() {
        return startTime;
    }

    public static void setStartTime(final int START_TIME) {
        BattleManager.startTime = START_TIME;
    }

    public final boolean isEndTurn() {
        return endTurn;
    }


    public final void setEndTurn(final boolean endTurn) {
        this.endTurn = endTurn;
    }

    public final boolean isSkipTurn() {
        return skipTurn;
    }

    public final void setSkipTurn(final boolean skipTurn) {
        this.skipTurn = skipTurn;
    }
}


