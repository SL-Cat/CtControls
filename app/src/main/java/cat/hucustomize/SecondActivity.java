package cat.hucustomize;


import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cat.customize.animation.Ratate3DAnimation;
import cat.customize.datepicker.DatePickerView;
import cat.customize.datepicker.calendar.CtCalendarFragment;
import cat.customize.datepicker.calendar.CtCalendarPagerFragment;
import cat.customize.datepicker.calendar.data.CalendarDate;
import cat.customize.dialog.CtPromtDialog;
import cat.customize.dialog.CtLoadDialog;
import cat.customize.dialog.CtTipsDialog;
import cat.customize.dialog.CtToastDialog;
import cat.customize.iview.CtRadioEdit;
import cat.customize.radio.IRadiosButton;
import cat.customize.ulite.system.AndroidUtils;

public class SecondActivity extends AppCompatActivity implements CtCalendarPagerFragment.OnPageChangeListener,
        CtCalendarFragment.OnDateClickListener, CtCalendarFragment.OnDateCancelListener {

    private boolean isChoiceModelSingle = true;
    boolean isShow = false;
    private Ratate3DAnimation openAnimation;
    private long duration = 1000;
    private ImageView ig;
    private Ratate3DAnimation rotate3dAnimation;
    private CtPromtDialog hintDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        radioEd();
        hintDialog();
        load();
        animation();
        toastDialog();
        radios();
        loopView();
        calendarView();
    }

    private void radioEd() {
        CtRadioEdit radioEdit = (CtRadioEdit) findViewById(R.id.ct_radio_ed);
        radioEdit.setOnCtRadioEditListener(new CtRadioEdit.CtOnRadioEditListener() {
            @Override
            public void onLeftIgClickListener() {
            }

            @Override
            public void onRightIgClickListener() {
            }

            @Override
            public void onConfirmClickListener(String edMsg) {
            }

            @Override
            public void onEditTextChanged(String str) {
            }
        });
    }

    private void hintDialog() {
        findViewById(R.id.ct_second_hint_dialog_first).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CtPromtDialog hintDialog = new CtPromtDialog(SecondActivity.this);
                hintDialog.setButtonLeftText("确认", -1);
                hintDialog.setButtonRightText("leftBtn", -1);
                hintDialog.setCancelable(false);
                hintDialog.setMsgTextSize(50);
                hintDialog.setTitleTextSize(50);
                hintDialog.setBigByScreenWidth(0.6f);
                hintDialog.setDialogAttributes(0.5f);
                //右侧
                hintDialog.setOnRightListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //如果对话框处于显示状态
                        if (hintDialog.isShowing()) {
                            hintDialog.dismiss();//关闭对话框
                        }
                    }
                });
                //左侧
                hintDialog.setOnLeftlListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AndroidUtils.MainHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if (hintDialog != null && hintDialog.isShowing()) {
                                    hintDialog.dismiss();
                                }
                            }
                        }, 500);
                    }
                });
                hintDialog.show();
            }
        });

        findViewById(R.id.ct_second_hint_dialog_second).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CtTipsDialog dateDialog = new CtTipsDialog(SecondActivity.this);
                dateDialog.setTitle("标题");
                dateDialog.setMsgTv("内容");
                dateDialog.show();
                dateDialog.setOnComfrimListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dateDialog.dismiss();
                    }
                });
            }
        });
    }

    public interface OnLister {
        void ok();

        void canle();
    }


    private void showDialog(OnLister onLister) {
        showDialog(null, "确认", "提示", -1, null, onLister);
    }

    private void showDialog(String title, String msg, OnLister onLister) {
        showDialog(msg, "确认", title, -1, null, onLister);
    }

    private void showDialog(String msg, OnLister onLister) {
        showDialog(msg, "确认", null, -1, null, onLister);
    }

    private void showDialog(final int time, final OnLister onLister) {
        showDialog(null, "确认", "秒后关闭", time, null, onLister);
    }

    private void showDialog(String msg, String conm, String title, int timeDismiss, String canle, final OnLister onLister) {
        if (hintDialog == null) {
            hintDialog = new CtPromtDialog(SecondActivity.this);
        }
        hintDialog.setButtonLeftText(conm, -1);
        hintDialog.setButtonRightText(canle, -1);
        hintDialog.setMessageText(msg);
        hintDialog.setTitleGravity(Gravity.LEFT);
        hintDialog.setTitleText(title);
        hintDialog.setCancelable(false);
        hintDialog.setDismissTime(timeDismiss, 1, new CtPromtDialog.OnTimerDismissListener() {
            @Override
            public void onTimerDismiss() {
                if (onLister != null) {
                    onLister.canle();
                }
            }
        });
        //右侧
        hintDialog.setOnRightListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //如果对话框处于显示状态
                if (hintDialog.isShowing()) {
                    hintDialog.dismiss();//关闭对话框
                }
                if (onLister != null) {
                    onLister.ok();
                }
            }
        });
        //左侧
        hintDialog.setOnLeftlListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hintDialog.isShowing()) {
                    hintDialog.dismiss();
                }
                if (onLister != null) {
                    onLister.canle();
                }
            }
        });
        hintDialog.show();
    }

    private int count = 0;

    private void load() {
        TextView loadTv = (TextView) findViewById(R.id.ct_second_load_dialog);
        loadTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CtLoadDialog loadDialog = new CtLoadDialog(SecondActivity.this);
                loadDialog.show();
                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        while (count < 10) {
                            count++;
                            SystemClock.sleep(1000);
                            Log.d("myDemo", "run: ");
                        }
                        loadDialog.dismiss();
                    }
                }).start();
            }
        });
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
        DatePickerView loopView = (DatePickerView) findViewById(R.id.ct_second_loop);
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add("ITEM " + i);
        }
        loopView.setData(list);
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
        Log.d("myDemo", "onDateClick: " + calendarDate.getSolar() + "-" + calendarDate.getLunar());
    }

    @Override
    public void onDateCancel(CalendarDate calendarDate) {
        Log.d("myDemo", "onDateCancel: " + calendarDate.getSolar() + "-" + calendarDate.getLunar());
    }

    @Override
    public void onPageChange(int year, int month) {
        Log.d("myDemo", "onPageChange: " + year + "-" + month);
    }
}