package cat.hucustomize;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cat.customize.animation.Ratate3DAnimation;
import cat.customize.datepicker.calendar.CtCalendarFragment;
import cat.customize.datepicker.calendar.CtCalendarPagerFragment;
import cat.customize.datepicker.calendar.data.CalendarDate;
import cat.customize.dialog.CtToastDialog;
import cat.customize.radio.IRadiosButton;
import cat.customize.radio.loop.ILoopView;

public class SecondActivity extends AppCompatActivity implements CtCalendarPagerFragment.OnPageChangeListener, CtCalendarFragment.OnDateClickListener, CtCalendarFragment.OnDateCancelListener {

    private boolean isChoiceModelSingle = false;
    boolean isShow = false;
    private Ratate3DAnimation openAnimation;
    private long duration = 1000;
    private ImageView ig;
    private Ratate3DAnimation rotate3dAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        load();
        animation();
        toastDialog();
        radios();
        loopView();
        calendarView();
    }

    private void load() {
        TextView loadTv = (TextView) findViewById(R.id.ct_second_load_dialog);
    }

    private void animation() {
        ig = (ImageView) findViewById(R.id.ct_an_ig);
        ig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ig.startAnimation(openAnimation);
            }
        });
        initOpenAnimation();
    }

    private void initOpenAnimation() {
        rotate3dAnimation = new Ratate3DAnimation(180, 360, false);
        rotate3dAnimation.setDuration(duration);
        rotate3dAnimation.setFillAfter(true);
        rotate3dAnimation.setInterpolator(new AccelerateInterpolator());

        openAnimation = new Ratate3DAnimation(0, 180, true);
        openAnimation.setDuration(duration);
        openAnimation.setFillAfter(true);
        openAnimation.setInterpolator(new AccelerateInterpolator());
        openAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            //翻转结束时
            @Override
            public void onAnimationEnd(Animation animation) {
                ig.startAnimation(rotate3dAnimation);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    private void toastDialog() {
        findViewById(R.id.ct_second_toast_dialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CtToastDialog ctToastDialog = new CtToastDialog(SecondActivity.this);
                ctToastDialog.setTitleBackground(R.mipmap.ct_refesh_ig);
                ctToastDialog.setInfoText("info");
                ctToastDialog.setConnectText("connect");
                ctToastDialog.show();
                //设置显示弹窗背景不变暗
                WindowManager.LayoutParams lp = ctToastDialog.getWindow().getAttributes();
                //调整明暗度，float值，完全透明不变暗是0.0f，完全变暗不透明是1.0f
                lp.dimAmount = 0.1f;
                //必须要设置回去
                ctToastDialog.getWindow().setAttributes(lp);
                //根据谷歌文档，给对应的Window添加FLAG_DIM_BEHIND标志位，dimAmount值才有效。
                ctToastDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            }
        });
    }

    private void calendarView() {
        findViewById(R.id.ct_second_time).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isShow = !isShow;
                if (isShow) {
                    findViewById(R.id.fl_content).setVisibility(View.VISIBLE);
                    FragmentManager fm = getSupportFragmentManager();
                    FragmentTransaction tx = fm.beginTransaction();
                    // Fragment fragment = new CalendarViewPagerFragment();
                    Fragment fragments = CtCalendarPagerFragment.newInstance(isChoiceModelSingle);
//        Fragment fragment = CalendarViewPagerFragment.newInstance(isChoiceModelSingle);
                    tx.replace(R.id.fl_content, fragments);
                    tx.commit();
                } else {
                    findViewById(R.id.fl_content).setVisibility(View.GONE);
                }
            }
        });
    }


    private void loopView() {
        ILoopView loopView = (ILoopView) findViewById(R.id.ct_second_loop);
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add("ITEM " + i);
        }
        loopView.setItems(list);
    }

    private void radios() {
        IRadiosButton radiosButton = (IRadiosButton) findViewById(R.id.ct_second_radios);
        List<String> list = new ArrayList<>();
        list.add("新的1");
        list.add("新的2");
        list.add("新的3");
        radiosButton.setButtonLits(list);
        radiosButton.setOnIRadiosItemClick(new IRadiosButton.OnIRadiosListener() {
            @Override
            public void onRadiosItemClick(int position, boolean isClick) {
                ToastUlit.Toast(SecondActivity.this, position + "");
            }
        });
    }

    @Override
    public void onDateClick(CalendarDate calendarDate) {
        ToastUlit.Toast(SecondActivity.this, calendarDate.getSolar() + "-" + calendarDate.getLunar());
    }

    @Override
    public void onDateCancel(CalendarDate calendarDate) {
        ToastUlit.Toast(SecondActivity.this, calendarDate.getSolar() + "-" + calendarDate.getLunar());
    }

    @Override
    public void onPageChange(int year, int month) {
        ToastUlit.Toast(SecondActivity.this, year + "-" + month);
    }
}