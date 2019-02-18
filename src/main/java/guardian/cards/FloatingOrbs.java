package guardian.cards;



import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ThornsPower;
import guardian.GuardianMod;
import guardian.actions.SpawnBronzeOrbAction;
import guardian.patches.AbstractCardEnum;
import guardian.powers.BronzeOrbDamagePower;
import guardian.powers.FloatingOrbsPower;

public class FloatingOrbs extends AbstractGuardianCard {
    public static final String ID = GuardianMod.makeID("FloatingOrbs");
    public static final String NAME;
    public static final String DESCRIPTION;
    public static String UPGRADED_DESCRIPTION;
    public static final String IMG_PATH = "cards/defendSlime.png";

    private static final CardStrings cardStrings;
    private static final CardType TYPE = CardType.POWER;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;

    //TUNING CONSTANTS

    private static final int COST = 1;
    private static final int SOCKETS = 0;
    private static final boolean SOCKETSAREAFTER = false;

    //END TUNING CONSTANTS

    public FloatingOrbs() {
        super(ID, NAME, GuardianMod.getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE, AbstractCardEnum.GUARDIAN, RARITY, TARGET);

        this.socketCount = SOCKETS;
        this.updateDescription();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        super.use(p,m);
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new FloatingOrbsPower(p), 1));
    }

    public AbstractCard makeCopy() {
        return new FloatingOrbs();
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            this.isInnate = true;
        }
    }

    public void updateDescription() {
        if (SOCKETS > 0) this.rawDescription = this.updateGemDescription(cardStrings.DESCRIPTION, SOCKETSAREAFTER);
        //GuardianMod.logger.info(DESCRIPTION);
        this.initializeDescription();
        this.rawDescription = UPGRADED_DESCRIPTION;
        this.initializeDescription();
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
        UPGRADED_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    }
}


