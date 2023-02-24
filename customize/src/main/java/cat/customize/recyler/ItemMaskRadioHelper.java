package cat.customize.recyler;

import android.content.Context;
import android.view.animation.ScaleAnimation;
import android.widget.FrameLayout;

import java.util.List;

import cat.customize.bean.MaskItemBean;
import cat.customize.listener.OnCtItemMaskHelperClickListener;
import cat.customize.listener.OnCtRadioTvItemListener;

/**
 * Created by HSL
 * on 2023/2/21.
 */

public class ItemMaskRadioHelper {

    private Context context;

    private ScaleAnimation scaleAnimation;
    private FrameLayout mRootFrameLayout; //列表Item根布局FrameLayout
    private int position = 0;
    private final ItemMaskMoreView maskMoreView;

    private OnCtItemMaskHelperClickListener onCtItemMaskHelperClickListener;

    public ItemMaskRadioHelper(Context context) {
        this.context = context;
        maskMoreView = new ItemMaskMoreView(context);
        maskMoreView.setOnCtRadioTvItemListener(new OnCtRadioTvItemListener() {
            @Override
            public void itemClick(int index) {
                if (onCtItemMaskHelperClickListener != null) {
                    onCtItemMaskHelperClickListener.onItemClick(position,index);
                }
            }
        });
    }

    public synchronized void setRootFrameLayout(FrameLayout frameLayout, int position) {
        if (mRootFrameLayout != null) {
            mRootFrameLayout.removeView(maskMoreView);
        }
        mRootFrameLayout = frameLayout;
        mRootFrameLayout.addView(maskMoreView);
        scaleAnimation = new ScaleAnimation(0.1f, 1.0f, 0.1f, 1.0f);
        scaleAnimation.setDuration(200);
        maskMoreView.setAnimation(scaleAnimation);
        scaleAnimation.start();
        this.position = position;
    }

    public synchronized void setRootFrameLayout(FrameLayout frameLayout, int position, ScaleAnimation animation) {
        if (mRootFrameLayout != null) {
            mRootFrameLayout.removeView(maskMoreView);
        }
        mRootFrameLayout = frameLayout;
        mRootFrameLayout.addView(maskMoreView);
        maskMoreView.setAnimation(animation);
        animation.start();
        this.position = position;
    }

    public void dismissMaskLayout() {
        if (mRootFrameLayout != null) {
            mRootFrameLayout.removeView(maskMoreView);
        }
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
        if (maskMoreView != null) {
            maskMoreView.setMargin(left, right, top, bottom);
        }
    }

    /**
     * 添加数据源
     *
     * @param list
     */
    public void setItems(List<MaskItemBean> list) {
        if (maskMoreView != null)
            maskMoreView.addItem(list);
    }

    public void setOnCtItemMaskHelperClickListener(OnCtItemMaskHelperClickListener onCtItemMaskHelperClickListener) {
        this.onCtItemMaskHelperClickListener = onCtItemMaskHelperClickListener;
    }

    public void setMaskBackgroundRes(int backgroundRes) {
        if (maskMoreView != null) {
            maskMoreView.setMaskBackgroundRes(backgroundRes);
        }
    }
}
