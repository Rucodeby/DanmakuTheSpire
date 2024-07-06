package danmaku.patches;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.DrawMaster;
import danmaku.DanmakuTheSpireMod;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;

import org.apache.logging.log4j.Logger;

public class NewAbstractDungeonPatch {

    @SpireEnum
    public static CardCrawlGame.GameMode DANMAKU_GAMEPLAY;

    @SpirePatch(
            clz = CardCrawlGame.class,
            method = "render"
    )
    public static class RenderNewDungeon {
        @SpireInsertPatch(
                locator = Locator.class
        )
        public static void Insert(CardCrawlGame __instance, SpriteBatch ___sb) {
            if(CardCrawlGame.mode == DANMAKU_GAMEPLAY)
                DanmakuTheSpireMod.dungeon.render(___sb);
        }

        public static class Locator extends SpireInsertLocator {

            @Override
            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                Matcher matcher = new Matcher.MethodCallMatcher(DrawMaster.class, "draw");
                return LineFinder.findInOrder(ctBehavior, matcher);
            }
        }
    }

    @SpirePatch(
            clz = CardCrawlGame.class,
            method = "update"
    )
    public static class UpdateNewDungeon {
        @SpireInsertPatch(
                locator = Locator.class
        )
        public static void Insert(CardCrawlGame __instance) {
            if(CardCrawlGame.mode == DANMAKU_GAMEPLAY)
                DanmakuTheSpireMod.dungeon.update();
        }

        public static class Locator extends SpireInsertLocator {

            @Override
            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                Matcher matcher = new Matcher.MethodCallMatcher(CardCrawlGame.class, "updateDebugSwitch");
                return LineFinder.findInOrder(ctBehavior, matcher);
            }
        }
    }

    @SpirePatch( clz = CardCrawlGame.class, method = "update" )
    @SpirePatch( clz = CardCrawlGame.class, method = "render" )
    public static class DisableLogSpam {
        public static ExprEditor Instrument() {
            return new ExprEditor() {
                @Override
                public void edit(MethodCall m) throws CannotCompileException {
                    if (m.getClassName().equals(Logger.class.getName()) && m.getMethodName().equals("info")) {
                        m.replace("$_ = null;");
                    }
                }
            };
        }
    }
}
