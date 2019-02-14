package guardian.cards;



import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToHandEffect;
import guardian.GuardianMod;
import guardian.actions.CompilePackageAction;
import guardian.patches.AbstractCardEnum;

import java.util.ArrayList;

public class PackageAutomaton extends AbstractGuardianCard {
    public static final String ID = GuardianMod.makeID("PackageAutomaton");
    public static final String NAME;
    public static final String DESCRIPTION;
    public static String UPGRADED_DESCRIPTION;
    public static final String IMG_PATH = "cards/chargeup.png";

    private static final CardStrings cardStrings;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.SELF;

    //TUNING CONSTANTS

    private static final int COST = 0;
    private static final int SOCKETS = 0;
    private static final boolean SOCKETSAREAFTER = true;

    //END TUNING CONSTANTS

    public PackageAutomaton() {
        super(ID, NAME, GuardianMod.getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE, AbstractCardEnum.GUARDIAN, RARITY, TARGET);

        this.exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        super.use(p,m);

        ArrayList derp = new ArrayList();
        AbstractCard tmp;

        tmp = new HyperBeam_Guardian();
        if (upgraded) tmp.upgrade();
        derp.add(tmp);

        tmp = new GuardProtocol();
        if (upgraded) tmp.upgrade();
        derp.add(tmp);

        tmp = new BronzeArmor();
        if (upgraded) tmp.upgrade();
        derp.add(tmp);

        AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect((AbstractCard)derp.get(0), (float) Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
        AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect((AbstractCard)derp.get(1), (float) Settings.WIDTH * .75F, (float)Settings.HEIGHT / 2.0F));
        AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect((AbstractCard)derp.get(2), (float) Settings.WIDTH * .25F, (float)Settings.HEIGHT / 2.0F));


    }

    public AbstractCard makeCopy() {
        return new PackageAutomaton();
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            this.rawDescription = UPGRADED_DESCRIPTION;
            this.initializeDescription();
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


