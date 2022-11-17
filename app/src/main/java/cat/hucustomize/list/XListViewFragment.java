package cat.hucustomize.list;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import java.util.ArrayList;
import java.util.List;

import cat.customize.xlist.XListView;
import cat.hucustomize.R;
import cat.hucustomize.ToastUlit;
import cat.hucustomize.adapter.LstAdapter;

/**
 * Created by HSL
 * on 2022/11/10.
 */

public class XListViewFragment extends Fragment implements XListView.IXListViewListener{
    private List<String> mList = new ArrayList<>();

    private LstAdapter lstAdapter;
    private XListView ls;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_xlist_view, container, false);
        for (int i = 0; i < 20; i++) {
            mList.add("" + i);
        }
        lsInit(view);
        return view;
    }

    private void lsInit(View view) {
        ls = (XListView) view.findViewById(R.id.lst);
        lstAdapter = new LstAdapter(getActivity(), mList);
        ls.setPullLoadEnable(true);
        ls.setPullRefreshEnable(true);
        ls.setXListViewListener(this);
        ls.setAdapter(lstAdapter);
        ls.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

        ls.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                ToastUlit.Toast(getActivity(), position + "");
                return true;
            }
        });
    }

    @Override
    public void onRefresh() {
        Log.d("myDemo", "onRefresh: ");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ls.stopRefresh();
            }
        }, 2000);
    }

    @Override
    public void onLoadMore() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ls.stopLoadMore();
            }
        }, 2000);
    }
}
