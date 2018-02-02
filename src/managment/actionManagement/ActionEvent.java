package managment.actionManagement;

import managment.playerManagement.Player;

public final class ActionEvent {

    private final ActionType actionType;
    private final Player player;
    private String message;

    public ActionEvent(final ActionType actionType, final Player player, final String message) {
        this.actionType = actionType;
        this.player = player;
        this.message = message;
    }

    public ActionEvent(final ActionType actionType, final Player player) {
        this.actionType = actionType;
        this.player = player;
    }

    public final ActionType getActionType() {
        return actionType;
    }
}
