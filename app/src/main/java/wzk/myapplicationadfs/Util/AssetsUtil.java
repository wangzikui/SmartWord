package wzk.myapplicationadfs.Util;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import wzk.myapplicationadfs.UserApplication;

/**
 * Created by wzk on 2016/3/24.
 */
public class AssetsUtil {
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

    public static ArrayList getLinesFromTXT (Context context, String fileName){     //按行获取txt内容
        ArrayList arrayList = new ArrayList(800);
        String line="";
        try {
            InputStreamReader inputReader = new InputStreamReader(((Activity)context).getAssets().open(fileName));
            BufferedReader bufReader = new BufferedReader(inputReader);
            while((line = bufReader.readLine()) != null){
                line += "\r\n";
                arrayList.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return arrayList;
    }

    public static ArrayList getATCinLine (Context context, TextView textView, String fileName){     //按行获取txt内容
        ArrayList arrayList = new ArrayList(100);
        String line="";
        try {
            InputStreamReader inputReader = new InputStreamReader(((Activity)context).getAssets().open(fileName));
            BufferedReader bufReader = new BufferedReader(inputReader);
            while((line = bufReader.readLine()) != null){
                line = TextJustifyUtil.justify(textView, line, textView.getWidth());
                line += "\r\n";
                arrayList.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return arrayList;
    }

    public static String[] catchTargetWords(HashMap<String , Integer> wordLevelPair, int level){
        ArrayList arrayList = new ArrayList();
        String[] result = null;
        for (Map.Entry<String, Integer> entry : wordLevelPair.entrySet()) {
            if (entry.getValue() <= level){
                arrayList.add(entry.getKey());
            }
        }
        result = new String[arrayList.size()];
        arrayList.toArray(result);
        return result;
    }

    public static void makeWordLevelPair(Context context, String[] oriWords){
        HashMap<String , Integer> map = new HashMap<String , Integer>();
        ArrayList arrayList = getLinesFromTXT(context, "nce4_words");   //获得词表
        String temp;
        int lev;
        for(int i = 0; i < arrayList.size(); i++){
            temp = ((String)arrayList.get(i)).trim();
            for (String str : oriWords){
                lev = MatchUtil.getLevel(str, temp);
                if(lev == 7){
                    continue;
                }
                map.put(str, lev);
            }
        }
        UserApplication.setWordLevelPair(map);
    }

}
