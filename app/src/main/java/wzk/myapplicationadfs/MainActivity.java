package wzk.myapplicationadfs;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;


import java.util.Timer;
import java.util.TimerTask;

import wzk.myapplicationadfs.fragment.FragmentContent;
import wzk.myapplicationadfs.fragment.FragmentLesson;
import wzk.myapplicationadfs.fragment.FragmentUnit;


public class MainActivity extends FragmentActivity {

    FragmentUnit fragmentUnit;
    FragmentLesson fragmentLesson;
    FragmentContent fragmentContent;
    private RadioGroup bottomRg;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private RadioButton rbOne, rbTwo, rbThree;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        fragmentManager = getSupportFragmentManager();
        fragmentUnit = new FragmentUnit();
        fragmentLesson = new FragmentLesson();
        fragmentContent = new FragmentContent();

        setFragmentIndicator();
    }
    private void setFragmentIndicator() {

        bottomRg = (RadioGroup) findViewById(R.id.bottomRg);
        rbOne = (RadioButton) findViewById(R.id.rbOne);
        rbTwo = (RadioButton) findViewById(R.id.rbTwo);
        rbThree = (RadioButton) findViewById(R.id.rbThree);

        bottomRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                fragmentTransaction = fragmentManager.beginTransaction();

                switch (checkedId) {
                    case R.id.rbOne:
                        fragmentTransaction.replace(R.id.content,fragmentUnit);
                        break;

                    case R.id.rbTwo:
                        fragmentTransaction.replace(R.id.content,fragmentLesson);
                        break;

                    case R.id.rbThree:
                        fragmentTransaction.replace(R.id.content,fragmentContent);
                        break;

                    default:
                        break;
                }
                fragmentTransaction.commit();
            }
        });

        rbOne.setChecked(true);
    }
    /**
     * 菜单、返回键响应
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
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

