package security;

import bonusDirectory.BonusSupplier;
import bonusDirectory.Deck;
import controllers.main.ControllerChoiceBonus;
import controllers.main.ControllerChoiceHero;
import controllers.main.profile.ControllerProfile;
import gui.windows.WindowType;
import heroes.abstractHero.AHeroBuilder;
import main.AGame;
import managment.playerManagement.PlayerManager;
import managment.profileManage.Profile;
import com.google.inject.Inject;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.util.*;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

// FIXME: 31.01.2018 wrap in scope
public final class AGate {

    //Authorization messages:
    private final String NOT_CORRECT_LOGIN = "Неверное имя пользователя или пароль";
    private final String SUCCESSFUL_LOGIN = "Авторизация прошла успешно";
    private final String BAD_COLLECTIONS = "Не удалось получить коллекции героев";
    private final String PLAYER_WAS_AUTHORIZED = "Такой пользователь уже авторизован";

    //Registration messages:
    private final String DIFFERENT_NAMES = "Имя пользователя не совпадает с текущим";
    private final String NOT_FILLED_LOGIN_FIELD = "Заполните поля с именем пользователя";
    private final String DIFFERENT_PASSWORD = "Пароль пользователя не совпадает с текущим";
    private final String PROFILE_IS_EXIST = "Такой профиль уже существует";
    private final String SUCCESSFUL_REGISTRATION = "Регистрация прошла успешно";

    //ProfileProperties:
    private final int LOGIN = 0;
    private final int PASS = 1;
    private final int PROFILE_LINES = 8;
    private final int RANK = 2; //Example (3/327)
    private final int MIN_LEVEL = 1;
    private final int MAX_LEVEL = 20;
    private final int MAX_RATING = 5000;
    private final int MIN_RATING = 0;

    //Logger:
    private final Logger logger = Logger.getLogger(AGate.class.getName());

    {
        logger.setLevel(Level.FINEST);
        logger.addHandler(new ConsoleHandler() {{
            setLevel(Level.FINEST);
        }});
    }

    @Inject
    private AGame aGame;

    @Inject
    private PlayerManager playerManager;

    //Extracted data:
    private List<String> accountData = null;
    private Map<String, List<Deck>> bonusData = null;
    private List<Deck> privilegedCollections = null;
    private Deck defaultPrimaryDeck = null;

    // FIXME: 31.01.2018 make with assist inject
    public static final class Endeavour {
        private final String message;
        private final boolean isSuccessful;

        Endeavour(final String message, final boolean isSuccessful) {
            this.message = message;
            this.isSuccessful = isSuccessful;
        }

        @Contract(pure = true)
        public final String getMessage() {
            return message;
        }

        @Contract(pure = true)
        public final boolean isSuccessful() {
            return isSuccessful;
        }
    }

    @NotNull
    public final Endeavour isAuthorizationSuccessful(final TextField textFieldSignIn
            , final PasswordField passwordFieldSignIn) {
        final Map mapOfPlayers = playerManager.getMapOfPlayers();
        if (mapOfPlayers.containsKey(textFieldSignIn.getText())) {
            return new Endeavour(PLAYER_WAS_AUTHORIZED, false);
        }
        try {
            final BufferedReader reader = new BufferedReader(new FileReader
                    (new File(AccountBuilder.profilePath(textFieldSignIn.getText()))));
            accountData = reader.lines().collect(Collectors.toList());
            logger.finest("AccountData: " + accountData.toString());
            if (accountData.size() != PROFILE_LINES) {
                return new Endeavour(NOT_CORRECT_LOGIN, false);
            }
            if (!textFieldSignIn.getText().equals(accountData.get(LOGIN))) {
                return new Endeavour(NOT_CORRECT_LOGIN, false);
            }
            if (!passwordFieldSignIn.getText().equals(accountData.get(PASS))) {
                return new Endeavour(NOT_CORRECT_LOGIN, false);
            }
            final String[] parts = accountData.get(RANK).split("/");
            if (Byte.parseByte(parts[0]) > MAX_LEVEL || Byte.parseByte(parts[0]) < MIN_LEVEL
                    || Integer.parseInt(parts[1]) < MIN_RATING || Integer.parseInt(parts[1]) > MAX_RATING) {
                return new Endeavour(NOT_CORRECT_LOGIN, false);
            }
            //Check wins for heroes & control sum:
            if (Integer.parseInt(accountData.get(3)) < 0 || Integer.parseInt(accountData.get(4)) < 0 ||
                    Integer.parseInt(accountData.get(5)) < 0 || Integer.parseInt(accountData.get(6)) < 0 ||
                    Integer.parseInt(accountData.get(7)) < 0 ||
                    Integer.parseInt(accountData.get(3)) != Integer.parseInt(accountData.get(5)) +
                            Integer.parseInt(accountData.get(6)) + Integer.parseInt(accountData.get(7))) {
                return new Endeavour(NOT_CORRECT_LOGIN, false);
            }
        } catch (final Exception e) {
            return new Endeavour(NOT_CORRECT_LOGIN, false);
        }
        logger.finest("Passed");
        return bonusContent(textFieldSignIn.getText());
    }

