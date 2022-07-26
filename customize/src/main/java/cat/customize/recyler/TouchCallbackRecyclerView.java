package cat.customize.recyler;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.OnScrollListener;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

/**
 * Created by HSL
 * on 2022/7/16.
 */

public class TouchCallbackRecyclerView extends RecyclerView {

    //用来标记是否正在向上滑动
    private boolean isSlidingUpward = false;

    public TouchCallbackRecyclerView(Context context) {
        this(context,null);
    }

    public TouchCallbackRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public TouchCallbackRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.addOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if(!isLoad) {
                    // 当不滑动时
                    if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                        //获取最后一个完全显示的itemPosition
                        int lastItemPosition = manager.findLastCompletelyVisibleItemPosition();
                        int itemCount = manager.getItemCount();

                        // 判断是否滑动到了最后一个item，并且是向上滑动
                        if (lastItemPosition == (itemCount - 1) && isSlidingUpward) {
                            //加载更多
                            if (mOnLoadMoreCallback != null) {
                                mOnLoadMoreCallback.onLoadListernr();
                            }
                        }
                    }
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                // 大于0表示正在向上滑动，小于等于0表示停止或向下滑动
                isSlidingUpward = dy > 0;

            }
        });
    }


    public interface ScrollCallback {
        /**
         * 滑动手指抬起事件
         *
         * @param diffY 抬起时相对于按下时的偏移量<br/>大于0：列表往下拉, 小于0： 列表往上拉
         */
        void onTouchUp(float diffY);
    }

    public interface OnLoadMoreCallback {
        void onLoadListernr();
    }

    private ScrollCallback mScrollCallback;

    /**
     * 加载更多回调
     */
    private OnLoadMoreCallback mOnLoadMoreCallback;

    public void setScrollCallback(ScrollCallback callback) {
        this.mScrollCallback = callback;
    }

    public void setOnLoadMoreCallback(OnLoadMoreCallback loadMoreCallback) {
        this.mOnLoadMoreCallback = loadMoreCallback;
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


    private boolean isLoad = false;

    public void isLoadStatus(boolean loadStatus){
        isLoad = loadStatus;
    }

}
