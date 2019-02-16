package guardian.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.MonsterRoom;
import com.megacrit.cardcrawl.screens.CombatRewardScreen;
import guardian.powers.GemFinderPower;
import javassist.CtBehavior;

@SpirePatch(clz= CombatRewardScreen.class, method="setupItemReward")
public class SetupItemReward {

    @SpireInsertPatch(locator = Locator.class)
    public static void gemFinderPatch(CombatRewardScreen rewardScreen) {
        if(AbstractDungeon.player.hasPower(GemFinderPower.POWER_ID) && AbstractDungeon.getCurrRoom() instanceof MonsterRoom) {
            ((GemFinderPower)AbstractDungeon.player.getPower(GemFinderPower.POWER_ID)).onTrigger();
        }
    }

    public static class Locator extends SpireInsertLocator {
        @Override
        public int[] Locate(CtBehavior ctBehavior) throws Exception {

            Matcher matcher = new Matcher.MethodCallMatcher(
                    CombatRewardScreen.class, "positionRewards");

            return LineFinder.findInOrder(ctBehavior, matcher);
        }
    }
}