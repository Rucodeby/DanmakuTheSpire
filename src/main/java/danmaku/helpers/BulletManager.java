package danmaku.helpers;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import danmaku.bullets.AbstractBullet;

import java.util.ArrayList;

public class BulletManager {

    public ArrayList<AbstractBullet> bullets = new ArrayList<>();

    public void render(SpriteBatch sb) {
        bullets.forEach(x -> x.render(sb));
    }

    public void update() {
        bullets.forEach(AbstractBullet::update);
    }
}
