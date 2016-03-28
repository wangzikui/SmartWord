package wzk.SmartWord;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import so.orion.gbslidebar.SlideAdapter;
import so.orion.slidebar.GBSlideBar;
import so.orion.slidebar.GBSlideBarListener;
import wzk.SmartWord.Util.AssetsUtil;
import wzk.SmartWord.Util.MatchUtil;
import wzk.SmartWord.Util.TXTUtil;
import wzk.SmartWord.adapter.MyExpandableListViewAdapter;


/**
 * Created by wzk on 2016/3/23.
 */
public class MainActivity extends Activity {

    private GBSlideBar gbSlideBar;
    private SlideAdapter mAdapter;
    private ExpandableListView expandableListView;
    private MyExpandableListViewAdapter adapter;
    private TextView article;
    private ArrayList atcArrayList; //分段存储文章内容
    private String atcString = "";    //文章全文
    private HashMap<Integer , Integer> allWordsLoc; //所有单词位置
    private HashMap<Integer , Integer> targetWordsLoc; //目标单词位置

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AssetsUtil.makeIndex(this);     //建立目录

        article = (TextView)findViewById(R.id.article);
        //获取屏宽
        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics dm = new DisplayMetrics();
        display.getMetrics(dm);
        int width = dm.widthPixels;
        //根据屏幕调整文字大小
        article.setLineSpacing(0f, 1.5f);
        article.setTextSize(8 * (float) width / 640f);
        article.setMovementMethod(LinkMovementMethod.getInstance());    //响应点击
        slideBarInit(); //滑动条初始化
        expListInit();  //下拉菜单初始化

    }

    public void slideBarInit() {
        gbSlideBar = (GBSlideBar) findViewById(R.id.gbslidebar);
        Resources resources = getResources();
        mAdapter = new SlideAdapter(resources, new int[]{   //设置按钮图片
                R.drawable.btn_tag_selector,
                R.drawable.btn_tag_selector,
                R.drawable.btn_tag_selector,
                R.drawable.btn_tag_selector,
                R.drawable.btn_tag_selector,
                R.drawable.btn_tag_selector});

        mAdapter.setTextColor(new int[]{    //设置字体颜色
                Color.CYAN,
                Color.BLUE,
                Color.GREEN,
                Color.YELLOW,
                Color.RED,
                Color.MAGENTA
        });

        gbSlideBar.setAdapter(mAdapter);
        gbSlideBar.setPosition(0);
        gbSlideBar.setOnGbSlideBarListener(new GBSlideBarListener() {
            @Override
            public void onPositionSelected(int position) {
                highLight(position);    //滑条位置事件为设置高亮
            }
        });
    }

    public void expListInit(){  //下拉菜单初始化
        expandableListView = (ExpandableListView)findViewById(R.id.expendlist);
        expandableListView.setGroupIndicator(null); //不显示左侧默认下拉控件

        // 监听组点击
        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @SuppressLint("NewApi")
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                String str = UserApplication.getGroupArray().get(groupPosition).toString();
                UserApplication.setUnit(str);
//                Toast.makeText(MainActivity.this, "unit变更为" + UserApplication.getUnit(), Toast.LENGTH_SHORT).show();
                //关闭其他组
                int count = expandableListView.getExpandableListAdapter().getGroupCount();
                for (int i = 0; i < count; i++) {
                    if (groupPosition != i) {
                        expandableListView.collapseGroup(i);
                    }
                }
                return false;
            }
        });
        // 监听每个分组里子控件的点击事件
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                atcArrayList = null; //分段存储文章内容
                atcString = "";    //文章全文
                allWordsLoc = null; //所有单词位置
                targetWordsLoc = null; //目标单词位置
                String str = ((ArrayList) UserApplication.getChildArray().get(groupPosition)).get(childPosition).toString();    //得文件名
                UserApplication.setLesson(str);
                UserApplication.MakeArticlePath();  //构造文件路径
                atcArrayList = AssetsUtil.getATCinLine(MainActivity.this, article, UserApplication.getArticlePath());   //构造文章内容
                MatchUtil.findWordsFromAtc(atcArrayList);   // 获取文章课后词汇
                AssetsUtil.makeWordLevelPair(MainActivity.this, UserApplication.getOriWords());     //构造词汇等级键值对
                for (int i = 0; i < atcArrayList.size(); i++) {
                    atcString += atcArrayList.get(i); //该段比较耗时
                }
                allWordsLoc = MatchUtil.getAllWordsLoc(atcString);    //获取所有单词位置
                article.setText(TXTUtil.getSpannedStr(atcString, allWordsLoc));   //显示带有点击事件的文章

                return false;
            }
        });
        adapter = new MyExpandableListViewAdapter(this);
        expandableListView.setAdapter(adapter);
    }

    public void highLight(int level){
        if(atcArrayList == null){
            return;
        }
        String[] targetWords;
        targetWords = AssetsUtil.catchTargetWords(UserApplication.getWordLevelPair(), level);   //获取制定等级词汇
        targetWordsLoc = MatchUtil.getWordsLoc(atcString, targetWords);     //获取各待高亮单词在文章中位置
        article.setText(TXTUtil.getSpannedStr(atcString, allWordsLoc, targetWordsLoc));  //设置点击与高亮
    }


    /**
     * 菜单、返回键响应
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK)
        {
            exitBy2Click();		//调用双击退出函数
        }
        return false;
    }

    /**
     * 双击退出函数
     */
    private static Boolean isExit = false;

    private void exitBy2Click() {
        Timer tExit;
        if (!isExit) {
            isExit = true; // 准备退出
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            tExit = new Timer();
            tExit.schedule(new TimerTask() {
                @Override
                public void run() {
                    isExit = false; // 取消退出
                }
            }, 2000); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务

        } else {
            finish();
            System.exit(0);
        }
    }
}
