package cat.customize.xlist;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ScrollView;

import java.util.Timer;
import java.util.TimerTask;

import cat.customize.ulite.system.AndroidUtils;

/**
 * Created by HKR on 2018/3/21.
 * 监听ScrollView滚动到顶部或者底部做相关事件拦截
 */

public class SmartScrollView extends ScrollView {
    private boolean isScrolledToTop = true;             // 初始化的时候设置一下值
    private boolean isScrolledToBottom = false;
    private Timer timer;

    public SmartScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private ISmartScrollChangedListener mSmartScrollChangedListener;

    /**
     * 定义监听接口
     */
    public interface ISmartScrollChangedListener {
        void onScrolledToButtom();

        void onScrolledToTop();

        void onScrolledToMedi();
    }

    public void setScanScrollChangedListener(ISmartScrollChangedListener smartScrollChangedListener) {
        mSmartScrollChangedListener = smartScrollChangedListener;
    }

    @Override
    protected void onOverScrolled(int scrollX, int scrollY, boolean clampedX, boolean clampedY) {
        super.onOverScrolled(scrollX, scrollY, clampedX, clampedY);
        if (scrollY == 0) {
            isScrolledToTop = clampedY;
            isScrolledToBottom = false;
        } else {
            isScrolledToTop = false;
            isScrolledToBottom = clampedY;
        }
        notifyScrollChangedListeners();
    }


    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (isFlag) {
            y = t;
        }
        if (getScrollY() == 0) {    // 小心踩坑1: 这里不能是getScrollY() <= 0
            isScrolledToTop = true;
            isScrolledToBottom = false;
        } else if (getScrollY() + getHeight() - getPaddingTop() - getPaddingBottom() == getChildAt(0).getHeight()) {
            // 小心踩坑2: 这里不能是 >=
            // 小心踩坑3（可能忽视的细节2）：这里最容易忽视的就是ScrollView上下的padding　
            isScrolledToBottom = true;
            isScrolledToTop = false;
        } else {
            isScrolledToTop = false;
            isScrolledToBottom = false;
        }
        notifyScrollChangedListeners();
        // 有时候写代码习惯了，为了兼容一些边界奇葩情况，上面的代码就会写成<=,>=的情况，结果就出bug了
        // 我写的时候写成这样：getScrollY() + getHeight() >= getChildAt(0).getHeight()
        // 结果发现快滑动到底部但是还没到时，会发现上面的条件成立了，导致判断错误
        // 原因：getScrollY()值不是绝对靠谱的，它会超过边界值，但是它自己会恢复正确，导致上面的计算条件不成立
        // 仔细想想也感觉想得通，系统的ScrollView在处理滚动的时候动态计算那个scrollY的时候也会出现超过边界再修正的情况
    }

    private void notifyScrollChangedListeners() {
        mSmartScrollChangedListener.onScrolledToMedi();
        if (isScrolledToTop) {
            if (mSmartScrollChangedListener != null) {
                mSmartScrollChangedListener.onScrolledToTop();
            }
        } else if (isScrolledToBottom) {
            if (mSmartScrollChangedListener != null) {
                mSmartScrollChangedListener.onScrolledToButtom();
            }
        }
    }

    boolean isFlag = false;

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                isFlag = true;
                y = (int) ev.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                int moveY = (int) ev.getRawY();
                y = moveY;
            case MotionEvent.ACTION_UP:
                isFlag = false;
                break;
        }
        return super.onTouchEvent(ev);
    }


    private int y = 0;

    public void start() {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                AndroidUtils.MainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (!isScrolledToBottom && !isFlag) {
                            y++;
                            scrollTo(0, y);
                        }
                    }
                });
            }
        }, 50, 50);
    }

    public void stop() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    public boolean isScrolledToTop() {
        return isScrolledToTop;
    }

    public boolean isScrolledToBottom() {
        return isScrolledToBottom;
    }
}
