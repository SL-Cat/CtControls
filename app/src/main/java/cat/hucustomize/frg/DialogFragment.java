package cat.hucustomize.frg;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cat.customize.dialog.CtLoadDialog;
import cat.customize.dialog.CtPromtDialog;
import cat.customize.dialog.CtTipsDialog;
import cat.customize.ulite.system.AndroidUtils;
import cat.hucustomize.R;
import cat.hucustomize.SecondActivity;

/**
 * Created by HSL
 * on 2023/3/13.
 */

public class DialogFragment extends Fragment {


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View logView = inflater.inflate(R.layout.dialog_layout,container,false);
        initView(logView);
        return logView;
    }

    private int count = 0;
    private void initView(View logView) {
        TextView loadTv = (TextView) logView.findViewById(R.id.ct_second_load_dialog);
        loadTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CtLoadDialog loadDialog = new CtLoadDialog(getActivity());
                count = 0;
                loadDialog.show();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while (count < 5) {
                            count++;
                            SystemClock.sleep(1000);
                        }
                        loadDialog.dismiss();
                    }
                }).start();
            }
        });

        logView.findViewById(R.id.ct_second_btn_dialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = "内容很长xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx";
                final CtPromtDialog hintDialog = new CtPromtDialog(getActivity());
                hintDialog.setButtonLeftText("确认", -1);
                hintDialog.setButtonRightText("取消", -1);
                hintDialog.setCancelable(false);
                hintDialog.setMsgTextSize(20);
                hintDialog.setTitleTextSize(25);
                hintDialog.setBigByScreenWidthHeight(0.6f, 0.4f);
                hintDialog.setDialogAttributes(0.5f);
                hintDialog.setMessageText(str + str + str + str + str + str);
                //右侧
                hintDialog.setOnRightListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //如果对话框处于显示状态
                        if (hintDialog.isShowing()) {
                            hintDialog.dismiss();//关闭对话框
                        }
                    }
                });
                //左侧
                hintDialog.setOnLeftlListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AndroidUtils.MainHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if (hintDialog != null && hintDialog.isShowing()) {
                                    hintDialog.dismiss();
                                }
                            }
                        }, 500);
                    }
                });
                hintDialog.show();
            }
        });

        logView.findViewById(R.id.ct_confirm_dialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CtTipsDialog dateDialog = new CtTipsDialog(getActivity());
                dateDialog.setTitle("标题");
                dateDialog.setMsgTv("内容");
                dateDialog.show();
                dateDialog.setOnComfrimListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dateDialog.dismiss();
                    }
                });
            }
        });
    }
}
