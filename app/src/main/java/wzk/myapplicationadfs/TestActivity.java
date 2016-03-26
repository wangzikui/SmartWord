package wzk.myapplicationadfs;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import so.orion.gbslidebar.SlideAdapter;
import so.orion.slidebar.GBSlideBar;
import so.orion.slidebar.GBSlideBarListener;
import wzk.myapplicationadfs.Util.AssetsUtil;
import wzk.myapplicationadfs.Util.MatchUtil;
import wzk.myapplicationadfs.adapter.MyExpandableListViewAdapter;

public class TestActivity extends Activity
{
    private GBSlideBar gbSlideBar;
    private SlideAdapter mAdapter;
    private ExpandableListView expandableListView;
    private MyExpandableListViewAdapter adapter;
    private TextView article;
    private ArrayList atcArrayList;

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
                R.drawable.btn_tag_selector,
                R.drawable.btn_tag_selector,
                R.drawable.btn_tag_selector,
                R.drawable.btn_tag_selector,
                R.drawable.btn_tag_selector});

        mAdapter.setTextColor(new int[]{
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
                highLight(position);
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
                for (int i = 0; i < count; i++) {
                    if (groupPosition != i) {
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
                String str = ((ArrayList) UserApplication.getChildArray().get(groupPosition)).get(childPosition).toString();
                UserApplication.setLesson(str);
                UserApplication.MakeArticlePath();
                atcArrayList = AssetsUtil.getLinesFromTXT(TestActivity.this, UserApplication.getArticlePath());
                MatchUtil.findWordsFromAtc(atcArrayList);
                AssetsUtil.makeWordLevelPair(TestActivity.this, UserApplication.getOriWords());
                str = "";
                for (int i = 0; i < atcArrayList.size(); i++){
                   str += atcArrayList.get(i);
                }
                article.setText(str);

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
        String[] oriWords = UserApplication.getOriWords();
        String[] targetWords = null;
        targetWords = AssetsUtil.catchTargetWords(UserApplication.getWordLevelPair(), level);
        String str = "";
        for (String s : targetWords){
            s +="\r\n";
            str += s;
        }
        article.setText(str);
    }
}