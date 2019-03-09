package guardian.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.utility.QueueCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import guardian.GuardianMod;

public class PickAxe extends CustomRelic {
    public static final String ID = "Guardian:PickAxe";
    public static final String IMG_PATH = "relics/pick.png";
    public static final String OUTLINE_IMG_PATH = "relics/pickOutline.png";
    private static final int HP_PER_CARD = 1;

    public PickAxe() {
        super(ID, new Texture(GuardianMod.getResourcePath(IMG_PATH)), new Texture(GuardianMod.getResourcePath(OUTLINE_IMG_PATH)),
                RelicTier.COMMON, LandingSound.FLAT);
    }

    @Override
    public String getUpdatedDescription() {
        if (this.counter == 0){
            return this.DESCRIPTIONS[3];
        } else {
            if (this.counter == 1){
                return this.DESCRIPTIONS[0];
            } else {
                return this.DESCRIPTIONS[1] + this.counter + this.DESCRIPTIONS[2];
            }

        }

    }

    @Override
    public void onPlayCard(AbstractCard c, AbstractMonster m) {
        super.onPlayCard(c, m);
        if (c.hasTag(GuardianMod.GEM)){
            AbstractCard gemCard = c.makeStatEquivalentCopy();
            AbstractDungeon.actionManager.addToBottom(new QueueCardAction(gemCard, m));
            this.flash();
        }
    }

    @Override
    public void onTrigger() {
        super.onTrigger();
        this.counter--;
        getUpdatedDescription();

    }

    public void checkNoCounter(){

    }

    @Override
    public AbstractRelic makeCopy() {
        return new PickAxe();
    }
}