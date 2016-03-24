package wzk.myapplicationadfs.Util;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;

import java.io.IOException;
import java.util.ArrayList;

import wzk.myapplicationadfs.UserApplication;

/**
 * Created by wzk on 2016/3/24.
 */
public class AssertsUtil {
    public static void makeIndex(Context context){
        ArrayList list = null;
        ArrayList subList = new ArrayList();
        String str = "";
        list = getFileList("GRE", context);
//        list = getFileList("", context);
        UserApplication.setGroupArray(list);
        int groupLenth = list.size();
        for (int i = 0; i < groupLenth; i ++) {
            str = list.get(i).toString();
            subList.add(getFileList("GRE/" + str, context));
        }
        UserApplication.setChildArray(subList);
    }
    public static ArrayList getFileList(String path, Context context) {
        String[] temp = null;
        ArrayList body = new ArrayList();
        Activity activity = (Activity) context;
        AssetManager assetManager = activity.getAssets();
        try {
            temp = assetManager.list(path);
        } catch (IOException e) { e.printStackTrace(); }
        for (String str : temp){
            body.add(str);
        }
        return body;
    }
}
