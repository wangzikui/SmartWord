package wzk.SmartWord;

import android.app.Application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by wzk on 2016/3/23.
 */
public class UserApplication extends Application {
    private static String Unit = "Unit1";   //单元名
    private static String Lesson = "1.      Finding fossil man .txt";   //文章文件
    private static List GroupArray = new ArrayList();   //内含单元名
    private static List ChildArray = new ArrayList();   //内含文件名
    private static String ParentPath = "GRE";
    private static String ArticlePath = ""; //  文件路径
    private static String[] OriWords;   //文章中出现的单词
    private static HashMap<String , Integer> WordLevelPair; //文章中出现的单词及其level
    @Override
    public void onCreate()
    {
        MakeArticlePath();
        super.onCreate();
    }

    public static String getLesson() { return Lesson; }

    public static String getParentPath() {
        return ParentPath;
    }

    public static String getUnit() {
        return Unit;
    }

    public static String getArticlePath() {
        return ArticlePath;
    }

    public static List getChildArray() { return ChildArray; }

    public static List getGroupArray() { return GroupArray; }

    public static String[] getOriWords() {
        return OriWords;
    }

    public static HashMap<String, Integer> getWordLevelPair() {
        return WordLevelPair;
    }

    public static void setLesson(String lesson) {
        Lesson = lesson;
    }

    public static void setParentPath(String parentPath) {
        ParentPath = parentPath;
    }

    public static void setUnit(String unit) {
        Unit = unit;
    }

    public static void setChildArray(List childArray) { ChildArray = childArray; }

    public static void setGroupArray(List group) { GroupArray = group; }

    public static void setOriWords(String[] oriWords) {
        OriWords = oriWords;
    }

    public static void setWordLevelPair(HashMap<String, Integer> wordLevelPair) {
        WordLevelPair = wordLevelPair;
    }

    public static void MakeArticlePath() {
        ArticlePath = ParentPath + "/" + Unit +"/" + Lesson;
    }
}
