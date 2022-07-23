package cat.customize.view;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.PopupWindow;

import cat.customize.R;

/**
 * Created by HSL
 * on 2022/7/19.
 */

public class PopupWindownView {
    public interface PopupCallBack {
        void onCallBack(PopupWindow mPopupWindow);
    }
    private ValueAnimator valueAnimator;
    private UpdateListener updateListener;
    private EndListener endListener;
    private long duration;
    private float start;
    private float end;
    private Interpolator interpolator = new LinearInterpolator();
    private static final long DURATION = 100;
    private static final float START_ALPHA = 0.7f;
    private static final float END_ALPHA = 1f;
    private boolean bright = false;
    private float bgAlpha = 1f;
    private PopupWindow mPopupWindow;
    private Activity activity;

    public PopupWindownView(Activity context) {
        // 默认动画时常1s
        duration = 200;
        start = 0.0f;
        end = 1.0f;
        // 匀速的插值器
        interpolator = new LinearInterpolator();
        this.activity = context;
        mPopupWindow = new PopupWindow(context);
    }


    public void setDuration(int timeLength) {
        duration = timeLength;
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
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {

                if (updateListener == null) {
                    return;
                }

                float cur = (float) valueAnimator.getAnimatedValue();
                updateListener.progress(cur);
            }
        });
        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                if (endListener == null) {
                    return;
                }
                endListener.endUpdate(animator);
            }

            @Override
            public void onAnimationCancel(Animator animator) {
            }

            @Override
            public void onAnimationRepeat(Animator animator) {
            }
        });
        valueAnimator.start();
    }

    public void addUpdateListener(UpdateListener updateListener) {
        this.updateListener = updateListener;
    }

    public void addEndListner(EndListener endListener) {
        this.endListener = endListener;
    }

    public interface EndListener {
        void endUpdate(Animator animator);
    }

    public interface UpdateListener {
        void progress(float progress);
    }


    /**
     * 弹出窗口
     *
     * @param view   在view控件下方
     * @param x      在view控件下方左右的偏移量
     * @param y      在view控件下方上下的偏移量
     * @param layout 弹出的窗口的layout
     */
    public void showPop(View view, int x, int y, int layout, PopupCallBack popupCallBack) {
        // 设置布局文件
        mPopupWindow.setContentView(LayoutInflater.from(activity).inflate(layout, null));
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
        mPopupWindow.showAsDropDown(view, x, y);
        // 设置pop关闭监听，用于改变背景透明度
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = activity.getWindow()
                        .getAttributes();
                lp.alpha = 1f;
                activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                activity.getWindow().setAttributes(lp);
            }
        });
//        mPopupWindow.setAnimationStyle(R.style.my_pop_animation);
//        mPopupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        toggleBright(START_ALPHA, END_ALPHA);
        popupCallBack.onCallBack(mPopupWindow);
    }

    public PopupWindow getPopupWindow(){
        if(mPopupWindow!=null) {
            return mPopupWindow;
        }else {
            return null;
        }
    }

    /**
     * 弹出窗口
     *
     * @param view   在view控件下方
     * @param layout 弹出的窗口的layout
     */
    public void showPop(View view, int layout, PopupCallBack popupCallBack) {
        showPop(view, -100, 0, layout, popupCallBack);
    }

    private void toggleBright(final float startAlpha, final float endAlpha) {
        // 三个参数分别为：起始值 结束值 时长，那么整个动画回调过来的值就是从0.5f--1f的
        setValueAnimator(startAlpha, endAlpha, DURATION);
        addUpdateListener(new UpdateListener() {
            @Override
            public void progress(float progress) {
                // 此处系统会根据上述三个值，计算每次回调的值是多少，我们根据这个值来改变透明度
                bgAlpha = bright ? progress : (startAlpha + endAlpha - progress);
                backgroundAlpha(bgAlpha);
            }
        });
        addEndListner(new EndListener() {
            @Override
            public void endUpdate(Animator animator) {
                // 在一次动画结束的时候，翻转状态
                bright = !bright;
            }
        });
        startAnimator();
    }

    /**
     * 此方法用于改变背景的透明度，从而达到“变暗”的效果
     */
    private void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        // 0.0-1.0
        lp.alpha = bgAlpha;
        activity.getWindow().setAttributes(lp);
        // everything behind this window will be dimmed.
        // 此方法用来设置浮动层，防止部分手机变暗无效
        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
    }
}
