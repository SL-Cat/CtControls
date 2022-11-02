package cat.customize.dialog;

import android.content.Context;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cat.customize.R;

/**
 * Created by HSL
 * on 2022/9/7.
 */

public class CtToastDialog extends BaseDialog {
    private Handler handler;
    private ImageView leftImage;
    private RelativeLayout dialogBg;
    private TextView infoTv;
    private TextView contTv;
    private int time = 1500;

    public CtToastDialog(@NonNull Context context) {
        super(context);
        initView(context);
        handler = new Handler();
    }

    private void initView(Context context) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View dialogView = layoutInflater.inflate(R.layout.ct_toast_dialog, null, false);
        setContentView(dialogView);
        dialogBg = ((RelativeLayout) dialogView.findViewById(R.id.ct_toast_ll));
        leftImage = ((ImageView) dialogView.findViewById(R.id.ct_toast_dialog_ig));
        infoTv = ((TextView) dialogView.findViewById(R.id.ct_toast_dialog_info));
        contTv = ((TextView) dialogView.findViewById(R.id.ct_toast_dialog_connect));
        setBigByScreenWidth(0.8f);
    }

    public void setDialogBg(int resource){
        dialogBg.setBackgroundResource(resource);
    }

    public void setTime(int time){
        this.time = time;
    }

    public void setTitleBackground(int resource) {
        leftImage.setBackgroundResource(resource);
    }

    public void setInfoText(String infoText) {
        if (!TextUtils.isEmpty(infoText)) {
            infoTv.setText(infoText);
        }
    }

    public void setConnectText(String connectText) {
        contTv.setVisibility(TextUtils.isEmpty(connectText) ? View.GONE:View.VISIBLE);
        if (!TextUtils.isEmpty(connectText)) {
            contTv.setText(connectText);
        }
    }

    public void setTitleVisibility(int visibility) {
        leftImage.setVisibility(visibility);
    }

    public void setInfoVisibility(int visibility) {
        infoTv.setVisibility(visibility);
    }

    public void setConnectVisibility(int visibility) {
        contTv.setVisibility(visibility);
    }

    @Override
    public void show() {
        super.show();
        handler.postDelayed(runnable, time);
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            dismiss();
        }
    };
}
