package cat.hucustomize;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;

import cat.customize.fragment.CreateFragUlite;
import cat.customize.ulite.system.AndroidUtils;
import cat.customize.view.CtRadioProgressView;
import cat.hucustomize.frg.WaveRfidFragment;

public class ThreeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_three);
        playBtn();
        CreateFragUlite createHelper = CreateFragUlite.getInstance(this);
        createHelper.addFragment(R.id.test_frag, new WaveRfidFragment());
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
}