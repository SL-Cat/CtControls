package cat.customize.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import cat.customize.R;
import cat.customize.ulite.system.AndroidUtils;

/**
 * Created by HSL on 2022/4/29.
 */

public class CtRadioView extends View {

    private Paint paint;

    private int wearSize = 45;
    private Context context;


    public CtRadioView(Context context) {
        this(context, null);
    }

    public CtRadioView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CtRadioView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.IReadCleanPowerStyle);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        initRaduis(canvas);
    }

    private void initRaduis(Canvas canvas) {
        paint = new Paint();
        paint.setColor(Color.WHITE);

        int radusWith = getMeasuredWidth() / 2;
        int radusHegiht = getMeasuredHeight() / 2;
        canvas.drawCircle(radusWith, radusHegiht, getMeasuredWidth() / 2, paint);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ct_refesh_ig);

        int bitmapWidth = bitmap.getWidth();
        int bitmapHeight = bitmap.getHeight();
        canvas.drawBitmap(bitmap, radusWith - (bitmapWidth / 2), radusHegiht - (bitmapHeight / 2), paint);

        invalidate();
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = measureWidth(widthMeasureSpec);
        int height = measureHeight(heightMeasureSpec);
        //设置宽高
        setMeasuredDimension(width, height);
    }

    //根据xml的设定获取宽度
    private int measureWidth(int measureSpec) {
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        //wrap_content
        if (specMode == MeasureSpec.AT_MOST) {
            specSize = AndroidUtils.dp2px(context, wearSize);
        }
        //fill_parent或者精确值
        else if (specMode == MeasureSpec.EXACTLY) {
            specSize = AndroidUtils.dp2px(context, wearSize);
        }
        return specSize;
    }

    //根据xml的设定获取高度
    private int measureHeight(int measureSpec) {
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        //wrap_content
        if (specMode == MeasureSpec.AT_MOST) {
            specSize = AndroidUtils.dp2px(context, wearSize);
        }
        //fill_parent或者精确值
        else if (specMode == MeasureSpec.EXACTLY) {
            specSize = AndroidUtils.dp2px(context, wearSize);
        }
        return specSize;

    }
}
