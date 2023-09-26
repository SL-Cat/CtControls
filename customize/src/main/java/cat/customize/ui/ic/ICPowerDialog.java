package cat.customize.ui.ic;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import cat.customize.R;
import cat.customize.dialog.BaseDialog;
import cat.customize.listener.OnCtSeekBarListener;
import cat.customize.view.CtSeekBar;

/**
 * Created by HSL
 * on 2023/5/26.
 */

public class ICPowerDialog extends BaseDialog {

    private int progressResult;
    private CtSeekBar seekTv;
    private Context context;
    private OnPowerListener onPowerListener;

    public ICPowerDialog(@NonNull Context context, int progress) {
        super(context);
        this.context = context;
        this.progressResult = progress;
        initView(context, progress);
        this.setCancelable(false);
    }

    private void initView(Context context, int progress) {
        //通过LayoutInflater获取布局
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.ic_dialog_power_layout, null);
        setContentView(dialogView);
        TextView cancelTv = (TextView) dialogView.findViewById(R.id.ic_dialog_left_button);
        TextView sureTv = (TextView) dialogView.findViewById(R.id.ic_dialog_right_button);
        final TextView codeTv = (TextView) dialogView.findViewById(R.id.dialog_power_code);
        seekTv = (CtSeekBar) dialogView.findViewById(R.id.dialog_power_sk);

        seekTv.setProgressDefault(progress);
        codeTv.setText(progress + "");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            seekTv.setProgressMin(1);
        }
        seekTv.setProgressMax(30);

        sureTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onPowerListener != null) {
                    onPowerListener.powerResult(progressResult);
                    dismiss();
                }
            }
        });
        cancelTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onPowerListener != null) {
                    onPowerListener.powerClean();
                    dismiss();
                }
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
        }
    }

    public void setPowerSettingListener(OnPowerListener onPowerListener) {
        this.onPowerListener = onPowerListener;
    }
}
