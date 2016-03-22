package wzk.myapplicationadfs.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import wzk.myapplicationadfs.R;
import wzk.myapplicationadfs.view.HeaderLayout;

/**
 * Created by wzk on 2016/3/23.
 */
public class BaseFragment extends Fragment {
    HeaderLayout headerLayout;
    Context mContext;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mContext = getActivity();
        headerLayout = (HeaderLayout)getView().findViewById(R.id.header_layout);
    }
}
