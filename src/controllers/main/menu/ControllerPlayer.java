package controllers.main.menu;

import com.google.inject.Inject;
import controllers.Controller;
import controllers.main.matchmaking.ControllerMatchMaking;
import gui.sceneMover.ASceneMover;
import gui.windows.WindowType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import main.AGame;
import managment.playerManagement.PlayerManager;
import managment.playerManagement.FictionalTeams;
import org.jetbrains.annotations.Contract;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

public final class ControllerPlayer implements Initializable, Controller{

    @FXML
    private AnchorPane root;

    @FXML
    private Text gameMode;

    @FXML
    private Button buttonStart;

    @FXML
    private Button buttonBack;

    @FXML
    private AnchorPane primaryPlayerPane;

    @FXML
    private AnchorPane secondaryPlayerPane;

    @FXML
    private Button primaryLeftPlayer;

    @FXML
    private Button primaryRightPlayer;

    @FXML
    private Button secondaryLeftPlayer;

    @FXML
    private Button secondaryRightPlayer;

    @Inject
    private AGame aGame;

    @Inject
    private PlayerManager playerManager;

    @Inject
    private ASceneMover ASceneMover;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @Override
    public void appearance() {

    }

    //Style & interface:
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public final void buttonStartClicked(){
//        final ControllerMatchMaking matchMaking = (ControllerMatchMaking) aGame.getWindowMap()
//                .get(WindowType.AUTHORIZATION).getController();

        //Temporary settings:  ------------------------>
//        matchMaking.appearance();
//        initPlayers();
        ///////////////////////<------------------------


        ASceneMover.moveToScene(WindowType.AUTHORIZATION);
    }

    public final void buttonBackClicked(){
        root.setVisible(false);
        root.setDisable(true);
        final ControllerMenu controllerMenu = (ControllerMenu) aGame.getWindowMap().get(WindowType.MENU).getController();
        controllerMenu.getPanelLocMch().setVisible(true);
        controllerMenu.getPanelLocMch().setDisable(false);
        controllerMenu.getPaneButtons().setVisible(false);
        controllerMenu.getPaneButtons().setDisable(true);
        controllerMenu.getPaneMessage().setVisible(false);
        controllerMenu.getPaneMessage().setDisable(true);
    }

    private void initPlayers(){
        //setup teams:
        playerManager.setLeftATeam(FictionalTeams.createLeft());
        playerManager.setRightATeam(FictionalTeams.createRight());
        //setup randomly start team:
        final Random random = new Random();
        if (random.nextBoolean()){
            playerManager.setCurrentATeam(playerManager.getRightATeam());
            playerManager.setOpponentATeam(playerManager.getLeftATeam());
        } else {
            playerManager.setCurrentATeam(playerManager.getLeftATeam());
            playerManager.setOpponentATeam(playerManager.getRightATeam());
        }
        playerManager.getCurrentATeam().setTurn(0);
        playerManager.getOpponentATeam().setTurn(1);
        playerManager.setupALocationGraphicBinder();
    }

    @Contract(pure = true)
    public AnchorPane getRoot() {
        return root;
    }

    @Contract(pure = true)
    public Text getGameMode() {
        return gameMode;
    }

    @Contract(pure = true)
    public Button getButtonStart() {
        return buttonStart;
    }

    @Contract(pure = true)
    public Button getButtonBack() {
        return buttonBack;
    }

    @Contract(pure = true)
    public AnchorPane getPrimaryPlayerPane() {
        return primaryPlayerPane;
    }

    @Contract(pure = true)
    public AnchorPane getSecondaryPlayerPane() {
        return secondaryPlayerPane;
    }

    @Contract(pure = true)
    public Button getPrimaryLeftPlayer() {
        return primaryLeftPlayer;
    }

    @Contract(pure = true)
    public Button getPrimaryRightPlayer() {
        return primaryRightPlayer;
    }

    @Contract(pure = true)
    public Button getSecondaryLeftPlayer() {
        return secondaryLeftPlayer;
    }

    @Contract(pure = true)
    public Button getSecondaryRightPlayer() {
        return secondaryRightPlayer;
    }
}
