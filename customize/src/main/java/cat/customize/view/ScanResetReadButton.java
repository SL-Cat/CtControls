package cat.customize.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cat.customize.R;

/**
 * Created by HSL
 * on 2022/8/23.
 */

public class ScanResetReadButton extends RelativeLayout implements View.OnClickListener {

    private Context context;
    private TextView scanBtn, readBtn;
    private ImageView midIg;
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
        int midImage = typedArray.getResourceId(R.styleable.IScanResetReadStyle_mid_image, R.mipmap.ct_refesh_ig);

        String leftTv = typedArray.getString(R.styleable.IScanResetReadStyle_scan_text);
        String rightTv = typedArray.getString(R.styleable.IScanResetReadStyle_read_text);
        if (leftTv != null) {
            scanBtn.setText(leftTv);
        }
        if (rightTv != null) {
            readBtn.setText(rightTv);
        }

        scanBtn.setBackgroundResource(leftBackground);
        readBtn.setBackgroundResource(rigthBackground);
        midIg.setImageResource(midImage);
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.ct_scan_read_leaner_scan) {
            if (onScanResetReadListener != null) {
                if (!readStatus) {
                    onScanResetReadListener.scanButton();
                } else {
                    onScanResetReadListener.readIng();
                }
            }
        }
        if (v.getId() == R.id.ct_scan_read_leaner_mid_ig) {
            if (onScanResetReadListener != null) {
                if (!readStatus) {
                    onScanResetReadListener.midImage();
                } else {
                    onScanResetReadListener.readIng();
                }
            }
        }
        if (v.getId() == R.id.ct_scan_read_leaner_raed) {
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
        if (isScanFalg) {
            readStatus = false;
            readBtn.setText(getResources().getString(R.string.ct_start_read));
            readBtn.setBackgroundResource(R.drawable.ct_right_radius_blue_bg);
            if (onScanResetReadListener != null) {
                onScanResetReadListener.readOrStop(false);
            }
        }
    }

    /**
     * 开始读取
     */
    public void startRead() {
        if (isReadFalg) {
            readStatus = true;
            readBtn.setText(getResources().getString(R.string.ct_stop_read));
            readBtn.setBackgroundResource(R.drawable.ct_right_radius_red_bg);
            if (onScanResetReadListener != null) {
                onScanResetReadListener.readOrStop(true);
            }
        }
    }

    private boolean isScanFalg = true;
    private int unClickScanDrawable = -1;

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

    private boolean isReadFalg = true;
    private int unClickReadDrawable = -1;

    /**
     * 关闭或者开启读取按钮
     *
     * @param type
     */
    public void hideOrOpenReadBtn(boolean type) {
        isReadFalg = type;
        if (type) {
            readBtn.setBackgroundResource(R.drawable.ct_right_radius_blue_bg);
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
}
