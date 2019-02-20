package guardian.cards;



import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import guardian.GuardianMod;
import guardian.actions.CardToTopOfDrawPileAction;
import guardian.actions.PlaceActualCardIntoStasis;
import guardian.patches.AbstractCardEnum;

public class SentryWave extends AbstractGuardianCard {
    public static final String ID = GuardianMod.makeID("SentryWave");
    public static final String NAME;
    public static final String DESCRIPTION;
    public static String UPGRADED_DESCRIPTION;
    public static final String IMG_PATH = "cards/sentryWave.png";

    private static final CardStrings cardStrings;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;

    //TUNING CONSTANTS

    private static final int COST = 0;
    private static final int DEBUFFCOUNT = 1;
    private static final int UPGRADE_DEBUFF = 1;
    private static final int SOCKETS = 0;
    private static final boolean SOCKETSAREAFTER = true;

    //END TUNING CONSTANTS

    public SentryWave() {
        super(ID, NAME, GuardianMod.getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE, AbstractCardEnum.GUARDIAN, RARITY, TARGET);

        this.baseMagicNumber = this.magicNumber = DEBUFFCOUNT;
        this.socketCount = SOCKETS;
        this.updateDescription();
        this.exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        super.use(p,m);

        for (AbstractMonster m2: AbstractDungeon.getMonsters().monsters) {
            if (!m2.isDead && !m2.isDying) {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m2, p, new WeakPower(m2, this.magicNumber, false), this.magicNumber));
            }
        }
        AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, 1));
        AbstractGuardianCard newCard = new SentryBeam();

        AbstractDungeon.actionManager.addToBottom(new CardToTopOfDrawPileAction(newCard));

        super.useGems(p,m);
    }

    public AbstractCard makeCopy() {
        return new SentryWave();
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_DEBUFF);
        }
    }

    public void updateDescription() {
        if (SOCKETS > 0) this.rawDescription = this.updateGemDescription(cardStrings.DESCRIPTION, SOCKETSAREAFTER);
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


