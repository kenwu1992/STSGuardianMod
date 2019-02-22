package guardian.patches;


        import com.megacrit.cardcrawl.core.Settings;
        import com.megacrit.cardcrawl.dungeons.*;
        import com.megacrit.cardcrawl.helpers.ModHelper;
        import com.megacrit.cardcrawl.rooms.AbstractRoom;
        import com.megacrit.cardcrawl.rooms.CampfireUI;
        import com.megacrit.cardcrawl.ui.campfire.AbstractCampfireOption;

        import com.evacipated.cardcrawl.modthespire.lib.*;

        import java.util.*;

        import basemod.*;
        import com.megacrit.cardcrawl.ui.campfire.SmithOption;
        import guardian.GuardianMod;
        import guardian.characters.GuardianCharacter;
        import guardian.ui.EnhanceBonfireOption;


@SpirePatch(clz = CampfireUI.class, method = "initializeButtons")
public class CampfirePatch {

    @SpirePostfixPatch
    public static void Postfix(CampfireUI obj) {

        ArrayList<AbstractCampfireOption> campfireButtons = (ArrayList<AbstractCampfireOption>)ReflectionHacks.getPrivate(obj, CampfireUI.class, "buttons");

        
            Boolean active = true;

            if (GuardianMod.getSocketableCards().size() == 0){
                active = false;
            }
            if (GuardianMod.getGemCards().size() == 0){
                active = false;
            }

            GuardianMod.socketBonfireOption = new EnhanceBonfireOption(active);
            campfireButtons.add(GuardianMod.socketBonfireOption);

        switch(campfireButtons.size()) {
            case 0:
                AbstractRoom.waitTimer = 0.0F;
                AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.COMPLETE;
                break;
            case 1:
                campfireButtons.get(0).setPosition(950.0F * Settings.scale, 720.0F * Settings.scale);
                break;
            case 2:
                ((AbstractCampfireOption)campfireButtons.get(0)).setPosition(800.0F * Settings.scale, 720.0F * Settings.scale);
                ((AbstractCampfireOption)campfireButtons.get(1)).setPosition(1110.0F * Settings.scale, 720.0F * Settings.scale);
                break;
            case 3:
                ((AbstractCampfireOption)campfireButtons.get(0)).setPosition(800.0F * Settings.scale, 720.0F * Settings.scale);
                ((AbstractCampfireOption)campfireButtons.get(1)).setPosition(1110.0F * Settings.scale, 720.0F * Settings.scale);
                ((AbstractCampfireOption)campfireButtons.get(2)).setPosition(950.0F * Settings.scale, 450.0F * Settings.scale);
                break;
            case 4:
                ((AbstractCampfireOption)campfireButtons.get(0)).setPosition(800.0F * Settings.scale, 720.0F * Settings.scale);
                ((AbstractCampfireOption)campfireButtons.get(1)).setPosition(1110.0F * Settings.scale, 720.0F * Settings.scale);
                ((AbstractCampfireOption)campfireButtons.get(2)).setPosition(800.0F * Settings.scale, 450.0F * Settings.scale);
                ((AbstractCampfireOption)campfireButtons.get(3)).setPosition(1110.0F * Settings.scale, 450.0F * Settings.scale);
                break;
            case 5:
                ((AbstractCampfireOption)campfireButtons.get(0)).setPosition(800.0F * Settings.scale, 720.0F * Settings.scale);
                ((AbstractCampfireOption)campfireButtons.get(1)).setPosition(1110.0F * Settings.scale, 720.0F * Settings.scale);
                ((AbstractCampfireOption)campfireButtons.get(2)).setPosition(800.0F * Settings.scale, 450.0F * Settings.scale);
                ((AbstractCampfireOption)campfireButtons.get(3)).setPosition(1110.0F * Settings.scale, 450.0F * Settings.scale);
                ((AbstractCampfireOption)campfireButtons.get(4)).setPosition(950.0F * Settings.scale, 250.0F * Settings.scale);
        }
    }
}


