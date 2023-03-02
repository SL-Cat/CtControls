package cat.customize.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cat.customize.R;
import cat.customize.ulite.system.AndroidUtils;
import cat.customize.ulite.system.CtLog;

/**
 * Created by HSL
 * on 2022/8/23.
 */

public class ScanResetReadButton extends RelativeLayout implements View.OnClickListener {

    private Context context;
    private TextView scanBtn, readBtn;
    private ImageView midIg;
    private LinearLayout linearLl;
    private boolean readStatus; //true:false 读取中：停止中

    private OnScanResetReadListener onScanResetReadListener;

    public interface OnScanResetReadListener {
        void scanButton();

        void readOrStop(boolean isFalg);

        void midImage();

        void readIng();
    }

    public void setOnScanResetReadListener(OnScanResetReadListener onScanResetReadListener) {
        this.onScanResetReadListener = onScanResetReadListener;
    }

    public ScanResetReadButton(Context context) {
        this(context, null);
    }

    public ScanResetReadButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScanResetReadButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        View view = View.inflate(context, R.layout.ct_scan_reset_read_layout, this);

        linearLl = ((LinearLayout) view.findViewById(R.id.ct_scan_read_leaner_ll));
        scanBtn = ((TextView) view.findViewById(R.id.ct_scan_read_leaner_scan));
        readBtn = ((TextView) view.findViewById(R.id.ct_scan_read_leaner_raed));
        midIg = ((ImageView) view.findViewById(R.id.ct_scan_read_leaner_mid_ig));

