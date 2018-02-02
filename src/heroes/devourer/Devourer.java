package heroes.devourer;

import bonusDirectory.Bonus;
import heroes.abstractHero.AHero;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;

import java.util.List;

public final class Devourer extends AHero {

    Devourer(final Double attack, final Double treatment, final Double hitPoints, final Double supplyHealth
            , final Double currentExperience, final int levelHero, final List<Double> listOfRequiredExperience
            , final List<Double> listOfDamage, final List<Double> listOfTreatment, final List<Double> listOfSupplyHealth
            , final List<Skill> collectionOfSkills, final ImageView face, final List<Media> listOfAttackVoices
            , final List<Media> listOfTreatmentVoices, final Presentation presentation
            , final List<Bonus> currentCollection) {
        super(attack, treatment, hitPoints, supplyHealth, currentExperience, levelHero, listOfRequiredExperience
                , listOfDamage, listOfTreatment, listOfSupplyHealth, collectionOfSkills, face, listOfAttackVoices
                , listOfTreatmentVoices, presentation, currentCollection);
    }

    @Override
    public final void showAttackAnimation() {

    }

    @Override
    public final void showTreatmentAnimation() {

    }
}
