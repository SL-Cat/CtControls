package cat.hucustomize.list;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.List;

import cat.customize.recyler.CommonRecycleAdapter;
import cat.customize.recyler.ItemLongClickMaskHelper;
import cat.customize.recyler.BaseRecyclerView;
import cat.customize.recyler.TouchCallbackRecyclerView;
import cat.customize.ulite.system.AndroidUtils;
import cat.hucustomize.R;
import cat.hucustomize.ToastUlit;
import cat.hucustomize.adapter.RecyerLoadAdapter;

public class RecyerActivity extends AppCompatActivity {

    private TouchCallbackRecyclerView ry;

    private RecyerLoadAdapter adapter;


    private List<String> mList = new ArrayList<>();
    private ItemLongClickMaskHelper clickMaskHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyer);
        for (int i = 0; i < 30; i++) {
            mList.add("" + i);
        }
        ry();
    }

    private int num = 0;

    private void ry() {
        ry = ((TouchCallbackRecyclerView) findViewById(R.id.main_ry_load));
        View maskView = getLayoutInflater().inflate(R.layout.ct_item_laod_more,null);
        clickMaskHelper = new ItemLongClickMaskHelper(this,maskView);

        adapter = new RecyerLoadAdapter(this, mList);
        adapter.setOnItemClickListener(new CommonRecycleAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(int position) {
                ToastUlit.Toast(RecyerActivity.this, mList.get(position));
            }

            @Override
            public void onItemLongClickListener(View view, int position) {
                clickMaskHelper.dismissMaskLayout(); //关闭之前的
                clickMaskHelper.setRootFrameLayout((FrameLayout) view, position);
            }
        });
        ry.setLayoutManager(new GridLayoutManager(this,2));
        ry.setAdapter(adapter);
        ry.setIsCanLoadMore(true);
        ry.setIsCanRefresh(true);
        ry.setIsCanSwipe(true);
        ry.setOnRefreshAndLoadListener(new TouchCallbackRecyclerView.RefreshOrLoadMoreListener() {
            @Override
            public void onRefreshStart() {
                Log.d("myDemo", "onRefreshStart: ");
                AndroidUtils.MainHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ry.stopRefreshOrLoadMore();
                    }
                },5000);
            }

            @Override
            public void onLoadMoreStart() {
                if (num < 5) {
                    Log.d("myDemo", "onLoadMoreStart: ");
                    num++;
                    AndroidUtils.MainHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mList.add("num：" + num);
                            adapter.notifyDataSetChanged();
                            ry.stopRefreshOrLoadMore();
                        }
                    },5000);
                } else {
                    ry.stopRefreshOrLoadMore();
                    ry.setIsCanLoadMore(false);
                    Log.d("myDemo", "end: ");
                }
            }
        });
        ry.setScrollCallback(new BaseRecyclerView.ScrollCallback() {
            @Override
            public void onTouchUp(float diffY) {
                clickMaskHelper.dismissMaskLayout();
            }
        });
        clickMaskHelper.setMaskItemListener(new ItemLongClickMaskHelper.ItemMaskClickListener() {
            @Override
            public void fristBtn(int positon) {
            }

            @Override
            public void secondBtn(int positon) {

            }

            @Override
            public void threeBtn(int positon) {

            }
        });
    }

}
