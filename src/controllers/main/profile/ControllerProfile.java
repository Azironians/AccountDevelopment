package controllers.main.profile;

import bonusDirectory.Deck;
import controllers.Controller;
import gui.clock.AClock;
import gui.sceneMover.ASceneMover;
import gui.windows.WindowType;
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
import java.util.logging.Logger;

public class ControllerProfile implements Initializable, Controller {
    private static Logger logger = Logger.getLogger(ControllerProfile.class.getName());

    @FXML
    private ImageView background;
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
    private ImageView Devourer;
    @FXML
    private ImageView LordVamp;
    @FXML
    private ImageView OrcBasher;

    @FXML
    private Text textFavouriteHero;

    @FXML
    private Text textProfileName;
    @FXML
    private Text textPlayer;

    @FXML
    private Pane paneStatistics;

    @FXML
    private Button btn;
    @FXML
    private Text time;

    @Inject
    private ASceneMover aSceneMover;

    private Profile currentProfile = FictionalProfiles.createPoul();

    private Deck defaultPrimaryDeck = new Deck("empty", "empty", -1, new ArrayList<>());

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
//        logger.log(Level.INFO, defaultPrimaryDeck.toString());
//        System.out.println(defaultPrimaryDeck);
//        System.out.println(defaultPrimaryDeck.getCollection().size());

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
    public void buttonOffShowStatisticsEntered(){
        buttonOffShowStatistics.setVisible(false);
        buttonOnShowStatistics.setVisible(true);
    }
    public void buttonOnShowStatisticsExited(){
        buttonOffShowStatistics.setVisible(true);
        buttonOnShowStatistics.setVisible(false);
    }
    public void buttonOnShowStatisticsCliced(){
        paneStatistics.setVisible(true);
    }

    //Getters & setters:
    public Text getTextProfileName() {
        return textProfileName;
    }

    public void setTextProfileName(final String textProfileName) {
        this.textProfileName.setText(textProfileName);
    }
}
