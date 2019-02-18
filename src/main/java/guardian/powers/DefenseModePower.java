package guardian.powers;


import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.ThornsPower;
import guardian.GuardianMod;
import guardian.characters.GuardianCharacter;


public class DefenseModePower extends AbstractGuardianPower {
    public static final String POWER_ID = "Guardian:DefenseModePower";

    public static PowerType POWER_TYPE = PowerType.BUFF;

    public static String[] DESCRIPTIONS;

    private static final int THORNS = 3;

    public DefenseModePower(AbstractCreature owner) {

       this.ID = POWER_ID;
        this.owner = owner;
        this.setImage("DefenseModePower84.png", "DefenseModePower32.png");
        this.type = POWER_TYPE;
        this.DESCRIPTIONS = CardCrawlGame.languagePack.getPowerStrings(this.ID).DESCRIPTIONS;

        this.name = CardCrawlGame.languagePack.getPowerStrings(this.ID).NAME;
        this.amount = 2;

        updateDescription();

    }

    public void updateDescription() {

            this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1] + THORNS + DESCRIPTIONS[2];

    }

    public void onInitialApplication() {
        super.onInitialApplication();
        switchToDefensiveMode();
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.owner, this.owner, new ThornsPower(this.owner, THORNS), THORNS));

    }

    public void atStartOfTurn() {
        if (this.amount == 1){
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, this));

        } else {
            this.amount -= 1;
        }

    }

    public void switchToDefensiveMode(){
        if (this.owner instanceof GuardianCharacter){
            ((GuardianCharacter)this.owner).switchToDefensiveMode();
        }
        if (GuardianMod.bronzeOrbInPlay != null) {
            GuardianMod.bronzeOrbInPlay.moveToBackline();
        }
        updateDescription();
    }

    public void switchToOffensiveMode(){

        if (this.owner instanceof GuardianCharacter){
            ((GuardianCharacter)this.owner).switchToOffensiveMode();
        }
        if (GuardianMod.bronzeOrbInPlay != null) {
            GuardianMod.bronzeOrbInPlay.moveToFrontline();
        }
    }

    @Override
    public void onRemove() {
        super.onRemove();
        switchToOffensiveMode();
        if (this.owner.hasPower("Thorns")){
            if (this.owner.getPower("Thorns").amount <= 3) {
                AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, "Thorns"));
            } else {
                AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(this.owner, this.owner, "Thorns", THORNS));
            }
        }

    }

}
