package cat.customize.recyler;

import android.content.Context;
import android.view.View;
import android.view.animation.ScaleAnimation;
import android.widget.FrameLayout;
import android.widget.TextView;

import cat.customize.R;


/**
 * Created by HSL
 * on 2022/7/16.
 * 长按条目添加遮罩操作帮助类
 */
public class ItemLongClickMaskHelper {

    private ScaleAnimation scaleAnimation;
    private FrameLayout mRootFrameLayout; //列表Item根布局FrameLayout
    private ItemMaskLayout mMaskItemLayout;
    private ItemMaskClickListener mItemMaskClickListener;
    private final TextView fristBtn;
    private final TextView secondBtn;
    private final TextView threeBtn;

    public ItemLongClickMaskHelper(Context context) {
        mMaskItemLayout = new ItemMaskLayout(context);
        fristBtn = mMaskItemLayout.findViewById(R.id.default_list_item_mask_tv_one);
        secondBtn = mMaskItemLayout.findViewById(R.id.default_list_item_mask_tv_second);
        threeBtn = mMaskItemLayout.findViewById(R.id.default_list_item_mask_tv_three);

        mMaskItemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismissMaskLayout();
            }
        });

        mMaskItemLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                dismissMaskLayout();
                return true;
            }
        });

        fristBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mItemMaskClickListener != null) {
                    dismissMaskLayout();
                    mItemMaskClickListener.fristBtn();
                }
            }
        });

        secondBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mItemMaskClickListener != null) {
                    dismissMaskLayout();
                    mItemMaskClickListener.secondBtn();
                }
            }
        });

        threeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mItemMaskClickListener != null) {
                    dismissMaskLayout();
                    mItemMaskClickListener.threeBtn();
                }
            }
        });
    }

    public synchronized void setRootFrameLayout(FrameLayout frameLayout) {
        if (mRootFrameLayout != null) {
            mRootFrameLayout.removeView(mMaskItemLayout);
        }
        mRootFrameLayout = frameLayout;
        mRootFrameLayout.addView(mMaskItemLayout);
        scaleAnimation = new ScaleAnimation(0.1f, 1.0f, 0.1f, 1.0f);
        scaleAnimation.setDuration(200);
        mMaskItemLayout.setAnimation(scaleAnimation);
        scaleAnimation.start();
//        mAnimSet.start();
    }

    public void dismissMaskLayout() {
        if (mRootFrameLayout != null) {
            mRootFrameLayout.removeView(mMaskItemLayout);
        }
    }

    public void setMaskItemListener(ItemMaskClickListener listener) {
        this.mItemMaskClickListener = listener;
    }

    public interface ItemMaskClickListener {
        void fristBtn();

        void secondBtn();

        void threeBtn();
    }

    private int dip2px(Context context, float dip) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (scale * dip + 0.5f);
    }

    public void visibilityBtn(boolean falg, int code) {
        switch (code) {
            case 0:
                fristBtn.setVisibility(falg ? View.VISIBLE : View.GONE);
                break;
            case 1:
                secondBtn.setVisibility(falg ? View.VISIBLE : View.GONE);
                break;
            case 2:
                threeBtn.setVisibility(falg ? View.VISIBLE : View.GONE);
                break;
        }
    }

}
