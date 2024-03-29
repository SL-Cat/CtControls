package cat.customize.binner.control;


import android.content.Context;
import android.view.animation.Interpolator;
import android.widget.Scroller;

/**
 * Created by HSL
 * on 2023/3/2.
 * Description: Scroller 来控制切换的速度.
 * 注：ViewPager默认是Scroller来控制，但是切换速度有点快
 */

public class ViewPagerScroller extends Scroller {
    // 切换速度
    private int mDuration = 800;
    private boolean mIsUseDefaultDuration = false;

    public ViewPagerScroller(Context context) {
        super(context);
    }

    public ViewPagerScroller(Context context, Interpolator interpolator) {
        super(context, interpolator);
    }

    public ViewPagerScroller(Context context, Interpolator interpolator, boolean flywheel) {
        super(context, interpolator, flywheel);
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy) {
        super.startScroll(startX, startY, dx, dy,mDuration);
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy, int duration) {
        super.startScroll(startX, startY, dx, dy, mIsUseDefaultDuration?duration:mDuration);
    }

    public void setUseDefaultDuration(boolean useDefaultDuration) {
        mIsUseDefaultDuration = useDefaultDuration;
    }

    public boolean isUseDefaultDuration() {
        return mIsUseDefaultDuration;
    }

    public void setDuration(int duration) {
        mDuration = duration;
    }


    public int getScrollDuration() {
        return mDuration;
    }
}
