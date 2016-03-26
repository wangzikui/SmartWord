package wzk.SmartWord.Util;

import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.BackgroundColorSpan;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by wzk on 2016/3/25.
 */
public class TXTUtil {
    public static SpannableString getHighLitStr(String oriString, HashMap<Integer , Integer> map){  //设置高亮，hashmap是word位置
        SpannableString sp = new SpannableString(oriString);
        Iterator iter = map.entrySet().iterator();
        Map.Entry entry;
        while (iter.hasNext()){
            entry = (Map.Entry) iter.next();
            sp.setSpan(new BackgroundColorSpan(Color.YELLOW), (Integer)entry.getKey() ,(Integer)entry.getValue(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return sp;
    }
}
