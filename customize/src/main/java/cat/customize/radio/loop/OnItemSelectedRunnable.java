package cat.customize.radio.loop;

/**
 * Created by HSL
 * on 2022/8/31.
 */

final class OnItemSelectedRunnable implements Runnable {
    final ILoopView loopView;

    OnItemSelectedRunnable(ILoopView loopview) {
        loopView = loopview;
    }

    @Override
    public final void run() {
        loopView.onItemSelectedListener.onItemSelected(loopView.getSelectedItem());
    }
}