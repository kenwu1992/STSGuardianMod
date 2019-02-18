package guardian.cards;



import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import guardian.GuardianMod;
import guardian.patches.AbstractCardEnum;

import java.security.Guard;

public class TemporalShield extends AbstractGuardianCard {
    public static final String ID = GuardianMod.makeID("TemporalShield");
    public static final String NAME;
    public static final String DESCRIPTION;
    public static String UPGRADED_DESCRIPTION;
    public static final String IMG_PATH = "cards/temporalShield.png";

    private static final CardStrings cardStrings;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;

    //TUNING CONSTANTS

    private static final int COST = 1;
    private static final int BLOCK = 4;
    private static final int BLOCKPERSTASIS = 2;
    private static final int UPGRADE_BLOCKPERSTASIS = 2;
    private static final int SOCKETS = 1;
    private static final boolean SOCKETSAREAFTER = true;

    private int lastKnownStasisCount = 0;

    //END TUNING CONSTANTS

    public TemporalShield() {
        super(ID, NAME, GuardianMod.getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE, AbstractCardEnum.GUARDIAN, RARITY, TARGET);

        this.magicNumber = this.baseMagicNumber = BLOCKPERSTASIS;
        this.baseBlock = BLOCK;
        this.socketCount = SOCKETS;
        this.updateDescription();



    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        super.use(p,m);
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));
        this.useGems(p, m);

    }

    public AbstractCard makeCopy() {
        return new TemporalShield();
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_BLOCKPERSTASIS);
        }
    }

    @Override
    public void update() {
        super.update();
        if (this.lastKnownStasisCount != GuardianMod.getStasisCount()){
            this.lastKnownStasisCount = GuardianMod.getStasisCount();
            upgradeBlock(this.lastKnownStasisCount * this.magicNumber);
            if (this.lastKnownStasisCount == 0) {
                this.upgradedBlock = false;
            }
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


