package cat.hucustomize;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cat.customize.recyler.CommonRecycleAdapter;
import cat.customize.recyler.TouchCallbackRecyclerView;
import cat.customize.xlist.XListView;
import cat.hucustomize.adapter.LstAdapter;
import cat.hucustomize.adapter.RecyerLoadAdapter;

public class RecyerActivity extends AppCompatActivity implements XListView.IXListViewListener {

    private TouchCallbackRecyclerView ry;

    private RecyerLoadAdapter adapter;

    private LstAdapter lstAdapter;


    private List<String> mList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyer);
        ry();
        ls();
    }

    private void ls() {
        XListView ls = (XListView) findViewById(R.id.lst);
        for (int i = 0; i < 20; i++) {
            mList.add("" + i);
        }
        lstAdapter =new LstAdapter(this,mList);
        ls.setPullLoadEnable(true);
        ls.setPullRefreshEnable(true);
        ls.setXListViewListener(this);
        ls.setAdapter(lstAdapter);
    }

    private void ry() {
        ry = ((TouchCallbackRecyclerView) findViewById(R.id.main_ry_load));
        TextView tv = ((TextView) findViewById(R.id.main_ry_tv));

        for (int i = 0; i < 20; i++) {
            mList.add("" + i);
        }
        adapter = new RecyerLoadAdapter(this, mList);
        adapter.setOnItemClickListener(new CommonRecycleAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(int position) {
                ToastUlit.Toast(RecyerActivity.this,mList.get(position));
            }

            @Override
            public void onItemLongClickListener(View view, int position) {

            }
        });
        ry.setLayoutManager(new LinearLayoutManager(this));
        ry.setAdapter(adapter);
        adapter.addFootView(R.layout.foot_item);
        ry.setOnLoadMoreCallback(new TouchCallbackRecyclerView.OnLoadMoreCallback() {
            @Override
            public void onLoadListernr() {
//                for (int i = 0; i < 20; i++) {
//                    mList.add(""+i);
//                }
                adapter.notifyDataSetChanged();
            }
        });
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.removeFoot();
            }
        });
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMore() {

    }
}
