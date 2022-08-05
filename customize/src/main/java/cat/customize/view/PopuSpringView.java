package cat.customize.view;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cat.customize.R;
import cat.customize.bean.PopuStrBean;
import cat.customize.xlist.BaseListAdapter;

/**
 * Created by HSL on 2022/7/25.
 */

public class PopuSpringView extends LinearLayout {

    private ListView lsView;
    private View inflate;

    public interface OnPopuSpringListener {
        void OnClickItem(String id);
    }

    private OnPopuSpringListener onPopuSpringListener;
    private ValueAnimator valueAnimator;
    // 默认动画时常1s
    private long duration = 200;
    private float start = 0.0f;
    private float end = 1.0f;

    // 匀速的插值器
    private Interpolator interpolator = new LinearInterpolator();
    private PopupWindow mPopupWindow;
    private Context context;
    private PopuSpringAdapter adapter;
    private List<PopuStrBean> mList = new ArrayList<>();
    private TextView springTv;
    private CardView springCard;
    private ImageView springIg;

    private int clickBaColor = 0;
    private int unClickBaColor = 0;
    private int clickTextColor = 0;
    private int unClickTextColor = 0;

    public PopuSpringView(Context context) {
        this(context, null);
    }

    public PopuSpringView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PopuSpringView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        mPopupWindow = new PopupWindow(context);
        View view = (View) View.inflate(context, R.layout.ct_popu_spring_layout, this);
        springTv = ((TextView) view.findViewById(R.id.ct_popu_spring_tv));
        springCard = ((CardView) view.findViewById(R.id.ct_popu_spring_crd));
        springIg = ((ImageView) view.findViewById(R.id.ct_popu_spring_ig));
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.ISpringStyle);
        String tvStr = typedArray.getString(R.styleable.ISpringStyle_spring_text);
        Integer loadIg = typedArray.getInteger(R.styleable.ISpringStyle_spring_image, R.mipmap.load_more_ig);
        springTv.setText(tvStr);
        springIg.setImageResource(loadIg);

        // 设置布局文件
        inflate = LayoutInflater.from(context).inflate(R.layout.ct_popu_spring_list_layout, null);
        lsView = (ListView) inflate.findViewById(R.id.ct_popu_spring_ls);

        springCard.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                showPop(springCard);

            }
        });
    }

    public void setStrText(String str) {
        if (springTv != null && str != null && !"".equals(str)) {
            springTv.setText(str);
        }
    }

    public void setSpringRightIg(int imageId) {
        if (springIg != null && imageId > 0) {
            springIg.setImageResource(imageId);
        }
    }

    public void setListViewBackground(int background) {
        if (lsView != null) {
            lsView.setBackgroundResource(background);
        }
    }

    public void setValueAnimator(float start, float end, long duration) {
        this.start = start;
        this.end = end;
        this.duration = duration;
    }

    public void setInterpolator(Interpolator interpolator) {
        this.interpolator = interpolator;
    }


    public void startAnimator() {
        if (valueAnimator != null) {
            valueAnimator = null;
        }
        valueAnimator = ValueAnimator.ofFloat(start, end);
        valueAnimator.setDuration(duration);
        valueAnimator.setInterpolator(interpolator);
        valueAnimator.start();
    }

    /**
     * 弹出窗口
     *
     * @param view 在view控件下方
     */
    private void showPop(View view) {
        int width = view.getWidth();
        ViewGroup.LayoutParams layoutParams = lsView.getLayoutParams();
        layoutParams.width = width;
        lsView.setLayoutParams(layoutParams);

        mPopupWindow.setContentView(inflate);
        // 为了避免部分机型不显示，我们需要重新设置一下宽高
        mPopupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        // 设置pop透明效果
        mPopupWindow.setBackgroundDrawable(new ColorDrawable(0x0000));
        // 设置pop出入动画
        mPopupWindow.setAnimationStyle(R.style.pop_add);
        // 设置pop获取焦点，如果为false点击返回按钮会退出当前Activity，如果pop中有Editor的话，focusable必须要为true
        mPopupWindow.setFocusable(true);
        // 设置pop可点击，为false点击事件无效，默认为true
        mPopupWindow.setTouchable(true);
        // 设置点击pop外侧消失，默认为false；在focusable为true时点击外侧始终消失
        mPopupWindow.setOutsideTouchable(true);
        // 相对于 + 号正下面，同时可以设置偏移量
        mPopupWindow.showAsDropDown(view, 0, 3);

        if (!openCustFlag) {
            adapter = new PopuSpringAdapter(context, mList);
            lsView.setAdapter(adapter);
            lsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    PopuStrBean popuStrBean = mList.get(position);
                    springTv.setText(popuStrBean.getName());
                    mPopupWindow.dismiss();
                    if (onPopuSpringListener != null) {
                        onPopuSpringListener.OnClickItem(popuStrBean.getId());
                    }
                }
            });
        }
        startAnimator();
    }

    private boolean openCustFlag = false; //判断是否启用自定义的adapter

    /**
     * 使用自定义的adapter 布局样式
     *
     * @param adapter
     * @param onItemClickListener
     */
    public void setAdapter(BaseListAdapter adapter, AdapterView.OnItemClickListener onItemClickListener) {
        openCustFlag = true;
        lsView.setAdapter(adapter);
        lsView.setOnItemClickListener(onItemClickListener);
        adapter.notifyDataSetChanged();
    }

    /**
     * 返回PopuWindow
     *
     * @return
     */
    public PopupWindow getPopupWindow() {
        if (mPopupWindow != null) {
            return mPopupWindow;
        } else {
            return null;
        }
    }

    public void dismiss() {
        if (mPopupWindow != null) {
            mPopupWindow.dismiss();
            mPopupWindow = null;
        }
    }

    /**
     * 添加数据，包含点击回调
     *
     * @param popuStrBeanList
     * @param onPopuSpringListener
     */
    public void setData(List<PopuStrBean> popuStrBeanList, OnPopuSpringListener onPopuSpringListener) {
        if (!openCustFlag) {
            this.onPopuSpringListener = onPopuSpringListener;
            mList.clear();
            mList.addAll(popuStrBeanList);
            if (adapter != null) {
                adapter.notifyDataSetChanged();
            }
        }
    }

    /**
     * 添加数据,仅做展示
     *
     * @param popuStrBeanList
     */
    public void setData(List<PopuStrBean> popuStrBeanList) {
        if (!openCustFlag) {
            mList.clear();
            mList.addAll(popuStrBeanList);
            if (adapter != null) {
                adapter.notifyDataSetChanged();
            }
        }
    }


    public void setSelectTextColor(int clickBaColor, int unClickBaColor, int clickTextColor, int unClickTextColor) {
        this.clickBaColor = clickBaColor;
        this.unClickBaColor = unClickBaColor;
        this.clickTextColor = clickTextColor;
        this.unClickTextColor = unClickTextColor;
    }

    private class PopuSpringAdapter extends BaseListAdapter<PopuStrBean> {

        public PopuSpringAdapter(Context context, List<PopuStrBean> list) {
            super(context, list);
        }

        @Override
        public View bindView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.ct_popu_spring_list_item, null);
            }
            TextView tv = (TextView) convertView.findViewById(R.id.ct_popu_spring_item_tv);
            LinearLayout Ll = (LinearLayout) convertView.findViewById(R.id.ct_popu_spring_list_item_ll);
            PopuStrBean popuStrBean = list.get(position);
            tv.setText(popuStrBean.getName());
            return convertView;
        }

    }
}
