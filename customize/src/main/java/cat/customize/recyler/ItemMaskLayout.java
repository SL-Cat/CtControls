package cat.customize.recyler;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import cat.customize.R;


/**
 * Created by HSL
 * on 2022/7/16.
 *
 * 长按条目遮罩界面
 */
public class ItemMaskLayout extends LinearLayout {

    public ItemMaskLayout(Context context) {
        this(context, null);
    }

    public ItemMaskLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ItemMaskLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.ct_list_item_mask, this, true);
    }
}
