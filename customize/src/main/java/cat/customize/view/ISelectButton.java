package cat.customize.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import cat.customize.R;

/**
 * Created by HSL on 2022/3/28.
 */

public class ISelectButton extends RelativeLayout implements View.OnClickListener {

    private TextView leftTv, rightTv;

    private IChooesOnClickListener chooesOnClickListener;

    public interface IChooesOnClickListener {

        void onClickChooesItemListerenr(int position);
    }

    public ISelectButton(Context context) {
        super(context, null);
    }

    public ISelectButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ISelectButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        View view = (View) View.inflate(context, R.layout.ct_select_btn_layout, this);

        leftTv = (TextView) view.findViewById(R.id.ct_select_left_btn);
        rightTv = (TextView) view.findViewById(R.id.ct_select_right_btn);
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.ISelectBtnStyle);
        String leftStr = typedArray.getString(R.styleable.ISelectBtnStyle_left_text);
        String rightStr = typedArray.getString(R.styleable.ISelectBtnStyle_right_text);
        leftTv.setText(leftStr);
        rightTv.setText(rightStr);
        selectItem(0);
        leftTv.setOnClickListener(this);
        rightTv.setOnClickListener(this);
    }

    /**
     * 添加按钮控件
     *
     * @param btnName
     */
    public void setTexts(List<String> btnName) {
        if (btnName != null && btnName.size() > 0) {
            for (int i = 0; i < btnName.size(); i++) {
                if (i == 0) {
                    leftTv.setText(btnName.get(0));
                }
                if (i == 1) {
                    rightTv.setText(btnName.get(1));
                }
            }
        }
        invalidate();
    }

    public void setButtonWidth(int width) {
        LinearLayout.LayoutParams linearParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        linearParams.width = width;// 控件的宽强制设成30
        leftTv.setLayoutParams(linearParams); //使设置好的布局参数应用到控件
        rightTv.setLayoutParams(linearParams);
        invalidate();
    }

    public void setButtonHeight(int height) {
        LinearLayout.LayoutParams linearParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        linearParams.height = height;
        leftTv.setLayoutParams(linearParams); //使设置好的布局参数应用到控件
        rightTv.setLayoutParams(linearParams);
        invalidate();
    }

    /**
     * 点击事件回调
     *
     * @param chooesOnClickListener
     */
    public void setChooesOnClickListener(IChooesOnClickListener chooesOnClickListener) {
        this.chooesOnClickListener = chooesOnClickListener;
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.ct_select_left_btn) {
            if (chooesOnClickListener != null) {
                chooesOnClickListener.onClickChooesItemListerenr(0);
            }
        } else if (i == R.id.ct_select_right_btn) {
            if (chooesOnClickListener != null) {
                chooesOnClickListener.onClickChooesItemListerenr(1);
            }
        }
    }


    /**
     * 当前选择的item
     *
     * @param position
     */
    public void selectItem(int position) {
        switch (position) {
            case 0:
                selectType = 0;
                leftTv.setSelected(true);
                leftTv.setTextColor(getResources().getColor(R.color.color_ffffff));
                rightTv.setSelected(false);
                rightTv.setTextColor(getResources().getColor(R.color.color_000000));
                break;
            case 1:
                selectType = 1;
                rightTv.setSelected(true);
                rightTv.setTextColor(getResources().getColor(R.color.color_ffffff));
                leftTv.setSelected(false);
                leftTv.setTextColor(getResources().getColor(R.color.color_000000));
                break;
            default:
                break;
        }
    }

    private int selectType;
    /**
     * 当前选择的item
     *
     * @param type  false:true  左侧:右侧
     */
    public void selectItem(boolean type) {
        if(type){
            selectType = 1;
            rightTv.setSelected(true);
            rightTv.setTextColor(getResources().getColor(R.color.color_ffffff));
            leftTv.setSelected(false);
            leftTv.setTextColor(getResources().getColor(R.color.color_000000));
        }else {
            selectType = 0;
            leftTv.setSelected(true);
            leftTv.setTextColor(getResources().getColor(R.color.color_ffffff));
            rightTv.setSelected(false);
            rightTv.setTextColor(getResources().getColor(R.color.color_000000));
        }
    }

    public int getSelectType(){
        return selectType;
    }
}
