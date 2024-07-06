package danmaku.screens;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.SeedHelper;
import com.megacrit.cardcrawl.helpers.TrialHelper;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.screens.charSelect.CharacterOption;
import com.megacrit.cardcrawl.screens.charSelect.CharacterSelectScreen;
import com.megacrit.cardcrawl.screens.mainMenu.MainMenuScreen;
import com.megacrit.cardcrawl.screens.mainMenu.MenuCancelButton;
import com.megacrit.cardcrawl.ui.buttons.ConfirmButton;
import danmaku.DanmakuTheSpireMod;
import danmaku.buttons.DifficultButton;
import danmaku.dungeons.Act1;
import danmaku.patches.CharacterOptionPatch;
import danmaku.patches.NewAbstractDungeonPatch;

import java.util.ArrayList;

public class ChooseDifficultScreen {

    public ConfirmButton confirmButton = new ConfirmButton(CharacterSelectScreen.TEXT[1]);
    public MenuCancelButton cancelButton = new MenuCancelButton();

    public ArrayList<DifficultButton> buttons = new ArrayList<>();
    @SpireEnum
    public static MainMenuScreen.CurScreen CHOOSE_DIFF;

    public ChooseDifficultScreen() {
        buttons.add(new DifficultButton(DanmakuTheSpireMod.Difficulties.EASY, Settings.WIDTH * 0.21f, Settings.HEIGHT * 0.7f));
        buttons.add(new DifficultButton(DanmakuTheSpireMod.Difficulties.NORMAL, Settings.WIDTH * 0.32f, Settings.HEIGHT * 0.3f));
        buttons.add(new DifficultButton(DanmakuTheSpireMod.Difficulties.HARD, Settings.WIDTH * 0.68f, Settings.HEIGHT * 0.3f));
        buttons.add(new DifficultButton(DanmakuTheSpireMod.Difficulties.LUNATIC, Settings.WIDTH * 0.79f, Settings.HEIGHT * 0.7f));
    }

    public void render(SpriteBatch sb) {
        for (DifficultButton b : buttons)
            b.render(sb);
        cancelButton.render(sb);
        confirmButton.render(sb);
    }

    public void update() {
        for (DifficultButton b : buttons)
            b.update();
        cancelButton.update();
        confirmButton.update();
        if (cancelButton.hb.clicked || InputHelper.pressedEscape) {
            InputHelper.pressedEscape = false;
            cancelButton.hb.clicked = false;
            cancelButton.hide();
            CardCrawlGame.mainMenuScreen.screen = ChooseShotTypeScreen.SHOT_TYPE;
            DanmakuTheSpireMod.chooseShotTypeScreen.confirmButton.isDisabled = false;
            DanmakuTheSpireMod.chooseShotTypeScreen.confirmButton.show();
        }
        if (confirmButton.hb.clicked) {
            confirmButton.hb.clicked = false;
            confirmButton.isDisabled = true;
            confirmButton.hide();
            CardCrawlGame.mainMenuScreen.isFadingOut = true;
            CardCrawlGame.mainMenuScreen.fadeOutMusic();
            Settings.isDailyRun = false;
            boolean isTrialSeed = TrialHelper.isTrialSeed(SeedHelper.getString(Settings.seed));
            if (isTrialSeed) {
                Settings.specialSeed = Settings.seed;
                long sourceTime = System.nanoTime();
                Random rng = new Random(sourceTime);
                Settings.seed = SeedHelper.generateUnoffensiveSeed(rng);
                Settings.isTrial = true;
            }
            AbstractDungeon.generateSeeds();
            DanmakuTheSpireMod.dungeon = new Act1();
            for( CharacterOption o : CardCrawlGame.mainMenuScreen.charSelectScreen.options )
                if ( o.selected ) {
                    danmaku.dungeons.AbstractDungeon.player = CharacterOptionPatch.DanmakuChar.field.get(o);
                    break;
                }
            CardCrawlGame.mode = NewAbstractDungeonPatch.DANMAKU_GAMEPLAY;
        }
    }

    public void open() {
        cancelButton.show(CharacterSelectScreen.TEXT[5]);
        CardCrawlGame.mainMenuScreen.screen = CHOOSE_DIFF;
    }

    public void deselectOthers(DifficultButton b) {
        for ( DifficultButton but : buttons )
            if ( !but.equals(b) )
                but.selected = false;
    }

    public DanmakuTheSpireMod.Difficulties getSelectedDiff() {
        for ( DifficultButton b : buttons)
            if ( b.selected )
                return b.difficult;
        return null;
    }
}
