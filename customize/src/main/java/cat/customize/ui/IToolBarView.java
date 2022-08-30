package cat.customize.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cat.customize.R;

/**
 * Created by HSL
 * on 2022/8/29.
 */

public class IToolBarView extends LinearLayout {


    private TextView title;
    private ImageView leftImage;
    private ImageView rightImage;
    private RelativeLayout relativeLayout;
    private Context context;
    private int toolBarBackground;

    public IToolBarView(Context context) {
        this(context,null);
    }

    public IToolBarView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public IToolBarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        View view = View.inflate(context, R.layout.ct_toolbar_layout, this);
        relativeLayout = ((RelativeLayout) view.findViewById(R.id.ct_toolbar_rl));
        title = ((TextView) view.findViewById(R.id.ct_toolbar_center_title));
        leftImage = ((ImageView) view.findViewById(R.id.ct_toolbar_left_ig));
        rightImage = ((ImageView) view.findViewById(R.id.ct_toolbar_right_ig));
        initStye(attrs);

    }

    private void initStye(@Nullable AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.IToolBarStyle);
        toolBarBackground = typedArray.getColor(R.styleable.IRadiosStyle_item_background, context.getResources().getColor(R.color.color_6610F2));
        int rightImageBackground = typedArray.getResourceId(R.styleable.IRadiosStyle_radios_click_more, R.mipmap.ct_more);
        String titleStr = typedArray.getString(R.styleable.IToolBarStyle_title_text);
        int leftImageBackground = typedArray.getResourceId(R.styleable.IToolBarStyle_left_image, R.mipmap.ct_back_ig);

        if(titleStr!=null) {
            title.setText(titleStr);
        }
        relativeLayout.setBackgroundResource(toolBarBackground);
        leftImage.setImageResource(leftImageBackground);
        rightImage.setImageResource(rightImageBackground);
    }



}
