package cat.customize.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import cat.customize.R;
import cat.customize.ulite.system.AndroidUtils;

/**
 * Created by HSL
 * on 2023/2/21.
 */

public class CtRadioTxView extends View {

    private Context context;

    private float textSize = 32f;

    private Paint paint;

    private Paint rectPaint;

    private String textStr = "测试";

    private int textColor = R.color.color_000000;

    private int rectColor = R.color.color_ff0000;

    private int index = -1;

    private int mWidth = 0;
    private Paint.FontMetrics fontMetrics;

    private int mHeight = 0;

    public CtRadioTxView(Context context) {
        this(context, null);
    }

    public CtRadioTxView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CtRadioTxView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        paint = new Paint();
        paint.setColor(Color.BLACK);
        rectPaint = new Paint();
        rectPaint.setColor(Color.RED);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        initView(canvas);
    }

    private void initView(Canvas canvas) {
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
        paint.setColor(getResources().getColor(textColor));
        rectPaint.setColor(getResources().getColor(rectColor));

        Rect rect = new Rect(0, 0, getWidth(), getHeight());
        RectF rectf = new RectF(rect);
        //绘制圆角矩形
        canvas.drawRoundRect(rectf, (float) (getHeight() / 1.2), (float) (getHeight() / 1.2), rectPaint);

        if (null != textStr && !"".equals(textStr)) {
            canvas.drawText(textStr, (getWidth() - mWidth)/2, rect.centerY() - fontMetrics.bottom / 2 - fontMetrics.top / 2, paint);
        }
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int specMode = MeasureSpec.getMode(widthMeasureSpec);
        if (textStr != null) {
            paint.setTextSize(AndroidUtils.dp2px(context, textSize));
            mWidth = (int) paint.measureText(textStr);

            fontMetrics = paint.getFontMetrics();
        }
        mHeight = (int) mWidth;
        setMeasuredDimension((int) (mWidth * 1.2), (int) (mHeight * 1.2));
    }


    public void setText(String text) {
        this.textStr = text;
    }

    public void setTextColor(int color) {
        this.textColor = color;
    }

    public void setTextSize(float size) {
        this.textSize = size;
    }

    public void setRectColor(int rectColor){
        this.rectColor = rectColor;
    }

    public void setIndex(int index){
        this.index =index;
    }

    public int getIndex(){
        return index;
    }
}
