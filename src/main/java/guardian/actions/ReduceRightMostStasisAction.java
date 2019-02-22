//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package guardian.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import com.megacrit.cardcrawl.vfx.combat.SmallLaserEffect;
import guardian.orbs.StasisOrb;

public class ReduceRightMostStasisAction extends AbstractGameAction {

    public ReduceRightMostStasisAction() {
        this.actionType = ActionType.DAMAGE;
        this.attackEffect = AttackEffect.SLASH_HORIZONTAL;
        this.duration = 0.01F;

    }

    public void update() {
        if (AbstractDungeon.player.orbs.size() > 0){

                for (AbstractOrb o:AbstractDungeon.player.orbs){
                    if (o instanceof StasisOrb){
                        if (((StasisOrb)o).passiveAmount > 1){
                            ((StasisOrb)o).passiveAmount--;
                            break;
                        }
                    }
                }


        }

            this.isDone = true;
        }

}
