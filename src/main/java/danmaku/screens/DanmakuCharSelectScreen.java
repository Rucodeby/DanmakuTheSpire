package danmaku.screens;

import basemod.ReflectionHacks;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import com.megacrit.cardcrawl.screens.charSelect.CharacterOption;
import com.megacrit.cardcrawl.screens.charSelect.CharacterSelectScreen;
import com.megacrit.cardcrawl.screens.mainMenu.MainMenuScreen;
import com.megacrit.cardcrawl.ui.panels.SeedPanel;
import danmaku.DanmakuTheSpireMod;
import danmaku.characters.AbstractDanmakuChar;
import danmaku.characters.Ironclad;
import danmaku.characters.Silent;
import danmaku.patches.CharacterOptionPatch;


public class DanmakuCharSelectScreen extends CharacterSelectScreen {

    private SeedPanel seedPanel = new SeedPanel();
    private static final float SEED_X = 60.0F * Settings.scale;
    private static final float SEED_Y = 90.0F * Settings.scale;
    private static final String CHOOSE_CHAR_MSG = TEXT[0];

    private boolean anySelected = false;
    private Color bgCharColor = new Color(1.0F, 1.0F, 1.0F, 0.0F);
    private static final float BG_Y_OFFSET_TARGET = 0.0F;
    private float bg_y_offset = 0.0F;
    private Hitbox seedHb;

    public void initialize() {
        addDanmakuOption(TEXT[2], new Ironclad(), ImageMaster.CHAR_SELECT_IRONCLAD, ImageMaster.CHAR_SELECT_BG_IRONCLAD);
        addDanmakuOption(TEXT[3], new Silent(), ImageMaster.CHAR_SELECT_SILENT, ImageMaster.CHAR_SELECT_BG_SILENT);
        positionButtons();
        FontHelper.cardTitleFont.getData().setScale(1.0F);
        seedHb =  new Hitbox(700.0F * Settings.scale, 60.0F * Settings.scale);
        seedHb.move(90.0F * Settings.scale, 70.0F * Settings.scale);
    }

    private void positionButtons() {
        int count = options.size();
        float offsetX = Settings.WIDTH / 2.0F - 330.0F * Settings.scale;
        for (int i = 0; i < count; i++) {
            if (Settings.isMobile) {
                options.get(i).hb.move(offsetX + i * 220.0F * Settings.scale, 230.0F * Settings.yScale);
            } else {
                options.get(i).hb.move(offsetX + i * 220.0F * Settings.scale, 190.0F * Settings.yScale);
            }
        }
    }

    public void open(boolean isEndless) {
        Settings.isEndless = isEndless;
        Settings.seedSet = false;
        Settings.seed = null;
        Settings.specialSeed = null;
        Settings.isTrial = false;
        CardCrawlGame.trial = null;
        cancelButton.show(TEXT[5]);
        CardCrawlGame.mainMenuScreen.screen = MainMenuScreen.CurScreen.CHAR_SELECT;
    }


    public void update() {
        anySelected = false;
        if (!this.seedPanel.shown) {
            for (CharacterOption o : options) {
                o.update();
                if (o.selected) {
                    anySelected = true;
                }
            }
            updateButtons();
            if (InputHelper.justReleasedClickLeft && !anySelected) {
                confirmButton.isDisabled = true;
                confirmButton.hide();
            }
            if (anySelected) {
                bgCharColor.a = MathHelper.fadeLerpSnap(bgCharColor.a, 1.0F);
                bg_y_offset = MathHelper.fadeLerpSnap(bg_y_offset, -0.0F);
            } else {
                bgCharColor.a = MathHelper.fadeLerpSnap(bgCharColor.a, 0.0F);
            }
            if (anySelected)
                seedHb.update();
        }
        seedPanel.update();
        if ((seedHb.hovered && InputHelper.justClickedLeft) || CInputActionSet.drawPile.isJustPressed()) {
            InputHelper.justClickedLeft = false;
            seedHb.hovered = false;
            seedPanel.show();
        }
        CardCrawlGame.mainMenuScreen.superDarken = this.anySelected;
    }

    public void justSelected() {
        bg_y_offset = 0.0F;
    }


    public void updateButtons() {
        cancelButton.update();
        confirmButton.update();
        if (cancelButton.hb.clicked || InputHelper.pressedEscape) {
            CardCrawlGame.mainMenuScreen.superDarken = false;
            InputHelper.pressedEscape = false;
            cancelButton.hb.clicked = false;
            cancelButton.hide();
            CardCrawlGame.mainMenuScreen.panelScreen.refresh();
            for (CharacterOption o : this.options)
                o.selected = false;
            bgCharColor.a = 0.0F;
            anySelected = false;
        }
        if (confirmButton.hb.clicked) {
            confirmButton.hb.clicked = false;
            confirmButton.isDisabled = true;
            confirmButton.hide();
            if (Settings.seed == null) {
                ReflectionHacks.privateMethod(CharacterSelectScreen.class, "setRandomSeed").invoke(this);
            } else {
                Settings.seedSet = true;
            }
            CharacterOption selected = null;
            for (CharacterOption o : this.options) {
                if (o.selected)
                    selected = o;
            }
            DanmakuTheSpireMod.chooseShotTypeScreen.open(CharacterOptionPatch.DanmakuChar.field.get(selected).getCharStrings());
        }
    }

