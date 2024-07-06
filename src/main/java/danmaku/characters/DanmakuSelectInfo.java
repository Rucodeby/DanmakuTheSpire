package danmaku.characters;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.characters.Ironclad;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.screens.CharSelectInfo;

public class DanmakuSelectInfo extends CharSelectInfo {

    private static final Ironclad placeholder = (Ironclad)CardCrawlGame.characterManager.recreateCharacter(AbstractPlayer.PlayerClass.IRONCLAD);
    public int HP, bombs;
    public AbstractDanmakuChar player;

    public DanmakuSelectInfo(String name, String flavorText, int hp, int bombs, AbstractDanmakuChar c) {
        super(name, flavorText, 70, 70, 0, 99, 5, placeholder, placeholder.getStartingRelics(), placeholder.getStartingDeck(), false);
        this.HP = hp;
        this.bombs = bombs;
        player = c;
    }
}
