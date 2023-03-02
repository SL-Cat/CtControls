package cat.hucustomize.list;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.ScaleAnimation;
import android.widget.FrameLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cat.customize.bean.MaskItemBean;
import cat.customize.listener.OnCtItemMaskHelperClickListener;
import cat.customize.recyler.BaseRecyclerView;
import cat.customize.recyler.CommonRecycleAdapter;
import cat.customize.recyler.ItemMaskRadioHelper;
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
    private ItemMaskRadioHelper helper;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_recyer, container, false);
        for (int i = 0; i < 10; i++) {
            mList.add("" + i);
        }
        ry(view);
        return view;
    }

    private int num = 0;

    private void ry(View view) {
        ry = ((TouchCallbackRecyclerView) view.findViewById(R.id.main_ry_load));
        helper = new ItemMaskRadioHelper(getActivity());
        //可不用
        helper.setMaskBackgroundRes(R.drawable.ct_mask_radius_bg);

        adapter = new RecyerLoadAdapter(getActivity(), mList);
        adapter.setSecondTime(0);
        adapter.setOnItemClickListener(new CommonRecycleAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(int position) {
                Log.d("myDemo", "onItemClickListener: "+position);
                ToastUlit.Toast(getActivity(), mList.get(position));
            }

            @Override
            public void onItemLongClickListener(View view, int position) {
                    //设置Item内容
                if (position > 3 && position < 6) {
                    List<MaskItemBean> maskItemBeans = new ArrayList<>();
                    maskItemBeans.add(new MaskItemBean("删除",R.color.color_FFC107,25, 1));
                    maskItemBeans.add(new MaskItemBean("审核",R.color.color_ff0000,25, 1));
                    maskItemBeans.add(new MaskItemBean("重置",R.color.color_007BFF,25, 1));
                    helper.setItems(maskItemBeans);
                } else  {
                    List<MaskItemBean> maskItemBeans = new ArrayList<>();
                    maskItemBeans.add(new MaskItemBean("删除", R.color.color_FFC107, 1));
                    maskItemBeans.add(new MaskItemBean("审核", R.color.color_ff0000, 1));
                    helper.setItems(maskItemBeans);
                }
                helper.dismissMaskLayout(); //关闭之前的
                //设置自定义动画;可不设置
                ScaleAnimation scaleAnimation = new ScaleAnimation(0.1f, 1.0f, 0.1f, 1.0f);
                scaleAnimation.setDuration(200);
                helper.setRootFrameLayout((FrameLayout) view, position, scaleAnimation);
            }
        });
        ry.setLayoutManager(new LinearLayoutManager(getActivity()));
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
                }, 1000);
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
                    }, 1000);
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
                helper.dismissMaskLayout();
            }
        });
        helper.setOnCtItemMaskHelperClickListener(new OnCtItemMaskHelperClickListener() {
            @Override
            public void onItemClick(int position, int index ) {
                Toast.makeText(getActivity(), "index" + index+": position "+position, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
