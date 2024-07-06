package danmaku.dungeons;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.UIStrings;
import danmaku.DanmakuTheSpireMod;
import danmaku.buttons.DifficultButton;
import danmaku.characters.AbstractDanmakuChar;
import danmaku.helpers.DanmakuManager;

import static danmaku.helpers.ImagesStash.bombimg;

public abstract class AbstractDungeon {
    public static AbstractDanmakuChar player;
    private static final String resPath = DanmakuTheSpireMod.imagesPath + "backgrounds/";

    private final static Texture gameFrame = new Texture(resPath + "gameFrame.png");
    private final static Texture dungeonBack = new Texture(resPath + "dungeonBack.jpg"); // Back of non playable area
    public Texture background; // Back of playable area
    public static float width = Settings.HEIGHT / 3f * 4f; // width of 4:3 resolution
    public static float sX = (Settings.WIDTH - width) / 2f; // starting X
    public static float frameScale = width / (float)dungeonBack.getWidth();
    public static float resScale = width / 1920.0F; // resolution 4:3 scale
    public static float xScale = resScale; // use this instead Settings.xScale to match 4:3 resolution
    protected static int score = 0, topScore = 0, graze = 0;
    private static final UIStrings strings = CardCrawlGame.languagePack.getUIString(DanmakuTheSpireMod.makeID("AbstractDungeon"));
    public static String[] TEXT = strings.TEXT;
    public static DanmakuTheSpireMod.Difficulties difficult;
    private static final InfoPanel infoPanel = new InfoPanel(sX + (58f + 50f) * xScale + gameFrame.getWidth() * frameScale,
            Settings.HEIGHT / 2f);
    public static DanmakuManager danmakuManager = new DanmakuManager();

    public AbstractDungeon(String backimg) {
        background = new Texture(backimg);
        DifficultButton selected = new DifficultButton();
        for ( DifficultButton b : DanmakuTheSpireMod.chooseDifficultScreen.buttons )
            if ( b.selected )
                selected = b;
        difficult = selected.difficult;
        infoPanel.difficultString = selected.name;
    }

    public void render(SpriteBatch sb) {
        // render all dungeon stuff
        sb.draw(background, sX, 0, width, Settings.HEIGHT);
        sb.draw(dungeonBack, sX, 0, width, Settings.HEIGHT);
        sb.setColor(Color.RED);
        sb.draw(gameFrame, sX + 58f * xScale, 28f * Settings.yScale, gameFrame.getWidth() * frameScale, gameFrame.getHeight() * frameScale);
        sb.setColor(Color.WHITE);
        infoPanel.render(sb);
        player.render(sb);
        danmakuManager.render(sb);
    }

    public void update() {
        // handle open pause screen and pause game
        player.update();
        danmakuManager.update();
    }

    private static class InfoPanel {

        float x, y, offsetY, numsX = sX + width - 50f * xScale,
                textGap = 18.5f * Settings.yScale, blockGap = 66f * Settings.yScale, iconGap = 22f,
                textScale = 1.4f;
        String difficultString;
        Color textColor = Settings.CREAM_COLOR;
        BitmapFont font = FontHelper.cardTitleFont;


        public InfoPanel(float x, float y) {
            this.x = x;
            this.y = y;
            font.getData().setScale(textScale);
        }

        public void render(SpriteBatch sb) {
            offsetY = 0f;
            FontHelper.renderSmartText(sb, font, TEXT[5], x, y + offsetY, width, 0f, textColor, textScale);
            FontHelper.renderFontRightToLeft(sb, font, Integer.toString(graze), numsX, y + offsetY, textColor);
            offsetY += FontHelper.getHeight(font) + textGap;
            FontHelper.renderSmartText(sb, font, TEXT[4], x, y + offsetY, textColor);
            if ( player.power != player.maxPower )
                FontHelper.renderFontRightToLeft(sb, font, Integer.toString(player.power), numsX, y + offsetY, textColor);
            else
                FontHelper.renderFontRightToLeft(sb, font, TEXT[6], numsX, y + offsetY, textColor);
            offsetY += FontHelper.getHeight(font) + blockGap;
            FontHelper.renderSmartText(sb, font, TEXT[3], x, y + offsetY, textColor);
            for (int i = 1; i < player.bombs + 1; ++i)
                sb.draw(bombimg, numsX - iconGap * i * xScale - 32f, y + offsetY - 32f, bombimg.packedWidth / 2F, bombimg.packedHeight / 2F, bombimg.packedWidth * 1.5F, bombimg.packedHeight * 1.5F, resScale, resScale, 0.0F);
            offsetY += FontHelper.getHeight(font) + textGap;
            FontHelper.renderSmartText(sb, font, TEXT[2], x, y + offsetY, textColor);
            for (int i = 1; i < player.hp + 1; ++i)
                sb.draw(ImageMaster.TP_HP, numsX - iconGap * i * xScale - 48f, y + offsetY - 48f, 32.0F, 32.0F, 96.0F, 96.0F, resScale, resScale, 0.0F, 0, 0, 64, 64, false, false);
            offsetY += FontHelper.getHeight(font) + blockGap;
            FontHelper.renderSmartText(sb, font, TEXT[1], x, y + offsetY, textColor);
            FontHelper.renderFontRightToLeft(sb, font, Integer.toString(score), numsX, y + offsetY, textColor);
            offsetY += FontHelper.getHeight(font) + textGap;
            FontHelper.renderSmartText(sb, font, TEXT[0], x, y + offsetY, textColor);
            FontHelper.renderFontRightToLeft(sb, font, Integer.toString(topScore), numsX, y + offsetY, textColor);
            offsetY += blockGap * 2f;
            FontHelper.renderFontCentered(sb, FontHelper.bannerNameFont, difficultString, (x + numsX) / 2f, y + offsetY, Settings.GOLD_COLOR, resScale * 1.35f);
        }
    }
}
