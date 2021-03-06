package controllers.main.profile;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class ControllerStatistic {
    @FXML
    private Pane paneStatistic;
    @FXML
    private ImageView panelStatistics;
    @FXML
    private Text textRank;
    @FXML
    private Text textRating;
    @FXML
    private Text textWins;
    @FXML
    private Text textLoses;
    @FXML
    private Text textWinsDevourer;
    @FXML
    private Text textWinsLV;
    @FXML
    private Text textWinsOrcBasher;

    @FXML
    private ImageView buttonOffCloseStatistics;
    @FXML
    private ImageView buttonOnCloseStatistics;

    public void buttonOffCloseStatisticsEntered(){
        buttonOffCloseStatistics.setVisible(false);
        buttonOnCloseStatistics.setVisible(true);
    }
    public void buttonOnCloseStatisticsExited(){
        buttonOffCloseStatistics.setVisible(true);
        buttonOnCloseStatistics.setVisible(false);
    }
    public void buttonOnCloseStatisticsClicked(){
        paneStatistic.setVisible(false);
    }

}
