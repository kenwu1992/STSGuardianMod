package guardian.orbs;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.OrbStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import guardian.GuardianMod;
import guardian.actions.DestroyOrbSlotForDamageAction;
import guardian.actions.ReturnStasisCardToHandAction;
import guardian.actions.StasisEvokeIfRoomInHandAction;
import guardian.cards.*;
import guardian.powers.BeamBuffPower;
import guardian.relics.StasisUpgradeRelic;
import guardian.vfx.AddCardToStasisEffect;
import guardian.vfx.SmallLaserEffectColored;


public class StasisOrb extends AbstractOrb {
    private static final OrbStrings orbString;
    public static final String[] DESC;
    private float vfxTimer = 1.0F;
    private float vfxIntervalMin = 0.15F;
    private float vfxIntervalMax = 0.8F;

    private static String ID = GuardianMod.makeID("StasisOrb");

    public AbstractCard stasisCard;
    private AbstractGameEffect stasisStartEffect;

    public StasisOrb(AbstractCard card) {
        this.stasisCard = card;
        this.stasisCard.tags.add(GuardianMod.STASISGLOW);
        this.stasisCard.beginGlowing();
        this.name = orbString.NAME + stasisCard.name;

        this.channelAnimTimer = 0.5F;
            if (card.isCostModifiedForTurn){
                this.basePassiveAmount = this.passiveAmount = card.costForTurn + 1;
            }
            else {
                this.basePassiveAmount = this.passiveAmount = card.cost + 1;
            }

        if (this.basePassiveAmount < 1){
            this.basePassiveAmount = this.passiveAmount = 1;
        }
        card.targetAngle = 0F;

        if (AbstractDungeon.player != null){
            if (AbstractDungeon.player.hasRelic(StasisUpgradeRelic.ID)){
                card.upgrade();
            }
        }

        this.updateDescription();
    }

    @Override
    public void applyFocus() {
        //FuturePlans orbs are not affected by Focus
    }

    public void updateDescription() {
        this.applyFocus();
        if (this.passiveAmount > 1){
            this.description = this.stasisCard.name + DESC[1] + this.passiveAmount + DESC[2];

        } else {
            this.description = this.stasisCard.name + DESC[0];
        }
    }


