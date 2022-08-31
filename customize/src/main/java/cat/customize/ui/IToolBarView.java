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

public class IToolBarView extends LinearLayout implements View.OnClickListener {

    private TextView title;
    private ImageView leftImage;
    private ImageView rightImage;
    private RelativeLayout relativeLayout;
    private Context context;
    private int toolBarBackground;

    private OnToolBarListener onToolBarListener;

    public interface OnToolBarListener{
        void onClickLeft();
        void onClickRight();
    }

    public void setOnToolBarListener(OnToolBarListener onToolBarListener){
        this.onToolBarListener = onToolBarListener;
    }

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

        leftImage.setOnClickListener(this);
        rightImage.setOnClickListener(this);

        initStye(attrs);
    }

    private void initStye(@Nullable AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.IToolBarStyle);
        toolBarBackground = typedArray.getColor(R.styleable.IRadiosStyle_item_background, context.getResources().getColor(R.color.color_6610F2));
        int rightImageBackground = typedArray.getResourceId(R.styleable.IRadiosStyle_radios_click_more, R.mipmap.ct_more);
        String titleStr = typedArray.getString(R.styleable.IToolBarStyle_title_text);
        int leftImageBackground = typedArray.getResourceId(R.styleable.IToolBarStyle_left_image, R.mipmap.ct_back_ig);
        boolean hideRightImage = typedArray.getBoolean(R.styleable.IToolBarStyle_hide_right_image, false);

        if(titleStr!=null) {
            title.setText(titleStr);
        }
        if (hideRightImage) {
            rightImage.setVisibility(VISIBLE);
        } else {
            rightImage.setVisibility(GONE);
        }
        relativeLayout.setBackgroundResource(toolBarBackground);
        leftImage.setImageResource(leftImageBackground);
        rightImage.setImageResource(rightImageBackground);
    }

    public void setTitle(String str){
        if(str!=null){
            title.setText(str);
        }else {
            title.setText("");
        }
    }

    public ImageView setLeftImage(int imageId){
        leftImage.setImageResource(imageId);
        return leftImage;
    }

    public ImageView setRightImage(int imageId){
        rightImage.setVisibility(VISIBLE);
        rightImage.setImageResource(imageId);
        return rightImage;
    }


    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.ct_toolbar_left_ig){
            if(onToolBarListener!=null){
                onToolBarListener.onClickLeft();
            }
        }
        if(v.getId()==R.id.ct_toolbar_right_ig){
            if(onToolBarListener!=null){
                onToolBarListener.onClickRight();
            }
        }
    }
}
