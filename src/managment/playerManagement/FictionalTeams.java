package managment.playerManagement;

import heroes.abstractHero.AHero;
import heroes.devourer.DevourerBuilder;
import heroes.lordVampire.LVBuilder;
import heroes.orcBash.OrcBashBuilder;
import managment.profileManage.Profile;
import org.jetbrains.annotations.NotNull;

/**
 * Test factory imitates players in match.
 * Temporary test clazz.
 */

public final class FictionalTeams {

    @NotNull
    public static ATeam createLeft(){

        final Profile joysProfile = new Profile("Joe", 0, null, null, null
                , null, null, null, null);
        final Profile goresProfile = new Profile("Gore", 0, null, null, null
                , null, null, null, null);

        final AHero joysDevourer = new DevourerBuilder().buildHero(null);
        final AHero goresOrcBash = new OrcBashBuilder().buildHero(null);

        final Player joysPlayer = new Player(joysProfile, joysDevourer);
        final Player goresPlayer = new Player(goresProfile, goresOrcBash);

        return new ATeam(goresPlayer, joysPlayer);
    }

    @NotNull
    public static ATeam createRight(){
        final Profile mikesProfile = new Profile("Mike", 0, null, null, null
                , null
                , null, null, null);
        final Profile kevinProfile = new Profile("Kevin", 0, null, null, null
                , null
                , null, null, null);

        final AHero mikesLordVampire = new LVBuilder().buildHero(null);
        final AHero kevinOrcBash = new OrcBashBuilder().buildHero(null);

        final Player mikesPlayer = new Player(mikesProfile, mikesLordVampire);
        final Player kevinPlayer = new Player(kevinProfile, kevinOrcBash);

        return new ATeam(mikesPlayer, kevinPlayer);
    }
}
