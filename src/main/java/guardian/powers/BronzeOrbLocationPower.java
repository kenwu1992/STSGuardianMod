package guardian.powers;


import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import guardian.GuardianMod;
import guardian.characters.GuardianCharacter;


public class BronzeOrbLocationPower extends AbstractGuardianPower {
    public static final String POWER_ID = "Guardian:BronzeOrbLocationPowerFront";

    public static PowerType POWER_TYPE = PowerType.BUFF;

    public static String[] DESCRIPTIONS;
    public static String[] DESCRIPTIONSdef;

    public boolean inRear = false;
    private String IDdef = "Guardian:BronzeOrbLocationPowerBack";



    public BronzeOrbLocationPower(AbstractCreature owner) {

       this.ID = POWER_ID;
        this.owner = owner;
        this.setImage("bronzeOrbProtectionPower84.png", "bronzeOrbProtectionPower32.png");

        this.type = POWER_TYPE;
        this.DESCRIPTIONS = CardCrawlGame.languagePack.getPowerStrings(this.ID).DESCRIPTIONS;
        this.DESCRIPTIONSdef = CardCrawlGame.languagePack.getPowerStrings(this.IDdef).DESCRIPTIONS;

        this.name = CardCrawlGame.languagePack.getPowerStrings(this.ID).NAME;

        updateDescription();

    }

    public void updateDescription() {
        if (inRear){
            this.description = DESCRIPTIONSdef[0];

        } else {
            this.description = DESCRIPTIONS[0];

        }

    }

    public void moveToBackline(){
        this.inRear = true;
        this.setImage("bronzeOrbProtectionPowerInactive84.png", "bronzeOrbProtectionPowerInactive32.png");
        this.name = CardCrawlGame.languagePack.getPowerStrings(this.IDdef).NAME;

        updateDescription();
    }

    public void moveToFrontline(){
        this.inRear = false;
        this.setImage("bronzeOrbProtectionPower84.png", "bronzeOrbProtectionPower32.png");
        this.name = CardCrawlGame.languagePack.getPowerStrings(this.ID).NAME;

        updateDescription();
    }


    public void atStartOfTurn() {
        //AbstractDungeon.actionManager.addToBottom(new DrawCardAction(AbstractDungeon.player, this.amount));
       // AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(this.amount));
    }

}
