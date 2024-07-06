package danmaku.characters;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.characters.TheSilent;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.screens.charSelect.CharacterOption;
import danmaku.DanmakuTheSpireMod;

public class Silent extends AbstractDanmakuChar{

    private static TheSilent placeholder = (TheSilent) CardCrawlGame.characterManager.recreateCharacter(AbstractPlayer.PlayerClass.THE_SILENT);
    private static final String charimg = DanmakuTheSpireMod.imagesPath + "characters/ironclad.png";

    public Silent() {
        super(2, 4, placeholder.chosenClass, DanmakuTheSpireMod.makeID("Silent"), charimg);
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
        return new Silent();
    }
}
