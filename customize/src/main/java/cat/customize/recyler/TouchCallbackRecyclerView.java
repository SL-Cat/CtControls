package cat.customize.recyler;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

/**
 * Created by HSL
 * on 2022/7/16.
 */

public class TouchCallbackRecyclerView extends RecyclerView {

    public TouchCallbackRecyclerView(Context context) {
        super(context);
    }

    public TouchCallbackRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TouchCallbackRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public interface ScrollCallback {
        /**
         * 滑动手指抬起事件
         *
         * @param diffY 抬起时相对于按下时的偏移量<br/>大于0：列表往下拉, 小于0： 列表往上拉
         */
        void onTouchUp(float diffY);
    }

    private ScrollCallback mScrollCallback;

    public void setScrollCallback(ScrollCallback callback) {
        this.mScrollCallback = callback;
    }

    private float mDownY, mMovingY, mUpY;
    private boolean isUp = false;

    @SuppressWarnings("deprecation")
    private static final float SLOP = ViewConfiguration.getTouchSlop();

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDownY = ev.getY();
                isUp = false;
                break;
            case MotionEvent.ACTION_MOVE:
                mMovingY = ev.getY();
                isUp = false;
                break;
            case MotionEvent.ACTION_UP:
                mUpY = ev.getY();
                isUp = true;
                break;
        }
        if (isUp && mScrollCallback != null && Math.abs(mUpY - mDownY) > SLOP) {
            mScrollCallback.onTouchUp(mMovingY - mDownY);
        }
        return super.dispatchTouchEvent(ev);
    }
}
