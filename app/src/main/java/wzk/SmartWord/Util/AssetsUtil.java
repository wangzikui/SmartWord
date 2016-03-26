package wzk.SmartWord.Util;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import wzk.SmartWord.UserApplication;

/**
 * Created by wzk on 2016/3/24.
 */
public class AssetsUtil {
    public static void makeIndex(Context context){  //建立目录，用于构建下拉菜单
        ArrayList<String> list;
        ArrayList<ArrayList<String>> subList = new ArrayList<>();
        String str;
        list = getFileList("GRE", context);
//        list = getFileList("", context);
        UserApplication.setGroupArray(list);
        int groupLenth = list.size();
        for (int i = 0; i < groupLenth; i ++) {
            str = list.get(i);
            subList.add(getFileList("GRE/" + str, context));
        }
        UserApplication.setChildArray(subList);
    }

    public static ArrayList<String> getFileList(String path, Context context) { //获取当前目录下属所有文件名
        String[] temp = null;
        ArrayList<String> body = new ArrayList<>();
        Activity activity = (Activity) context;
        AssetManager assetManager = activity.getAssets();
        try {
            temp = assetManager.list(path);
        } catch (IOException e) { e.printStackTrace(); }
        Collections.addAll(body, temp);
        return body;
    }

    public static ArrayList<String> getLinesFromTXT(Context context, String fileName){     //按行获取txt内容，主用于读取单词表
        ArrayList<String> arrayList = new ArrayList<>(800);
        String line;
        try {
            InputStreamReader inputReader = new InputStreamReader(context.getAssets().open(fileName));
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

    public static ArrayList<String> getATCinLine(Context context, TextView textView, String fileName){     //按行获取txt内容，主用于读取文章
        ArrayList<String> arrayList = new ArrayList<>(100);
        String line;
        try {
            InputStreamReader inputReader = new InputStreamReader(context.getAssets().open(fileName));
            BufferedReader bufReader = new BufferedReader(inputReader);
            while((line = bufReader.readLine()) != null){
                line = TextJustifyUtil.justify(textView, line, textView.getWidth());    //交由TextJustifyUtil修正，使之分散对齐
                line += "\r\n";
                arrayList.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return arrayList;
    }

    public static String[] catchTargetWords(HashMap<String , Integer> wordLevelPair, int level){    //指定level获取符合的words
        ArrayList<String> arrayList = new ArrayList<>();
        String[] result;
        for (Map.Entry<String, Integer> entry : wordLevelPair.entrySet()) {
            if (entry.getValue() <= level){
                arrayList.add(entry.getKey());
            }
        }
        result = new String[arrayList.size()];
        arrayList.toArray(result);
        return result;
    }

    public static void makeWordLevelPair(Context context, String[] oriWords){   //由word组生成wordlevel对放入application
        HashMap<String , Integer> map = new HashMap<>();
        ArrayList<String> arrayList = getLinesFromTXT(context, "nce4_words");   //获得词表
        String temp;
        int lev;
        for(int i = 0; i < arrayList.size(); i++){
            temp = (arrayList.get(i)).trim();
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
