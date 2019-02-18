package guardian.powers;


import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;


public class DefensiveModeBuffsPower extends AbstractGuardianPower {
    public static final String POWER_ID = "Guardian:DefensiveModeBuffsPower";
    public static PowerType POWER_TYPE = PowerType.BUFF;

    public static String[] DESCRIPTIONS;
    public int thorns;
    public int dexterity;
    public int metallicize;


    public DefensiveModeBuffsPower(AbstractCreature owner, AbstractCreature source, int thorns, int dexterity, int metallicize) {

        this.ID = POWER_ID;
        this.owner = owner;
        this.setImage("Orbwalk84.png", "Orbwalk32.png");
        this.type = POWER_TYPE;
        this.DESCRIPTIONS = CardCrawlGame.languagePack.getPowerStrings(this.ID).DESCRIPTIONS;
        this.name = CardCrawlGame.languagePack.getPowerStrings(this.ID).NAME;
        this.thorns = thorns;
        this.dexterity = dexterity;
        this.metallicize = metallicize;

        updateDescription();

    }

    public void updateDescription() {
        String desc;
        desc = DESCRIPTIONS[0];
        if (this.thorns > 0){
            desc += this.thorns + DESCRIPTIONS[1];
            if (this.dexterity > 0 || this.metallicize > 0) {
                desc += " NL ";
            }
        }
        if (this.dexterity > 0){
            desc += this.dexterity + DESCRIPTIONS[2];
            if (this.metallicize > 0) {
                desc += " NL ";
            }
        }
        if (this.metallicize > 0){
            desc += this.metallicize + DESCRIPTIONS[3];
        }

            this.description = desc;

    }

}