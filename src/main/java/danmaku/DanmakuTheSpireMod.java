package danmaku;

import basemod.BaseMod;
import basemod.interfaces.EditKeywordsSubscriber;
import basemod.interfaces.EditStringsSubscriber;
import basemod.interfaces.PostInitializeSubscriber;
import com.badlogic.gdx.Gdx;
import com.evacipated.cardcrawl.mod.stslib.Keyword;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.localization.*;
import danmaku.dungeons.AbstractDungeon;
import danmaku.screens.ChooseDifficultScreen;
import danmaku.screens.ChooseShotTypeScreen;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.charset.StandardCharsets;

@SpireInitializer
public class DanmakuTheSpireMod implements EditStringsSubscriber, EditKeywordsSubscriber, PostInitializeSubscriber {
    public static final Logger logger = LogManager.getLogger(DanmakuTheSpireMod.class.getName());
    private static String modID;

    public static ChooseShotTypeScreen chooseShotTypeScreen;
    public static ChooseDifficultScreen chooseDifficultScreen;
    public static AbstractDungeon dungeon;

    public static String imagesPath = "danmakuResources/images/";

    public static boolean danmakuMode = false;
    public DanmakuTheSpireMod() {
        logger.info("Subscribe to BaseMod hooks");
        BaseMod.subscribe(this);
        logger.info("Done subscribing");

        setModID("danmaku");
    }

    public static void setModID(String ID) {
        modID = ID;
    }

    public static String getModID() {
        return modID;
    }

    public static void initialize() {
        logger.info("=========================== Initializing The Danmaku ============================");

        @SuppressWarnings("unused")
        DanmakuTheSpireMod transKokoroMod = new DanmakuTheSpireMod();

        logger.info("=========================== /The Danmaku Initialized/ ===========================");
    }

    private String getLang() {
        switch(Settings.language) {
            case RUS:
                return "rus";
            default:
                return "eng";
        }
    }

    @Override
    public void receiveEditStrings() {
        String lang = getLang();
        String resourcesPath = "danmakuResources/localization/";
        BaseMod.loadCustomStringsFile(CharacterStrings.class, resourcesPath + lang + "/CharacterStrings.json");
        BaseMod.loadCustomStringsFile(RelicStrings.class, resourcesPath + lang + "/RelicStrings.json");
        BaseMod.loadCustomStringsFile(UIStrings.class, resourcesPath + lang + "/UIStrings.json");
    }

    @Override
    public void receiveEditKeywords() {
        logger.info("Setting up custom keywords");
        Gson gson = new Gson();
        String json = Gdx.files.internal("danmakuResources/localization/rus/KeywordStrings.json").readString(String.valueOf(StandardCharsets.UTF_8));
        Keyword[] keywords = gson.fromJson(json, Keyword[].class);

        if (keywords != null) {
            for (Keyword keyword : keywords) {
                BaseMod.addKeyword(DanmakuTheSpireMod.getModID().toLowerCase(), keyword.PROPER_NAME, keyword.NAMES, keyword.DESCRIPTION);
            }
        }
        logger.info("Done setting up custom keywords");
    }

    public static String makeID(String idText) {
        return getModID() + ":" + idText;
    }

    @Override
    public void receivePostInitialize() {
        chooseShotTypeScreen = new ChooseShotTypeScreen();
        chooseDifficultScreen = new ChooseDifficultScreen();
    }

    public enum Difficulties {
        EASY,
        NORMAL,
        HARD,
        LUNATIC
    }
}
