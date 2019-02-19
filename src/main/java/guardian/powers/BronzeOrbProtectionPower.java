package guardian.powers;


import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import guardian.GuardianMod;
import guardian.characters.GuardianCharacter;


public class BronzeOrbProtectionPower extends AbstractGuardianPower {
    public static final String POWER_ID = "Guardian:BronzeOrbProtectionPower";

    public static PowerType POWER_TYPE = PowerType.BUFF;

    public static String[] DESCRIPTIONS;

    public boolean isActive;

    public BronzeOrbProtectionPower(AbstractCreature owner) {

       this.ID = POWER_ID;
        this.owner = owner;
        this.setImage("bronzeOrbProtectionPower84.png", "bronzeOrbProtectionPower32.png");

        this.type = POWER_TYPE;
        this.DESCRIPTIONS = CardCrawlGame.languagePack.getPowerStrings(this.ID).DESCRIPTIONS;


        this.name = CardCrawlGame.languagePack.getPowerStrings(this.ID).NAME;

        updateDescription();

    }

    public void updateDescription() {
        if (isActive){
            this.description = DESCRIPTIONS[0];

        } else {
            this.description = DESCRIPTIONS[1];

        }

    }

    public void setActive(){
        this.isActive = true;
        this.setImage("bronzeOrbProtectionPower84.png", "bronzeOrbProtectionPower32.png");

        updateDescription();
    }

    public void setInactive(){
        this.isActive = false;
        this.setImage("bronzeOrbProtectionPowerInactive84.png", "bronzeOrbProtectionPowerInactive32.png");

        updateDescription();
    }

    @Override
    public int onAttacked(DamageInfo info, int damageAmount) {
        if (this.isActive) {
            damageAmount = damageAmount / 2;
            info.base = info.base /2;
            if (GuardianMod.bronzeOrbInPlay != null) {
                AbstractDungeon.actionManager.addToBottom(new DamageAction(GuardianMod.bronzeOrbInPlay, new DamageInfo(info.owner, damageAmount, info.type), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
            } else {
                GuardianMod.logger.info("ERROR - Bronze Orb power cut damage in half but there is no Bronze Orb in play");
            }
        }

        return damageAmount;
    }
}
