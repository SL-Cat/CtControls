package cat.hucustomize.list;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cat.customize.recyler.CommonRecycleAdapter;
import cat.customize.recyler.TouchCallbackRecyclerView;
import cat.customize.xlist.XListView;
import cat.hucustomize.R;
import cat.hucustomize.ToastUlit;
import cat.hucustomize.adapter.LstAdapter;
import cat.hucustomize.adapter.RecyerLoadAdapter;

public class RecyerActivity extends AppCompatActivity {

    private TouchCallbackRecyclerView ry;

    private RecyerLoadAdapter adapter;


    private List<String> mList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyer);
        for (int i = 0; i < 20; i++) {
            mList.add("" + i);
        }
        ry();
    }



    private void ry() {
        ry = ((TouchCallbackRecyclerView) findViewById(R.id.main_ry_load));

        adapter = new RecyerLoadAdapter(this, mList);
        adapter.setOnItemClickListener(new CommonRecycleAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(int position) {
                ToastUlit.Toast(RecyerActivity.this, mList.get(position));
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
                adapter.notifyDataSetChanged();
            }
        });
    }

}
