package cat.customize.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;

/**
 * Created by HSL on 2022/8/1.
 */

public class CtWaveRfidView extends View {

    private Paint paint;
    private Path path;

    // view宽度
    private int width;
    // view高度
    private int height;

    // 波浪高低偏移量
    private int offset = 20;

    // X轴，view的偏移量
    private int xoffset = 0;

    // view的Y轴高度
    private int viewY = 0;

    // 波浪速度
    private int waveSpeed = 50;

    private ValueAnimator animator;

    public CtWaveRfidView(Context context) {
        this(context,null);
    }

    public CtWaveRfidView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CtWaveRfidView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }


    private void init(Context context) {

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);

        paint.setStrokeWidth(5);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.RED);

        path = new Path();

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        width = wm.getDefaultDisplay().getWidth();
        height = wm.getDefaultDisplay().getHeight();

        animator = new ValueAnimator();
        animator.setFloatValues(0, width);
        animator.setDuration(waveSpeed * 20);
        animator.setRepeatCount(-1);
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float change = (float) animation.getAnimatedValue();
                xoffset = (int) change;
                invalidate();
            }
        });

        animator.start();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec));
    }

    private int measureWidth(int measureSpec) {
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        //设置一个默认值，就是这个View的默认宽度为500，这个看我们自定义View的要求
        int result = 200;
        if (specMode == MeasureSpec.AT_MOST) {//相当于我们设置为wrap_content
            result = specSize;
        } else if (specMode == MeasureSpec.EXACTLY) {//相当于我们设置为match_parent或者为一个具体的值
            result = specSize;
        }
        width = result;
        return result;
    }

    private int measureHeight(int measureSpec) {
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        int result = 200;
        if (specMode == MeasureSpec.AT_MOST) {
            result = specSize;
        } else if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        }
        height = specSize;
        return result;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 保存画布当前状态
        canvas.save();

        // 裁剪画布
        Path clipPath = new Path();
        clipPath.addCircle(540,850,300, Path.Direction.CW);
        canvas.clipPath(clipPath);
        path.reset();

        viewY = height / 2;

        // 绘制屏幕外的波浪
        path.moveTo(xoffset - width, viewY);
        path.quadTo(width / 4 + xoffset - width, viewY - offset, width / 2 + xoffset - width, viewY);
        path.moveTo(width / 2 + xoffset - width, viewY);
        path.quadTo(width / 4 * 3 + xoffset - width, viewY + offset, width + xoffset - width, viewY);

        // 绘制屏幕内的波浪
        path.moveTo(xoffset, viewY);
        path.quadTo(width / 4 + xoffset, viewY - offset, width / 2 + xoffset, viewY);
        path.moveTo(width / 2 + xoffset, viewY);
        path.quadTo(width / 4 * 3 + xoffset, viewY + offset, width + xoffset, viewY);

        // 新增了这里
        path.lineTo(width + xoffset, height);
        path.lineTo(xoffset - width, height);
        path.lineTo(xoffset - width, viewY);

        canvas.drawPath(path, paint);

        // 将画布还原成裁剪前的状态
        canvas.restore();

    }
}
