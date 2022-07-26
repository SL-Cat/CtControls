package cat.hucustomize.adapter;

import android.content.Context;

import java.util.List;

import cat.customize.recyler.CommonRecycleAdapter;
import cat.customize.recyler.CommonViewHolder;
import cat.hucustomize.R;

/**
 * Created by HSL on 2022/7/26.
 */

public class RecyerLoadAdapter extends CommonRecycleAdapter<String> {

    public RecyerLoadAdapter(Context context, List<String> dataList) {
        super(context, dataList, R.layout.test_item);
    }

    @Override
    protected void bindData(CommonViewHolder holder, List<String> data, int position) {

    }
}
