package cat.hucustomize.list;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import java.util.ArrayList;
import java.util.List;

import cat.customize.xlist.XListView;
import cat.hucustomize.R;
import cat.hucustomize.ToastUlit;
import cat.hucustomize.adapter.LstAdapter;
import cat.hucustomize.adapter.RecyerLoadAdapter;

public class XListViewActivity extends AppCompatActivity implements XListView.IXListViewListener {
    private List<String> mList = new ArrayList<>();

    private LstAdapter lstAdapter;
    private XListView ls;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xlist_view);
        for (int i = 0; i < 20; i++) {
            mList.add("" + i);
        }
        ls();
    }

    private void ls() {
        ls = (XListView) findViewById(R.id.lst);
        lstAdapter = new LstAdapter(this, mList);
        ls.setPullLoadEnable(true);
        ls.setPullRefreshEnable(true);
        ls.setXListViewListener(this);
        ls.setAdapter(lstAdapter);
        ls.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ToastUlit.Toast(XListViewActivity.this, position + "");
            }
        });

        ls.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                ToastUlit.Toast(XListViewActivity.this, position + "");
                return true;
            }
        });
    }


    @Override
    public void onRefresh() {
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
