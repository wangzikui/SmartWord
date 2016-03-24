package wzk.myapplicationadfs;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.Toast;

import java.util.ArrayList;

import so.orion.gbslidebar.SlideAdapter;
import so.orion.slidebar.GBSlideBar;
import so.orion.slidebar.GBSlideBarListener;
import wzk.myapplicationadfs.Util.AssertsUtil;
import wzk.myapplicationadfs.adapter.MyExpandableListViewAdapter;

public class TestActivity extends Activity
{
    private GBSlideBar gbSlideBar;
    private SlideAdapter mAdapter;
    private ExpandableListView expandableListView;
    private MyExpandableListViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        AssertsUtil.makeIndex(this);

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
        expandableListView.setOnGroupClickListener(new OnGroupClickListener() {
            @SuppressLint("NewApi")
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                String str = UserApplication.getGroupArray().get(groupPosition).toString();
                UserApplication.setUnit(str);
                Toast.makeText(TestActivity.this, "unit变更为" + UserApplication.getUnit(), Toast.LENGTH_SHORT).show();
                //关闭其他组
                int count = expandableListView.getExpandableListAdapter().getGroupCount();
                for (int i = 0; i < count; i++){
                    if (groupPosition != i){
                        expandableListView.collapseGroup(i);
                    }
                }
                return false;
            }
        });
        // 监听每个分组里子控件的点击事件
        expandableListView.setOnChildClickListener(new OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                String str = ((ArrayList)UserApplication.getChildArray().get(groupPosition)).get(childPosition).toString();
                UserApplication.setLesson(str);
                UserApplication.MakeArticlePath();
                Toast.makeText(TestActivity.this, "path变更为" + UserApplication.getArticlePath(), Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        adapter = new MyExpandableListViewAdapter(this);
        expandableListView.setAdapter(adapter);
    }
}