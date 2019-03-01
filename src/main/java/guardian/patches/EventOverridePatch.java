package guardian.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.city.BackToBasics;
import com.megacrit.cardcrawl.events.shrines.AccursedBlacksmith;

import guardian.characters.GuardianCharacter;
import guardian.events.AccursedBlacksmithGuardian;
import guardian.events.BackToBasicsGuardian;
import guardian.events.GemMine;
import guardian.events.StasisEgg;


@SpirePatch(clz=AbstractDungeon.class,method="initializeCardPools")
public class EventOverridePatch {

    public static void Prefix(AbstractDungeon dungeon_instance) {
        if (AbstractDungeon.player instanceof GuardianCharacter) {
            if (AbstractDungeon.specialOneTimeEventList.contains("Accursed Blacksmith")){
                AbstractDungeon.specialOneTimeEventList.add(AccursedBlacksmithGuardian.ID);
                AbstractDungeon.specialOneTimeEventList.remove(AccursedBlacksmith.ID);
            }
            dungeon_instance.eventList.remove(BackToBasics.ID);
        } else {
            dungeon_instance.eventList.remove(BackToBasicsGuardian.ID);
            dungeon_instance.eventList.remove(GemMine.ID);
            dungeon_instance.eventList.remove(StasisEgg.ID);

        }


    }
}