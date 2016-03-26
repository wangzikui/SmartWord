package wzk.myapplicationadfs;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import so.orion.gbslidebar.SlideAdapter;
import so.orion.slidebar.GBSlideBar;
import so.orion.slidebar.GBSlideBarListener;
import wzk.myapplicationadfs.Util.AssetsUtil;
import wzk.myapplicationadfs.adapter.MyExpandableListViewAdapter;


/**
 * Created by wzk on 2016/3/23.
 */
public class MainActivity extends Activity {

    private GBSlideBar gbSlideBar;
    private SlideAdapter mAdapter;
    private ExpandableListView expandableListView;
    private MyExpandableListViewAdapter adapter;
    private TextView article;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        AssetsUtil.makeIndex(this);

        article = (TextView)findViewById(R.id.article);
        slideBarInit();
        expListInit();

    }

    public void slideBarInit() {
        gbSlideBar = (GBSlideBar) findViewById(R.id.gbslidebar);
        Resources resources = getResources();
        mAdapter = new SlideAdapter(resources, new int[]{
                R.drawable.btn_tag_selector,
                R.drawable.btn_more_selector,
                R.drawable.btn_reject_selector});

        mAdapter.setTextColor(new int[]{
                Color.GREEN,
                Color.BLUE,
                Color.RED
        });

        gbSlideBar.setAdapter(mAdapter);
        gbSlideBar.setPosition(2);
        gbSlideBar.setOnGbSlideBarListener(new GBSlideBarListener() {
            @Override
            public void onPositionSelected(int position) {
                Log.d("edanelx", "selected " + position);
            }
        });
    }

    public void expListInit(){
        expandableListView = (ExpandableListView)findViewById(R.id.expendlist);
        expandableListView.setGroupIndicator(null);

        // 监听组点击
        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @SuppressLint("NewApi")
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                String str = UserApplication.getGroupArray().get(groupPosition).toString();
                UserApplication.setUnit(str);
                Toast.makeText(MainActivity.this, "unit变更为" + UserApplication.getUnit(), Toast.LENGTH_SHORT).show();
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
                String str = ((ArrayList) UserApplication.getChildArray().get(groupPosition)).get(childPosition).toString();
                UserApplication.setLesson(str);
                UserApplication.MakeArticlePath();
                Toast.makeText(MainActivity.this, "path变更为" + UserApplication.getArticlePath(), Toast.LENGTH_SHORT).show();
                ArrayList arrayList = AssetsUtil.getLinesFromTXT(MainActivity.this, UserApplication.getArticlePath());
                str = "";
                for (int i = 0; i < arrayList.size(); i++) {
                    str += arrayList.get(i);
                }
                article.setText(str);
                return false;
            }
        });
        adapter = new MyExpandableListViewAdapter(this);
        expandableListView.setAdapter(adapter);
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
        Timer tExit = null;
        if (isExit == false) {
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
