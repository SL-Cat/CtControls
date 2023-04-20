package cat.customize.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import cat.customize.R;
import cat.customize.ulite.system.AndroidUtils;
import cat.customize.ulite.system.CtLog;

/**
 * /**
 * Created by HKR on 2022/10/01.
 * <p>
 * 描述：  带进度条的按钮
 */
public class CtRadioProgressView extends View {
    /**
     * 中心点X轴坐标
     */

    private int viewCenterX;

    /**
     * 中心点Y轴坐标
     */

    private int viewCenterY;

    /**
     * 有效长度的一半(View长宽较小者的一半)
     */

    private int viewHalfLength;


    /**
     * 包围最外侧圆环的矩形
     */

    private RectF rectF = new RectF();

    /**
     * 包围进度圆弧的矩形
     */

    private RectF rectF2 = new RectF();

    /**
     * 进度
     */

    private float progress = 0;


    /**
     * 是否进行过了测量
     */

    private boolean isMeasured = false;

    /**
     * 画笔颜色
     */

    private int colora = 0xffffffff;
    private int colorb = R.color.color_007BFF;

    /**
     * 最外侧圆环画笔
     */

    private Paint paintA = new Paint();

    /**
     * 进度圆弧画笔
     */

    private Paint paintB = new Paint();

    /**
     * 进度的总值
     */
    private int maxProgress = 100;

    /**
     * 当前进度值
     */
    private int current_num = 0;

    /**
     * 状态监听器
     */

    private OnStatusChangeListener onStatusChangeListener;

    /**
     * 构造器
     */

    public CtRadioProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    @Override

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        if (!isMeasured) {
            getWidthAndHeight();

            isMeasured = true;

        }

    }

    /**
     * 得到视图等的高度宽度尺寸数据
     */

    private void getWidthAndHeight() {
        int viewHeight = getMeasuredHeight();

        int viewWidth = getMeasuredWidth();

        viewCenterX = viewWidth / 2;

        viewCenterY = viewHeight / 2;

        viewHalfLength = viewHeight < viewWidth ? viewHeight / 2 : viewWidth / 2;

        int paintAwidth = viewHalfLength / 2;

        int paintBwidth = viewHalfLength / 2;

        rectF.left = viewCenterX - (viewHalfLength - paintAwidth / 2);

        rectF.top = viewCenterY - (viewHalfLength - paintAwidth / 2);

        rectF.right = viewCenterX + (viewHalfLength - paintAwidth / 2);

        rectF.bottom = viewCenterY + (viewHalfLength - paintAwidth / 2);

        rectF2.left = viewCenterX - (viewHalfLength - paintBwidth / 2);

        rectF2.top = viewCenterY - (viewHalfLength - paintBwidth / 2);

        rectF2.right = viewCenterX + (viewHalfLength - paintBwidth / 2);

        rectF2.bottom = viewCenterY + (viewHalfLength - paintBwidth / 2);

        paintA.setColor(colora);
        paintA.setStrokeWidth(paintAwidth);
        paintA.setAntiAlias(true);
        paintA.setStyle(Paint.Style.STROKE);
        paintA.setTextSize(50);

        paintB.setColor(getResources().getColor(colorb));
        paintB.setStrokeWidth(paintBwidth);
        paintB.setAntiAlias(true);
        paintB.setStyle(Paint.Style.STROKE);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

// 画未完成进度的圆环
        canvas.drawArc(rectF, 0, 360, false, paintA);

        // 画已经完成进度的圆弧 从-90度开始，即从圆环顶部开始
        canvas.drawArc(rectF2, -90, progress * 3.6f, false, paintB);

        Paint paint = new Paint();
        paint.setTextSize(32);
        paint.setColor(getResources().getColor(R.color.color_000000));
        paint.setAntiAlias(true);
        float v = paint.measureText("" + current_num);
        canvas.drawText(current_num + "", (getWidth() / 2) - (v / 2), viewCenterY + 16, paint);


    }

    /**
     * 设置总数
     *
     * @param maxProgress
     */
    public void setMaxProgress(int maxProgress) {
        this.maxProgress = maxProgress;
    }

    /**
     * 设置当前数
     */
    public void setProgress(int operation) {
        if (operation < 0) {
            operation = 0;

        }
        if (operation <= maxProgress) {
            if (maxProgress != -1) {
                double i = (operation / (double) maxProgress) * 100;
                progress = (float) i;
            } else {
                progress = 0;
            }
            this.current_num = operation;
            invalidate();
        }

    }


    /**
     * 播放暂停状态监听的接口
     */

    public interface OnStatusChangeListener {
        void over();

    }

    /**
     * 设置监听接口
     */

    public void setOnStatusChangeListener(OnStatusChangeListener onStatusChangeListener) {
        this.onStatusChangeListener = onStatusChangeListener;

    }

    /**
     * 位置信息
     */

    private class Point {
        float x;

        float y;

    }
}