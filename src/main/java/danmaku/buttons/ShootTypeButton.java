package danmaku.buttons;

import basemod.IUIElement;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import danmaku.DanmakuTheSpireMod;

public class ShootTypeButton implements IUIElement {

    String name, shotName, bombName;
    float x;
    float y = Settings.HEIGHT / 2f;
    Color hiColor;

    private static final float sizeScale = 1.2f;
    Hitbox nameHb, bodyHb;
    public boolean selected = false;

    public ShootTypeButton(String name, String shotName, String bombName, Color highlight, float startX) {
        this.name = name;
        this.shotName = shotName;
        this.bombName = bombName;
        hiColor = highlight;
        hiColor.a = 0.75f;
        x = startX;
        float w = FontHelper.getWidth(FontHelper.bannerNameFont, name, 0.75F * Settings.scale) * 1.05f;
        float h = FontHelper.getHeight(FontHelper.bannerNameFont, name, 0.75F * Settings.scale) * 1.5f;
        nameHb = new Hitbox(x - w / 2f, y + 35f * Settings.yScale, w, h);
        w = FontHelper.getWidth(FontHelper.tipHeaderFont, shotName, 1.75f * Settings.scale) * sizeScale;
        h = FontHelper.getHeight(FontHelper.tipHeaderFont, shotName, 1.75f * Settings.scale) * sizeScale;
        float secW = FontHelper.getWidth(FontHelper.tipHeaderFont, bombName, 1.75f * Settings.scale) * sizeScale;
        if (secW > w)
            w = secW;
        h += FontHelper.getHeight(FontHelper.tipHeaderFont, bombName, 1.75f * Settings.scale) * sizeScale;
        bodyHb = new Hitbox(x - w / 2f, y - h / 2f - 35f * Settings.yScale, w, h + 35f * Settings.yScale);
    }

    @Override
    public void render(SpriteBatch sb) {
        if (selected || nameHb.hovered || bodyHb.hovered) {
            FontHelper.renderFontCentered(sb, FontHelper.bannerNameFont, name, x, y + 70f * Settings.yScale, hiColor, Settings.scale);
            FontHelper.renderFontCentered(sb, FontHelper.tipHeaderFont, shotName, x, y, hiColor, 2.1f * Settings.scale);
            FontHelper.renderFontCentered(sb, FontHelper.tipHeaderFont, bombName, x, y - 50f * Settings.yScale, hiColor, 2.1f * Settings.scale);
        }
        FontHelper.renderFontCentered(sb, FontHelper.bannerNameFont, name, x, y + 60f * Settings.yScale, Settings.GOLD_COLOR, 0.75F * Settings.scale);
        FontHelper.renderFontCentered(sb, FontHelper.tipHeaderFont, shotName, x, y, Settings.CREAM_COLOR, 1.75f * Settings.scale);
        FontHelper.renderFontCentered(sb, FontHelper.tipHeaderFont, bombName, x, y - 40f * Settings.yScale, Settings.CREAM_COLOR, 1.75f * Settings.scale);
        nameHb.render(sb);
        bodyHb.render(sb);
    }

    @Override
    public void update() {
        nameHb.update();
        bodyHb.update();
        if ((nameHb.hovered || bodyHb.hovered) && InputHelper.justClickedLeft) {
            selected = true;
            DanmakuTheSpireMod.chooseShotTypeScreen.confirmButton.isDisabled = false;
            DanmakuTheSpireMod.chooseShotTypeScreen.confirmButton.show();
            DanmakuTheSpireMod.chooseShotTypeScreen.deselectOther(this);
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
