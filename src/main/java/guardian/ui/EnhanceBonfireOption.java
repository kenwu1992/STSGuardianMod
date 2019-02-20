package guardian.ui;

        import com.megacrit.cardcrawl.core.CardCrawlGame;

        import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
        import com.megacrit.cardcrawl.helpers.ImageMaster;
        import com.megacrit.cardcrawl.localization.UIStrings;
        import com.megacrit.cardcrawl.ui.campfire.AbstractCampfireOption;
        import guardian.GuardianMod;
        import guardian.vfx.SocketGemEffect;


public class EnhanceBonfireOption extends AbstractCampfireOption
{
    private static final UIStrings UI_STRINGS;


    public static final String[] DESCRIPTIONS;


    //private ArrayList<String> idleMessages;
    public EnhanceBonfireOption(boolean active) {
        this.label =  DESCRIPTIONS[0];

        this.usable = active;
        if (active) {
            this.description = DESCRIPTIONS[1];
            this.img = ImageMaster.loadImage(GuardianMod.getResourcePath("ui/scrapcampfire.png"));
        } else {
            this.description = DESCRIPTIONS[2];
            this.img = ImageMaster.loadImage(GuardianMod.getResourcePath("ui/scrapcampfiredisabled.png"));
        }
    }

    @Override
    public void useOption() {
        if (this.usable) {
            AbstractDungeon.effectList.add(new SocketGemEffect());

        }
    }

    public void reCheck(){
        if (GuardianMod.getSocketableCards().size() == 0){
            this.usable = false;
        }
        if (GuardianMod.getGemCards().size() == 0){
            this.usable = false;
        }
        if (this.usable) {
            this.description = DESCRIPTIONS[1];
            this.img = ImageMaster.loadImage(GuardianMod.getResourcePath("ui/scrapcampfire.png"));
        } else {
            this.description = DESCRIPTIONS[2];
            this.img = ImageMaster.loadImage(GuardianMod.getResourcePath("ui/scrapcampfiredisabled.png"));
        }
    }

    static {
        UI_STRINGS = CardCrawlGame.languagePack.getUIString("Guardian:EnhanceBonfireOptions");
        DESCRIPTIONS = UI_STRINGS.TEXT;

    }
}
