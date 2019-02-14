package guardian.powers;


import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import guardian.GuardianMod;
import guardian.characters.GuardianCharacter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class GuardianModePower extends AbstractGuardianPower {
    public static final String POWER_ID = "Guardian:GuardianModePowerOffense";

    public static PowerType POWER_TYPE = PowerType.BUFF;

    public static String[] DESCRIPTIONS;
    public static String[] DESCRIPTIONSdef;

    public boolean inDefensive = false;
    private String IDdef = "Guardian:GuardianModePowerDefense";

    public GuardianModePower(AbstractCreature owner) {

       this.ID = POWER_ID;
        this.IDdef = POWER_ID;
        this.owner = owner;
        this.setImage("OffenseModePower84.png", "OffenseModePower32.png");
        this.type = POWER_TYPE;
        this.DESCRIPTIONS = CardCrawlGame.languagePack.getPowerStrings(this.ID).DESCRIPTIONS;
        this.DESCRIPTIONSdef = CardCrawlGame.languagePack.getPowerStrings(this.IDdef).DESCRIPTIONS;

        this.name = CardCrawlGame.languagePack.getPowerStrings(this.ID).NAME;

        updateDescription();

    }

    public void updateDescription() {
        if (inDefensive){
            this.description = DESCRIPTIONSdef[0];

        } else {
            this.description = DESCRIPTIONS[0];

        }

    }

    public void onInitialApplication() {
        super.onInitialApplication();
        if (this.owner instanceof GuardianCharacter){
            ((GuardianCharacter)this.owner).switchToOffensiveMode();
        }
    }

    public void atStartOfTurn() {
        //AbstractDungeon.actionManager.addToBottom(new DrawCardAction(AbstractDungeon.player, this.amount));
       // AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(this.amount));
    }

    public void switchToDefensiveMode(){
        if (this.owner instanceof GuardianCharacter){
            ((GuardianCharacter)this.owner).switchToDefensiveMode();
        }
        this.inDefensive = true;
        this.setImage("DefenseModePower84.png", "DefenseModePower32.png");
        this.name = CardCrawlGame.languagePack.getPowerStrings(this.IDdef).NAME;
        if (GuardianMod.bronzeOrbInPlay != null) {
            GuardianMod.bronzeOrbInPlay.moveToFrontline();
        }
        updateDescription();
    }

    public void switchToOffensiveMode(){

        if (this.owner instanceof GuardianCharacter){
            ((GuardianCharacter)this.owner).switchToOffensiveMode();
        }
        this.inDefensive = false;
        this.setImage("OffenseModePower84.png", "OffenseModePower32.png");
        this.name = CardCrawlGame.languagePack.getPowerStrings(this.ID).NAME;
        if (GuardianMod.bronzeOrbInPlay != null) {
            GuardianMod.bronzeOrbInPlay.moveToBackline();
        }
        updateDescription();
    }

}
