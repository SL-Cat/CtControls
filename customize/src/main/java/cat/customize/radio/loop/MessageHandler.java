package cat.customize.radio.loop;

import android.os.Handler;
import android.os.Message;

/**
 * Created by HSL
 * on 2022/8/31.
 */


final class MessageHandler extends Handler {
    public static final int WHAT_INVALIDATE_LOOP_VIEW = 1000;
    public static final int WHAT_SMOOTH_SCROLL = 2000;
    public static final int WHAT_SMOOTH_SCROLL_INERTIA = 2001;
    public static final int WHAT_ITEM_SELECTED = 3000;

    final ILoopView loopview;

    MessageHandler(ILoopView loopview) {
        this.loopview = loopview;
    }

    @Override
    public final void handleMessage(Message msg) {
        switch (msg.what) {
            case WHAT_INVALIDATE_LOOP_VIEW:
                loopview.invalidate();
                break;

            case WHAT_SMOOTH_SCROLL:
                removeMessages(WHAT_SMOOTH_SCROLL_INERTIA);
                loopview.smoothScroll(ILoopView.ACTION.FLING);
                break;

            case WHAT_ITEM_SELECTED:
                loopview.onItemSelected();
                break;
        }
    }

}
