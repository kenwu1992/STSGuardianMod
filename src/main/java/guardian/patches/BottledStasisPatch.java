package guardian.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;

@SpirePatch(
        clz= AbstractCard.class,
        method=SpirePatch.CLASS
)
public class BottledStasisPatch
{
    public static SpireField<Boolean> inBottledStasis = new SpireField<>(() -> false);

    @SpirePatch(
            clz = AbstractCard.class,
            method="makeStatEquivalentCopy"
    )
    public static class MakeStatEquivalentCopy
    {
        public static AbstractCard Postfix(AbstractCard __result, AbstractCard __instance)
        {
            inBottledStasis.set(__result, inBottledStasis.get(__instance));
            return __result;
        }
    }
}