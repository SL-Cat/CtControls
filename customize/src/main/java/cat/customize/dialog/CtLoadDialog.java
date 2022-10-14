package cat.customize.dialog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.os.Handler;


import cat.customize.R;
import cat.customize.animation.Ratate3DAnimation;
import cat.customize.ulite.system.AndroidUtils;
import cat.customize.view.BaseDialog;

/**
 * Created by HSL
 * on 2022/9/8.
 */

public class CtLoadDialog extends BaseDialog {

    private RelativeLayout dialogBg;
    private ImageView leftImage;
    private TextView infoTv;
    private Ratate3DAnimation rotate3dAnimation;
    private Ratate3DAnimation openAnimation;
    private int duration  =1000;

    public CtLoadDialog(@NonNull Context context) {
        super(context);
        initView(context);
        this.setCancelable(false);
    }

    private void initView(Context context) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View dialogView = layoutInflater.inflate(R.layout.ct_load_dialog, null, false);
        setContentView(dialogView);
        dialogBg = ((RelativeLayout) dialogView.findViewById(R.id.ct_load_rl));
        leftImage = ((ImageView) dialogView.findViewById(R.id.ct_load_dialog_ig));
        infoTv = ((TextView) dialogView.findViewById(R.id.ct_load_dialog_info));
        setBigByScreenWidth(0.8f);
        initAnimation();
        //设置显示弹窗背景不变暗
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        //调整明暗度，float值，完全透明不变暗是0.0f，完全变暗不透明是1.0f
        lp.dimAmount = 0.2f;
        //必须要设置回去
        getWindow().setAttributes(lp);
        //根据谷歌文档，给对应的Window添加FLAG_DIM_BEHIND标志位，dimAmount值才有效。
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
    }

    /**
     * 设置加载动画时间
     * @param time
     */
    public void setAnimationTime(int time){
        this.duration = time;
    }

    public void setInfoTv(String str){
        if (!TextUtils.isEmpty(str)) {
            infoTv.setText(str);
        }
    }

    private void initAnimation() {
        rotate3dAnimation = new Ratate3DAnimation(180, 360, false);
        rotate3dAnimation.setDuration(duration);
        rotate3dAnimation.setFillAfter(true);
        rotate3dAnimation.setInterpolator(new AccelerateInterpolator());

        openAnimation = new Ratate3DAnimation(0, 180, true);
        openAnimation.setDuration(duration);
        openAnimation.setFillAfter(true);
        openAnimation.setInterpolator(new AccelerateInterpolator());
        openAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }
            //翻转结束时
            @Override
            public void onAnimationEnd(Animation animation) {
                leftImage.startAnimation(rotate3dAnimation);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        rotate3dAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }
            //翻转结束时
            @Override
            public void onAnimationEnd(Animation animation) {
                AndroidUtils.MainHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        leftImage.startAnimation(openAnimation);
                    }
                },200);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    @Override
    public void show() {
        super.show();
        leftImage.startAnimation(openAnimation);
    }

    @Override
    public void dismiss() {
        leftImage.clearAnimation();
        super.dismiss();
    }

}
