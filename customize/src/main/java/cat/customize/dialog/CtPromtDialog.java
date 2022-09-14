package cat.customize.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import cat.customize.R;


/**
 * Created by HSL on 2020/12/23.
 * 圆脚弹框
 */

public class CtPromtDialog extends Dialog {

    private TextView dialogTitle;
    private TextView dialogMessage;
    private TextView dialogLeft;
    private TextView dialogRight;
    private Context context;

    //构造方法
    public CtPromtDialog(Context context) {
        //设置对话框样式
        super(context, R.style.ct_RadiusDialog);
        this.context = context;
        //通过LayoutInflater获取布局
        View view = LayoutInflater.from(getContext()).inflate(R.layout.ct_hint_dialog_layout, null);
        dialogTitle = view.findViewById(R.id.dialog_title);
        dialogMessage = view.findViewById(R.id.dialog_message);
        dialogLeft = view.findViewById(R.id.dialog_left_button);
        dialogRight = view.findViewById(R.id.dialog_right_button);
        setContentView(view);  //设置显示的视图
    }

    //设置标题
    public void setTitleText(String title) {
        dialogTitle.setText(title);
    }  //设置标题

    public void setMessageText(String message) {
        if (message!=null) {
            dialogMessage.setText(message);
        } else {
            dialogTitle.setPadding(10, 50, 10, 50);
            dialogMessage.setVisibility(View.GONE);
        }
    }

    public void setButtonLeftText(String confirm, int confirmColor) {
        if (confirm!=null) {
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
        if (cancel!=null) {
            dialogRight.setText(cancel);
            if (cancelColor != -1) {
                dialogRight.setTextColor(cancelColor);
            }
        } else {
            dialogLeft.setTextColor(ContextCompat.getColor(context, R.color.color_007BFF));
            dialogRight.setVisibility(View.GONE);
        }
    }

    //左侧按钮
    public void setOnLeftlListener(View.OnClickListener listener) {
        dialogLeft.setOnClickListener(listener);
    }

    //右侧按钮
    public void setOnRightListener(View.OnClickListener listener) {
        dialogRight.setOnClickListener(listener);
    }
}
