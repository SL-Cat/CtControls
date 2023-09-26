package cat.customize.ui.ic;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import cat.customize.R;


/**
 * Created by HSL
 * on 2023/5/17.
 */

public class ICToolBar extends LinearLayout {
    private Context context;
    private TextView bigTv;
    private TextView midTv;
    private ImageView leftIg;
    private ImageView dwonIg;
    private ImageView rightIg;

    public ICToolBar(Context context) {
        this(context,null);
    }

    public ICToolBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ICToolBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        View view = View.inflate(context, R.layout.ic_toolbar_layout, this);
        bigTv = (TextView) view.findViewById(R.id.ic_title_big_title);
        midTv = (TextView) view.findViewById(R.id.ic_title_mind_title);
        leftIg = (ImageView) view.findViewById(R.id.ic_title_left_ig);
        dwonIg = (ImageView) view.findViewById(R.id.ic_title_big_down_ig);
        rightIg = (ImageView) view.findViewById(R.id.ic_title_right_ig);
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.IcToolBar);
        String titleStr = typedArray.getString(R.styleable.IcToolBar_text);
        bigTv.setText(titleStr);
    }

    public void setOnLeftListener(OnClickListener onClickListener){
        leftIg.setOnClickListener(onClickListener);
    }

    public void setOnRightListener(int imageId, OnClickListener onClickListener){
        rightIg.setVisibility(VISIBLE);
        rightIg.setImageResource(imageId);
        rightIg.setOnClickListener(onClickListener);
    }

    public void setOnRightListener(OnClickListener onClickListener){
        if(onClickListener!=null) {
            rightIg.setVisibility(VISIBLE);
            rightIg.setOnClickListener(onClickListener);
        }else {
            rightIg.setVisibility(GONE);
        }
    }



    public void setMidTv(String str){
        if(str!=null){
            midTv.setVisibility(VISIBLE);
            midTv.setText(str);
        }
    }

    public ImageView getLeftIg(){
        return leftIg;
    }

    public ImageView getRightIg(){
        return rightIg;
    }

    public void setOnTitleListener(OnClickListener onTitleListener){
        if(onTitleListener!=null) {
            dwonIg.setVisibility(VISIBLE);
            bigTv.setOnClickListener(onTitleListener);
            dwonIg.setOnClickListener(onTitleListener);
        }else dwonIg.setVisibility(GONE);
    }

    public void setBigTv(String str){
        bigTv.setText(str);
    }

    public void setTbEnabled(boolean enabled){
        dwonIg.setEnabled(enabled);
        bigTv.setEnabled(enabled);
        leftIg.setEnabled(enabled);
        rightIg.setEnabled(enabled);
    }
}
