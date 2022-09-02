package cat.customize.radio;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cat.customize.R;

/**
 * Created by HSL
 * on 2022/8/26.
 */

public class IRadiosButton extends LinearLayout implements View.OnClickListener {

    private Context context;
    private List<String> strList = new ArrayList<>();
    private List<TextView> buttonLits = new ArrayList<>();

    private int maxLines = 3;

    /**
     * 多选:单选 true:false
     */
    private boolean selectType = false;

    private OnIRadiosListener onIRadiosListener;
    private float textSize;
    private int buttonBackground;
    private int textUnColor, textClickColor;


    public interface OnIRadiosListener {
        void onRadiosItemClick(int position, boolean isClick);
    }

    public void setOnIRadiosItemClick(OnIRadiosListener onIRadiosListener) {
        this.onIRadiosListener = onIRadiosListener;
    }

    public IRadiosButton(Context context) {
        this(context, null);
    }

    public IRadiosButton(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IRadiosButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        setOrientation(VERTICAL);
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.IRadiosStyle);
        textUnColor = typedArray.getColor(R.styleable.IRadiosStyle_item_text_un_color, context.getResources().getColor(R.color.color_000000));
        textClickColor = typedArray.getColor(R.styleable.IRadiosStyle_item_text_click_color, context.getResources().getColor(R.color.color_007BFF));
        textSize = typedArray.getDimension(R.styleable.IRadiosStyle_item_text_size, 16);
        buttonBackground = typedArray.getResourceId(R.styleable.IRadiosStyle_item_background, R.drawable.ct_radios_item_select_bg);
        selectType = typedArray.getBoolean(R.styleable.IRadiosStyle_radios_click_more, false);

        strList.add("按钮1");
        strList.add("按钮2");
        strList.add("按钮3");
        setRadiosBtn(strList);
    }

    private void setRadiosBtn(List<String> strList) {
        if (strList.size() > maxLines) {
            List<String> datas = strList.subList(0, maxLines);
            addLinearView(datas, strList, false);
        } else {
            addLinearView(strList, strList, true);
        }
    }

    private void addLinearView(List<String> datas, List<String> strList, boolean flag) {
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(HORIZONTAL);
        for (String data : datas) {
            TextView textButton = new TextView(context);
            LinearLayout.LayoutParams weight1 = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1.0f);
            weight1.setMargins(10, 10, 10, 10);
            textButton.setLayoutParams(weight1);
            textButton.setGravity(Gravity.CENTER);
            textButton.setPadding(20, 10, 20, 10);
            //android 获取资源id 0 表示没有找到资源,所以设置id 从 1 开始
            textButton.setId(buttonLits.size()+1);
            textButton.setText(data);
            textButton.setTextSize(textSize);
            textButton.setTextColor(textUnColor);
            textButton.setBackgroundResource(buttonBackground);
            textButton.setOnClickListener(this);
            linearLayout.addView(textButton);
            buttonLits.add(textButton);
        }
        addView(linearLayout);
        if (!flag) {
            strList.removeAll(datas);
            setRadiosBtn(strList);
        }
    }

    @Override
    public void onClick(View v) {
        for (int i = 0; i < buttonLits.size(); i++) {
            TextView button = buttonLits.get(i);
            if (selectType) {
                if (v.getId() == button.getId()) {
                    if (button.isSelected()) {
                        button.setTextColor(textUnColor);
                        button.setSelected(false);
                    } else {
                        button.setTextColor(textClickColor);
                        button.setSelected(true);
                    }
                    if (onIRadiosListener != null) {
                        onIRadiosListener.onRadiosItemClick(i, button.isSelected());
                    }
                }
            } else {
                if (v.getId() == button.getId()) {
                    button.setTextColor(textClickColor);
                    button.setSelected(true);
                    if (onIRadiosListener != null) {
                        onIRadiosListener.onRadiosItemClick(i, button.isSelected());
                    }
                } else {
                    unClickButton(i);
                }
            }
        }
    }

    public void setClickType(boolean clickType) {
        this.selectType = clickType;
    }


    /**
     * 是否允许全部取消
     */
    public void isNullClick() {

    }

    /**
     * 取消某个按钮
     *
     * @param position
     */
    public void unClickButton(int position) {
        if (buttonLits.size() >= position - 1) {
            TextView textView = buttonLits.get(position);
            textView.setSelected(false);
            textView.setTextColor(textUnColor);
        }
    }

    /**
     * 选中某个按钮
     *
     * @param position
     */
    public void selectButton(int position) {
        if (buttonLits.size() >= position - 1) {
            TextView textView = buttonLits.get(position);
            textView.setSelected(true);
            textView.setTextColor(textClickColor);
        }
    }

    /**
     * 清空所有已选状态
     */
    public void cleanClick() {
        for (TextView buttonLit : buttonLits) {
            buttonLit.setSelected(false);
            buttonLit.setTextColor(textUnColor);
        }
    }

    /**
     * 返回所有选择得按钮ID
     *
     * @return
     */
    public List<Integer> getAllClick() {
        List<Integer> clicks = new ArrayList<>();
        for (int i = 0; i < buttonLits.size(); i++) {
            TextView textView = buttonLits.get(i);
            if (textView.isSelected()) {
                clicks.add(i);
            }
        }
        return clicks;
    }

    public void setButtonLits(List<String> list) {
        buttonLits.clear();
        strList.clear();
        strList.addAll(list);
        removeAllViews();
        setRadiosBtn(strList);
    }

}
