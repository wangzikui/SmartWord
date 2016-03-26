package wzk.SmartWord.Util;

import android.graphics.Paint;
import android.text.TextUtils;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by wzk on 2016/3/26.
 */
public class TextJustifyUtil {
    public static String justify(TextView textView,String str,float contentWidth) {
        String text=str;
        Paint paint=textView.getPaint();

        ArrayList<String> lineList=lineBreak(text,paint,contentWidth);

        return TextUtils.join(" ", lineList).replaceFirst("\\s", "");
    }


    private static ArrayList<String> lineBreak(String text,Paint paint,float contentWidth){
        String [] wordArray=text.split("\\s");
        ArrayList<String> lineList = new ArrayList<String>();
        String myText="";

        for(String word:wordArray){
            if(paint.measureText(myText+" "+word)<=contentWidth)
                myText=myText+" "+word;
            else{
                int totalSpacesToInsert=(int)((contentWidth-paint.measureText(myText))/paint.measureText(" "));
                lineList.add(justifyLine(myText,totalSpacesToInsert));
                myText=word;
            }
        }
        lineList.add(myText);
        return lineList;
    }

    private static String justifyLine(String text,int totalSpacesToInsert){
        String[] wordArray=text.split("\\s");
        String toAppend=" ";

        while((totalSpacesToInsert)>=(wordArray.length-1)){
            toAppend=toAppend+" ";
            totalSpacesToInsert=totalSpacesToInsert-(wordArray.length+1);//aaa
        }
        int i=0;
        String justifiedText="";
        for(String word:wordArray){
            if(i<totalSpacesToInsert)
                justifiedText=justifiedText+word+" "+toAppend;

            else
                justifiedText=justifiedText+word+toAppend;

            i++;
        }

        return justifiedText;
    }
}
