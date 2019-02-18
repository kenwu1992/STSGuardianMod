package guardian.cards;

import basemod.abstracts.CustomCard;

import basemod.abstracts.CustomSavable;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.google.gson.reflect.TypeToken;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import guardian.GuardianMod;
import guardian.powers.BeamBuffPower;

import java.lang.reflect.Type;
import java.util.ArrayList;


public abstract class AbstractGuardianCard extends CustomCard implements CustomSavable<ArrayList<Integer>> {

    public Integer socketCount = 0;
    public ArrayList<GuardianMod.socketTypes> sockets = new ArrayList<>();
    public GuardianMod.socketTypes thisGemsType = null;

    public int multihit;
    public int upgradeMulthit;
    public boolean isMultihitModified;

    public int secondaryM;
    public boolean upgradesecondaryM;
    public boolean isSecondaryMModified;

    public AbstractGuardianCard(String id, String name, String img, int cost, String rawDescription, CardType type, CardColor color,
                                CardRarity rarity, CardTarget target) {

        super(id, name, img, cost, rawDescription, type,
                color, rarity, target);

        if (AbstractDungeon.player != null){
            upgradeMultihit();
        }

    }

    @Override
    public Type savedType()
    {
        return new TypeToken<ArrayList<Integer>>(){}.getType();
    }

    @Override
    public ArrayList<Integer> onSave()
    {
        if (sockets.size() > 0){
        ArrayList<Integer> savedSockets = new ArrayList<>();
        for (int i = 0; i < socketCount; i++) {
            if (sockets.size() > i) {
                switch (sockets.get(i)) {
                    case RED:
                        savedSockets.add(0);
                        break;
                    case GREEN:
                        savedSockets.add(1);
                        break;
                    case ORANGE:
                        savedSockets.add(2);
                        break;
                    case WHITE:
                        savedSockets.add(3);
                        break;
                    case CYAN:
                        savedSockets.add(4);
                        break;
                }
            }
        }
            return savedSockets;
        } else {
            return null;
        }
    }

    @Override
    public void onLoad(ArrayList<Integer> loadedSockets) {
        if (loadedSockets != null){
        for (int i = 0; i < socketCount; i++) {
            if (loadedSockets.size() > i) {
                switch (loadedSockets.get(i)) {
                    case 0:
                        sockets.add(GuardianMod.socketTypes.RED);
                        break;
                    case 1:
                        sockets.add(GuardianMod.socketTypes.GREEN);
                        break;
                    case 2:
                        sockets.add(GuardianMod.socketTypes.ORANGE);
                        break;
                    case 3:
                        sockets.add(GuardianMod.socketTypes.WHITE);
                        break;
                    case 4:
                        sockets.add(GuardianMod.socketTypes.CYAN);
                        break;
                }
            }
        }
        }
        updateDescription();
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        if (this.hasTag(GuardianMod.STASISGLOW)) this.tags.remove(GuardianMod.STASISGLOW);

    }

    public void addGemToSocket(AbstractGuardianCard gem){
        AbstractDungeon.player.masterDeck.removeCard(gem);
        sockets.add(gem.thisGemsType);
        this.updateDescription();
    }

    public void updateDescription(){
        //Overwritten in all cards
    }

    public void useGems(AbstractPlayer p, AbstractMonster m){
        for (GuardianMod.socketTypes gem:
             sockets) {
            switch (gem){
                case RED:
                    Gem_Red.gemEffect(p, m);
                    break;
                case GREEN:
                    Gem_Green.gemEffect(p, m);
                    break;
                case ORANGE:
                    Gem_Orange.gemEffect(p, m);
                    break;
                case WHITE:
                    Gem_White.gemEffect(p, m);
                    break;
                case CYAN:
                    Gem_Cyan.gemEffect(p, m);
                    break;
            }
        }
    }

    public String updateGemDescription(String desc, Boolean after){
        String addedDesc= "";

        for (int i = 0; i < this.socketCount; i++) {
            if (this.sockets.size() > i){
                GuardianMod.socketTypes gem = this.sockets.get(i);
                if (after) addedDesc = addedDesc + " NL ";
                switch (gem){
                    case RED:
                        addedDesc = addedDesc + Gem_Red.UPGRADED_DESCRIPTION;
                        break;
                    case GREEN:
                        addedDesc = addedDesc + Gem_Green.UPGRADED_DESCRIPTION;
                        break;
                    case ORANGE:
                        addedDesc = addedDesc + Gem_Orange.UPGRADED_DESCRIPTION;
                        break;
                    case WHITE:
                        addedDesc = addedDesc + Gem_White.UPGRADED_DESCRIPTION;
                        break;
                    case CYAN:
                        addedDesc = addedDesc + Gem_Cyan.UPGRADED_DESCRIPTION;
                        break;
                }
                if (!after) addedDesc = addedDesc + " NL ";
            } else {
                if (after) addedDesc = addedDesc + " NL ";
                addedDesc = addedDesc + "Empty Socket";
                if (!after) addedDesc = addedDesc + " NL ";
            }
        }

        if (this instanceof BaubleBeam && this.sockets.size() > 0) {
            if (!((BaubleBeam) this).gemCostModified) {
                GuardianMod.logger.info("Bauble sockets " + this.sockets.size());
                this.modifyCostForCombat(this.sockets.size() * -1);
                ((BaubleBeam) this).gemCostModified = true;
            }
        }

        if (after){
            return desc + addedDesc;
        } else {
            return addedDesc + desc;

        }

    }

