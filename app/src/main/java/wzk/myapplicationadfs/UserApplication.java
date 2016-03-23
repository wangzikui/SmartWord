package wzk.myapplicationadfs;

import android.app.Application;

/**
 * Created by wzk on 2016/3/23.
 */
public class UserApplication extends Application {
    private static String Unit = "Unit1";
    private static String Lesson = "1.      Finding fossil man .txt";
    private static String ParentPath = "GRE";
    private static String ArticlePath = "";
    @Override
    public void onCreate()
    {
        MakeArticlePath();
        super.onCreate();
    }

    public static String getLesson() {
        return Lesson;
    }

    public static String getParentPath() {
        return ParentPath;
    }

    public static String getUnit() {
        return Unit;
    }

    public static String getArticlePath() {
        return ArticlePath;
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

    public static void MakeArticlePath() {
        ArticlePath = ParentPath + "/" + Unit +"/" + Lesson;
    }
}
