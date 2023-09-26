package cat.customize.dialog;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

import cat.customize.R;
import cat.customize.ulite.system.AndroidUtils;


/**
 * Created by HSL on 2020/12/23.
 * 圆脚弹框
 */

public class CtPromtDialog extends BaseDialog {

    private TextView dialogTitle;
    private TextView dialogMessage;
    private TextView dialogLeft;
    private TextView dialogRight;
    private Context context;
    private int dismissTime = -1;
    private Timer timer;
    private OnTimerDismissListener onTimerDismissListener;

    public interface OnTimerDismissListener {
        void onTimerDismiss();
    }

    //构造方法
    public CtPromtDialog(Context context) {
        //设置对话框样式
        super(context, R.style.ct_RadiusDialog);
        this.context = context;
        //通过LayoutInflater获取布局
        View view = LayoutInflater.from(getContext()).inflate(R.layout.ct_hint_dialog_layout, null);
        dialogTitle = view.findViewById(R.id.dialog_title);
        dialogMessage = view.findViewById(R.id.dialog_message);
        dialogLeft = view.findViewById(R.id.ic_dialog_left_button);
        dialogRight = view.findViewById(R.id.ic_dialog_right_button);
        setContentView(view);  //设置显示的视图
        setBigByScreenWidth(0.8f);
        setDialogAttributes(0.2f);
    }

    /**
     * 设置主阴影深度
     * @param attributes
     */
    public void setDialogAttributes(float attributes){
        //设置显示弹窗背景不变暗
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        //调整明暗度，float值，完全透明不变暗是0.0f，完全变暗不透明是1.0f
        lp.dimAmount = attributes;
        //必须要设置回去
        getWindow().setAttributes(lp);
        //根据谷歌文档，给对应的Window添加FLAG_DIM_BEHIND标志位，dimAmount值才有效。
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
    }

    //设置标题
    public void setTitleText(String title) {
        if (title != null) {
            this.timerTitle = title;
            if (dismissTime >= 0 && type == 1) {
                dialogTitle.setText(dismissTime + title);
            } else {
                dialogTitle.setText(title);
            }
        } else {
            dialogMessage.setPadding(10, 50, 10, 50);
            dialogTitle.setVisibility(View.GONE);
        }
    }

    //设置内容
    public void setMessageText(String message) {
        if (message != null) {
            this.timerMsg = message;
            if (dismissTime >= 0 && type == 2) {
                dialogMessage.setText(dismissTime + message);
            } else {
                dialogMessage.setText(message);
            }
        } else {
            dialogTitle.setPadding(10, 50, 10, 50);
            dialogMessage.setVisibility(View.GONE);
        }
    }

    public void setTitleGravity(int gravity){
        dialogTitle.setGravity(gravity);
    }

    private String timerTitle = null;
    private String timerMsg = null;
    private int type = 0;

    /**
     * 设置一个定时关闭
     *
     * @param time                   时间
     * @param type                   1:2  时间显示位置;
     *                              1：标题；2：内容
     * @param onTimerDismissListener 到时后关闭触发
     */
    public void setDismissTime(int time, int type, OnTimerDismissListener onTimerDismissListener) {
        this.dismissTime = time;
        this.type = type;
        this.onTimerDismissListener = onTimerDismissListener;
    }

    public void setButtonLeftText(String confirm, int confirmColor) {
        if (confirm != null) {
            dialogLeft.setText(confirm);
            if (confirmColor != -1) {
                dialogLeft.setTextColor(confirmColor);
            }
        } else {
            dialogRight.setTextColor(ContextCompat.getColor(context, R.color.color_007BFF));
            dialogLeft.setVisibility(View.GONE);
        }
    }

    @SuppressLint("NewApi")
    public void setButtonRightText(String cancel, int cancelColor) {
        if (cancel != null) {
            dialogRight.setText(cancel);
            if (cancelColor != -1) {
                dialogRight.setTextColor(cancelColor);
            }
        } else {
            dialogLeft.setTextColor(ContextCompat.getColor(context, R.color.color_007BFF));
            dialogRight.setVisibility(View.GONE);
        }
    }

    public void setMsgTextSize(float size){
        dialogMessage.setTextSize(size);
    }

    public void setTitleTextSize(float size){
        dialogTitle.setTextSize(size);
    }

    public void setLeftTextSize(float size){
        dialogLeft.setTextSize(size);
    }

    public void setRightTextSize(float size){
        dialogRight.setTextSize(size);
    }

    public TextView getTitleView(){
        return dialogTitle;
    }
    public TextView getMessageView(){
        return dialogMessage;
    }
    public TextView getRightView(){
        return dialogRight;
    }
    public TextView getLeftView(){
        return dialogLeft;
    }

    //左侧按钮
    public void setOnLeftlListener(View.OnClickListener listener) {
        dialogLeft.setOnClickListener(listener);
    }

    //右侧按钮
    public void setOnRightListener(View.OnClickListener listener) {
        dialogRight.setOnClickListener(listener);
    }

    @Override
    public void show() {
        super.show();
        timerCount();
    }

    /**
     * 开启了定时关闭
     */
    private void timerCount() {
        if (dismissTime > 0) {
            if (timer == null) {
                timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        if (dismissTime > 0) {
                            dismissTime--;
                            AndroidUtils.MainHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    setTitleText(timerTitle);
                                    setMessageText(timerMsg);
                                }
                            });
                        } else {
                            AndroidUtils.MainHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    if (isShowing()) {
                                        onTimerDismissListener.onTimerDismiss();
                                        dismiss();
                                    }
                                }
                            });
                        }
                    }
                }, 0, 1000);
            }
        }
    }

    @Override
    public void dismiss() {
        dismissTime = 0;
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        super.dismiss();
    }
}
