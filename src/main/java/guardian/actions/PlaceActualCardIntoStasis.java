package guardian.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.EmptyDeckShuffleAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import guardian.GuardianMod;
import guardian.orbs.StasisOrb;


public class PlaceActualCardIntoStasis extends AbstractGameAction {
    private AbstractCard card;

    public PlaceActualCardIntoStasis(AbstractCard card) {
        this.card = card;
        this.actionType = ActionType.DAMAGE;
    }

    public void update() {
        if (GuardianMod.canSpawnStasisOrb()) {

            AbstractDungeon.actionManager.addToBottom(new ChannelAction(new StasisOrb(card)));
        }

        this.isDone = true;
    }
}
