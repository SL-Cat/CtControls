package cat.customize.fragment;

import android.content.Context;

import java.util.Timer;
import java.util.TimerTask;

import cat.customize.R;
import cat.customize.dialog.CtLoadDialog;
import cat.customize.dialog.CtToastDialog;
import cat.customize.ulite.system.AndroidUtils;

/**
 * Created by HSL
 * on 2023/4/17.
 */

public class CtUiHelper {
    private CtUiHelperListener ctUiHelperListener;
    private Context context;
    private static CtLoadDialog loadDialog;
    private static CtToastDialog toastDialog;

    private static int isLoadFlag = 0;
    private static Timer timer;


    public CtUiHelper(Context context, CtUiHelperListener ctUiHelperListener) {
        this.context = context;
        this.ctUiHelperListener = ctUiHelperListener;
    }


    private void initLoad() {
        if (loadDialog == null) {
            loadDialog = ctUiHelperListener.createDialog();
        } else {
            loadDialog = new CtLoadDialog(context);
        }
    }

    private void countLoad() {
        isLoadFlag = 0;
        if (timer == null) {
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    if (isLoadFlag > 10) {
                        dismissLoading();
                    } else {
                        isLoadFlag++;
                    }
                }
            }, 2000, 2000);
        }
    }


    /**
     * 显示加载中提示框
     */
    public void showLoading() {
        AndroidUtils.MainHandler.post(new Runnable() {
            @Override
            public void run() {
                initLoad();
                countLoad();
                if (loadDialog != null && loadDialog.isShowing()) return;
                loadDialog.setInfoTv(context.getString(R.string.ct_loading_text));
                loadDialog.show();
            }
        });
    }


    /**
     * 显示加载框 or 更新加载框内容
     * @param context
     * @param msg
     */
    public void showLoading(Context context, String msg) {
        AndroidUtils.MainHandler.post(new Runnable() {
            @Override
            public void run() {
                initLoad();
                countLoad();
                loadDialog.setInfoTv(msg);
                loadDialog.show();
            }
        });
    }

    /**
     * 关闭加载框
     */
    public void dismissLoading() {
        if (loadDialog != null && loadDialog.isShowing()) {
            AndroidUtils.MainHandler.post(new Runnable() {
                @Override
                public void run() {
                    if (loadDialog != null && loadDialog.isShowing()) {
                        loadDialog.dismiss();
                        loadDialog = null;
                        isLoadFlag = 0;
                    }
                }
            });
        }
    }

    private void initDialog() {
        if (toastDialog == null) {
            toastDialog = ctUiHelperListener.createToast();
        } else {
            toastDialog = new CtToastDialog(context);
        }
    }


    /**
     * 普通提示的吐丝
     * * @param info
     */
    public void toast(String info) {
        AndroidUtils.MainHandler.post(new Runnable() {
            @Override
            public void run() {
                initDialog();
                if (null != info && !"".equals(info)) {
                    toastDialog.setInfoText(info);
                }
                if (!toastDialog.isShowing()) {
                    toastDialog.show();
                }
            }
        });
    }


    /**
     * 异常提示的吐丝
     *
     * @param info
     */
    public void toastError(int ig, String info) {
        initDialog();
        if (ig != -1)
            toastDialog.setTitleBackground(ig);
        if (null != info) {
            toastDialog.setInfoText(info);
        }
        if (!toastDialog.isShowing()) {
            toastDialog.show();
        }
    }

    /**
     * 成功提示的吐丝
     *
     * @param info
     */
    public void toastSuccess(int ig, String info) {
        initDialog();
        if (ig != -1)
            toastDialog.setTitleBackground(ig);
        if (null != info) {
            toastDialog.setInfoText(info);
        }
        if (!toastDialog.isShowing()) {
            toastDialog.show();
        }
    }

    /**
     * 关闭吐丝
     */
    public void closeToast() {
        if (toastDialog != null && toastDialog.isShowing()) {
            toastDialog.dismiss();
            toastDialog = null;
        }
    }

    /**
     * 关闭所有提示框
     */
    public void cancelUlite() {
        if (toastDialog != null || loadDialog != null) {
            AndroidUtils.MainHandler.post(new Runnable() {
                @Override
                public void run() {
                    if (toastDialog != null && toastDialog.isShowing()) {
                        toastDialog.dismiss();
                        toastDialog = null;
                    }
                    if (loadDialog != null && loadDialog.isShowing()) {
                        loadDialog.dismiss();
                        loadDialog = null;
                    }
                    if (timer != null) {
                        timer.cancel();
                        timer = null;
                    }
                }
            });
        }
    }
}
