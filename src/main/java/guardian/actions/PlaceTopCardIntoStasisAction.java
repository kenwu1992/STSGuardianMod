package guardian.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.EmptyDeckShuffleAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import guardian.orbs.StasisOrb;


public class PlaceTopCardIntoStasisAction extends AbstractGameAction {
    private AbstractCard card;

    public PlaceTopCardIntoStasisAction(AbstractCard card) {
        this.card = card;
        this.actionType = ActionType.DAMAGE;
    }

    public void update() {

        if (AbstractDungeon.player.drawPile.isEmpty() && AbstractDungeon.player.discardPile.isEmpty()) {
            this.isDone = true;
        } else {
            if (AbstractDungeon.player.drawPile.isEmpty()) {
                AbstractDungeon.actionManager.addToBottom(new EmptyDeckShuffleAction());
                AbstractDungeon.actionManager.addToBottom(new PlaceTopCardIntoStasisAction(card));

            } else {
                AbstractDungeon.actionManager.addToBottom(new ChannelAction(new StasisOrb(AbstractDungeon.player.drawPile.getTopCard())));
            }
        }

        this.isDone = true;
    }
}
