package guardian.cards;


import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import guardian.GuardianMod;
import guardian.patches.AbstractCardEnum;


public class TemporalStrike extends AbstractGuardianCard {
    public static final String ID = GuardianMod.makeID("TemporalStrike");
    public static final String NAME;
    public static String DESCRIPTION;
    public static String UPGRADED_DESCRIPTION;
    public static final String IMG_PATH = "cards/temporalStrike.png";
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    private static final CardStrings cardStrings;

    //TUNING CONSTANTS

    private static final int COST = 1;
    private static final int DAMAGE = 9;
    private static final int UPGRADE_DAMAGE = 3;
    private static final int SOCKETS = 0;
    private static final boolean SOCKETSAREAFTER = true;

    //END TUNING CONSTANTS

    public TemporalStrike() {
        super(ID, NAME, GuardianMod.getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE, AbstractCardEnum.GUARDIAN, RARITY, TARGET);

        this.baseDamage = DAMAGE;


        //this.sockets.add(GuardianMod.socketTypes.RED);
        this.socketCount = SOCKETS;
        this.updateDescription();

    }



    @Override
    public float calculateModifiedCardDamage(AbstractPlayer player, AbstractMonster mo, float tmp) {
        super.calculateModifiedCardDamage(player, mo, tmp);
        tmp += + GuardianMod.getStasisCount() * this.magicNumber;

        return tmp;

    }

    public void use(AbstractPlayer p, AbstractMonster m) {


        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        if (this.hasTag(GuardianMod.STASISGLOW)){
            AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_VERTICAL));

        }
        super.use(p, m);
        this.useGems(p, m);

    }


    public AbstractCard makeCopy() {
        return new TemporalStrike();
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_DAMAGE);
        }


    }

    public void updateDescription() {
        if (this.socketCount > 0) this.rawDescription = this.updateGemDescription(cardStrings.DESCRIPTION, SOCKETSAREAFTER);
        GuardianMod.logger.info(DESCRIPTION);
        this.initializeDescription();
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
        UPGRADED_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    }
}


