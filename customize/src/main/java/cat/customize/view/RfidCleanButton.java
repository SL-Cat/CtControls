package cat.customize.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cat.customize.R;
import cat.customize.SecondClickUtils;

/**
 * Created by HSL on 2022/7/4.
 */

public class RfidCleanButton extends RelativeLayout implements View.OnClickListener {

    private LinearLayout linearLayout;
    private TextView cleanBtn;
    private TextView startBtn;
    private TextView coutinueBtn;
    private boolean isRead = false;

    private OnRfidBtnListener onRfidBtnListener;

    public interface OnRfidBtnListener {
        void startRead(boolean isRead);

        void clean();
    }

    public void setOnRfidBtnListener(OnRfidBtnListener onRfidBtnListener) {
        this.onRfidBtnListener = onRfidBtnListener;
    }

    public RfidCleanButton(Context context) {
        this(context, null);
    }

    public RfidCleanButton(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RfidCleanButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View view = (View) View.inflate(context, R.layout.ct_rfid_clean_btn, this);
        linearLayout = ((LinearLayout) view.findViewById(R.id.ct_rfid_clean_ll));
        cleanBtn = ((TextView) view.findViewById(R.id.ct_rfid_clean_clean_btn));
        startBtn = ((TextView) view.findViewById(R.id.ct_rfid_clean_start_btn));
        coutinueBtn = ((TextView) view.findViewById(R.id.ct_rfid_clean_continue_btn));
        cleanBtn.setOnClickListener(this);
        startBtn.setOnClickListener(this);
        coutinueBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (!SecondClickUtils.isFastClick()) {
            int i = v.getId();
            if (i == R.id.ct_rfid_clean_start_btn) {
                startRead();
            } else if (i == R.id.ct_rfid_clean_continue_btn) {
                continueRead();
            } else if (i == R.id.ct_rfid_clean_clean_btn) {
                clean();
            }
        }
    }

    public void initView() {
        linearLayout.setVisibility(GONE);
        startBtn.setVisibility(VISIBLE);
        coutinueBtn.setText("继续读取");
        isRead = false;
    }

    private void clean() {
        if (onRfidBtnListener != null) {
            onRfidBtnListener.clean();
        }
    }

    private void continueRead() {
        linearLayout.setVisibility(VISIBLE);
        startBtn.setVisibility(GONE);
        isRead = !isRead;
        if (isRead) {
            coutinueBtn.setText("停止读取");
            coutinueBtn.setBackgroundResource(R.color.color_ff0000);
        } else {
            coutinueBtn.setText("继续读取");
            coutinueBtn.setBackgroundResource(R.color.color_014099);
        }
        if (onRfidBtnListener != null) {
            onRfidBtnListener.startRead(isRead);
        }
    }

    private void startRead() {
        linearLayout.setVisibility(VISIBLE);
        startBtn.setVisibility(GONE);
        coutinueBtn.setText("停止读取");
        coutinueBtn.setBackgroundResource(R.color.color_ff0000);
        isRead = true;
        if (onRfidBtnListener != null) {
            onRfidBtnListener.startRead(isRead);
        }
    }


    public void read() {
        linearLayout.setVisibility(VISIBLE);
        startBtn.setVisibility(GONE);
        coutinueBtn.setText("停止读取");
        coutinueBtn.setBackgroundResource(R.color.color_ff0000);
        isRead = true;
    }

    public void stop() {
        coutinueBtn.setText("继续读取");
        coutinueBtn.setBackgroundResource(R.color.color_014099);
        isRead = false;
    }
}
