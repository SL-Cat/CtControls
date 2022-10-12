package cat.customize.datepicker.looper;

import android.view.MotionEvent;

/**
 * Created by HSL
 * on 2022/9/28.
 */

public final class LoopViewGestureListener extends android.view.GestureDetector.SimpleOnGestureListener {

    private final CtLoopView wheelView;


    public LoopViewGestureListener(CtLoopView wheelView) {
        this.wheelView = wheelView;
    }

    @Override
    public final boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        wheelView.scrollBy(velocityY);
        return true;
    }
}
