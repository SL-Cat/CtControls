package cat.hucustomize;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.TextView;

import java.security.PublicKey;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import cat.customize.fragment.CreateFragUlite;
import cat.customize.listener.OnCtSeekBarListener;
import cat.customize.ulite.system.AndroidUtils;
import cat.customize.view.CtRadioProgressView;
import cat.customize.view.CtSeekBar;
import cat.hucustomize.frg.WaveRfidFragment;

public class ThreeActivity extends AppCompatActivity {

    public static CreateFragUlite createHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_three);
        playBtn();
        createHelper = CreateFragUlite.getInstance(this, R.id.test_frag);
        createHelper.addFragment(new MainFragment());
        ((CtSeekBar) findViewById(R.id.sek)).setOnCtSeekBarListener(new OnCtSeekBarListener() {
            @Override
            public void onProgressChanged(float progress) {
                ((TextView) findViewById(R.id.tv)).setText(progress + "-" + (int) +progress);
            }
        });
    }

    private int num = 0;

    private void playBtn() {
        final CtRadioProgressView buttonView = (CtRadioProgressView) findViewById(R.id.ct_play_btn);
        buttonView.setMaxProgress(1000);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                AndroidUtils.MainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        buttonView.setProgress(num += 10);
                    }
                });
            }
        }, 200, 200);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (createHelper.fragmentList.size() > 0) {
            Fragment fragment = createHelper.fragmentList.get(0);
            fragment.getActivity().onBackPressed();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}