    // FIXME: 31.01.2018 move to BonusSupplier
    @NotNull
    private Endeavour bonusContent(final String profileName) {

        bonusData = new HashMap<>(); //all decks
        privilegedCollections = new ArrayList<>(); //best deck for each hero
        final BonusSupplier bonusSupplier = new BonusSupplier(profileName);
        logger.finest("" + bonusSupplier);
        try {
            int runner = bonusSupplier.getRunner();
            int shift = bonusSupplier.getShift();
            for (final File file : bonusSupplier.getFilesOfBonuses()) {
                final String heroName = file.getName().substring(0, file.getName().length() - 4); //without ".hoa"
                logger.finest(heroName);
                final List<Deck> heroCollections = bonusSupplier.parseBonusesForCurrentHero(
                        heroName, new BufferedReader(new FileReader(file)).lines().collect(Collectors.toList()), runner);
                privilegedCollections.add(findPrivilegedWrapper(heroCollections));
                bonusData.put(heroName, heroCollections);
                runner += shift;
            }
            defaultPrimaryDeck = findPrivilegedWrapper(privilegedCollections);
            if (bonusData.size() == AHeroBuilder.getHeroNames().size()) {
                return new Endeavour(SUCCESSFUL_LOGIN, true);
            } else {
                return new Endeavour(BAD_COLLECTIONS, false);
            }
        } catch (final Exception e) {
            return new Endeavour(BAD_COLLECTIONS, false);
        }
    }

    @Nullable
    private Deck findPrivilegedWrapper(final List<Deck> deckList) {
        if (deckList.size() > 0) {
            Deck privilegedDeck = deckList.get(0);
            for (final Deck deck : deckList) {
                if (deck.getPriority() > privilegedDeck.getPriority()) {
                    privilegedDeck = deck;
                }
            }
            return privilegedDeck;
        } else {
            return null;
        }
    }

    public final void doAuthorization() {
        final String[] parts = accountData.get(2).split("/");
        final Profile profile = new Profile(accountData.get(0), Byte.parseByte(parts[0]), Integer.parseInt(parts[1]),
                Integer.parseInt(accountData.get(3)), Integer.parseInt(accountData.get(4)), Integer.parseInt(accountData.get(5)),
                Integer.parseInt(accountData.get(6)), Integer.parseInt(accountData.get(7)), bonusData);

        final ControllerProfile controllerProfile = (ControllerProfile) aGame.getWindowMap()
                .get(WindowType.PROFILE).getController();
        controllerProfile.setCurrentProfile(profile);
        controllerProfile.setDefaultPrimaryDeck(defaultPrimaryDeck);
        controllerProfile.setTextProfileName(accountData.get(0));

        final ControllerChoiceBonus controllerChoiceBonus = (ControllerChoiceBonus) aGame.getWindowMap()
                .get(WindowType.CHOICE_BONUS).getController();
        controllerChoiceBonus.setBonusData(bonusData);
        controllerChoiceBonus.setPrivilegedCollections(privilegedCollections);
        controllerChoiceBonus.setDefaultPrimaryDeck(defaultPrimaryDeck);

        final ControllerChoiceHero controllerChoiceHero = (ControllerChoiceHero) aGame.getWindowMap()
                .get(WindowType.CHOICE_HERO).getController();
        controllerChoiceHero.setPrivilegedCollections(privilegedCollections);
        controllerChoiceHero.setDefaultPrimaryDeck(defaultPrimaryDeck);

        logger.finest("PRIMARY DECK: \n" + defaultPrimaryDeck.toString());
        logger.finest("PRIVILEGED DECKS: \n" + privilegedCollections.toString());
        logger.finest( "BONUS DATA: \n" + bonusData.toString());
    }

    @NotNull
    public final Endeavour isRegistrationSuccessful(final TextField textFieldNewName
            , final TextField textFieldNewNameRepeat
            , final PasswordField passwordFieldNewPassword
            , final PasswordField passwordFieldNewPasswordRepeat) {
        if (!textFieldNewName.getText().equals(textFieldNewNameRepeat.getText())) {
            return new Endeavour(DIFFERENT_NAMES, false);
        }
        if (textFieldNewName.getText().equals("")) {
            return new Endeavour(NOT_FILLED_LOGIN_FIELD, false);
        }
        if (!passwordFieldNewPassword.getText().equals(passwordFieldNewPasswordRepeat.getText())) {
            return new Endeavour(DIFFERENT_PASSWORD, false);
        }
        if (new File(AccountBuilder.profilePath(textFieldNewName.getText())).canRead()) {
            return new Endeavour(PROFILE_IS_EXIST, false);
        }
        return new Endeavour(SUCCESSFUL_REGISTRATION, true);
    }

    public final void doRegistration(final TextField textFieldNewName, final PasswordField passwordFieldNewPassword) {
        final String login = textFieldNewName.getText();
        final String password = passwordFieldNewPassword.getText();
        BufferedWriter bufferedWriter;
        try {
            //Creating of new account:
            final File newDirectory = new File(AccountBuilder.directoryPath(login));
            if (newDirectory.mkdirs()) {
                final File newProfile = new File(AccountBuilder.profilePath(login));
                bufferedWriter = new BufferedWriter(new FileWriter(newProfile));
                final List<String> profileList = AccountBuilder.createAccount(login, password);
                int i = 0;
                for (final String line : profileList) {
                    bufferedWriter.write(line);
                    i++;
                    if (i < profileList.size()) bufferedWriter.newLine();
                }
                bufferedWriter.close();
            }
            //Creating start decks for account:
            final File newCollectionDirectory = new File(AccountBuilder.collectionsPath(login));
            if (newCollectionDirectory.mkdirs()) {
                for (final String hero : AHeroBuilder.getHeroNames()) {
                    bufferedWriter = new BufferedWriter(new FileWriter(new File
                            (AccountBuilder.heroCollectionPath(login, hero))));
                    final String classicCollectionID = AccountBuilder.createDefaultDeck(hero);
                    bufferedWriter.write(classicCollectionID);
                    bufferedWriter.close();
                }
            }
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }
}
