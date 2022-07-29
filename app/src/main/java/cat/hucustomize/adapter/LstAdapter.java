package cat.hucustomize.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import cat.customize.xlist.BaseListAdapter;
import cat.customize.xlist.SwipeItemLayout;
import cat.hucustomize.R;

/**
 * Created by HSL on 2022/7/29.
 */

public class LstAdapter extends BaseListAdapter<String> {




    public LstAdapter(Context context, List<String> list) {
        super(context, list);
    }

    @Override
    public View bindView(int position, View convertView, ViewGroup parent) {
        View view01 = mInflater.inflate(R.layout.test_item,null);
        View view02 = mInflater.inflate(R.layout.view_item,null);
        convertView = new SwipeItemLayout(view01,view02,null,null);
        return convertView;
    }
}
