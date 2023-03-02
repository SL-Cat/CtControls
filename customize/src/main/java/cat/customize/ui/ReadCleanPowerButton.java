package cat.customize.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cat.customize.R;
import cat.customize.SecondClickUtils;
import cat.customize.ulite.system.AndroidUtils;

/**
 * Created by HSL on 2022/7/28.
 */

public class ReadCleanPowerButton extends LinearLayout implements View.OnClickListener {

    private LinearLayout powerLl;
    private RelativeLayout readRl, resetRl;
    private TextView powerTv, resetTv, startTv, stopTv;
    private OnReadCleanPowerListener onReadCleanPowerListener;
    private boolean scanFlag = false;
    private boolean defaultPower = true;
    private int btnPower = 1;
    private Context context;
    private SettingPowerBottomDialog sd;
    private float height = 50;
    private RelativeLayout viewRl;
    private View view;

    public interface OnReadCleanPowerListener {
        void readOrStop(boolean scanFlag);

        void resetData();

        void setPower(int power);
    }

    public ReadCleanPowerButton(Context context) {
        this(context, null);
    }

    public ReadCleanPowerButton(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ReadCleanPowerButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        view = View.inflate(context, R.layout.ct_read_clean_power_layout, this);
        viewRl = ((RelativeLayout) view.findViewById(R.id.ct_read_clean_power_Rl));
        powerTv = ((TextView) view.findViewById(R.id.ct_read_power_text));
        readRl = ((RelativeLayout) view.findViewById(R.id.ct_read_button_fra));
        resetRl = ((RelativeLayout) view.findViewById(R.id.ct_read_reset_rl));
        powerLl = ((LinearLayout) view.findViewById(R.id.ct_read_power_btn));
        startTv = ((TextView) view.findViewById(R.id.ct_read_start_btn));
        resetTv = ((TextView) view.findViewById(R.id.ct_read_reset_tv));
        stopTv = ((TextView) view.findViewById(R.id.ct_read_stop_btn));
        initStyle(attrs);


        startTv.setOnClickListener(this);
        resetTv.setOnClickListener(this);
        stopTv.setOnClickListener(this);
        powerLl.setOnClickListener(this);
    }

    private void initStyle(@Nullable AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.IReadCleanPowerStyle);
        int startBackground = typedArray.getResourceId(R.styleable.IReadCleanPowerStyle_start_background, R.color.color_014099);
        int stopBackground = typedArray.getResourceId(R.styleable.IReadCleanPowerStyle_stop_background, R.color.color_506FAF);
        int resetBackground = typedArray.getResourceId(R.styleable.IReadCleanPowerStyle_reset_background, R.drawable.ct_read_reset_btn);
        int powerBackground = typedArray.getResourceId(R.styleable.IReadCleanPowerStyle_power_background, R.drawable.ct_read_power_btn);

        String startText = typedArray.getString(R.styleable.IReadCleanPowerStyle_start_text);
        String stopText = typedArray.getString(R.styleable.IReadCleanPowerStyle_stop_text);
        String resetText = typedArray.getString(R.styleable.IReadCleanPowerStyle_reset_text);

        int startTextColor = typedArray.getColor(R.styleable.IReadCleanPowerStyle_start_text_color, Color.WHITE);
        int stopTextColor = typedArray.getColor(R.styleable.IReadCleanPowerStyle_stop_text_color, Color.WHITE);
        int resetTextColor = typedArray.getColor(R.styleable.IReadCleanPowerStyle_reset_text_color, Color.WHITE);
        int powerTextColor = typedArray.getColor(R.styleable.IReadCleanPowerStyle_power_text_color, Color.WHITE);

        if (startText != null) {
            startTv.setText(startText);
        }
        if (stopText != null) {
            stopTv.setText(stopText);
        }
        if (resetText != null) {
            resetTv.setText(resetText);
        }

        startTv.setTextColor(startTextColor);
        stopTv.setTextColor(stopTextColor);
        powerTv.setTextColor(powerTextColor);
        resetTv.setTextColor(resetTextColor);

        resetRl.setBackgroundResource(resetBackground);
        readRl.setBackgroundResource(startBackground);
        stopTv.setBackgroundResource(stopBackground);
        powerLl.setBackgroundResource(powerBackground);
    }

