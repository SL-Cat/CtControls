package cat.customize.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by HSL on 2022/1/12.
 */

@SuppressLint("AppCompatCustomView")
public class CtStrText extends TextView {

    private String TAG = CtStrText.class.getSimpleName();

    public CtStrText(Context context) {
        super(context);
    }

    public CtStrText(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CtStrText(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.setIncludeFontPadding(false);
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        if(text!=null&& text.toString()!=null) {
            if (text.toString().trim() != null && !"NULL".equals(text.toString().trim().toUpperCase())) {
                super.setText(text, type);
            } else {
                super.setText("-", type);
            }
        }else {
            super.setText("-", type);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

}
