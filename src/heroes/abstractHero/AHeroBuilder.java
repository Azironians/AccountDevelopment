package heroes.abstractHero;

import bonusDirectory.Bonus;
import org.jetbrains.annotations.Contract;

import java.util.*;

public interface AHeroBuilder {

    @Contract(pure = true)
    static Set<String> getHeroNames() {
        return setOfHeroes;
    }

     Set<String> setOfHeroes = new HashSet<>() {
        {
            add("Devourer");
            add("LordVampire");
            add("OrcBash");
        }
    };

    AHero buildHero(final List<Bonus> deck);
}