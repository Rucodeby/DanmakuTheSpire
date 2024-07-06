package danmaku.buttons;

import basemod.IUIElement;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.localization.UIStrings;
import danmaku.DanmakuTheSpireMod;

public class DifficultButton implements IUIElement {

    static final Texture texture = ImageMaster.loadImage(DanmakuTheSpireMod.imagesPath + "buttons/DifficultButton.png");
    Color c, ghostColor, normColor;
    Color transCream = new Color(Settings.CREAM_COLOR.r, Settings.CREAM_COLOR.g, Settings.CREAM_COLOR.b, 0.4f);
    Color transGold = new Color(Settings.GOLD_COLOR.r, Settings.GOLD_COLOR.g, Settings.GOLD_COLOR.b, 0.4f);
    static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(DanmakuTheSpireMod.makeID("DifficultButton"));
    public static String[] TEXT = uiStrings.TEXT;
    public String name;
    String desc;
    public DanmakuTheSpireMod.Difficulties difficult;
    public boolean selected = false;
    float x, y, w, h;
    float imgScale = 1.15f;
    Hitbox hb;


    public DifficultButton () {
        this(DanmakuTheSpireMod.Difficulties.EASY, 0f, 0f);
    }
    public DifficultButton (DanmakuTheSpireMod.Difficulties difficult, float startX, float startY) {
        this.difficult = difficult;
        x = startX;
        y = startY;
        switch (difficult) {
            default:
            case EASY:
                name = TEXT[0];
                desc = TEXT[1];
                normColor = Color.GREEN;
                break;
            case NORMAL:
                name = TEXT[2];
                desc = TEXT[3];
                normColor = Color.BLUE;
                break;
            case HARD:
                name = TEXT[4];
                desc = TEXT[5];
                normColor = Color.RED;
                break;
            case LUNATIC:
                name = TEXT[6];
                desc = TEXT[7];
                normColor = Color.VIOLET;
                break;
        }
        ghostColor = new Color(normColor.r, normColor.g, normColor.b, 0.4f);
        w = texture.getWidth() * imgScale * Settings.scale;
        h = texture.getHeight() * imgScale * Settings.scale;
        hb = new Hitbox(x - w / 2f, y - h / 2f, w, h);
    }

    @Override
    public void render(SpriteBatch sb) {
        if (selected || hb.hovered)
            c = normColor;
        else
            c = ghostColor;
        sb.setColor(c);
        sb.draw(texture, x - w / 2f, y - h / 2f, w, h);
        if (selected || hb.hovered) {
            FontHelper.renderSmartText(sb, FontHelper.tipHeaderFont, desc, x - w / 2f + 25f * Settings.xScale, y, w + 30f * Settings.scale,
                    30f * Settings.scale, Settings.CREAM_COLOR, 1.35f * Settings.scale);
            FontHelper.renderSmartText(sb, FontHelper.bannerNameFont, name, x - w / 2f + 40f * Settings.xScale, y + h * 0.55f, Settings.GOLD_COLOR);
        } else {
            FontHelper.renderSmartText(sb, FontHelper.tipHeaderFont, desc, x - w / 2f + 25f * Settings.xScale, y, w + 30f * Settings.scale,
                    30f * Settings.scale, transCream, 1.35f * Settings.scale);
            FontHelper.renderSmartText(sb, FontHelper.bannerNameFont, name, x - w / 2f + 40f * Settings.xScale, y + h * 0.55f, transGold);
        }
        hb.render(sb);
    }

    @Override
    public void update() {
        hb.update();
        if (hb.hovered && InputHelper.justClickedLeft) {
            selected = true;
            DanmakuTheSpireMod.chooseDifficultScreen.deselectOthers(this);
            DanmakuTheSpireMod.chooseDifficultScreen.confirmButton.isDisabled = false;
            DanmakuTheSpireMod.chooseDifficultScreen.confirmButton.show();
        }
    }

    @Override
    public int renderLayer() {
        return 0;
    }

    @Override
    public int updateOrder() {
        return 0;
    }
}
