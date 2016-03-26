package wzk.SmartWord.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import wzk.SmartWord.R;
import wzk.SmartWord.UserApplication;

/**
 * Created by wzk on 2016/3/24.
 */
public class MyExpandableListViewAdapter extends BaseExpandableListAdapter {

    private Context context;

    public MyExpandableListViewAdapter(Context context) {
        this.context = context;
    }

    /**
     *
     * 获取组的个数
     *
     * @return
     * @see android.widget.ExpandableListAdapter#getGroupCount()
     */
    @Override
    public int getGroupCount()
    {
        return UserApplication.getGroupArray().size();
    }

    /**
     *
     * 获取指定组中的子元素个数
     *
     * @param groupPosition
     * @return
     * @see android.widget.ExpandableListAdapter#getChildrenCount(int)
     */
    @Override
    public int getChildrenCount(int groupPosition) {
        return ((ArrayList)UserApplication.getChildArray().get(groupPosition)).size();
    }

    /**
     *
     * 获取指定组中的数据
     *
     * @param groupPosition
     * @return
     * @see android.widget.ExpandableListAdapter#getGroup(int)
     */
    @Override
    public Object getGroup(int groupPosition)
    {
        return UserApplication.getGroupArray().get(groupPosition);
    }

    /**
     *
     * 获取指定组中的指定子元素数据。
     *
     * @param groupPosition
     * @param childPosition
     * @return
     * @see android.widget.ExpandableListAdapter#getChild(int, int)
     */
    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return ((ArrayList)UserApplication.getChildArray().get(groupPosition)).get(childPosition);
    }

    /**
     *
     * 获取指定组的ID，这个组ID必须是唯一的
     *
     * @param groupPosition
     * @return
     * @see android.widget.ExpandableListAdapter#getGroupId(int)
     */
    @Override
    public long getGroupId(int groupPosition)
    {
        return groupPosition;
    }

    /**
     *
     * 获取指定组中的指定子元素ID
     *
     * @param groupPosition
     * @param childPosition
     * @return
     * @see android.widget.ExpandableListAdapter#getChildId(int, int)
     */
    @Override
    public long getChildId(int groupPosition, int childPosition)
    {
        return childPosition;
    }

    /**
     *
     * 组和子元素是否持有稳定的ID,也就是底层数据的改变不会影响到它们。
     *
     * @return
     * @see android.widget.ExpandableListAdapter#hasStableIds()
     */
    @Override
    public boolean hasStableIds()
    {
        return true;
    }

    /**
     *
     * 获取显示指定组的视图对象
     *
     * @param groupPosition 组位置
     * @param isExpanded 该组是展开状态还是伸缩状态
     * @param convertView 重用已有的视图对象
     * @param parent 返回的视图对象始终依附于的视图组
     * @return
     * @see android.widget.ExpandableListAdapter#getGroupView(int, boolean, android.view.View,
     *      android.view.ViewGroup)
     */
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupHolder groupHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.expendlist_group, null);
            groupHolder = new GroupHolder();
            groupHolder.unit = (TextView)convertView.findViewById(R.id.unit);
            groupHolder.expState = (ImageView)convertView.findViewById(R.id.img);
            convertView.setTag(groupHolder);
        }
        else {
            groupHolder = (GroupHolder)convertView.getTag();
        }

        if (!isExpanded) {
            groupHolder.expState.setBackgroundResource(R.drawable.notok);
        }
        else {
            groupHolder.expState.setBackgroundResource(R.drawable.ok);
        }

        groupHolder.unit.setText(UserApplication.getGroupArray().get(groupPosition).toString());
        return convertView;
    }

    /**
     *
     * 获取一个视图对象，显示指定组中的指定子元素数据。
     *
     * @param groupPosition 组位置
     * @param childPosition 子元素位置
     * @param isLastChild 子元素是否处于组中的最后一个
     * @param convertView 重用已有的视图(View)对象
     * @param parent 返回的视图(View)对象始终依附于的视图组
     * @return
     * @see android.widget.ExpandableListAdapter#getChildView(int, int, boolean, android.view.View,
     *      android.view.ViewGroup)
     */
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ItemHolder itemHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.expendlist_item, null);
            itemHolder = new ItemHolder();
//            itemHolder.chnName = (TextView)convertView.findViewById(R.id.chn_name);
            itemHolder.engName = (TextView)convertView.findViewById(R.id.eng_name);
//            itemHolder.readState = (ImageView)convertView.findViewById(R.id.read_state);
            convertView.setTag(itemHolder);
        } else {
            itemHolder = (ItemHolder)convertView.getTag();
        }
//        itemHolder.chnName.setText(item_list.get(groupPosition).get(childPosition));
        itemHolder.engName.setText(((ArrayList)UserApplication.getChildArray().get(groupPosition)).get(childPosition).toString());
//        itemHolder.readState.setBackgroundResource(item_list2.get(groupPosition).get(childPosition));
        return convertView;
    }

    /**
     *
     * 是否选中指定位置上的子元素。
     *
     * @param groupPosition
     * @param childPosition
     * @return
     * @see android.widget.ExpandableListAdapter#isChildSelectable(int, int)
     */
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition)
    {
        return true;
    }

}

class GroupHolder {
    public TextView unit;
    public ImageView expState;
}

class ItemHolder {
//    public TextView chnName ;
    public TextView engName;
//    public ImageView readState;
}
