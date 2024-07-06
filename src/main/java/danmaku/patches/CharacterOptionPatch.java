package danmaku.patches;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import com.megacrit.cardcrawl.screens.charSelect.CharacterOption;
import danmaku.characters.AbstractDanmakuChar;
import danmaku.characters.DanmakuSelectInfo;
import danmaku.characters.Ironclad;
import danmaku.screens.DanmakuCharSelectScreen;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import javassist.expr.ExprEditor;
import javassist.expr.FieldAccess;
import javassist.expr.MethodCall;

import static danmaku.helpers.ImagesStash.bombimg;

public class CharacterOptionPatch {

    @SpirePatch(
            clz = CharacterOption.class,
            method = SpirePatch.CLASS
    )
    public static class DanmakuChar {
        public static SpireField<AbstractDanmakuChar> field = new SpireField<>(Ironclad::new);
    }

    /*@SpirePatch(
            clz = CharacterOption.class,
            method = SpirePatch.CONSTRUCTOR,
            paramtypez = {
                    String.class,
                    AbstractPlayer.class,
                    Texture.class,
                    Texture.class
            }
    )*/

    @SpirePatch(
            clz = CharacterOption.class,
            method = "renderInfo"
    )
    public static class ChangeRenderThings {
        @SpireInsertPatch(
                locator = Locator.class
        )
        public static SpireReturn<Void> Insert(CharacterOption __instance, SpriteBatch sb, String ___flavorText, float ___infoX, float ___infoY, float ___NAME_OFFSET_Y, CharSelectInfo ___charInfo) {
            if (CardCrawlGame.mainMenuScreen.charSelectScreen instanceof DanmakuCharSelectScreen) {
                DanmakuSelectInfo info = (DanmakuSelectInfo) ___charInfo;
                if (!Settings.isMobile) {
                    FontHelper.renderSmartText(sb, FontHelper.bannerNameFont, __instance.name, ___infoX - 35.0F * Settings.scale, ___infoY + ___NAME_OFFSET_Y, 99999.0F, 38.0F * Settings.scale, Settings.GOLD_COLOR);
                    float gap = 22f;
                    for (int i = 1; i < info.bombs + 1; ++i)
                        sb.draw(bombimg, ___infoX - (-8.0F - gap * info.bombs + gap * i) * Settings.scale - 32.0F, ___infoY - 34.0F * Settings.scale - 32.0F, bombimg.packedWidth / 2F, bombimg.packedHeight / 2F, bombimg.packedWidth * 1.5F, bombimg.packedHeight * 1.5F, Settings.scale, Settings.scale, 0.0F);
                    for (int i = 1; i < info.HP + 1; ++i)
                        sb.draw(ImageMaster.TP_HP, ___infoX - (-2.0F - gap * info.HP + gap * i) * Settings.scale - 48.0F, ___infoY + 10.0F * Settings.scale - 48.0F, 32.0F, 32.0F, 96.0F, 96.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 64, 64, false, false);
                    FontHelper.renderSmartText(sb, FontHelper.tipHeaderFont, ___flavorText, ___infoX - 26.0F * Settings.scale, ___infoY + 120.0F * Settings.scale, 10000.0F, 30.0F * Settings.scale, Settings.CREAM_COLOR);
                } else {
                    FontHelper.renderSmartText(sb, FontHelper.bannerNameFont, __instance.name, ___infoX - 35.0F * Settings.scale, ___infoY + 350.0F * Settings.scale, 99999.0F, 38.0F * Settings.scale, Settings.GOLD_COLOR, 1.1F);
                    sb.draw(ImageMaster.TP_HP, ___infoX - 10.0F * Settings.scale - 32.0F, ___infoY + 230.0F * Settings.scale - 32.0F, 32.0F, 32.0F, 64.0F, 64.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 64, 64, false, false);
                    if (__instance.selected) {
                        FontHelper.renderSmartText(sb, FontHelper.buttonLabelFont, ___flavorText, ___infoX - 26.0F * Settings.scale, ___infoY + 170.0F * Settings.scale, 10000.0F, 40.0F * Settings.scale, Settings.CREAM_COLOR, 0.9F);
                    }
                }
                return SpireReturn.Return(null);
            }
            return SpireReturn.Continue();
        }

        public static class Locator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                Matcher matcher = new Matcher.FieldAccessMatcher(Settings.class, "isMobile");
                return LineFinder.findInOrder(ctBehavior, matcher);
            }
        }

        @SpirePatch(
                clz = CharacterOption.class,
                method = "updateHitbox"
        )
        public static class ChangeText {
            public static ExprEditor Instrument() {
                return new ExprEditor() {
                    @Override
                    public void edit(MethodCall m) throws CannotCompileException {
                        if (m.getClassName().equals(AbstractPlayer.class.getName()) && m.getMethodName().equals("getPrefs")) {
                            m.replace(
                                    "if (" + CardCrawlGame.class.getName() + ".mainMenuScreen.charSelectScreen instanceof " +
                                            DanmakuCharSelectScreen.class.getName() + ") {" +
                                            "$_ = null;" +
                                            "} else {" +
                                            "$_ = $proceed($$);" +
                                            "}"
                            );
                        } else if (m.getClassName().equals(AbstractPlayer.class.getName()) && m.getMethodName().equals("doCharSelectScreenSelectEffect")) {
                            m.replace("if (" + CardCrawlGame.class.getName() + ".mainMenuScreen.charSelectScreen instanceof " +
                                    DanmakuCharSelectScreen.class.getName() + ") {"
                                    /*"$_ = (" + AbstractDanmakuChar.class.getName() + ")("
                                    + DanmakuChar.class.getName() + ".field.get(this)).doCharSelectScreenSelectEffect();" +*/
                                    + ChangeText.class.getName() + ".replaceM(this);" +
                                    "} else {" +
                                    "$_ = $proceed($$);" +
                                    "}");
                        }
                    }
                };
            }

            public static void replaceM(CharacterOption instance) {
                DanmakuChar.field.get(instance).doCharSelectScreenSelectEffect();
            }
        }

        @SpirePatch(
                clz = CharacterOption.class,
                method = "updateHitbox"
        )
        public static class ChangeChoosenClass {
            public static ExprEditor Instrument() {
                return new ExprEditor() {
                    @Override
                    public void edit(FieldAccess f) throws CannotCompileException {
                        if (f.isReader() && f.getClassName().equals(AbstractPlayer.class.getName()) && f.getFieldName().equals("chosenClass")) {
                            /*f.replace(
                                    "if (" + CardCrawlGame.class.getName() + ".mainMenuScreen.charSelectScreen instanceof " +
                                            DanmakuCharSelectScreen.class.getName() + ") {" +
                                            "$_ = (" + AbstractDanmakuChar.class.getName() + ")("
                                            + DanmakuChar.class.getName() + ".field.get(this)).choosenClass;" +
                                            "} else {" +
                                            "$_ = $proceed($$);" +
                                            "}"
                            );*/
                            f.replace("$_ = " + ChangeChoosenClass.class.getName() + ".replacing(this, $proceed($$));");
                        }
                    }
                };
            }

            public static AbstractPlayer.PlayerClass replacing(CharacterOption instance, AbstractPlayer.PlayerClass origVal) {
                if (CardCrawlGame.mainMenuScreen.charSelectScreen instanceof DanmakuCharSelectScreen)
                    return DanmakuChar.field.get(instance).choosenClass;
                return origVal;
            }
        }
    }
}
