package cat.customize.datepicker.looper;

import android.os.Handler;
import android.os.Message;

/**
 * Created by HSL
 * on 2022/9/28.
 */

public final class MessageHandler extends Handler {
    public static final int WHAT_INVALIDATE_LOOP_VIEW = 1000;
    public static final int WHAT_SMOOTH_SCROLL = 2000;
    public static final int WHAT_ITEM_SELECTED = 3000;

    private final CtLoopView wheelView;

    public MessageHandler(CtLoopView wheelView) {
        this.wheelView = wheelView;
    }

    @Override
    public final void handleMessage(Message msg) {
        switch (msg.what) {
            case WHAT_INVALIDATE_LOOP_VIEW:
                wheelView.invalidate();
                break;

            case WHAT_SMOOTH_SCROLL:
                wheelView.smoothScroll(CtLoopView.ACTION.FLING);
                break;

            case WHAT_ITEM_SELECTED:
                wheelView.onItemSelected();
                break;
        }
    }

}
