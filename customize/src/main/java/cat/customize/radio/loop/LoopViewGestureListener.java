package cat.customize.radio.loop;

import android.view.MotionEvent;

/**
 * Created by HSL
 * on 2022/8/31.
 */

final class LoopViewGestureListener extends android.view.GestureDetector.SimpleOnGestureListener {

    final ILoopView loopView;

    LoopViewGestureListener(ILoopView loopview) {
        loopView = loopview;
    }

    @Override
    public final boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        loopView.scrollBy(velocityY);
        return true;
    }
}