//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        int specMode = MeasureSpec.getMode(heightMeasureSpec);
//        int width = MeasureSpec.getSize(widthMeasureSpec);
//        int mHeight = 0;
//        //wrap_content
//        if (specMode == MeasureSpec.AT_MOST) {
//            mHeight = (int) height;
//        }
//        //fill_parent或者精确值
//        else if (specMode == MeasureSpec.EXACTLY) {
//            mHeight = MeasureSpec.getSize(heightMeasureSpec);
//        } else {
//            mHeight = MeasureSpec.getSize(heightMeasureSpec);
//        }
//        setMeasuredDimension(width, mHeight);
//        LinearLayout.LayoutParams rl = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
//        rl.height = mHeight;
//        stopTv.setLayoutParams(rl);
//        view.setLayoutParams(rl);
//        invalidate();
//        Log.d("myDemo", "onMeasure: " +rl.height+"--"+view.getHeight());
//    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
    }

    public void setOnReadCleanPowerListener(OnReadCleanPowerListener onReadCleanPowerListener) {
        this.onReadCleanPowerListener = onReadCleanPowerListener;
    }

    @Override
    public void onClick(View v) {
        if (!SecondClickUtils.isFastClick(300)) {
            int i = v.getId();
            if (i == R.id.ct_read_power_btn) {
                if (!scanFlag) {
                    if (defaultPower) {
                        showPowerDialog();
                    } else {
                        if (onReadCleanPowerListener != null) {
                            onReadCleanPowerListener.setPower(btnPower);
                        }
                    }
                }
            } else if (i == R.id.ct_read_reset_tv) {
                if (!scanFlag) {
                    if (onReadCleanPowerListener != null) {
                        onReadCleanPowerListener.resetData();
                    }
                }
            } else if (i == R.id.ct_read_start_btn) {
                startReadStatus();
            } else if (i == R.id.ct_read_stop_btn) {
                stopReadStatus();
            }
        }
    }

    public void setPowerCode(int power) {
        if (power > 0 && power <= 30) {
            this.btnPower = power;
            powerTv.setText(String.valueOf(btnPower));
        }
    }

    /**
     * 是否启用默认功率设置弹窗
     *
     * @param isFlag true 启用; false 不启用
     */
    public void openDefaultPower(boolean isFlag) {
        this.defaultPower = isFlag;
    }


    private void showPowerDialog() {
        sd = new SettingPowerBottomDialog(context, btnPower);
        sd.setBigByScreenWidth(1);
        sd.setGravity(Gravity.BOTTOM);
        sd.setPowerSettingListener(new SettingPowerBottomDialog.OnPowerSettingListener() {
            @Override
            public void powerResult(int power) {
                btnPower = power;
                powerTv.setText(String.valueOf(btnPower));
                if (onReadCleanPowerListener != null) {
                    onReadCleanPowerListener.setPower(power);
                }
            }
        });
        sd.show();
    }

    public void dismissDialog() {
        if (sd != null && sd.isShowing()) {
            sd.dismiss();
        }
    }

    public SettingPowerBottomDialog getPowerView() {
        return sd != null ? sd : null;
    }

    public boolean powerIsShow() {
        if (sd != null) {
            return sd.isShowing();
        } else return false;
    }

    public void startReadStatus() {
        if (!powerIsShow()) {
            scanFlag = true;
            readRl.setVisibility(GONE);
            stopTv.setVisibility(VISIBLE);
            if (onReadCleanPowerListener != null) {
                onReadCleanPowerListener.readOrStop(scanFlag);
            }
        }
    }


    public void stopReadStatus() {
        if (!powerIsShow()) {
            scanFlag = false;
            readRl.setVisibility(VISIBLE);
            stopTv.setVisibility(GONE);
            if (onReadCleanPowerListener != null) {
                onReadCleanPowerListener.readOrStop(scanFlag);
            }
        }
    }

    public TextView getStartTv() {
        return startTv != null ? startTv : null;
    }

    public TextView getStopTv() {
        return stopTv != null ? stopTv : null;
    }

    public TextView getResetTv() {
        return resetTv != null ? resetTv : null;
    }

    public LinearLayout getPowerLl() {
        return powerLl != null ? powerLl : null;
    }

    public RelativeLayout getReadRl() {
        return readRl != null ? readRl : null;
    }
}
