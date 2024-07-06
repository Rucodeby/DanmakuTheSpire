package danmaku.characters;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.Hitbox;
import danmaku.DanmakuTheSpireMod;

public class Ironclad extends AbstractDanmakuChar{

    private static final com.megacrit.cardcrawl.characters.Ironclad placeholder = (com.megacrit.cardcrawl.characters.Ironclad) CardCrawlGame.characterManager.recreateCharacter(AbstractPlayer.PlayerClass.IRONCLAD);
    private static final String charimg = DanmakuTheSpireMod.imagesPath + "characters/ironclad.png";

    public Ironclad() {
        super(5, 3, placeholder.chosenClass, DanmakuTheSpireMod.makeID("Ironclad"), charimg);
    }

    @Override
    public void doCharSelectScreenSelectEffect() {
        placeholder.doCharSelectScreenSelectEffect();
    }

    @Override
    public String getLocalizedCharacterName() {
        return placeholder.getLocalizedCharacterName();
    }

    @Override
    public String getFlavorText() {
        return placeholder.TEXT[0];
    }

    @Override
    public AbstractDanmakuChar newInstance() {
        return new Ironclad();
    }
}