    public void render(SpriteBatch sb) {
        sb.setColor(this.bgCharColor);
        if (bgCharImg != null)
            if (Settings.isSixteenByTen) {
                sb.draw(bgCharImg, Settings.WIDTH / 2.0F - 960.0F, Settings.HEIGHT / 2.0F - 600.0F, 960.0F, 600.0F, 1920.0F, 1200.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 1920, 1200, false, false);
            } else if (Settings.isFourByThree) {
                sb.draw(bgCharImg, Settings.WIDTH / 2.0F - 960.0F, Settings.HEIGHT / 2.0F - 600.0F + this.bg_y_offset, 960.0F, 600.0F, 1920.0F, 1200.0F, Settings.yScale, Settings.yScale, 0.0F, 0, 0, 1920, 1200, false, false);
            } else if (Settings.isLetterbox) {
                sb.draw(bgCharImg, Settings.WIDTH / 2.0F - 960.0F, Settings.HEIGHT / 2.0F - 600.0F + this.bg_y_offset, 960.0F, 600.0F, 1920.0F, 1200.0F, Settings.xScale, Settings.xScale, 0.0F, 0, 0, 1920, 1200, false, false);
            } else {
                sb.draw(bgCharImg, Settings.WIDTH / 2.0F - 960.0F, Settings.HEIGHT / 2.0F - 600.0F + this.bg_y_offset, 960.0F, 600.0F, 1920.0F, 1200.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 1920, 1200, false, false);
            }
        cancelButton.render(sb);
        confirmButton.render(sb);
        boolean anythingSelected = false;
        if (!seedPanel.shown)
            for (CharacterOption o : options) {
                if (o.selected)
                    anythingSelected = true;
                o.render(sb);
            }
        if (!seedPanel.shown && !anythingSelected)
            if (!Settings.isMobile) {
                FontHelper.renderFontCentered(sb, FontHelper.losePowerFont, CHOOSE_CHAR_MSG, Settings.WIDTH / 2.0F, 340.0F * Settings.yScale, Settings.CREAM_COLOR, 1.2F);
            } else {
                FontHelper.renderFontCentered(sb, FontHelper.losePowerFont, CHOOSE_CHAR_MSG, Settings.WIDTH / 2.0F, 380.0F * Settings.yScale, Settings.CREAM_COLOR, 1.2F);
            }
    }

    private void renderSeedSettings(SpriteBatch sb) {
        if (!anySelected)
            return;
        Color textColor = Settings.GOLD_COLOR;
        if (seedHb.hovered) {
            textColor = Settings.GREEN_TEXT_COLOR;
            TipHelper.renderGenericTip(InputHelper.mX + 50.0F * Settings.scale, InputHelper.mY + 100.0F * Settings.scale, TEXT[11], TEXT[12]);
        }
        if (!Settings.isControllerMode) {
            if (Settings.seedSet) {
                FontHelper.renderSmartText(sb, FontHelper.cardTitleFont, TEXT[10], SEED_X, SEED_Y, 9999.0F, 0.0F, textColor);
                FontHelper.renderFontLeftTopAligned(sb, FontHelper.cardTitleFont,

                        SeedHelper.getUserFacingSeedString(), SEED_X - 30.0F * Settings.scale +
                                FontHelper.getSmartWidth(FontHelper.cardTitleFont, TEXT[13], 9999.0F, 0.0F), 90.0F * Settings.scale, Settings.BLUE_TEXT_COLOR);
            } else {
                FontHelper.renderSmartText(sb, FontHelper.cardTitleFont, TEXT[13], SEED_X, SEED_Y, 9999.0F, 0.0F, textColor);
            }
        } else {
            if (Settings.seedSet) {
                FontHelper.renderSmartText(sb, FontHelper.cardTitleFont, TEXT[10], SEED_X + 100.0F * Settings.scale, SEED_Y, 9999.0F, 0.0F, textColor);
                FontHelper.renderFontLeftTopAligned(sb, FontHelper.cardTitleFont,

                        SeedHelper.getUserFacingSeedString(), SEED_X - 30.0F * Settings.scale +
                                FontHelper.getSmartWidth(FontHelper.cardTitleFont, TEXT[13], 9999.0F, 0.0F) + 100.0F * Settings.scale, 90.0F * Settings.scale, Settings.BLUE_TEXT_COLOR);
            } else {
                FontHelper.renderSmartText(sb, FontHelper.cardTitleFont, TEXT[13], SEED_X + 100.0F * Settings.scale, SEED_Y, 9999.0F, 0.0F, textColor);
            }
            sb.draw(ImageMaster.CONTROLLER_LT, 80.0F * Settings.scale - 32.0F, 80.0F * Settings.scale - 32.0F, 32.0F, 32.0F, 64.0F, 64.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 64, 64, false, false);
        }
        seedHb.render(sb);
    }

    public void deselectOtherOptions(CharacterOption characterOption) {
        for(CharacterOption o: options)
            if (o != characterOption)
                o.selected = false;
    }

    private void addDanmakuOption(String optionName, AbstractDanmakuChar c, Texture buttonImg, Texture portraitImg) {
        CharacterOption option = new CharacterOption(optionName, CardCrawlGame.characterManager.recreateCharacter(AbstractPlayer.PlayerClass.IRONCLAD), buttonImg, portraitImg);
        CharacterOptionPatch.DanmakuChar.field.set(option, c);
        CharSelectInfo info = c.getLoadout();
        ReflectionHacks.setPrivate(option, option.getClass(), "charInfo", info);
        ReflectionHacks.setPrivate(option, option.getClass(), "hp", info.hp);
        ReflectionHacks.setPrivate(option, option.getClass(), "gold", info.gold);
        ReflectionHacks.setPrivate(option, option.getClass(), "flavorText", info.flavorText);
        options.add(option);
    }
}
