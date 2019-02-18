package guardian.powers;


import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.MetallicizePower;
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
        if (this.owner.hasPower(DefensiveModeBuffsPower.POWER_ID)){
            int extraDex = ((DefensiveModeBuffsPower)this.owner.getPower(DefensiveModeBuffsPower.POWER_ID)).dexterity;
            int extraThorns = ((DefensiveModeBuffsPower)this.owner.getPower(DefensiveModeBuffsPower.POWER_ID)).thorns;
            int extraMetallicize = ((DefensiveModeBuffsPower)this.owner.getPower(DefensiveModeBuffsPower.POWER_ID)).metallicize;
            if (extraDex > 0){
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.owner, this.owner, new DexterityPower(this.owner, extraDex)));
            }
            if (extraMetallicize > 0){
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.owner, this.owner, new MetallicizePower(this.owner, extraMetallicize)));
            }
            if (extraThorns > 0){
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.owner, this.owner, new ThornsPower(this.owner, extraThorns)));
            }
        }
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
        int trueThorns = THORNS;
        if (this.owner.hasPower(DefensiveModeBuffsPower.POWER_ID)){
            trueThorns += ((DefensiveModeBuffsPower)this.owner.getPower(DefensiveModeBuffsPower.POWER_ID)).thorns;
        }
        if (this.owner.hasPower("Thorns")){
            if (this.owner.getPower("Thorns").amount <= trueThorns) {
                AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, "Thorns"));
            } else {
                AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(this.owner, this.owner, "Thorns", trueThorns));
            }
        }

        int trueDex = 0;
        if (this.owner.hasPower(DefensiveModeBuffsPower.POWER_ID)){
            trueDex += ((DefensiveModeBuffsPower)this.owner.getPower(DefensiveModeBuffsPower.POWER_ID)).dexterity;
        }
        if (this.owner.hasPower(DexterityPower.POWER_ID)){
            if (this.owner.getPower(DexterityPower.POWER_ID).amount <= trueThorns) {
                AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, DexterityPower.POWER_ID));
            } else {
                AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(this.owner, this.owner, DexterityPower.POWER_ID, trueDex));
            }
        }

        int trueMetallicize = 0;
        if (this.owner.hasPower(DefensiveModeBuffsPower.POWER_ID)){
            trueMetallicize += ((DefensiveModeBuffsPower)this.owner.getPower(DefensiveModeBuffsPower.POWER_ID)).metallicize;
        }
        if (this.owner.hasPower(MetallicizePower.POWER_ID)){
            if (this.owner.getPower(MetallicizePower.POWER_ID).amount <= trueThorns) {
                AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, MetallicizePower.POWER_ID));
            } else {
                AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(this.owner, this.owner, MetallicizePower.POWER_ID, trueMetallicize));
            }
        }

    }

}
