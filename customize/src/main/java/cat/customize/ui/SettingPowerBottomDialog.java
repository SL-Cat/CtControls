package cat.customize.ui;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import cat.customize.R;
import cat.customize.dialog.BaseDialog;
import cat.customize.view.CtSeekBar;
import cat.customize.listener.OnCtSeekBarListener;

/**
 * Created by HSL on 2022/7/5.
 */

public class SettingPowerBottomDialog extends BaseDialog {


    private OnPowerSettingListener mOnPowerSettingListener;
    private int progressResult;
    private CtSeekBar seekTv;
    private TextView maxCodeTv;

    public SettingPowerBottomDialog(@NonNull Context context, int progress) {
        super(context);
        this.progressResult = progress;
        initView(context, progress);
    }

    private void initView(Context context, int progress) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.ct_set_power_bottom_layout, null, false);
        setContentView(dialogView);
        TextView cancelTv = (TextView) dialogView.findViewById(R.id.setting_power_dialog_cancel);
        TextView sureTv = (TextView) dialogView.findViewById(R.id.setting_power_dialog_sure);
        maxCodeTv = (TextView) dialogView.findViewById(R.id.setting_power_dialog_max_code);
        final TextView codeTv = (TextView) dialogView.findViewById(R.id.setting_power_dialog_code);
        seekTv = (CtSeekBar) dialogView.findViewById(R.id.setting_power_dialog_seek);

        seekTv.setProgressDefault(progress);
        codeTv.setText(progress + "");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            seekTv.setProgressMin(1);
        }
        seekTv.setProgressMax(30);
        maxCodeTv.setText(seekTv.getProgressMax() + "dBM");

        sureTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnPowerSettingListener != null) {
                    mOnPowerSettingListener.powerResult(progressResult);
                    dismiss();
                }
            }
        });
        cancelTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        seekTv.setOnCtSeekBarListener(new OnCtSeekBarListener() {
            @Override
            public void onProgressChanged(float progress) {
                progressResult = (int) progress;
                codeTv.setText(progressResult + "");
            }
        });
    }


    /**
     * 设置最大值
     * @param progressMax
     */
    public void setProgressMax(int progressMax) {
        if (seekTv != null) {
            seekTv.setProgressMax(progressMax);
            if(maxCodeTv!=null) {
                maxCodeTv.setText(seekTv.getProgressMax() + "dBM");
            }
        }
    }

    public interface OnPowerSettingListener {
        void powerResult(int power);
    }

    public void setPowerSettingListener(OnPowerSettingListener mOnPowerSettingListener) {
        this.mOnPowerSettingListener = mOnPowerSettingListener;
    }


    public void setBigByScreenWidth(float width) {
        int paramsWidth = 0;
        Window window = getWindow();
        assert window != null;
        DisplayMetrics dm = getContext().getResources().getDisplayMetrics();
        int screenWidth = dm.widthPixels;
        if (width > 0 && screenWidth > 0) {
            paramsWidth = (int) (screenWidth * width);
        }
        window.setLayout(paramsWidth, LinearLayout.LayoutParams.WRAP_CONTENT);
    }

}
