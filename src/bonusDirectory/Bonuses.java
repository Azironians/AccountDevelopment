package bonusDirectory;

import bonusDirectory.GeneralBonuses.*;

import java.util.HashMap;
import java.util.Map;

public class Bonuses {
    private Map<Integer, Bonus> mapOfBonus = new HashMap<>();

    Bonuses() {
        mapOfBonus.putAll(new GeneralBonuses().generalBonusMap);
    }


    Map<Integer, Bonus> getMapOfBonus() {
        return mapOfBonus;
    }

    private class GeneralBonuses{

        private Map<Integer, Bonus> generalBonusMap = new HashMap<>(16);

        private GeneralBonuses(){
            generalBonusMap.put(attack.getId(), attack);
            generalBonusMap.put(axeUp.getId(), axeUp);
            generalBonusMap.put(berserk.getId(), berserk);
            generalBonusMap.put(doubleInHead.getId(), doubleInHead);
            generalBonusMap.put(adaptation.getId(), adaptation);
            generalBonusMap.put(elixirOfLife.getId(), elixirOfLife);
            generalBonusMap.put(exorcism.getId(), exorcism);
            generalBonusMap.put(strengthenTheArmor.getId(), strengthenTheArmor);
            generalBonusMap.put(counterSpell.getId(), counterSpell);
            generalBonusMap.put(giveFire.getId(), giveFire);
            generalBonusMap.put(magicTotem.getId(), magicTotem);
            generalBonusMap.put(wayOfWizard.getId(), wayOfWizard);
            generalBonusMap.put(anticipation.getId(), anticipation);
            generalBonusMap.put(feedBack.getId(), feedBack);
            generalBonusMap.put(nerf.getId(), nerf);
            generalBonusMap.put(stepByStep.getId(), stepByStep);
        }

        private final AAttack attack = new AAttack("AAttack", 1);
        private final AAxeUp axeUp = new AAxeUp("AAxeUp", 2);
        private final ABerserk berserk = new ABerserk("ABerserk", 3);
        private final ADoubleInHead doubleInHead = new ADoubleInHead("ADoubleInHead", 4);
        private final HAdaptation adaptation = new HAdaptation("HAdaptation", 5);
        private final HElixirOfLife elixirOfLife = new HElixirOfLife("HElixirOfLife", 6);
        private final HExorcism exorcism = new HExorcism("HExorcism", 7);
        private final HStrengthenTheArmor strengthenTheArmor = new HStrengthenTheArmor("HStrengthenTheArmor", 8);
        private final SCounterSpell counterSpell = new SCounterSpell("SCounterSpell", 9);
        private final SGiveFire giveFire = new SGiveFire("SGiveFire", 10);
        private final SMagicTotem magicTotem = new SMagicTotem("SMagicTotem", 11);
        private final SWayOfWizard wayOfWizard = new SWayOfWizard("SWayOfWizard", 12);
        private final XAnticipation anticipation = new XAnticipation("XAnticipation", 13);
        private final XFeedBack feedBack = new XFeedBack("XFeedBack", 14);
        private final XNerf nerf = new XNerf("XNerf", 15);
        private final XStepByStep stepByStep = new XStepByStep("XStepByStep", 16);
    }

}
