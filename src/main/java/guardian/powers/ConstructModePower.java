package guardian.powers;


import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.EntanglePower;
import com.megacrit.cardcrawl.powers.ThornsPower;
import guardian.GuardianMod;
import guardian.characters.GuardianCharacter;


public class ConstructModePower extends AbstractGuardianPower {
    public static final String POWER_ID = "Guardian:ConstructModePower";

    public static PowerType POWER_TYPE = PowerType.BUFF;

    public static String[] DESCRIPTIONS;

    private static final int THORNS = 3;

    public ConstructModePower(AbstractCreature owner, int amount) {

       this.ID = POWER_ID;
        this.owner = owner;
        this.setImage("OffenseModePower84.png", "OffenseModePower32.png");
        this.type = POWER_TYPE;
        this.DESCRIPTIONS = CardCrawlGame.languagePack.getPowerStrings(this.ID).DESCRIPTIONS;

        this.name = CardCrawlGame.languagePack.getPowerStrings(this.ID).NAME;
        this.amount = amount;

        updateDescription();

    }

    public void updateDescription() {
        if (this.amount == 1) {
            this.description = DESCRIPTIONS[0];
        } else {
            this.description = DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[2];
        }

    }

    //ATTACK PREVENTION IN PATCH: ConstructModeAttackPreventionPatch

    public void onInitialApplication() {
        super.onInitialApplication();
      //  switchToConstructMode();
       // AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, "Guardian:DefenseModePower"));


    }

    public void atStartOfTurn() {
        if (this.amount == 1){
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, this));

        } else {
            this.amount -= 1;
        }

    }

    public void switchToConstructMode(){

        if (this.owner instanceof GuardianCharacter){
            ((GuardianCharacter)this.owner).switchToShatteredMode();
        }
        /*
        if (GuardianMod.bronzeOrbInPlay != null) {
            GuardianMod.bronzeOrbInPlay.moveToBackline();
        }
        */

    }

    public void switchToOffensiveMode(){

        if (this.owner instanceof GuardianCharacter){
            ((GuardianCharacter)this.owner).switchToOffensiveMode();
        }
        /*
        if (GuardianMod.bronzeOrbInPlay != null) {
            GuardianMod.bronzeOrbInPlay.moveToFrontline();
        }
        */
    }

    @Override
    public void onRemove() {
        super.onRemove();
      //  switchToOffensiveMode();
    }

    @Override
    public int onLoseHp(int damageAmount) {
        return 0;
    }

    @Override
    public int onAttacked(DamageInfo info, int damageAmount) {
        info.base = 0;
        info.output = 0;
        return 0;
    }
}
