package wzk.myapplicationadfs.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import wzk.myapplicationadfs.R;

/**
 * Created by wzk on 2016/3/23.
 */
public class HeaderLayout extends LinearLayout {
    LayoutInflater mInflater;
    RelativeLayout mHeader;
    TextView mTitleView;
    LinearLayout mLeftContainer, mRightContainer;
    Button mBackBtn;

    public HeaderLayout(Context context) {
        super(context);
        init();
    }


    public HeaderLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mInflater = LayoutInflater.from(getContext());
        mHeader = (RelativeLayout) mInflater.inflate(R.layout.base_common_header, null, false);
        mTitleView = (TextView) mHeader.findViewById(R.id.header_titleView);
        mLeftContainer = (LinearLayout) mHeader.findViewById(R.id.header_leftContainer);
        mRightContainer = (LinearLayout) mHeader.findViewById(R.id.header_rightContainer);
        mBackBtn = (Button) mHeader.findViewById(R.id.header_backBtn);
        addView(mHeader);
    }

    public void setTitle(int titileId) {
        mTitleView.setText(titileId);
    }

    public void setTitle(String title) {
        mTitleView.setText(title);
    }

    public void setBackBtn() {
        setBackBtn(null);
    }

    public void setBackBtn(OnClickListener listener) {
        setBackBtn(null, R.string.empty_string, listener);
    }

    public void setBackBtn(Drawable drawable, int backTextId, OnClickListener listener) {
        if (drawable != null) {
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            mBackBtn.setCompoundDrawables(drawable, null, null, null);

        }
        mBackBtn.setVisibility(View.VISIBLE);
        mBackBtn.setText(backTextId);
        if (listener == null) {
            listener = new OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((Activity) getContext()).finish();
                }
            };
        }
        mBackBtn.setOnClickListener(listener);
    }

    public void showRightBtn(Drawable right, OnClickListener listener) {
//        View imageViewLayout = mInflater.inflate(R.layout.common_header_right_btn, null, false);
//        ImageButton rightButton = (ImageButton) imageViewLayout.findViewById(R.id.header_rightimageBtn);
        ImageButton rightButton = (ImageButton) mHeader.findViewById(R.id.header_rightimageBtn);
        rightButton.setVisibility(View.VISIBLE);
        if (right != null) {
            rightButton.setImageDrawable(right);
        }

        rightButton.setOnClickListener(listener);
        rightButton.getBackground().setAlpha(0);
    }

    public void setRightBtnListener(OnClickListener listener) {
        ImageButton rightButton = (ImageButton) mHeader.findViewById(R.id.header_rightimageBtn);
        rightButton.setVisibility(View.VISIBLE);

        rightButton.setOnClickListener(listener);
        rightButton.getBackground().setAlpha(0);
    }
}





