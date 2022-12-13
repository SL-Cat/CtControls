package cat.customize.radio;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import cat.customize.R;
import cat.customize.ulite.system.AndroidUtils;

/**
 * Created by HSL
 * on 2022/8/31.
 */

public class ISwitchbutton extends View {
    //画笔
    private final Paint mPaint = new Paint();
    private static float MBTNHEIGHT;
    private static final int OFFSET = 3;
    private int mHeight;
    private int mWidth = 60;
    private float mAnimate = 0L;
    //此处命名不规范，目的和Android自带的switch有相同的用法
    private boolean checked = false;
    //是否开启点击后自动切换状态的操作,默认开启
    private boolean openChecked = true;
    private float mScale;
    private int mSelectColor;
    private OnCheckedChangeListener mOnCheckedChangeListener;
    private Paint.FontMetrics fontMetrics;
    private Context context;
    private float textWidth;
    private int textPadding = 10;
    private String textStr;

    public ISwitchbutton(Context context) {
        this(context, null);
    }

    public ISwitchbutton(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ISwitchStyle);
        mSelectColor = typedArray.getColor(R.styleable.ISwitchStyle_switch_button_color, context.getResources().getColor(R.color.color_007BFF));
        MBTNHEIGHT = typedArray.getFloat(R.styleable.ISwitchStyle_switch_button_height, 0.45f);
        checked = typedArray.getBoolean(R.styleable.ISwitchStyle_switch_button_click, false);
        textStr = typedArray.getString(R.styleable.ISwitchStyle_text);

        textPaint = new Paint();
        textPaint.setColor(getResources().getColor(R.color.color_000000));
        textPaint.setAntiAlias(true);

        typedArray.recycle();
    }

    private Paint textPaint;

    /**
     * @param widthMeasureSpec
     * @param heightMeasureSpec 高度是是宽度的0.55倍
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int specMode = MeasureSpec.getMode(widthMeasureSpec);
        int width = 0;
        //wrap_content
        if (specMode == MeasureSpec.AT_MOST) {
            textPaint.setTextSize(AndroidUtils.dp2px(context, 10));
        }
        //fill_parent或者精确值
        else {
            mWidth = MeasureSpec.getSize(widthMeasureSpec);
            textPaint.setTextSize(AndroidUtils.dp2px(context, (float) (mWidth * 0.16)));
        }
        if (textStr != null) {
            textWidth = textPaint.measureText(textStr);
            fontMetrics = textPaint.getFontMetrics();
        }
        width = (int) (textWidth + textPadding);
        mHeight = (int) (MBTNHEIGHT * (mWidth + textPadding));
        setMeasuredDimension((int) (mWidth + width), mHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);
        mPaint.setColor(mSelectColor);
        Rect rect = new Rect(0, 0, mWidth, getHeight());
        RectF rectf = new RectF(rect);
        //绘制圆角矩形
        canvas.drawRoundRect(rectf, getHeight() / 2, getHeight() / 2, mPaint);

        if (null != textStr && !"".equals(textStr)) {
            canvas.drawText(textStr, mWidth + textPadding, rect.centerY() - fontMetrics.bottom / 2 - fontMetrics.top / 2, textPaint);
        }

        //以下save和restore很重要，确保动画在中间一层 ，如果大家不明白，可以去搜下用法
        canvas.save();
        mPaint.setColor(Color.parseColor("#CFCFCF"));
        mAnimate = mAnimate - 0.1f > 0 ? mAnimate - 0.1f : 0; // 动画标示 ，重绘10次，借鉴别人的动画
        mScale = (!checked ? 1 - mAnimate : mAnimate);
//        canvas.scale(mScale, mScale, getWidth() - getHeight() / 2, rect.centerY());
        canvas.scale(mScale, mScale, mWidth - getHeight() / 2, rect.centerY());
        //绘制缩放的灰色圆角矩形
        canvas.drawRoundRect(rectf, getHeight() / 2, getHeight() / 2, mPaint);

        mPaint.setColor(Color.WHITE);
        Rect rect_inner = new Rect(OFFSET, OFFSET, mWidth - OFFSET, getHeight() - OFFSET);
//        Rect rect_inner = new Rect(OFFSET, OFFSET, getWidth() - OFFSET, getHeight() - OFFSET);
        RectF rect_f_inner = new RectF(rect_inner);
        //绘制缩放的白色圆角矩形，和上边的重叠实现灰色边框效果
        canvas.drawRoundRect(rect_f_inner, (getHeight() - 8) / 2, (getHeight() - 8) / 2, mPaint);
        canvas.restore();

        //中间圆形平移
//        int sWidth = getWidth();
        int sWidth = mWidth;
        int bTranslateX = sWidth - getHeight();
        final float translate = bTranslateX * (!checked ? mAnimate : 1 - mAnimate);
        canvas.translate(translate, 0);

        //以下两个圆带灰色边框
        mPaint.setColor(Color.parseColor("#CFCFCF"));
        canvas.drawCircle(getHeight() / 2, getHeight() / 2, getHeight() / 2 - OFFSET / 2, mPaint);

        mPaint.setColor(Color.WHITE);
        canvas.drawCircle(getHeight() / 2, getHeight() / 2, getHeight() / 2 - OFFSET, mPaint);

        if (mScale > 0) {
            mPaint.reset();
            invalidate();
        }
    }

    /**
     * 事件分发
     *
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                return true;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                if (openChecked) {
                    mAnimate = 1;
                    checked = !checked;

                    if (mOnCheckedChangeListener != null) {

                        mOnCheckedChangeListener.OnCheckedChanged(checked);

                    }
                    invalidate();
                }
                break;
        }
        return super.onTouchEvent(event);
    }

    /**
     * 有些情况需要判断后才可开关;设置不自动切花状态
     *
     * @param open
     */
    public void openCheckedListerner(boolean open) {
        this.openChecked = open;
    }

    /**
     * 设置当前按钮状态
     *
     * @param status
     */
    public void setCheckStatus(boolean status) {
        mAnimate = 1;
        checked = status;

        if (mOnCheckedChangeListener != null) {

            mOnCheckedChangeListener.OnCheckedChanged(checked);

        }
        invalidate();
    }


    public void setText(String str) {
        textStr = str;
        textWidth = textPaint.measureText(textStr);
        fontMetrics = textPaint.getFontMetrics();
        Log.d("myDemo", "setText: ");
        requestLayout();
    }

    /**
     * 状态构造函数
     *
     * @return
     */
    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    /**
     * 构造函数
     *
     * @return
     */
    public OnCheckedChangeListener getmOnCheckedChangeListener() {
        return mOnCheckedChangeListener;
    }

    /**
     * 调用方法
     *
     * @param mOnCheckedChangeListener
     */
    public void setmOnCheckedChangeListener(OnCheckedChangeListener mOnCheckedChangeListener) {
        this.mOnCheckedChangeListener = mOnCheckedChangeListener;
    }

    /**
     * 滑动接口
     */
    public interface OnCheckedChangeListener {
        void OnCheckedChanged(boolean isChecked);
    }
}
