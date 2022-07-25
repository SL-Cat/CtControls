package cat.customize.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import cat.customize.R;

/**
 * Created by HSL on 2022/7/5.
 */

public class BaseDialog extends Dialog {
    protected Activity mActivity;

    public BaseDialog(@NonNull Context context) {
        this(context, R.style.radiusDialog);
    }

    public BaseDialog(@NonNull Context context,int style){
        super(context,style);
        if (context instanceof Activity) {
            mActivity = (Activity) context;
        }
    }

    public void setBig(int width, int height) {
        WindowManager.LayoutParams params = getWindow().getAttributes();
        if (width > 0) {
            params.width = width;
        }
        if (height > 0) {
            params.height = height;
        }
        getWindow().setAttributes(params);
    }


    //按屏幕比例
    public void setBigByScreen(float width, float height) {
        WindowManager.LayoutParams params = getWindow().getAttributes();
        DisplayMetrics dm = getContext().getResources().getDisplayMetrics();
        int screenWidth = dm.widthPixels;
        // 屏幕宽（像素，如：px）
        int screenHeight = dm.heightPixels; // 屏幕高（像素，如：px）

        if (width > 0) {
            params.width = (int) (screenWidth * width);
        }
        if (height > 0) {
            params.height = (int) (screenHeight * height);
        }
        getWindow().setAttributes(params);
    }

    public void setBigByScreenWidth(float width){
        int paramsWidth = 0;
        Window window = getWindow();
        assert window != null;
        DisplayMetrics dm = getContext().getResources().getDisplayMetrics();
        int screenWidth = dm.widthPixels;
        if (width > 0 && screenWidth > 0){
            paramsWidth = (int) (screenWidth * width);
        }

        window.setLayout(paramsWidth, LinearLayout.LayoutParams.WRAP_CONTENT);
    }

    public void setBigByScreenWidthHeight(float width,float height){
        int paramsWidth = 0;
        int paramsHeight = 0;
        Window window = getWindow();
        assert window != null;
        DisplayMetrics dm = getContext().getResources().getDisplayMetrics();
        int screenHeight = dm.heightPixels;
        int screenWidth = dm.widthPixels;
        if (width > 0 && screenWidth > 0){
            paramsWidth = (int) (screenWidth * width);
        }
        if (height > 0 && screenHeight > 0){
            paramsHeight = (int) (screenHeight * height);
        }
        window.setLayout(paramsWidth, paramsHeight);
    }

    //按屏幕比例
    public void setBigFullExceptStateBar() {
        WindowManager.LayoutParams params = getWindow().getAttributes();
        DisplayMetrics dm = getContext().getResources().getDisplayMetrics();
        int screenWidth = dm.widthPixels;
        int screenHeight = dm.heightPixels;
        params.width = screenWidth;
        params.height = screenHeight - getStatusHeight(getContext());
        getWindow().setAttributes(params);
    }

    /**
     * 获得状态栏的高度
     *
     * @param context
     * @return
     */
    public static int getStatusHeight(Context context) {
        int statusHeight = -1;
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height")
                    .get(object).toString());
            statusHeight = context.getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            Log.e("BaseDialog", "获取状态栏高度失败");
            e.printStackTrace();
        }
        return statusHeight;
    }

    // 设置弹出窗口的和背景透明度。范围0-1f，，1是不透明，0是透明
    public void setalpha(float bg, float dialog) {
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.alpha = dialog;// 弹框的透明度
        params.dimAmount = bg;// 背景的透明度
        getWindow().setAttributes(params);
    }

    //设置弹出位置
    public void setGravity(int gravity) {
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = gravity;
        getWindow().setAttributes(params);
    }

    //设置偏移
    public void setShowParams(int x, int y) {
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.x = x;//设置x坐标
        params.y = y;//设置y坐标
        getWindow().setAttributes(params);
    }


    //设置弹出或者关闭动画
    public void setAnim(int animStyle) {
        getWindow().setWindowAnimations(animStyle);
    }

    @Override
    public boolean onKeyDown(int keyCode, @NonNull KeyEvent event) {
        if (mActivity != null) {
            return mActivity.onKeyDown(keyCode, event);
        }
        return super.onKeyDown(keyCode, event);
    }
}
