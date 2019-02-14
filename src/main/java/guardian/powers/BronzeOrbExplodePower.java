package guardian.powers;


import com.evacipated.cardcrawl.mod.stslib.powers.abstracts.TwoAmountPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.common.SuicideAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.ExplosionSmallEffect;
import guardian.characters.GuardianCharacter;


public class BronzeOrbExplodePower extends AbstractGuardianTwoAmountPower {
    public static final String POWER_ID = "Guardian:BronzeOrbExplodePower";

    public static PowerType POWER_TYPE = PowerType.BUFF;

    public static String[] DESCRIPTIONS;



    public BronzeOrbExplodePower(AbstractCreature owner, int explodeAmount, int turns) {

       this.ID = POWER_ID;
        this.owner = owner;
        this.loadRegion("explosive");

        this.type = POWER_TYPE;
        this.DESCRIPTIONS = CardCrawlGame.languagePack.getPowerStrings(this.ID).DESCRIPTIONS;
        this.amount2 = turns;
        this.amount = explodeAmount;
        this.name = CardCrawlGame.languagePack.getPowerStrings(this.ID).NAME;

        updateDescription();

    }

    public void updateDescription() {
        if (this.amount2 > 1){
            this.description = DESCRIPTIONS[2] + this.amount2 + DESCRIPTIONS[3] + this.amount + DESCRIPTIONS[1];
        } else {
            this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];

        }
    }

    public void updateExplode(){
        if (this.amount2 == 1 && !this.owner.isDying) {
            AbstractDungeon.actionManager.addToBottom(new VFXAction(new ExplosionSmallEffect(this.owner.hb.cX, this.owner.hb.cY), 0.1F));
            AbstractDungeon.actionManager.addToBottom(new SuicideAction((AbstractMonster)this.owner));
            DamageInfo damageInfo = new DamageInfo(this.owner, this.amount, DamageInfo.DamageType.THORNS);
            for (AbstractMonster m: AbstractDungeon.getMonsters().monsters) {
                if (!m.isDead && !m.isDying) {
                    AbstractDungeon.actionManager.addToBottom(new DamageAction(m, damageInfo, AbstractGameAction.AttackEffect.FIRE, true));
                }
            }
            } else {
            this.amount2--;
            this.updateDescription();
        }
    }

    public void atStartOfTurn() {
        //AbstractDungeon.actionManager.addToBottom(new DrawCardAction(AbstractDungeon.player, this.amount));
       // AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(this.amount));
    }

}
