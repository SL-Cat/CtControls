package cat.customize.recyler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.ScaleAnimation;
import android.widget.FrameLayout;
import android.widget.TextView;

import cat.customize.R;


/**
 * Created by HSL
 * on 2022/7/16.
 * 长按条目添加遮罩操作帮助类
 * 长按主要是需要recyclerView 的item根布局是Fragment
 */
public class ItemLongClickMaskHelper {

    private ScaleAnimation scaleAnimation;
    private FrameLayout mRootFrameLayout; //列表Item根布局FrameLayout
    private int position = 0;
    private View view;
    private Context context;
    private TextView fristBtn = null;
    private TextView secondBtn = null;
    private TextView threeBtn = null;
    private ItemMaskClickListener mItemMaskClickListener;

    public ItemLongClickMaskHelper(Context context, View view) {
        this.context = context;
        this.view = view;
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismissMaskLayout();
            }
        });
        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                dismissMaskLayout();
                return true;
            }
        });
    }

    public ItemLongClickMaskHelper(Context context) {
        this.context = context;
        view = LayoutInflater.from(context).inflate(R.layout.ct_list_item_mask, null);
        fristBtn = view.findViewById(R.id.default_list_item_mask_tv_delete);
        secondBtn = view.findViewById(R.id.default_list_item_mask_tv_reset);
        threeBtn = view.findViewById(R.id.default_list_item_mask_tv_review);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismissMaskLayout();
            }
        });
        view.setOnLongClickListener(new View.OnLongClickListener() {
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

    public synchronized void setRootFrameLayout(FrameLayout frameLayout, int position) {
        if (mRootFrameLayout != null) {
            mRootFrameLayout.removeView(view);
        }
        mRootFrameLayout = frameLayout;
        mRootFrameLayout.addView(view);
        scaleAnimation = new ScaleAnimation(0.1f, 1.0f, 0.1f, 1.0f);
        scaleAnimation.setDuration(200);
        view.setAnimation(scaleAnimation);
        scaleAnimation.start();
        this.position = position;
//        mAnimSet.start();
    }

    public void dismissMaskLayout() {
        if (mRootFrameLayout != null) {
            mRootFrameLayout.removeView(view);
        }
    }

    private int dip2px(Context context, float dip) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (scale * dip + 0.5f);
    }

    public void setMaskItemListener(ItemMaskClickListener listener) {
        this.mItemMaskClickListener = listener;
    }

    public interface ItemMaskClickListener {
        void fristBtn(int position);

        void secondBtn(int position);

        void threeBtn(int position);
    }

    public void visibilityBtn(boolean falg, int code) {
        if(fristBtn!=null&&secondBtn!=null&&threeBtn!=null) {
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

    public TextView getFristBtn(){
        if(fristBtn!=null){
            return fristBtn;
        }else return null;
    }

    public TextView getSecondBtn(){
        if(secondBtn!=null){
            return secondBtn;
        }else return null;
    }

    public TextView getThreeBtn(){
        if(threeBtn!=null){
            return threeBtn;
        }else return null;
    }
}
