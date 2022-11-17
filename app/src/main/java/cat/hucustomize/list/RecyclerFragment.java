package cat.hucustomize.list;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.List;

import cat.customize.recyler.BaseRecyclerView;
import cat.customize.recyler.CommonRecycleAdapter;
import cat.customize.recyler.ItemLongClickMaskHelper;
import cat.customize.recyler.TouchCallbackRecyclerView;
import cat.customize.ulite.system.AndroidUtils;
import cat.hucustomize.R;
import cat.hucustomize.ToastUlit;
import cat.hucustomize.adapter.RecyerLoadAdapter;

/**
 * Created by HSL
 * on 2022/11/10.
 */

public class RecyclerFragment extends Fragment {
    private TouchCallbackRecyclerView ry;

    private RecyerLoadAdapter adapter;


    private List<String> mList = new ArrayList<>();
    private ItemLongClickMaskHelper clickMaskHelper;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_recyer,container,false);
        for (int i = 0; i < 30; i++) {
            mList.add("" + i);
        }
        ry(view);
        return view;
    }

    private int num = 0;

    private void ry(View view) {
        ry = ((TouchCallbackRecyclerView) view.findViewById(R.id.main_ry_load));
        View maskView = getLayoutInflater().inflate(R.layout.ct_item_laod_more,null);
        clickMaskHelper = new ItemLongClickMaskHelper(getActivity(),maskView);

        adapter = new RecyerLoadAdapter(getActivity(), mList);
        adapter.setOnItemClickListener(new CommonRecycleAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(int position) {
                ToastUlit.Toast(getActivity(), mList.get(position));
            }

            @Override
            public void onItemLongClickListener(View view, int position) {
                clickMaskHelper.dismissMaskLayout(); //关闭之前的
                clickMaskHelper.setRootFrameLayout((FrameLayout) view, position);
            }
        });
        ry.setLayoutManager(new GridLayoutManager(getActivity(),2));
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
