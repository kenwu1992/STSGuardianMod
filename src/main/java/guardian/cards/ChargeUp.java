package guardian.cards;



import basemod.helpers.BaseModCardTags;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.NextTurnBlockPower;
import guardian.GuardianMod;
import guardian.patches.AbstractCardEnum;
import guardian.powers.NextTurnGainStrengthPower;

public class ChargeUp extends AbstractGuardianCard {
    public static final String ID = GuardianMod.makeID("ChargeUp");
    public static final String NAME;
    public static final String DESCRIPTION;
    public static String UPGRADED_DESCRIPTION;
    public static final String IMG_PATH = "cards/chargeup.png";

    private static final CardStrings cardStrings;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;

    //TUNING CONSTANTS

    private static final int COST = 1;
    private static final int BLOCK = 9;
    private static final int UPGRADE_BLOCK = 3;
    private static final int STRENGTH = 1;
    private static final int UPGRADE_STRENGTH = 1;
    private static final int SOCKETS = 0;
    private static final boolean SOCKETSAREAFTER = true;

    //END TUNING CONSTANTS

    public ChargeUp() {
        super(ID, NAME, GuardianMod.getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE, AbstractCardEnum.GUARDIAN, RARITY, TARGET);


        this.baseBlock = BLOCK;
        this.baseMagicNumber = this.magicNumber = STRENGTH;

        this.exhaust = true;
        this.socketCount = SOCKETS;
        this.updateDescription();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        super.use(p,m);
        AbstractDungeon.actionManager.addToBottom(new SFXAction("MONSTER_GUARDIAN_DESTROY"));

        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new NextTurnBlockPower(p, this.block), this.block));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new NextTurnGainStrengthPower(p, p, this.magicNumber), this.magicNumber));

        super.useGems(p,m);
    }

    public AbstractCard makeCopy() {
        return new ChargeUp();
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBlock(UPGRADE_BLOCK);
            upgradeMagicNumber(UPGRADE_STRENGTH);
        }
    }

    public void updateDescription() {
        if (this.socketCount > 0) this.rawDescription = this.updateGemDescription(cardStrings.DESCRIPTION, SOCKETSAREAFTER);
        //GuardianMod.logger.info(DESCRIPTION);
        this.initializeDescription();
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
        UPGRADED_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    }
}


