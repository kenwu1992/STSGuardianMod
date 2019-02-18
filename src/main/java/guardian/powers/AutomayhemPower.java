package guardian.powers;


import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import guardian.actions.PlaceRandom1CostIntoStasis;
import guardian.actions.PlaceTopCardIntoStasisAction;


public class AutomayhemPower extends AbstractGuardianPower {
    public static final String POWER_ID = "Guardian:AutomayhemPower";
    public static PowerType POWER_TYPE = PowerType.BUFF;

    public static String[] DESCRIPTIONS;
    private AbstractCreature source;


    public AutomayhemPower(AbstractCreature owner, AbstractCreature source, int amount) {

        this.ID = POWER_ID;
        this.owner = owner;
        this.source = source;
        this.setImage("Orbwalk84.png", "Orbwalk32.png");
        this.type = POWER_TYPE;
        this.amount = amount;
        this.DESCRIPTIONS = CardCrawlGame.languagePack.getPowerStrings(this.ID).DESCRIPTIONS;
        this.name = CardCrawlGame.languagePack.getPowerStrings(this.ID).NAME;

        updateDescription();

    }

    public void updateDescription() {
        if (this.amount == 1){
            this.description = DESCRIPTIONS[0];

        } else {
            this.description = DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[2];

        }

    }

    @Override
    public void atStartOfTurn() {
        super.atStartOfTurn();
        this.flash();
        AbstractDungeon.actionManager.addToBottom(new PlaceRandom1CostIntoStasis(this.amount));
    }
}