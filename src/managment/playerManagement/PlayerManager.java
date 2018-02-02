package managment.playerManagement;

import com.google.inject.Inject;
import controllers.main.matchmaking.ControllerMatchMaking;
import gui.locations.ALocationGraphicBinder;
import gui.windows.WindowType;
import main.AGame;
import org.jetbrains.annotations.Contract;

import java.util.HashMap;
import java.util.Map;

public final class PlayerManager {

    @Inject
    private AGame aGame;

    private ALocationGraphicBinder aLocationGraphicBinder;

    private Map<String, Player> mapOfPlayers = null;

    private int countPlayers = 0;

    private ATeam rightATeam;

    private ATeam leftATeam;

    private ATeam currentATeam;

    private ATeam opponentATeam;

    //Setters:
    public final void setPlayerCount(int countPlayers) {
        this.countPlayers = countPlayers;
        this.mapOfPlayers = new HashMap<>(countPlayers);
    }

    //Getters:
    @Contract(pure = true)
    public final Map<String, Player> getMapOfPlayers() {
        return mapOfPlayers;
    }

    @Contract(pure = true)
    public final int getCountPlayers() {
        return this.countPlayers;
    }

    @Contract(pure = true)
    public final ATeam getRightATeam() {
        return rightATeam;
    }

    @Contract(pure = true)
    public final ATeam getLeftATeam() {
        return leftATeam;
    }

    @Contract(pure = true)
    public final ATeam getCurrentATeam() {
        return currentATeam;
    }

    @Contract(pure = true)
    public final ATeam getOpponentATeam() {
        return opponentATeam;
    }

    public final void setCurrentATeam(ATeam currentATeam) {
        this.currentATeam = currentATeam;
    }

    public final void setOpponentATeam(ATeam opponentATeam) {
        this.opponentATeam = opponentATeam;
    }

    public final void setupALocationGraphicBinder(){
        final ControllerMatchMaking controllerMatchMaking = (ControllerMatchMaking) aGame.getWindowMap()
                .get(WindowType.MATCHMAKING).getController();
        this.aLocationGraphicBinder = new ALocationGraphicBinder(controllerMatchMaking, leftATeam, rightATeam);
        this.aLocationGraphicBinder.bind();
    }

    public final void setRightATeam(final ATeam rightATeam) {
        this.rightATeam = rightATeam;
    }

    public final void setLeftATeam(final ATeam leftATeam) {
        this.leftATeam = leftATeam;
    }

    @Contract(pure = true)
    public final ALocationGraphicBinder getaLocationGraphicBinder() {
        return aLocationGraphicBinder;
    }
}
