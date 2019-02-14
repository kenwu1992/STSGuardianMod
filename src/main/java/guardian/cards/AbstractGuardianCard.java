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

import java.lang.reflect.Type;
import java.util.ArrayList;


public abstract class AbstractGuardianCard extends CustomCard implements CustomSavable<ArrayList<Integer>> {

    public Integer socketCount = 0;
    public ArrayList<GuardianMod.socketTypes> sockets = new ArrayList<>();
    public GuardianMod.socketTypes thisGemsType = null;

    public int multihit;
    public boolean upgradeMulthit;
    public boolean isMultihitModified;

    public int secondaryM;
    public boolean upgradesecondaryM;
    public boolean isSecondaryMModified;

    public AbstractGuardianCard(String id, String name, String img, int cost, String rawDescription, CardType type, CardColor color,
                                CardRarity rarity, CardTarget target) {

        super(id, name, img, cost, rawDescription, type,
                color, rarity, target);


    }

    @Override
    public Type savedType()
    {
        return new TypeToken<Integer[][]>(){}.getType();
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
                        addedDesc = addedDesc + Gem_Red.DESCRIPTION;
                        break;
                }
                if (!after) addedDesc = addedDesc + " NL ";
            } else {
                if (after) addedDesc = addedDesc + " NL ";
                addedDesc = addedDesc + "Empty Socket";
                if (!after) addedDesc = addedDesc + " NL ";
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
                    case BLUE:
                       // GuardianMod.logger.info("case BLUE");
                        socketTexture = GuardianMod.socketTextures.get(2);
                       // GuardianMod.logger.info("texture is " + socketTexture);
                        break;
                    case GREEN:
                      //  GuardianMod.logger.info("case GREEN");
                        socketTexture = GuardianMod.socketTextures.get(3);
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
       // GuardianMod.logger.info("SEcopy sockets this: " + this.sockets);
       // GuardianMod.logger.info("SEcopy sockets c: " + ((AbstractGuardianCard)card).sockets);
        ((AbstractGuardianCard)card).updateDescription();
        return card;
    }
}