    @Override
    public void render(SpriteBatch sb) {
        super.render(sb);
        if (socketCount > 0){
            Texture socketTexture = null;
            for (int i = 0; i < socketCount; i++) {

                if (sockets.size() > i)
                {
                  //  GuardianMod.logger.info("Calculating needs for socket: " + sockets.get(i) + " Index = " + i + " on " + this.name + " sockets array size: " + sockets.size() + " textures array size: " + GuardianMod.socketTextures.size());

                switch (sockets.get(i)) {
                    case RED:
                     //   GuardianMod.logger.info("case RED");
                        socketTexture = GuardianMod.socketTextures.get(1);
                     //   GuardianMod.logger.info("texture is " + socketTexture);
                        break;
                    case GREEN:
                       // GuardianMod.logger.info("case BLUE");
                        socketTexture = GuardianMod.socketTextures.get(2);
                       // GuardianMod.logger.info("texture is " + socketTexture);
                        break;
                    case ORANGE:
                      //  GuardianMod.logger.info("case GREEN");
                        socketTexture = GuardianMod.socketTextures.get(3);
                    //    GuardianMod.logger.info("texture is " + socketTexture);
                        break;
                    case WHITE:
                        //  GuardianMod.logger.info("case GREEN");
                        socketTexture = GuardianMod.socketTextures.get(4);
                        //    GuardianMod.logger.info("texture is " + socketTexture);
                        break;
                    case CYAN:
                        //  GuardianMod.logger.info("case GREEN");
                        socketTexture = GuardianMod.socketTextures.get(5);
                        //    GuardianMod.logger.info("texture is " + socketTexture);
                        break;
                }

                } else {
                    socketTexture = GuardianMod.socketTextures.get(0);
                }

               // GuardianMod.logger.info("reached socket texture call, texture is " + socketTexture);
                if (socketTexture != null) renderSocket(sb, socketTexture, i);
              //  GuardianMod.logger.info("passed socket texture call");
            }
        }

    }

    private void renderSocket(SpriteBatch sb, Texture baseTexture, Integer i){
        //GuardianMod.logger.info("Attempting to render socket: " + baseTexture + " on " + this.name);
        float scale = this.drawScale * Settings.scale;
        sb.draw(baseTexture, this.current_x - 150f * scale, this.current_y + (100f + (-50F * i)) * scale, 0F, 0F, baseTexture.getWidth(), baseTexture.getHeight(), scale, scale, this.angle, 0, 0, baseTexture.getWidth(), baseTexture.getHeight(), false, false);

    }

    @Override
    public void triggerOnEndOfPlayerTurn() {
        super.triggerOnEndOfPlayerTurn();
        if (this.hasTag(GuardianMod.STASISGLOW)) this.tags.remove(GuardianMod.STASISGLOW);
    }

    public void upgradeMultihit(){
        if (GuardianMod.getMultihitModifiers() > 0){
            this.upgradeMulthit = GuardianMod.getMultihitModifiers();
            if (this.upgradeMulthit > 0) this.isMultihitModified = true;
        }

    }

    public float calculateModifiedCardDamage(AbstractPlayer player, AbstractMonster mo, float tmp) {
        int bonus = 0;
        if (this.hasTag(GuardianMod.BEAM)) {
            if (player.hasPower(BeamBuffPower.POWER_ID)) {
                bonus = player.getPower(BeamBuffPower.POWER_ID).amount;
            }
            if (mo != null) {
                if (mo.hasPower(BeamBuffPower.POWER_ID)) {
                    bonus = bonus + mo.getPower(BeamBuffPower.POWER_ID).amount;
                }
            }
        }
        return tmp + bonus;
    }

    @Override
    public AbstractCard makeStatEquivalentCopy() {
        AbstractCard card = this.makeCopy();

        for(int i = 0; i < this.timesUpgraded; ++i) {
            card.upgrade();
        }

        card.name = this.name;
        card.target = this.target;
        card.upgraded = this.upgraded;
        card.timesUpgraded = this.timesUpgraded;
        card.baseDamage = this.baseDamage;
        card.baseBlock = this.baseBlock;
        card.baseMagicNumber = this.baseMagicNumber;
        card.cost = this.cost;
        card.costForTurn = this.costForTurn;
        card.isCostModified = this.isCostModified;
        card.isCostModifiedForTurn = this.isCostModifiedForTurn;
        card.inBottleLightning = this.inBottleLightning;
        card.inBottleFlame = this.inBottleFlame;
        card.inBottleTornado = this.inBottleTornado;
        card.isSeen = this.isSeen;
        card.isLocked = this.isLocked;
        card.misc = this.misc;
        card.freeToPlayOnce = this.freeToPlayOnce;
        ((AbstractGuardianCard)card).sockets = this.sockets;
        if (this instanceof BaubleBeam){
            ((BaubleBeam) card).gemCostModified = ((BaubleBeam) this).gemCostModified;
        }

       // GuardianMod.logger.info("SEcopy sockets this: " + this.sockets);
       // GuardianMod.logger.info("SEcopy sockets c: " + ((AbstractGuardianCard)card).sockets);
        ((AbstractGuardianCard)card).updateDescription();
        return card;
    }
}