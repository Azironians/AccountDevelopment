package controllers.main;

import bonusDirectory.Deck;
import controllers.Controller;
import managment.playerManagement.Player;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

// FIXME: 02.02.2018 добавить картинку героя, замазать фон, опустить стрелочки
public class ControllerChoiceHero implements Initializable, Controller {
    @FXML
    private ImageView spotLightDev;
    @FXML
    private ImageView spotLightLV;
    @FXML
    private ImageView spotLightBHR;
    @FXML
    private ImageView btnOffLeft;
    @FXML
    private ImageView btnOnLeft;
    @FXML
    private ImageView btnOnRight;
    @FXML
    private ImageView btnOffRight;
    @FXML
    private ImageView currentBackground;
    @FXML
    private Button btnChoiceHero;
    @FXML
    private Button btnBackToProfile;
    @FXML
    private Pane paneMessage;
    @FXML
    private ImageView btnOffBack;
    @FXML
    private ImageView btnOnBack;
    @FXML
    private ImageView btnOffChoiceHero;
    @FXML
    private ImageView btnOnChoiceHero;

    private Player currentProfile = null;

    private List<Deck> privilegedCollections = null;

    private Deck defaultPrimaryDeck;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        appearance();
    }

    @Override
    public void appearance() {

    }

    public List<Deck> getPrivilegedCollections() {
        return privilegedCollections;
    }

    public void setPrivilegedCollections(List<Deck> privilegedCollections) {
        this.privilegedCollections = privilegedCollections;
    }

    public Deck getDefaultPrimaryDeck() {
        return defaultPrimaryDeck;
    }

    public void setDefaultPrimaryDeck(Deck defaultPrimaryDeck) {
        this.defaultPrimaryDeck = defaultPrimaryDeck;
    }
}
