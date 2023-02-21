package cat.customize.bean;

import cat.customize.R;

/**
 * Created by HSL
 * on 2023/2/21.
 */

public class MaskItemBean {

    private String msg;
    private int drawableColor;

    private float textSize = 18;
    private int textColor = R.color.color_ffffff;

    private int index;

    public MaskItemBean() {
    }
    public MaskItemBean(String msg, int drawableColor, float textSize, int index) {
        this.msg = msg;
        this.drawableColor = drawableColor;
        this.index = index;
        this.textSize = textSize;
    }

    public MaskItemBean(String msg, int drawableColor, int index) {
        this.msg = msg;
        this.drawableColor = drawableColor;
        this.index = index;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getDrawableColor() {
        return drawableColor;
    }

    public void setDrawableColor(int drawableColor) {
        this.drawableColor = drawableColor;
    }

    public float getTextSize() {
        return textSize;
    }

    public void setTextSize(float textSize) {
        this.textSize = textSize;
    }

    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
