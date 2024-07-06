package danmaku.bullets;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.helpers.Hitbox;
import danmaku.DanmakuTheSpireMod;
import danmaku.actions.MoveAction;
import danmaku.dungeons.AbstractDungeon;

public abstract class AbstractBullet {

    public Texture bulletImg;
    public Hitbox hb;
    public float scale;
    public boolean isEnemy;

    protected static String resourcesPath = DanmakuTheSpireMod.imagesPath + "bullets/";

    public AbstractBullet( boolean isEnemy, String texture, float x, float y) {
        bulletImg = new Texture(texture);
        this.isEnemy = isEnemy;
        hb = new Hitbox(x, y, bulletImg.getWidth() * scale * AbstractDungeon.resScale, bulletImg.getHeight() * scale * AbstractDungeon.resScale);
    }

    public void render(SpriteBatch sb) {
        sb.draw(bulletImg, hb.x, hb.y, bulletImg.getWidth() * scale * AbstractDungeon.resScale, bulletImg.getHeight() * scale * AbstractDungeon.resScale);
        hb.render(sb);
    }

    public void update() {
        hb.update();
    }

}
