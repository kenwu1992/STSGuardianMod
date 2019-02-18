package guardian.cards;



import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.MetallicizePower;
import com.megacrit.cardcrawl.powers.ThornsPower;
import guardian.GuardianMod;
import guardian.actions.SwitchToDefenseModeAction;
import guardian.patches.AbstractCardEnum;
import guardian.powers.DefenseModePower;
import guardian.powers.DefensiveModeBuffsPower;

public class ArmoredScales extends AbstractGuardianCard {
    public static final String ID = GuardianMod.makeID("ArmoredScales");
    public static final String NAME;
    public static final String DESCRIPTION;
    public static String UPGRADED_DESCRIPTION;
    public static final String IMG_PATH = "cards/defendSlime.png";

    private static final CardStrings cardStrings;
    private static final CardType TYPE = CardType.POWER;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;

    //TUNING CONSTANTS

    private static final int COST = 1;
    private static final int METALLICIZE = 3;
    private static final int UPGRADE_METALLICIZE = 1;
    private static final int SOCKETS = 0;
    private static final boolean SOCKETSAREAFTER = true;

    //END TUNING CONSTANTS

    public ArmoredScales() {
        super(ID, NAME, GuardianMod.getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE, AbstractCardEnum.GUARDIAN, RARITY, TARGET);

        this.socketCount = SOCKETS;
        this.updateDescription();
        this.baseMagicNumber = this.magicNumber = METALLICIZE;
        
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        super.use(p,m);
        if (p.hasPower(DefensiveModeBuffsPower.POWER_ID)){
            ((DefensiveModeBuffsPower)p.getPower(DefensiveModeBuffsPower.POWER_ID)).metallicize += this.magicNumber;
            p.getPower(DefensiveModeBuffsPower.POWER_ID).flash();
        } else {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new DefensiveModeBuffsPower(p, p, 0,0,this.magicNumber)));
        }
        if (p.hasPower(DefenseModePower.POWER_ID)){
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new MetallicizePower(p, this.magicNumber)));
        }
        AbstractDungeon.actionManager.addToBottom(new SwitchToDefenseModeAction(p));

    }

    public AbstractCard makeCopy() {
        return new ArmoredScales();
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_METALLICIZE);
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


