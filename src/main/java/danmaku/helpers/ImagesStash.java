package danmaku.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import danmaku.DanmakuTheSpireMod;

public class ImagesStash {
    public static TextureAtlas.AtlasRegion bombimg = new TextureAtlas(Gdx.files.internal("powers/powers.atlas")).findRegion("48/the_bomb");
    public static Texture ironcladBullet = new Texture(DanmakuTheSpireMod.imagesPath + "bullets/swordRed.png");
}
