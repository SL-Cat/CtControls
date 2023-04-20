package cat.customize.dialog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import cat.customize.R;
import cat.customize.xlist.BaseListAdapter;

/**
 * Created by HSL
 * on 2023/4/3.
 */

public class CtListDialog extends BaseDialog {

    private final TextView cancelTv;
    private final TextView confirmTv;
    private final TextView titleTv;
    private final ListView listView;

    public CtListDialog(@NonNull Context context) {
        super(context, R.style.ct_RadiusDialog);
        //通过LayoutInflater获取布局
        View view = LayoutInflater.from(getContext()).inflate(R.layout.ct_dialog_listview_layout, null);
        cancelTv = ((TextView) view.findViewById(R.id.ct_dialog_list_cancel));
        confirmTv = ((TextView) view.findViewById(R.id.ct_dialog_list_confirm));
        titleTv = ((TextView) view.findViewById(R.id.ct_dialog_list_title));
        listView = ((ListView) view.findViewById(R.id.ct_dialog_list_ls));

        setContentView(view);  //设置显示的视图
        setCancelable(false);
        setBigByScreenWidthHeight(0.8f, 0.6f);

    }

    public void setAdapter(BaseListAdapter adapter,AdapterView.OnItemClickListener onItemClickListener) {
        if (adapter != null) {
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if(onItemClickListener!=null){
                        onItemClickListener.onItemClick(parent,view,position,id);
                    }
                }
            });
        }
    }

    public void confirmListener(View.OnClickListener onClickListener){
        if(onClickListener!=null){
            confirmTv.setVisibility(View.VISIBLE);
            confirmTv.setOnClickListener(onClickListener);
        }
    }
}
