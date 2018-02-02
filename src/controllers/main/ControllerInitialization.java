package controllers.main;

import controllers.Controller;
import gui.sceneMover.ASceneMover;
import gui.windows.WindowType;
import com.google.inject.Inject;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import debug.DebugThread;

import java.net.URL;
import java.util.ResourceBundle;

//Finished:
public final class ControllerInitialization implements Initializable, Controller {
    //Meeting with slf4j:
    private final Logger logger = LoggerFactory.getLogger(ControllerInitialization.class);

    {
        PropertyConfigurator.configure("src\\log4j.properties");
        logger.info("Hello world");
    }

    @FXML
    private ImageView animation;
    @FXML
    private ImageView labelOfA;
    @FXML
    private Button trigger;

    @Inject
    private ASceneMover ASceneMover;

    @Inject
    private DebugThread debugThread;

    @Override
    public final void initialize(final URL location, final ResourceBundle resources) {
        appearance();
    }

    @Override
    public final void appearance() {
        debugThread.debug();

        final Timeline timer = new Timeline(new KeyFrame(Duration.millis(9250)));
        timer.setOnFinished(animationEnd -> {
            animation.setVisible(false);
            ASceneMover.moveToScene(WindowType.MENU);
        });
        trigger.setOnKeyPressed(event -> timer.jumpTo(Duration.millis(9500)));
        timer.play();
    }







}


//
//    final Timeline timer = new Timeline(new KeyFrame(Duration.millis(9250)));
//        timer.setOnFinished(animationEnd -> {
//                labelOfA.setVisible(true);
//                animation.setVisible(false);
//final Timeline delay = new Timeline(new KeyFrame(Duration.seconds(2)));
//        delay.setOnFinished(labelEnd -> ASceneMover.moveToScene(WindowType.MENU));
//        delay.play();
//        });
//        trigger.setOnKeyPressed(event -> timer.jumpTo(Duration.millis(9500)));
//        timer.play();