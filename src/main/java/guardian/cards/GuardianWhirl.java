package guardian.cards;


import basemod.helpers.BaseModCardTags;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.CleaveEffect;
import guardian.GuardianMod;
import guardian.patches.AbstractCardEnum;


public class GuardianWhirl extends AbstractGuardianCard {
    public static final String ID = GuardianMod.makeID("GuardianWhirl");
    public static final String NAME;
    public static String DESCRIPTION;
    public static String UPGRADED_DESCRIPTION;
    public static final String IMG_PATH = "cards/guardianwhirl.png";
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;

    private static final CardStrings cardStrings;

    //TUNING CONSTANTS

    private static final int COST = 2;
    private static final int DAMAGE = 4;
    private static final int UPGRADE_BONUS = 1;
    private static final int MULTICOUNT = 4;
    private static final int SOCKETS = 0;
    private static final boolean SOCKETSAREAFTER = true;

    //END TUNING CONSTANTS

    public GuardianWhirl() {
        super(ID, NAME, GuardianMod.getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE, AbstractCardEnum.GUARDIAN, RARITY, TARGET);

        this.baseDamage = DAMAGE;
        this.tags.add(GuardianMod.MULTIHIT);

        //this.sockets.add(GuardianMod.socketTypes.RED);

        this.multihit = MULTICOUNT;
        this.socketCount = SOCKETS;
        this.isMultiDamage = true;

        this.updateDescription();

    }



    public void use(AbstractPlayer p, AbstractMonster m) {
        super.use(p, m);

        AbstractDungeon.actionManager.addToBottom(new SFXAction("ATTACK_WHIRLWIND"));
        for (int i = 0; i < MULTICOUNT; i++) {
            AbstractDungeon.actionManager.addToBottom(new SFXAction("ATTACK_HEAVY"));

            AbstractDungeon.actionManager.addToBottom(new VFXAction(p, new CleaveEffect(), 0.05F));
            AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn, com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.NONE));
        }
        this.useGems(p, m);
    }


    public AbstractCard makeCopy() {
        return new GuardianWhirl();
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_BONUS);
            this.socketCount++;
            this.updateDescription();
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


