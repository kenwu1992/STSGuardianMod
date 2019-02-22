package guardian.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import guardian.cards.AbstractGuardianCard;
import guardian.cards.BaubleBeam;

@SpirePatch(
        clz= AbstractCard.class,
        method=SpirePatch.CLASS
)
public class BottledStasisPatch
{
    public static SpireField<Boolean> inBottledStasis = new SpireField<>(() -> false);
    public static SpireField<Boolean> inStasisEgg = new SpireField<>(() -> false);

    @SpirePatch(
            clz = AbstractCard.class,
            method="makeStatEquivalentCopy"
    )
    public static class MakeStatEquivalentCopy
    {
        public static AbstractCard Postfix(AbstractCard __result, AbstractCard __instance)
        {
            inBottledStasis.set(__result, inBottledStasis.get(__instance));
            inStasisEgg.set(__result, inStasisEgg.get(__instance));
            if (__instance instanceof AbstractGuardianCard){
                ((AbstractGuardianCard)__result).sockets = ((AbstractGuardianCard)__instance).sockets;
                ((AbstractGuardianCard)__result).eventSockets = ((AbstractGuardianCard)__instance).eventSockets;
                ((AbstractGuardianCard)__result).socketCount = ((AbstractGuardianCard)__instance).socketCount;
                if (__result instanceof BaubleBeam){
                    ((BaubleBeam)__result).updateCost();
                }

                ((AbstractGuardianCard)__result).updateDescription();
            }


            return __result;
        }
    }
}