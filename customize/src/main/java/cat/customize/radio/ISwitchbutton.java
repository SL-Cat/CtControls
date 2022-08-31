package cat.customize.radio;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import cat.customize.R;
import cat.customize.ulite.DensityUtil;

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
    private float mAnimate = 0L;
    //此处命名不规范，目的和Android自带的switch有相同的用法
    private boolean checked = false;
    private float mScale;
    private int mSelectColor;
    private OnCheckedChangeListener mOnCheckedChangeListener;

    public ISwitchbutton(Context context) {
        this(context, null);
    }

    public ISwitchbutton(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ISwitchStyle);
        mSelectColor = typedArray.getColor(R.styleable.ISwitchStyle_switch_button_color, context.getResources().getColor(R.color.color_007BFF));
        MBTNHEIGHT = typedArray.getFloat(R.styleable.ISwitchStyle_switch_button_height, 0.5f);
        checked = typedArray.getBoolean(R.styleable.ISwitchStyle_switch_button_click, false);
        typedArray.recycle();
    }

    /**
     * @param widthMeasureSpec
     * @param heightMeasureSpec 高度是是宽度的0.55倍
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int specMode = MeasureSpec.getMode(widthMeasureSpec);
        int width=0;
        //wrap_content
        if (specMode == MeasureSpec.AT_MOST) {
            width = 60;
            mHeight = (int) (MBTNHEIGHT * width);
        }
        //fill_parent或者精确值
        else if (specMode == MeasureSpec.EXACTLY) {
            width = MeasureSpec.getSize(widthMeasureSpec);
            mHeight = (int) (MBTNHEIGHT * width);
        } else {
            width = MeasureSpec.getSize(widthMeasureSpec);
            mHeight = (int) (MBTNHEIGHT * width);
        }

        setMeasuredDimension(width, mHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);
        mPaint.setColor(mSelectColor);
        Rect rect = new Rect(0, 0, getWidth(), getHeight());
        RectF rectf = new RectF(rect);
        //绘制圆角矩形
        canvas.drawRoundRect(rectf, mHeight / 2, mHeight / 2, mPaint);

        //以下save和restore很重要，确保动画在中间一层 ，如果大家不明白，可以去搜下用法

        canvas.save();
        mPaint.setColor(Color.parseColor("#CFCFCF"));
        mAnimate = mAnimate - 0.1f > 0 ? mAnimate - 0.1f : 0; // 动画标示 ，重绘10次，借鉴别人的动画
        mScale = (!checked ? 1 - mAnimate : mAnimate);
        canvas.scale(mScale, mScale, getWidth() - getHeight() / 2, rect.centerY());
        //绘制缩放的灰色圆角矩形
        canvas.drawRoundRect(rectf, mHeight / 2, mHeight / 2, mPaint);

        mPaint.setColor(Color.WHITE);
        Rect rect_inner = new Rect(OFFSET, OFFSET, getWidth() - OFFSET, getHeight() - OFFSET);
        RectF rect_f_inner = new RectF(rect_inner);
        //绘制缩放的白色圆角矩形，和上边的重叠实现灰色边框效果
        canvas.drawRoundRect(rect_f_inner, (mHeight - 8) / 2, (mHeight - 8) / 2, mPaint);
        canvas.restore();

        //中间圆形平移
        int sWidth = getWidth();
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
                mAnimate = 1;
                checked = !checked;

                if (mOnCheckedChangeListener != null) {

                    mOnCheckedChangeListener.OnCheckedChanged(checked);

                }
                invalidate();
                break;
        }
        return super.onTouchEvent(event);
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
