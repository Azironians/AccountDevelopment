package managment.playerManagement;

import org.jetbrains.annotations.Contract;

public final class ATeam {
    
    private Player currentPlayer;
    private Player alternativePlayer;
    private int turn;

    ATeam(final Player currentPlayer, final Player alternativePlayer) {
        this.currentPlayer = currentPlayer;
        this.alternativePlayer = alternativePlayer;
    }

    private boolean swapAccess = true;

    public final boolean swapPlayers(){
        if (swapAccess){
            final Player swapper = currentPlayer;
            currentPlayer = alternativePlayer;
            alternativePlayer = swapper;
            return true;
        }
        return false;
    }

    @Contract(pure = true)
    public final Player getCurrentPlayer() {
        return currentPlayer;
    }

    @Contract(pure = true)
    public final Player getAlternativePlayer() {
        return alternativePlayer;
    }

    @Contract(pure = true)
    public final int getTurn() {
        return turn;
    }

    @Override
    public final String toString() {
        return "ATeam{" +
                "currentPlayer=" + currentPlayer.getProfile().getName() +
                ", alternativePlayer=" + alternativePlayer.getProfile().getName() +
                ", turn=" + turn +
                '}';
    }

    public final void setTurn(final int turn) {
        this.turn = turn;
    }

    public final boolean isSwapAccess() {
        return swapAccess;
    }

    public final void setSwapAccess(boolean swapAccess) {
        this.swapAccess = swapAccess;
    }
}
