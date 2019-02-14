package guardian.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.EmptyDeckShuffleAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import guardian.orbs.StasisOrb;
import guardian.powers.GuardianModePower;


public class GuardianSwitchModesAction extends AbstractGameAction {
    private boolean toDefensive;
    private AbstractPlayer p;

    public GuardianSwitchModesAction(AbstractPlayer p, boolean toDefensive) {
        this.toDefensive = toDefensive;
        this.p = p;
        this.actionType = ActionType.DAMAGE;
    }

    public void update() {

        if (p.hasPower(GuardianModePower.POWER_ID)){
            GuardianModePower pG = (GuardianModePower)p.getPower(GuardianModePower.POWER_ID);

            if (toDefensive){
                if (!pG.inDefensive){
                    pG.switchToDefensiveMode();
                }
            } else {
                if (pG.inDefensive){
                    pG.switchToOffensiveMode();
                }
            }

        } else {
            AbstractPower newPower = new GuardianModePower(p);
            if (this.toDefensive){
                ((GuardianModePower) newPower).switchToDefensiveMode();
            }
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p,p,new GuardianModePower(p)));
        }

        this.isDone = true;
    }
}
