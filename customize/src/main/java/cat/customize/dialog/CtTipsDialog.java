package cat.customize.dialog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import cat.customize.R;

/**
 * Created by HSL
 * on 2022/10/12.
 */

public class CtTipsDialog extends BaseDialog {
    private Context context;
    private final TextView title;
    private final TextView msgTv;
    private final TextView comfirmTv;
    private final LinearLayout bgLl;
    private final View logView;

    public CtTipsDialog(@NonNull Context context) {
        super(context);
        this.context = context;
        logView = LayoutInflater.from(context).inflate(R.layout.ct_tips_dialog, null);
        bgLl = (LinearLayout) logView.findViewById(R.id.ct_tips_dialog_Ll);
        title = (TextView) logView.findViewById(R.id.ct_tips_dialog_title);
        msgTv = (TextView) logView.findViewById(R.id.ct_tips_dialog_msg);
        comfirmTv = (TextView) logView.findViewById(R.id.ct_tips_dialog_comfrim);
        msgTv.setVisibility(View.GONE);
        setCancelable(false);
        setContentView(logView);
        setBigByScreenWidth(0.9f);
    }

    public void setTitle(String titleStr){
        title.setText(titleStr);
    }

    public void setMsgTv(String msg){
        if(msg!=null&&!"".equals(msg)) {
            msgTv.setVisibility(View.VISIBLE);
            msgTv.setText(msg);
        }
    }

    public void setConfirmTv(String str){
        comfirmTv.setText(str);
    }

    public void setOnComfrimListener(View.OnClickListener listener) {
        comfirmTv.setOnClickListener(listener);
    }

    public TextView getTitle(){
        return title;
    }

    public TextView getMsgTv(){
        return msgTv;
    }

    public TextView getComfirmTv(){
        return comfirmTv;
    }

    public LinearLayout getBgLl(){
        return bgLl;
    }
}
