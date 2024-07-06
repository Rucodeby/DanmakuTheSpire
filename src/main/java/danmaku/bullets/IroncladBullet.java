package danmaku.bullets;

import com.megacrit.cardcrawl.core.Settings;
import danmaku.actions.MoveAction;
import danmaku.characters.AbstractDanmakuChar;
import danmaku.dungeons.AbstractDungeon;

public class IroncladBullet extends AbstractCharacterBullet{
    private static final String texture = resourcesPath + "swordRed.png";

    public IroncladBullet(MoveAction move) {
        super(texture, AbstractDanmakuChar.hb.cX, AbstractDanmakuChar.hb.cY + 20f * Settings.yScale, move);
    }
}
