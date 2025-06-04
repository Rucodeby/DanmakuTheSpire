package danmaku.bullets;

import com.megacrit.cardcrawl.core.Settings;
import danmaku.characters.AbstractDanmakuChar;

public class IroncladBullet extends AbstractCharacterBullet{
    private static final String texture = resourcesPath + "swordRed.png";

    public IroncladBullet() {
        super(texture, AbstractDanmakuChar.hb.cX, AbstractDanmakuChar.hb.cY + 20f * Settings.yScale);
    }
}
