package danmaku.screens;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.localization.CharacterStrings;
import com.megacrit.cardcrawl.screens.charSelect.CharacterSelectScreen;
import com.megacrit.cardcrawl.screens.mainMenu.MainMenuScreen;
import com.megacrit.cardcrawl.screens.mainMenu.MenuCancelButton;
import com.megacrit.cardcrawl.ui.buttons.ConfirmButton;
import danmaku.DanmakuTheSpireMod;
import danmaku.buttons.ShootTypeButton;

public class ChooseShotTypeScreen {

    public ConfirmButton confirmButton = new ConfirmButton(CharacterSelectScreen.TEXT[1]);
    public MenuCancelButton cancelButton = new MenuCancelButton();
    public ShootTypeButton firstInfo;
    public ShootTypeButton secondInfo;
    @SpireEnum
    public static MainMenuScreen.CurScreen SHOT_TYPE;

    public void render(SpriteBatch sb) {
        firstInfo.render(sb);
        secondInfo.render(sb);
        cancelButton.render(sb);
        confirmButton.render(sb);
    }

    public void update() {
        cancelButton.update();
        confirmButton.update();
        firstInfo.update();
        secondInfo.update();
        if (cancelButton.hb.clicked || InputHelper.pressedEscape) {
            InputHelper.pressedEscape = false;
            cancelButton.hb.clicked = false;
            cancelButton.hide();
            CardCrawlGame.mainMenuScreen.screen = MainMenuScreen.CurScreen.CHAR_SELECT;
            CardCrawlGame.mainMenuScreen.charSelectScreen.confirmButton.isDisabled = false;
            CardCrawlGame.mainMenuScreen.charSelectScreen.confirmButton.show();
        }
        if (confirmButton.hb.clicked) {
            confirmButton.hb.clicked = false;
            confirmButton.isDisabled = true;
            confirmButton.hide();
            DanmakuTheSpireMod.chooseDifficultScreen.open();
        }
    }

    public void open(CharacterStrings typeStrings, Color firstHighlight, Color secondHighlight) {
        firstInfo = new ShootTypeButton(typeStrings.NAMES[0], typeStrings.TEXT[0], typeStrings.OPTIONS[0],
                firstHighlight, Settings.WIDTH * 0.25f);
        secondInfo = new ShootTypeButton(typeStrings.NAMES[1], typeStrings.TEXT[1], typeStrings.OPTIONS[1],
                secondHighlight, Settings.WIDTH * 0.75f);
        cancelButton.show(CharacterSelectScreen.TEXT[5]);
        CardCrawlGame.mainMenuScreen.screen = SHOT_TYPE;
    }

    public void open(CharacterStrings typeStrings) {
        open(typeStrings, Color.RED, Color.BLUE);
    }

    public void deselectOther(ShootTypeButton b) {
        if (b.equals(firstInfo))
            secondInfo.selected = false;
        else
            firstInfo.selected = false;
    }
}
