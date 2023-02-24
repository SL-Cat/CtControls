package cat.customize.recyler;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import cat.customize.R;
import cat.customize.bean.MaskItemBean;
import cat.customize.listener.OnCtRadioTvItemListener;
import cat.customize.view.CtRadioTxView;

/**
 * Created by HSL
 * on 2022/12/2.
 */

public class ItemMaskMoreView extends FrameLayout {

    private Context context;
    /**
     * 滑动主控件
     */
    private HorizontalScrollView horizontalScrollView;

    private LinearLayout mainLinear;

    private List<MaskItemBean> itemLs = new ArrayList<>();

    private OnCtRadioTvItemListener onCtRadioTvItemListener;

    private int leftMargin = 5;
    private int rightMargin = 5;
    private int topMargin = 4;
    private int bottomMargin = 4;

    private int backgroundColor = R.drawable.ct_mask_radius_bg;

    public ItemMaskMoreView(Context context) {
        this(context, null);
    }

    public ItemMaskMoreView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ItemMaskMoreView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        horizontalScrollView = new HorizontalScrollView(context);
        mainLinear = new LinearLayout(context);
        setBackgroundResource(backgroundColor);
        getBackground().setAlpha(130);
        itemLs.add(new MaskItemBean(context.getString(R.string.ct_delete), R.color.color_ff0000, 1));
        itemLs.add(new MaskItemBean(context.getString(R.string.ct_reset), R.color.color_FFC107, 2));
        itemLs.add(new MaskItemBean(context.getString(R.string.ct_review), R.color.color_00FF05, 3));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        HorizontalScrollView.LayoutParams hsPl = new HorizontalScrollView.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        hsPl.gravity = Gravity.CENTER;
        removeAllViews();
        horizontalScrollView.removeAllViews();
        mainLinear.removeAllViews();

        LinearLayout.LayoutParams lyPl = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        for (int i = 0; i < itemLs.size(); i++) {
            MaskItemBean maskItemBean = itemLs.get(i);
            CtRadioTxView radioTxView = new CtRadioTxView(context);
            radioTxView.setIndex(maskItemBean.getIndex());
            radioTxView.setText(maskItemBean.getMsg());
            radioTxView.setTextColor(getResources().getColor(maskItemBean.getTextColor()));
            radioTxView.setTextSize(maskItemBean.getTextSize());
            radioTxView.setRectColor(getResources().getColor(maskItemBean.getDrawableColor()));
            LinearLayout.LayoutParams tvPl = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            tvPl.leftMargin = leftMargin;
            tvPl.rightMargin = rightMargin;
            tvPl.topMargin = topMargin;
            tvPl.bottomMargin = bottomMargin;
            radioTxView.setLayoutParams(tvPl);
            radioTxView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onCtRadioTvItemListener != null) {
                        onCtRadioTvItemListener.itemClick(radioTxView.getIndex());
                    }
                }
            });
            mainLinear.addView(radioTxView);

        }

        mainLinear.setOrientation(LinearLayout.HORIZONTAL);
        mainLinear.setGravity(Gravity.CENTER);
        mainLinear.setLayoutParams(lyPl);
        horizontalScrollView.setLayoutParams(hsPl);
        horizontalScrollView.addView(mainLinear);
        addView(horizontalScrollView);

        // 计算所有子控件需要用到的宽高
        int height = 0;              //记录根容器的高度
        int width = 0;               //记录根容器的宽度
        int count = getChildCount(); //记录容器内的子控件个数
        for (int i = 0; i < count; i++) {
            //测量子控件
            View child = getChildAt(i);
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
            //获得子控件的高度和宽度
            int childHeight = child.getMeasuredHeight();
            int childWidth = child.getMeasuredWidth() + child.getPaddingLeft() + child.getPaddingRight();
            //得到最大宽度，并且累加高度
            height += childHeight;
            width = Math.max(childWidth, width);
        }
        // 设置当前View的宽高
        setMeasuredDimension(getMeasuredWidth(), height);
        invalidate();
    }

    /**
     * 设置按钮间距
     *
     * @param left
     * @param right
     * @param top
     * @param bottom
     */
    public void setMargin(int left, int right, int top, int bottom) {
        this.leftMargin = left;
        this.rightMargin = right;
        this.topMargin = top;
        this.bottomMargin = bottom;
    }

    /**
     * 添加数据
     *
     * @param beanList
     */
    public void addItem(List<MaskItemBean> beanList) {
        itemLs.clear();
        itemLs.addAll(beanList);
        requestLayout();
    }

    public void setOnCtRadioTvItemListener(OnCtRadioTvItemListener onCtRadioTvItemListener) {
        this.onCtRadioTvItemListener = onCtRadioTvItemListener;
    }

    /**
     * 修改背景色
     *
     * @param backgroundRes
     */
    public void setMaskBackgroundRes(int backgroundRes) {
        setBackgroundResource(backgroundRes);
    }

    /**
     * 设置背景透明度
     * @param alpha
     */
    public void setBackAlpha(int alpha){
        getBackground().setAlpha(alpha);
    }
}
