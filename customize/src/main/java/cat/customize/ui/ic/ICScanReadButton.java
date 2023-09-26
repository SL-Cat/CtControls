package cat.customize.ui.ic;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


import cat.customize.R;
import cat.customize.SecondClickUtils;

/**
 * Created by HSL
 * on 2023/5/18.
 */

public class ICScanReadButton extends LinearLayout implements View.OnClickListener {
    private Context context;
    private TextView scanBtn;
    private TextView powerTv;
    private LinearLayout readBtn;
    private LinearLayout resetBtn;
    private TextView stopReadBtn;
    private LinearLayout scanReadLl;
    private LinearLayout stopRl;
    private OnReadOrStopListener onReadOrStopListener;
    private ICPowerDialog sd;
    private View resetView;
    private int type = 0;

    public interface OnReadOrStopListener {
        boolean startRead();

        boolean stopRead();

        boolean pause(boolean status);

        void setPower(int power);
    }

    public ICScanReadButton(Context context) {
        this(context, null);
    }

    public ICScanReadButton(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ICScanReadButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        View view = View.inflate(context, R.layout.ic_scan_read_layout, this);
        scanBtn = (TextView) view.findViewById(R.id.ic_scan_btn);
        readBtn = (LinearLayout) view.findViewById(R.id.ic_read_btn);
        resetView = (View) view.findViewById(R.id.ic_reset_view);
        scanReadLl = (LinearLayout) view.findViewById(R.id.ic_scan_read_ll);
        resetBtn = (LinearLayout) view.findViewById(R.id.ic_reset_btn);
        stopReadBtn = (TextView) view.findViewById(R.id.ic_stop_btn);
        powerTv = (TextView) view.findViewById(R.id.ic_scan_read_poser_btn);
        stopRl = (LinearLayout) view.findViewById(R.id.ic_stop_rl);

        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.ICScanReadButton);
        String str = typedArray.getString(R.styleable.ICScanReadButton_text);
        type = typedArray.getInteger(R.styleable.ICScanReadButton_type, type);
        if (str != null) {
            scanBtn.setText(str);
        }
        setType(type);
        readBtn.setOnClickListener(this);
        powerTv.setOnClickListener(this);
        stopReadBtn.setOnClickListener(this);
    }

    public void setResetListener(OnClickListener onClickListener) {
        resetBtn.setOnClickListener(onClickListener);
    }

    public void setScanListener(OnClickListener onClickListener) {
        scanBtn.setOnClickListener(onClickListener);
    }

    public void setOnReadOrStopListener(OnReadOrStopListener onReadOrStopListener) {
        this.onReadOrStopListener = onReadOrStopListener;
    }

    /**
     * 设置类型控制显示的按钮
     * 0：全显示
     * 1: 隐藏读取
     * 2：隐藏扫描
     * 3:都显示不可点击,只可重置
     *
     * @param type
     */
    public void setType(int type) {
        this.type = type;
        switch (type) {
            case 0:
                LayoutParams params = new LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT,2);
                scanBtn.setLayoutParams(params);
                readBtn.setVisibility(VISIBLE);
                scanBtn.setVisibility(VISIBLE);
                break;
            case 1:
                readBtn.setVisibility(GONE);
                LayoutParams scanBtnPar = new LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT,4);
                scanBtn.setLayoutParams(scanBtnPar);
                scanBtn.setVisibility(VISIBLE);
                break;
            case 2:
                LayoutParams readBtnPar = new LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT,4);
                readBtn.setLayoutParams(readBtnPar);
                readBtn.setVisibility(VISIBLE);
                scanBtn.setVisibility(GONE);
                break;
        }
    }

    /**
     * 返回当前按钮类型
     *  0：全显示
     *  1: 隐藏读取
     *  2：隐藏扫描
     *  3:都显示不可点击,只可重置
     * @return
     */
    public int getType(){
        return type;
    }

    /**
     * 设置操作按钮是否可以点击
     *
     * @param enabled
     */
    public void setEnabled(boolean enabled) {
        readBtn.setVisibility(enabled ? VISIBLE : GONE);
        scanBtn.setVisibility(enabled ? VISIBLE : GONE);
        resetView.setVisibility(enabled ? VISIBLE : GONE);
    }

    @Override
    public void onClick(View v) {
        if (!SecondClickUtils.isFastClick()) {
            int id = v.getId();
            if (id == R.id.ic_read_btn) {
                startRead();
            } else if (id == R.id.ic_stop_btn) {
                stopRead();
            } else if (id == R.id.ic_scan_read_poser_btn) {
                settPower();
            }
        }
    }

    private int btnPower = 1;

    public void setPowerCode(int power) {
        if (power > 0 && power <= 30) {
            this.btnPower = power;
            powerTv.setText(power + "dBM >");
        }
    }

    private boolean openPower = false;

    private void settPower() {
        if (onReadOrStopListener != null) {
            boolean b = onReadOrStopListener.pause(false);
            if (b) {
                openPower = true;
                sd = new ICPowerDialog(context, btnPower);
                sd.setBigByScreenWidth(0.8f);
                sd.setGravity(Gravity.CENTER);
                sd.setPowerSettingListener(new OnPowerListener() {
                    @Override
                    public void powerResult(int power) {
                        if (onReadOrStopListener != null) {
                            onReadOrStopListener.setPower(power);
                        }
                    }

                    @Override
                    public void powerClean() {
                        if (onReadOrStopListener != null) {
                            onReadOrStopListener.setPower(btnPower);
                        }
                    }
                });
                sd.show();
            }
        }
    }

    /**
     * 设置功率成功开启读写器
     */
    public void setPowerSuccess() {
        if (onReadOrStopListener != null) {
            if (openPower) {
                onReadOrStopListener.pause(true);
                openPower = false;
            }
        }
    }

    public void startRead() {
        if (onReadOrStopListener != null) {
            boolean b = onReadOrStopListener.startRead();
            if (b) {
                scanReadLl.setVisibility(GONE);
                stopRl.setVisibility(VISIBLE);
            }
        }
    }

    public void stopRead() {
        if (onReadOrStopListener != null) {
            if(sd!=null && sd.isShowing()){
                sd.dismiss();
                if (onReadOrStopListener != null) {
                    onReadOrStopListener.setPower(btnPower);
                }
            }else {
                boolean b = onReadOrStopListener.stopRead();
                if (b) {
                    stopRl.setVisibility(GONE);
                    scanReadLl.setVisibility(VISIBLE);
                }
            }
        }
    }
}