        scanBtn.setOnClickListener(this);
        readBtn.setOnClickListener(this);
        midIg.setOnClickListener(this);
        initStyle(attrs);
    }

    private void initStyle(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.IScanResetReadStyle);
        int rigthBackground = typedArray.getResourceId(R.styleable.IScanResetReadStyle_right_background, R.drawable.ct_right_radius_blue_bg);
        int leftBackground = typedArray.getResourceId(R.styleable.IScanResetReadStyle_left_background, R.drawable.ct_left_radius_green_bg);
        int migPadding = typedArray.getInteger(R.styleable.IScanResetReadStyle_mid_padding, 15);
        int midImage = typedArray.getResourceId(R.styleable.IScanResetReadStyle_mid_image, R.mipmap.ct_refesh_ig);
        int height = typedArray.getInteger(R.styleable.IScanResetReadStyle_scan_read_height, 40);

        String leftTv = typedArray.getString(R.styleable.IScanResetReadStyle_scan_text);
        String rightTv = typedArray.getString(R.styleable.IScanResetReadStyle_read_text);
        if (leftTv != null) {
            scanBtn.setText(leftTv);
        }
        if (rightTv != null) {
            readBtn.setText(rightTv);
        }
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        params.height = AndroidUtils.dp2px(context,height);
        params.addRule(RelativeLayout.CENTER_VERTICAL);

        linearLl.setLayoutParams(params);
        scanBtn.setBackgroundResource(leftBackground);
        readBtn.setBackgroundResource(rigthBackground);
        midIg.setImageResource(midImage);
        midIg.setPadding(migPadding,migPadding,migPadding,migPadding);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.ct_scan_read_leaner_scan) {
            CtLog.d("customize", "onClick: ct_scan_read_leaner_scan");
            if (!isScanFalg) return;
            if (onScanResetReadListener != null) {
                if (!readStatus) {
                    onScanResetReadListener.scanButton();
                } else {
                    onScanResetReadListener.readIng();
                }
            }
        }
        if (v.getId() == R.id.ct_scan_read_leaner_mid_ig) {
            CtLog.d("customize", "onClick: ct_scan_read_leaner_mid_ig");
            if (onScanResetReadListener != null) {
                if (!readStatus) {
                    onScanResetReadListener.midImage();
                } else {
                    onScanResetReadListener.readIng();
                }
            }
        }
        if (v.getId() == R.id.ct_scan_read_leaner_raed) {
            CtLog.d("customize", "onClick: ct_scan_read_leaner_raed");
            if (!isReadFalg) return;
            if (readStatus) {
                stopRead();
            } else {
                startRead();
            }
        }
    }

    /**
     * 停止读取
     */
    public void stopRead() {
        readStatus = false;
        readBtn.setText(getResources().getString(R.string.ct_start_read));
        if (isVisibiScan) {
            readBtn.setBackgroundResource(R.drawable.ct_right_radius_blue_bg);
        } else {
            readBtn.setBackgroundResource(R.drawable.ct_radius_blue_bg);
        }
        if (onScanResetReadListener != null) {
            onScanResetReadListener.readOrStop(false);
        }
    }

    /**
     * 开始读取
     */
    public void startRead() {
        readStatus = true;
        readBtn.setText(getResources().getString(R.string.ct_stop_read));
        if (isVisibiScan) {
            readBtn.setBackgroundResource(R.drawable.ct_right_radius_red_bg);
        } else {
            readBtn.setBackgroundResource(R.drawable.ct_radius_red_bg);
        }
        if (onScanResetReadListener != null) {
            onScanResetReadListener.readOrStop(true);
        }
    }

    private boolean isScanFalg = true;//是否可以点击
    private boolean isReadFalg = true;

    private boolean isVisibiScan = true; //是否隐藏了条码 ; 隐藏后读取按钮动态背景要变换

    private int unClickScanDrawable = -1; //不可点击时的背景
    private int unClickReadDrawable = -1;

    /**
     * 关闭或者开启扫描按钮
     *
     * @param type
     */
    public void hideOrOpenScanBtn(boolean type) {
        isScanFalg = type;
        if (type) {
            scanBtn.setBackgroundResource(R.drawable.ct_left_radius_green_bg);
        } else {
            if (unClickScanDrawable != -1) {
                scanBtn.setBackgroundResource(unClickScanDrawable);
            } else {
                scanBtn.setBackgroundResource(R.drawable.ct_left_radius_unclick_bg);
            }
        }
    }

    public void setUnClickScanDrawable(int drawableId) {
        unClickScanDrawable = drawableId;
    }


    /**
     * 关闭或者开启读取按钮
     *
     * @param type
     */
    public void hideOrOpenReadBtn(boolean type) {
        isReadFalg = type;
        if(readStatus)return;
        if (type) {
            readBtn.setBackgroundResource(R.drawable.ct_radius_blue_bg);
        } else {
            if (unClickReadDrawable != -1) {
                readBtn.setBackgroundResource(unClickReadDrawable);
            } else {
                readBtn.setBackgroundResource(R.drawable.ct_right_radius_unclick_bg);
            }
        }
    }

    public void setUnClickReadDrawable(int drawableId) {
        unClickReadDrawable = drawableId;
    }

    @SuppressLint("WrongConstant")
    public void visibilityLeft(boolean isShow) {
        isVisibiScan = isShow;
        if (!isShow) {
            scanBtn.setVisibility(GONE);
            LinearLayout.LayoutParams readLl = new LinearLayout.LayoutParams(0, LayoutParams.WRAP_CONTENT, 1);
            if(readStatus){
                readBtn.setBackgroundResource(R.drawable.ct_radius_red_bg);
            }else {
                readBtn.setBackgroundResource(R.drawable.ct_radius_blue_bg);
            }
            readBtn.setLayoutParams(readLl);
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            layoutParams.addRule(RelativeLayout.LEFT_OF);
            midIg.setLayoutParams(layoutParams);
        } else {
            scanBtn.setVisibility(VISIBLE);
            readBtn.setBackgroundResource(R.drawable.ct_right_radius_blue_bg);
            if (readBtn.getVisibility() == 0) {
                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
                midIg.setLayoutParams(layoutParams);
            }
        }
    }

    @SuppressLint("WrongConstant")
    public void visibilityRgiht(boolean isShow) {
        if (!isShow) {
            readBtn.setVisibility(GONE);
            LinearLayout.LayoutParams readLl = new LinearLayout.LayoutParams(0, LayoutParams.WRAP_CONTENT, 1);
            scanBtn.setLayoutParams(readLl);
            scanBtn.setBackgroundResource(R.drawable.ct_radius_green_bg);
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            layoutParams.addRule(RelativeLayout.LEFT_OF);
            midIg.setLayoutParams(layoutParams);
        } else {
            readBtn.setVisibility(VISIBLE);
            scanBtn.setBackgroundResource(R.drawable.ct_left_radius_green_bg);
            if (scanBtn.getVisibility() == 0) {
                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
                midIg.setLayoutParams(layoutParams);
            }
        }
    }

    @SuppressLint("WrongConstant")
    public void visibilityConter(boolean isShow) {
        if (!isShow) {
            midIg.setVisibility(GONE);
        } else {
            midIg.setVisibility(VISIBLE);
        }
    }

    public TextView getLeftBtn() {
        return scanBtn;
    }

    public TextView getRightBtn() {
        return readBtn;
    }

    public ImageView getConterIg() {
        return midIg;
    }

}
