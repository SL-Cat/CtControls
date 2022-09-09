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
    private  TextView fristBtn = null;
    private  TextView secondBtn = null;
    private  TextView threeBtn = null;
    private int position = 0;


    public ItemLongClickMaskHelper(Context context) {
        mMaskItemLayout = new ItemMaskLayout(context);
        fristBtn = mMaskItemLayout.findViewById(R.id.default_list_item_mask_tv_delete);
        secondBtn = mMaskItemLayout.findViewById(R.id.default_list_item_mask_tv_reset);
        threeBtn = mMaskItemLayout.findViewById(R.id.default_list_item_mask_tv_review);

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
                    mItemMaskClickListener.fristBtn(position);
                }
            }
        });

        secondBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mItemMaskClickListener != null) {
                    dismissMaskLayout();
                    mItemMaskClickListener.secondBtn(position);
                }
            }
        });

        threeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mItemMaskClickListener != null) {
                    dismissMaskLayout();
                    mItemMaskClickListener.threeBtn(position);
                }
            }
        });
    }

    public synchronized void setRootFrameLayout(FrameLayout frameLayout,int position) {
        if (mRootFrameLayout != null) {
            mRootFrameLayout.removeView(mMaskItemLayout);
        }
        mRootFrameLayout = frameLayout;
        mRootFrameLayout.addView(mMaskItemLayout);
        scaleAnimation = new ScaleAnimation(0.1f, 1.0f, 0.1f, 1.0f);
        scaleAnimation.setDuration(200);
        mMaskItemLayout.setAnimation(scaleAnimation);
        scaleAnimation.start();
        this.position = position;
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
        void fristBtn(int position);

        void secondBtn(int position);

        void threeBtn(int position);
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
