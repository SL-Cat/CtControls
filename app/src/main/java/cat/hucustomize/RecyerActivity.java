package cat.hucustomize;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

import cat.customize.recyler.TouchCallbackRecyclerView;
import cat.hucustomize.adapter.RecyerLoadAdapter;

public class RecyerActivity extends AppCompatActivity {

    private TouchCallbackRecyclerView ry;

    private RecyerLoadAdapter adapter;

    private List<String> mList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyer);
        ry = ((TouchCallbackRecyclerView) findViewById(R.id.main_ry_load));
        for (int i = 0; i < 20; i++) {
            mList.add(""+i);
        }
        adapter = new RecyerLoadAdapter(this,mList);
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
    }
}