    @Override
    public void onStartOfTurn() {
        super.onStartOfTurn();
        if (GuardianMod.stasisDelay) {
            AbstractDungeon.actionManager.addToTop(new WaitAction(1.4F));
            GuardianMod.stasisDelay = false;
        }
        if (this.stasisCard instanceof FierceBash){
            ((FierceBash)this.stasisCard).stasisBonus();
        }
        if (this.stasisCard instanceof Orbwalk){
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new StrengthPower(AbstractDungeon.player, 1), 1));

        }
        if (this.stasisCard instanceof SpacetimeBattery){
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, ((SpacetimeBattery)this.stasisCard).magicNumber));

        }
        if (this.stasisCard instanceof GatlingBeam){
            AbstractMonster m = AbstractDungeon.getMonsters().getRandomMonster(true);
            for (int i = 0; i < ((GatlingBeam)stasisCard).turnsInStasis + 1; i++) {

                AbstractDungeon.actionManager.addToBottom(new SFXAction("ATTACK_MAGIC_BEAM_SHORT", 0.5F));
                AbstractDungeon.actionManager.addToBottom(new VFXAction(new SmallLaserEffectColored(m.hb.cX, m.hb.cY, AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY, Color.BLUE), 0.1F));
                AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new com.megacrit.cardcrawl.cards.DamageInfo(AbstractDungeon.player, stasisCard.damage, stasisCard.damageTypeForTurn), AbstractGameAction.AttackEffect.FIRE));
            }
            ((GatlingBeam)stasisCard).turnsInStasis++;

        }

        if (this.passiveAmount > 0){
            this.passiveAmount -= 1;
        }

        if (this.passiveAmount <= 0) {
            AbstractDungeon.actionManager.addToBottom(new StasisEvokeIfRoomInHandAction(this));
        }
    }

    public void onEvoke() {
        if (this.stasisCard instanceof TimeBomb){
            AbstractDungeon.player.exhaustPile.addToTop(this.stasisCard);
            AbstractDungeon.actionManager.addToBottom(new DestroyOrbSlotForDamageAction(this.stasisCard.magicNumber, this));
        } else if (this.stasisCard instanceof GatlingBeam || (this.stasisCard instanceof Orbwalk && !this.stasisCard.upgraded)){
            AbstractDungeon.player.exhaustPile.addToTop(this.stasisCard);

        } else {
            stasisCard.freeToPlayOnce = true;
            AbstractDungeon.actionManager.addToTop(new ReturnStasisCardToHandAction(this.stasisCard));
            this.stasisCard.superFlash(Color.GOLDENROD);

        }
        //GuardianMod.updateStasisCount();
    }


    public void triggerEvokeAnimation() {
    }

    public void updateAnimation() {
        super.updateAnimation();

    }

    @Override
    public void update() {

        super.update();
        this.stasisCard.target_x = this.tX;
        this.stasisCard.target_y = this.tY;
        this.stasisCard.update();
        if (this.hb.hovered) {
            this.stasisCard.targetDrawScale = 1F;
        } else {
            this.stasisCard.targetDrawScale = GuardianMod.stasisCardRenderScale;
        }
    }

    public void render(SpriteBatch sb) {
        if (this.stasisStartEffect == null && !this.hb.hovered) {
            renderActual(sb);
        }
        else if (this.stasisStartEffect.isDone == true && !this.hb.hovered) {
            renderActual(sb);
        }
    }

    public void renderActual(SpriteBatch sb){
            this.stasisCard.render(sb);
            if (!this.hb.hovered) this.renderText(sb);
            this.hb.render(sb);
    }

    public void renderPreview(SpriteBatch sb) {
        if (this.stasisStartEffect == null && this.hb.hovered) {
            renderActual(sb);
        }
        else if (this.stasisStartEffect.isDone == true && this.hb.hovered) {
            renderActual(sb);
        }
    }

    public void playChannelSFX() {
       // CardCrawlGame.sound.play("ORB_LIGHTNING_CHANNEL", 0.1F);
        if (AbstractDungeon.player.drawPile.contains(stasisCard)){
            AbstractDungeon.player.drawPile.removeCard(stasisCard);
            stasisStartEffect = new AddCardToStasisEffect(stasisCard,this, AbstractDungeon.overlayMenu.combatDeckPanel.current_x + (100F * Settings.scale), AbstractDungeon.overlayMenu.combatDeckPanel.current_y + (100F * Settings.scale), AbstractDungeon.overlayMenu.combatDeckPanel.current_x + (200F * Settings.scale), AbstractDungeon.overlayMenu.combatDeckPanel.current_y + (600F * Settings.scale));
            AbstractDungeon.effectsQueue.add(stasisStartEffect);
        }
        else if (AbstractDungeon.player.hand.contains(stasisCard)){
            AbstractDungeon.player.hand.removeCard(stasisCard);
            stasisStartEffect = new AddCardToStasisEffect(stasisCard,this, stasisCard.current_x, stasisCard.current_y, AbstractDungeon.overlayMenu.discardPilePanel.current_x - (200F * Settings.scale), AbstractDungeon.overlayMenu.discardPilePanel.current_y + (600F * Settings.scale));
            AbstractDungeon.effectsQueue.add(stasisStartEffect);
        }
        else if (AbstractDungeon.player.discardPile.contains(stasisCard)){
            AbstractDungeon.player.discardPile.removeCard(stasisCard);
            stasisStartEffect = new AddCardToStasisEffect(stasisCard,this, AbstractDungeon.overlayMenu.discardPilePanel.current_x - (100F * Settings.scale), AbstractDungeon.overlayMenu.discardPilePanel.current_y + (100F * Settings.scale), AbstractDungeon.overlayMenu.discardPilePanel.current_x - (200F * Settings.scale), AbstractDungeon.overlayMenu.discardPilePanel.current_y + (600F * Settings.scale));
            AbstractDungeon.effectsQueue.add(stasisStartEffect);
        }
        else if (AbstractDungeon.player.exhaustPile.contains(stasisCard)){
            AbstractDungeon.player.exhaustPile.removeCard(stasisCard);
            stasisStartEffect = new AddCardToStasisEffect(stasisCard,this, AbstractDungeon.overlayMenu.discardPilePanel.current_x - (100F * Settings.scale), AbstractDungeon.overlayMenu.exhaustPanel.current_y + (100F * Settings.scale), AbstractDungeon.overlayMenu.discardPilePanel.current_x - (200F * Settings.scale), AbstractDungeon.overlayMenu.discardPilePanel.current_y + (600F * Settings.scale));
            AbstractDungeon.effectsQueue.add(stasisStartEffect);
        } else if (AbstractDungeon.player.limbo.contains(stasisCard)){
            AbstractDungeon.player.limbo.removeCard(stasisCard);
            stasisStartEffect = new AddCardToStasisEffect(stasisCard,this, Settings.WIDTH / 2, Settings.HEIGHT * .25F, Settings.WIDTH / 2, Settings.HEIGHT / 2);
            AbstractDungeon.effectsQueue.add(stasisStartEffect);
        } else {
            stasisStartEffect = new AddCardToStasisEffect(stasisCard,this, Settings.WIDTH / 2, Settings.HEIGHT * .25F, Settings.WIDTH / 2, Settings.HEIGHT / 2);
            AbstractDungeon.effectsQueue.add(stasisStartEffect);
        }
        this.stasisCard.targetDrawScale = GuardianMod.stasisCardRenderScale;
        this.stasisCard.retain = false;
       // GuardianMod.updateStasisCount();
    }

    public AbstractOrb makeCopy() {
        StasisOrb so = new StasisOrb(this.stasisCard);
        so.passiveAmount = so.basePassiveAmount = this.passiveAmount;
        return so;
    }

    static {
        orbString = CardCrawlGame.languagePack.getOrbString(ID);
        DESC = orbString.DESCRIPTION;
    }
}
