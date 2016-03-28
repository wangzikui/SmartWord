package wzk.SmartWord.Util;

import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.BackgroundColorSpan;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by wzk on 2016/3/25.
 */
public class TXTUtil {
    public static SpannableString getSpannedStr(String oriString, HashMap<Integer , Integer> allMap){  //设置高亮，allmap是所有word位置
        final SpannableString sp = new SpannableString(oriString);
        final BackgroundColorSpan bcs = new BackgroundColorSpan(Color.RED);
        Iterator iter = allMap.entrySet().iterator();
        Map.Entry entry;
        while (iter.hasNext()){     //遍历allMap
            entry = (Map.Entry) iter.next();
            sp.setSpan(new ClickableSpan() {
                @Override
                public void onClick(View widget) {
                    TextView tv = (TextView) widget;
                    Spanned s = (Spanned) tv.getText();
                    int start = s.getSpanStart(this);
                    int end = s.getSpanEnd(this);
                    sp.removeSpan(bcs);
                    sp.setSpan(bcs, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    ((TextView) widget).setText(sp);
                }
            }, (Integer) entry.getKey(), (Integer) entry.getValue(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return sp;
    }

    public static SpannableString getSpannedStr(String oriString, HashMap<Integer , Integer> allMap, HashMap<Integer , Integer> targetMap){  //设置高亮与点击，allmap是所有word位置, targetMap是指定高亮word位置
        final SpannableString sp = new SpannableString(oriString);
        final BackgroundColorSpan bcs = new BackgroundColorSpan(Color.RED);
        Map.Entry entry;
        Iterator iter = allMap.entrySet().iterator();//allmap遍历子
        while (iter.hasNext()){     //遍历allmap，点击事件
            entry = (Map.Entry) iter.next();
            sp.setSpan(new ClickableSpan() {
                @Override
                public void onClick(View widget) {
                    TextView tv = (TextView) widget;
                    Spanned s = (Spanned) tv.getText();
                    int start = s.getSpanStart(this);
                    int end = s.getSpanEnd(this);
                    ((TextView) widget).setText(sp);
                    sp.removeSpan(bcs);
                    ((TextView) widget).setText(sp);
                    sp.setSpan(bcs, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    ((TextView) widget).setText(sp);
                }
            }, (Integer)entry.getKey() ,(Integer)entry.getValue(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        iter = targetMap.entrySet().iterator();//targetmap遍历子
        while (iter.hasNext()){     //遍历targetmap，高亮
            entry = (Map.Entry) iter.next();
            sp.setSpan(new BackgroundColorSpan(Color.YELLOW), (Integer)entry.getKey() ,(Integer)entry.getValue(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return sp;
    }
}
