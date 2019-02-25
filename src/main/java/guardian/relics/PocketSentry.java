package guardian.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import guardian.GuardianMod;
import guardian.cards.SentryBeam;
import guardian.rewards.GemRewardAllRarities;

public class PocketSentry extends CustomRelic {
    public static final String ID = "Guardian:PocketSentry";
    public static final String IMG_PATH = "relics/pocketSentry.png";
    public static final String OUTLINE_IMG_PATH = "relics/pocketSentryOutline.png";
    public static final String LARGE_IMG_PATH = "relics/pocketSentryLarge.png";
    private static final int HP_PER_CARD = 1;

    public PocketSentry() {
        super(ID, new Texture(GuardianMod.getResourcePath(IMG_PATH)), new Texture(GuardianMod.getResourcePath(OUTLINE_IMG_PATH)),
                RelicTier.COMMON, LandingSound.FLAT);
        this.largeImg = ImageMaster.loadImage(GuardianMod.getResourcePath(LARGE_IMG_PATH));
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public void atBattleStart() {
        super.atBattleStart();
        this.flash();
        AbstractCard beam = new SentryBeam();
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(beam));
    }

    @Override
    public AbstractRelic makeCopy() {
        return new PocketSentry();
    }
}