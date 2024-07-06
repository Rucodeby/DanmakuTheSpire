package danmaku.helpers;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import danmaku.actions.MoveAction;
import danmaku.bullets.AbstractBullet;

import java.util.ArrayList;
import java.util.Collection;

public class DanmakuManager {

    public ArrayList<AbstractBullet> bullets = new ArrayList<>();
    public ArrayList<MoveAction> actions = new ArrayList<>();

    public void render(SpriteBatch sb) {
        bullets.forEach(x -> x.render(sb));
    }

    public void update() {
        bullets.forEach(AbstractBullet::update);
    }
}
