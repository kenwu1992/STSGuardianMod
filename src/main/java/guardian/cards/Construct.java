package guardian.cards;



import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import guardian.GuardianMod;
import guardian.actions.SwitchToDefenseModeAction;
import guardian.patches.AbstractCardEnum;
import guardian.powers.ConstructPower;
import guardian.powers.DefenseModePower;
import guardian.powers.DefensiveModeBuffsPower;

public class Construct extends AbstractGuardianCard {
    public static final String ID = GuardianMod.makeID("Construct");
    public static final String NAME;
    public static final String DESCRIPTION;
    public static String UPGRADED_DESCRIPTION;
    public static final String IMG_PATH = "cards/construct.png";

    private static final CardStrings cardStrings;
    private static final CardType TYPE = CardType.POWER;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;

    //TUNING CONSTANTS

    private static final int COST = 1;
    private static final int ARTIFACT = 2;
    private static final int UPGRADE_ARTIFACT = 1;
    private static final int SOCKETS = 0;
    private static final boolean SOCKETSAREAFTER = true;

    //END TUNING CONSTANTS

    public Construct() {
        super(ID, NAME, GuardianMod.getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE, AbstractCardEnum.GUARDIAN, RARITY, TARGET);

        this.socketCount = SOCKETS;
        this.updateDescription();
        this.magicNumber = this.baseMagicNumber = ARTIFACT;
        
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        super.use(p,m);
        
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new ArtifactPower(p,this.magicNumber)));
        
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new ConstructPower(p, p,1),1));
        
        

    }

    public AbstractCard makeCopy() {
        return new Construct();
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_ARTIFACT);
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


