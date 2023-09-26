package cat.customize.ui.ic;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import cat.customize.R;


/**
 * Created by HSL
 * on 2023/5/18.
 */

public class InputEditText extends LinearLayout {

    private ImageView deleteIg;
    private Context context;
    private EditText editText;
    private ImageView rightIg;
    private LinearLayout viewLl;
    private LinearLayout scanBg;

    public InputEditText(Context context) {
        this(context, null);
    }

    public InputEditText(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public InputEditText(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        View view = View.inflate(context, R.layout.ic_edit_layout, this);
        viewLl = ((LinearLayout) view.findViewById(R.id.ic_edit_bg));
        scanBg = ((LinearLayout) view.findViewById(R.id.ic_edit_scan_bg));
        deleteIg = (ImageView) view.findViewById(R.id.ic_edit_delete_ig);
        rightIg = (ImageView) view.findViewById(R.id.ic_edit_right_ig);
        editText = (EditText) view.findViewById(R.id.ic_edit_ed);
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.InputEditText);
        String hintStr = typedArray.getString(R.styleable.InputEditText_hintText);
        int imageId = typedArray.getResourceId(R.styleable.InputEditText_image, R.mipmap.search_ig);
        int backColor = typedArray.getResourceId(R.styleable.InputEditText_background, R.color.color_ffffff);
        if (hintStr != null) {
            editText.setHint(hintStr);
        }
        viewLl.setBackgroundResource(backColor);
        rightIg.setImageResource(imageId);
        editText.setImeOptions(EditorInfo.IME_ACTION_SEND);
        setTextChangeListener(null);
    }

    public void setTextChangeListener(TextWatcher textChangeListener){
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String str = s.toString();
                if (str != null && !"".equals(str)) {
                    visibleOrGoneRightIg(true);
                } else {
                    visibleOrGoneRightIg(false);
                }
                if(textChangeListener!=null) {
                    textChangeListener.afterTextChanged(s);
                }
            }
        });
    }

    public void setDeleteIg(OnClickListener onClickListener) {
        deleteIg.setOnClickListener(onClickListener);
    }

    public void setText(String str) {
        if (str != null) editText.setText(str);
    }

    public String getText() {
        String str = editText.getText().toString().trim();
        return str != null && !str.equals("") ? str.toUpperCase() : "";
    }

    public void setOnEditorActionListener(TextView.OnEditorActionListener onEditorActionListener) {
        editText.setOnEditorActionListener(onEditorActionListener);
    }

    public ImageView getRightImage() {
        return rightIg;
    }

    public ImageView getDeleteIg(){
        return deleteIg;
    }

    public EditText getEditText() {
        return editText;
    }

    public void setRightOnClick(OnClickListener onClick) {
        rightIg.setOnClickListener(onClick);
    }

    /**
     * 设置扫描条码是增加还是减少
     * @param type
     */
    public void setScanType(boolean type){
        rightIg.setSelected(type);
        if(type) {
            scanBg.setBackgroundResource(R.drawable.ct_red_line_bg);
        }else {
            scanBg.setBackgroundResource(R.drawable.ct_radius_divider_white_bg);
        }
    }

    public boolean getScanSelect(){
        return rightIg.isSelected();
    }

    private void visibleOrGoneRightIg(boolean status) {
        int visibility = deleteIg.getVisibility();
        if (status) {
            if (visibility == 0) return;
            Animation animBottomIn = AnimationUtils.loadAnimation(context, cat.customize.R.anim.right_to_left);
            deleteIg.setVisibility(VISIBLE);
            deleteIg.startAnimation(animBottomIn);
        } else {
            if (visibility == 8 || visibility == 4) return;
            deleteIg.setVisibility(GONE);
        }
    }

    public void setEdEnabled(boolean edEnabled){
        deleteIg.setEnabled(edEnabled);
        rightIg.setEnabled(edEnabled);
        editText.setEnabled(edEnabled);
    }
}
