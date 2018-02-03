package controllers.main.profile;

import bonusDirectory.Deck;
import controllers.Controller;
import controllers.main.ControllerChoiceBonus;
import controllers.main.ControllerChoiceHero;
import gui.clock.AClock;
import gui.sceneMover.ASceneMover;
import gui.windows.WindowType;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import main.AGame;
import managment.profileManage.FictionalProfiles;
import managment.profileManage.Profile;
import com.google.inject.Inject;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.function.Consumer;
import java.util.logging.Logger;

public class ControllerProfile implements Initializable, Controller {
    private static Logger logger = Logger.getLogger(ControllerProfile.class.getName());



    @Inject
    private AGame aGame;

    @FXML
    private ImageView buttonOffLogOut;
    @FXML
    private ImageView buttonOnLogOut;
    @FXML
    private ImageView buttonOffShowStatistics;
    @FXML
    private ImageView buttonOnShowStatistics;
    @FXML
    private ImageView buttonOffChoiceHeroes;
    @FXML
    private ImageView buttonOnChoiceHeroes;

    @FXML
    public Pane paneHeroes;
    @FXML
    private ImageView devourer;
    @FXML
    private ImageView lordVamp;
    @FXML
    private ImageView orcBasher;

    @FXML
    private Text textFavouriteHero;

    @FXML
    private Text textProfileName;

    @FXML
    private Pane paneStatistics;

    @FXML
    private Button btn;
    @FXML
    private Text time;

    @Inject
    private ASceneMover aSceneMover;

    private Profile currentProfile = FictionalProfiles.createPoul();

    private Deck defaultPrimaryDeck = new Deck("", "", -1, new ArrayList<>());

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        AClock.launchTimer(time);
        btn.setOnKeyPressed(event -> {
            System.out.println(defaultPrimaryDeck);
            System.out.println(defaultPrimaryDeck.getCollection().size());
        });
    }

    public Profile getCurrentProfile() {
        return currentProfile;
    }

    public void setCurrentProfile(Profile currentProfile) {
        this.currentProfile = currentProfile;
    }

    public Deck getDefaultPrimaryDeck() {
        return defaultPrimaryDeck;
    }

    public void setDefaultPrimaryDeck(Deck defaultPrimaryDeck) {
        this.defaultPrimaryDeck = defaultPrimaryDeck;
    }

    @Override
    public void appearance() {
        final String name = defaultPrimaryDeck.getHero();
        switch (name) {
            case "Devourer":
                devourer.setVisible(true);
                break;
            case "LordVampire":
                lordVamp.setVisible(true);
                break;
            case "OrcBasher":
                orcBasher.setVisible(true);
        }
        final String headline = textFavouriteHero.getText() + name;
        textFavouriteHero.setText(headline);
    }

    private void removeAccountData() {
        final ControllerChoiceBonus controllerChoiceBonus = (ControllerChoiceBonus) aGame.getWindowMap()
                .get(WindowType.CHOICE_BONUS).getController();
        controllerChoiceBonus.setBonusData(null);
        controllerChoiceBonus.setPrivilegedCollections(null);
        controllerChoiceBonus.setDefaultPrimaryDeck(null);

        final ControllerChoiceHero controllerChoiceHero = (ControllerChoiceHero) aGame.getWindowMap()
                .get(WindowType.CHOICE_HERO).getController();
        controllerChoiceHero.setPrivilegedCollections(null);
        controllerChoiceHero.setDefaultPrimaryDeck(null);
        currentProfile = null;
        defaultPrimaryDeck = null;
        resetHeroImages();
    }

    private void resetHeroImages(){
        final ObservableList<Node> images = paneHeroes.getChildren();
        images.forEach(image -> image.setVisible(false));
    }

    //Style & gameInterface:
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void buttonOnChoiceHeroesClicked() {
        aSceneMover.moveToScene(WindowType.CHOICE_HERO);
    }

    public void buttonOffChoiceHeroesEntered() {
        buttonOffChoiceHeroes.setVisible(false);
        buttonOnChoiceHeroes.setVisible(true);
    }

    public void buttonOnChoiceHeroesExited() {
        buttonOnChoiceHeroes.setVisible(false);
        buttonOffChoiceHeroes.setVisible(true);
    }

    public void buttonOffShowStatisticsEntered() {
        buttonOffShowStatistics.setVisible(false);
        buttonOnShowStatistics.setVisible(true);
    }

    public void buttonOnShowStatisticsExited() {
        buttonOffShowStatistics.setVisible(true);
        buttonOnShowStatistics.setVisible(false);
    }

    public void buttonOnShowStatisticsClicked() {
        paneStatistics.setVisible(true);
    }

    public void buttonOffLogOutEntered() {
        buttonOffLogOut.setVisible(false);
        buttonOnLogOut.setVisible(true);
    }

    public void buttonOnLogOutExited() {
        buttonOffLogOut.setVisible(true);
        buttonOnLogOut.setVisible(false);
    }

    public void buttonOnLogOutClicked() {
        removeAccountData();
        aSceneMover.moveToScene(WindowType.AUTHORIZATION);
    }


    //Getters & setters:
    public Text getTextProfileName() {
        return textProfileName;
    }

    public void setTextProfileName(final String textProfileName) {
        this.textProfileName.setText(textProfileName);
    }
}
