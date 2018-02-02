package bonusDirectory;

import heroes.abstractHero.AHeroBuilder;
import org.jetbrains.annotations.Contract;
import security.AccountBuilder;

import java.io.*;
import java.util.*;
import java.util.logging.Logger;

// FIXME: 31.01.2018 wrap in scope
public final class BonusSupplier {
    private final Logger logger = Logger.getLogger(BonusSupplier.class.getName());

    private String profileName = null;
    private Collection<File> deckFiles = null;
    private final Bonuses bonuses = new Bonuses();
    private final static int DECK_SIZE = 16;
    private final int runner = 16; //Верхние границы:
    private final int shift = 48; //Кол-во бонусов:

    public BonusSupplier(String profileName) {
        this.profileName = profileName;
        this.deckFiles = new ArrayList<>();
        for (String hero : AHeroBuilder.getHeroNames()) {
            deckFiles.add(new File(AccountBuilder.heroCollectionPath(profileName, hero)));
        }
    }

    @Contract(pure = true)
    private boolean isClassicBonus(int bonus) {
        return bonus > 0 && bonus <= 16;
    }

    public List<Deck> parseBonusesForCurrentHero(String heroName, List<String> listOfInputCollection, int start) {
        final List<Deck> outputList = new ArrayList<>();
        for (final String inputCollection : listOfInputCollection) {
            final String[] data = inputCollection.split(" ");
            final Set<Bonus> bonusSet = new HashSet<>();
            for (int i = 2; i < data.length; i++) {
                int bonus = Integer.parseInt(data[i]);
                if (isClassicBonus(bonus) || (bonus >= start && bonus <= start + 48)) {
                    bonusSet.add(bonuses.getMapOfBonus().get(bonus));
                } else {
                    throw new IllegalArgumentException("This bonus is not valid");
                }
            }
            if (bonusSet.size() != DECK_SIZE) {
                throw new IllegalArgumentException("This bonus is not valid");
            }
            List<Bonus> bonusList = new ArrayList<>(bonusSet);
            outputList.add(new Deck(data[0], heroName, Integer.parseInt(data[1]), bonusList));
        }
        return outputList;
    }

//    @NotNull
//    private String joinToString(List<Bonus> bonusList) {
//        StringBuilder sb = new StringBuilder();
//        for (Bonus b : bonusList) {
//            sb.append(b.getId());
//            sb.append(" ");
//        }
//        sb.deleteCharAt(sb.length() - 1);
//        return sb.toString();
//    }

    public Collection<File> getFilesOfBonuses() {
        return deckFiles;
    }

    @Contract(pure = true)
    public int getRunner() {
        return runner;
    }

    @Contract(pure = true)
    public int getShift() {
        return shift;
    }
}