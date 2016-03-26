package wzk.SmartWord.Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import wzk.SmartWord.UserApplication;

/**
 * Created by wzk on 2016/3/25.
 */
public class MatchUtil {
    public static HashMap<Integer , Integer> getWordsLoc(String ori, String[] words){
        HashMap<Integer , Integer> map = new HashMap<>();
        int startIndex = 0;
        int wordLenth = 0;
        Matcher mt;
        Pattern pt;
        for (String str : words) {
            wordLenth = str.length();
            pt = Pattern.compile(str);
            mt = pt.matcher(ori);
            while(mt.find()){
                startIndex = mt.start();
                if(mt.group().equals(str)){
                    map.put(startIndex , startIndex + wordLenth);
                }
            }
            mt.reset();
        }
        return map;
    }

    public static void findWordsFromAtc(ArrayList arrayList){
        String[] result;
        ArrayList temp = new ArrayList();
        String regex = "^[a-zA-Z]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher;
        for (int i = 0; i < arrayList.size();i++){
            matcher = pattern.matcher(arrayList.get(i).toString().trim());
            if (matcher.find()){
                temp.add(matcher.group());
            }
        }
        result = new String[temp.size()];
        temp.toArray(result);
        UserApplication.setOriWords(result);
    }

    public static int getLevel(String word, String str){    //level找词专用
        String[] temp;
        String strTemp = str.trim();
        Pattern p  =  Pattern.compile(word);
        Matcher m  =  p.matcher(str);
        if (m.find()){
            temp = strTemp.split("\t");
            return Integer.parseInt(temp[1]);
        }
        return 7;
    }
}
