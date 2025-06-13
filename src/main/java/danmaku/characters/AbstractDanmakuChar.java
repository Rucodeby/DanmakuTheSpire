package danmaku.characters;

import basemod.ReflectionHacks;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.localization.CharacterStrings;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import com.megacrit.cardcrawl.screens.charSelect.CharacterOption;
import danmaku.patches.CharacterOptionPatch;


public abstract class AbstractDanmakuChar{

    public int hp, bombs, power = 0, maxPower = 130;
    public AbstractPlayer.PlayerClass choosenClass;
    public String ID;
    protected CharacterStrings charStrings;

    public CharacterOption option;

    private static Texture charimg;
    public static Hitbox hb;

    public AbstractDanmakuChar(int hp, int bombs, AbstractPlayer.PlayerClass choosenClass, String id, String charimgPath) {
        this.hp = hp;
        this.bombs = bombs;
        this.choosenClass = choosenClass;
        ID = id;
        charStrings = CardCrawlGame.languagePack.getCharacterString(id);
        charimg = new Texture(charimgPath);
        hb = new Hitbox((Settings.WIDTH - charimg.getWidth()) / 2f, Settings.HEIGHT / 10f, charimg.getWidth(), charimg.getHeight());
    }

    public CharSelectInfo getLoadout() {
        return new DanmakuSelectInfo(getLocalizedCharacterName(), getFlavorText(), hp, bombs, this);
    }

    public String getTitle(AbstractPlayer.PlayerClass playerClass) {
        return null;
    }

    public abstract void doCharSelectScreenSelectEffect();

    public abstract String getLocalizedCharacterName();

    public abstract String getFlavorText();

    public abstract AbstractDanmakuChar newInstance();

    public CharacterOption getCharacterOption() {
        CharacterOptionPatch.DanmakuChar.field.set(option, this);
        CharSelectInfo info = this.getLoadout();
        ReflectionHacks.setPrivate(option, option.getClass(), "charInfo", info);
        ReflectionHacks.setPrivate(option, option.getClass(), "hp", info.hp);
        ReflectionHacks.setPrivate(option, option.getClass(), "gold", info.gold);
        ReflectionHacks.setPrivate(option, option.getClass(), "flavorText", info.flavorText);
        return option;
    }

    public CharacterStrings getCharStrings() { return charStrings; }

    public Preferences getPrefs() { return null; } //Mb rewrite it to save own prefs

    public void render(SpriteBatch sb) {
        sb.draw(charimg, hb.x, hb.y);
        hb.render(sb);
    }

    public void update() {
        if (Gdx.input.isKeyPressed(Input.Keys.UP))
            hb.moveY(hb.cY + 1f);
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN))
            hb.moveY(hb.cY - 1f);
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
            hb.moveX(hb.cX - 1f);
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT))
            hb.moveX(hb.cX + 1f);
        if (Gdx.input.isKeyPressed(Input.Keys.Z)) {
            // Attack
        }
        if (Gdx.input.isKeyPressed(Input.Keys.X)) {
            // Bomb
        }


        hb.update();
    }
}
