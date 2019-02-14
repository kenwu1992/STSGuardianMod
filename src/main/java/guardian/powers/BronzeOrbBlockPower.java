package guardian.powers;


import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;


public class BronzeOrbBlockPower extends AbstractGuardianPower {
    public static final String POWER_ID = "Guardian:BronzeOrbBlockPower";

    public static PowerType POWER_TYPE = PowerType.BUFF;

    public static String[] DESCRIPTIONS;

    public boolean isActive;

    public BronzeOrbBlockPower(AbstractCreature owner, int amount) {

       this.ID = POWER_ID;
        this.owner = owner;

        this.type = POWER_TYPE;
        this.amount = amount;
        this.loadRegion("dexterity");

        this.DESCRIPTIONS = CardCrawlGame.languagePack.getPowerStrings(this.ID).DESCRIPTIONS;

        this.name = CardCrawlGame.languagePack.getPowerStrings(this.ID).NAME;

        updateDescription();

    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];

    }

}
