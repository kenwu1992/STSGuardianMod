package guardian.patches;


        import com.megacrit.cardcrawl.dungeons.*;
        import com.megacrit.cardcrawl.rooms.CampfireUI;
        import com.megacrit.cardcrawl.ui.campfire.AbstractCampfireOption;

        import com.evacipated.cardcrawl.modthespire.lib.*;

        import java.util.*;

        import basemod.*;
        import guardian.GuardianMod;
        import guardian.characters.GuardianCharacter;
        import guardian.ui.EnhanceBonfireOption;


@SpirePatch(clz = CampfireUI.class, method = "initializeButtons")
public class CampfirePatch {
    public static void Prefix(CampfireUI obj) {

        final ArrayList<AbstractCampfireOption> campfireButtons = (ArrayList<AbstractCampfireOption>)ReflectionHacks.getPrivate((Object)obj, (Class)CampfireUI.class, "buttons");

        if (AbstractDungeon.player instanceof GuardianCharacter) {
            Boolean active = true;

            if (GuardianMod.getSocketableCards().size() == 0){
                active = false;
            }
            if (GuardianMod.getGemCards().size() == 0){
                active = false;
            }

            campfireButtons.add(new EnhanceBonfireOption(active));


        }
    }
}


