package cat.customize.iview;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import cat.customize.R;
import cat.customize.ulite.system.AndroidUtils;

/**
 * Created by HSL
 * on 2022/9/20.
 */

public class CtRadioEdit extends LinearLayout implements View.OnClickListener, TextWatcher {

    private LinearLayout confirmLl, editBgLl;
    private TextView confirmTv;
    private ImageView leftIg;
    private ImageView rightIg;
    private EditText editEd;
    private CtOnRadioEditListener onCtRadioEditListener;
    private int mViewHeight = 40;
    private Context context;


    public interface CtOnRadioEditListener {
        void onLeftIgClickListener();

        void onRightIgClickListener();

        void onConfirmClickListener(String edMsg);

        void onEditTextChanged(String str);
    }

    public void setOnCtRadioEditListener(CtOnRadioEditListener onCtRadioEditListener) {
        this.onCtRadioEditListener = onCtRadioEditListener;
    }

    public CtRadioEdit(Context context) {
        this(context, null);
    }

    public CtRadioEdit(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CtRadioEdit(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        View view = View.inflate(context, R.layout.ct_radio_edit_layout, this);
        editBgLl = ((LinearLayout) view.findViewById(R.id.ct_radio_edit_bg));
        confirmLl = ((LinearLayout) view.findViewById(R.id.ct_radio_edit_confirm));
        confirmTv = (TextView) view.findViewById(R.id.ct_radio_edit_confirm_tv);
        leftIg = (ImageView) view.findViewById(R.id.ct_radio_edit_left_ig);
        rightIg = (ImageView) view.findViewById(R.id.ct_radio_edit_right_ig);
        editEd = (EditText) view.findViewById(R.id.ct_radio_edit_input);

        confirmLl.setOnClickListener(this);
        rightIg.setOnClickListener(this);
        leftIg.setOnClickListener(this);
        editEd.addTextChangedListener(this);
        editEd.setImeOptions(EditorInfo.IME_ACTION_SEND);
        editEd.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (event != null)
                    if (event.getAction() == KeyEvent.ACTION_UP) return true;
                if (actionId == EditorInfo.IME_ACTION_SEND || actionId == 0) {
                    if (!editEd.getText().toString().toUpperCase().trim().equals("")) {
                        String str = editEd.getText().toString().toUpperCase().trim();
                        if (onCtRadioEditListener != null) {
                            onCtRadioEditListener.onConfirmClickListener(str);
                        }
                    }
                    return true;
                }
                return false;
            }
        });
        initStype(attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int specMode = MeasureSpec.getMode(heightMeasureSpec);
        int specSize = MeasureSpec.getSize(heightMeasureSpec);
        //wrap_content
        if (specMode == MeasureSpec.AT_MOST) {
            specSize = AndroidUtils.dp2px(context, mViewHeight);
        } else if (specMode == MeasureSpec.EXACTLY) {
            specSize = getMeasuredHeight();

        }
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.height = specSize;
        editBgLl.setLayoutParams(layoutParams);

        invalidate();
    }

    private void initStype(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.IRadioEditStyle);
        String btnStr = typedArray.getString(R.styleable.IRadioEditStyle_radio_edit_text);
        int bgRs = typedArray.getResourceId(R.styleable.IRadioEditStyle_background, R.drawable.ct_radius_white_select_bg);
        float testSize = typedArray.getDimension(R.styleable.IRadioEditStyle_text_size, 14);
        boolean btnStatus = typedArray.getBoolean(R.styleable.IRadioEditStyle_radio_edit_button, true);
        String hitStr = typedArray.getString(R.styleable.IRadioEditStyle_radio_edit_input);
        int leftIgRs = typedArray.getResourceId(R.styleable.IRadioEditStyle_radio_edit_left_image, R.mipmap.ct_tag_ig);
        int rightIgRs = typedArray.getResourceId(R.styleable.IRadioEditStyle_radio_edit_right_image, R.mipmap.ct_delete_ig);

        editBgLl.setBackgroundResource(bgRs);
        editEd.setTextSize(testSize);
        if (btnStatus) {
            confirmLl.setVisibility(VISIBLE);
            if (btnStr != null) {
                confirmTv.setText(btnStr);
            }
        } else {
            confirmLl.setVisibility(GONE);
        }
        if (hitStr != null) {
            editEd.setHint(hitStr);
        }
        leftIg.setImageResource(leftIgRs);
        rightIg.setImageResource(rightIgRs);

        typedArray.recycle();
    }

    @Override
    public void onClick(View v) {
        if (onCtRadioEditListener != null) {
            if (v.getId() == R.id.ct_radio_edit_left_ig) {
                onCtRadioEditListener.onLeftIgClickListener();
            } else if (v.getId() == R.id.ct_radio_edit_right_ig) {
                onCtRadioEditListener.onRightIgClickListener();
            } else if (v.getId() == R.id.ct_radio_edit_confirm) {
                String trim = editEd.getText().toString().trim();
                onCtRadioEditListener.onConfirmClickListener(trim != null ? trim : "");
            }
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (onCtRadioEditListener != null) {
            String str = s.toString();
            onCtRadioEditListener.onEditTextChanged(str);
        }
    }

    public void clean() {
        editEd.setText("");
    }

    public EditText getEditText() {
        return editEd != null ? editEd : null;
    }
}
