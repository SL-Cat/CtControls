package cat.customize.recyler;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import cat.customize.R;
import cat.customize.ulite.system.AndroidUtils;


/**
 * Created by HSL
 * on 2022/9/13.
 */

public class TouchCallbackRecyclerView extends FrameLayout {
    private BaseRecyclerView recyclerView;//展示数据的view
    private View footView, headView;
    private ImageView footImg, headImg;
    private RefreshOrLoadMoreListener listener;
    private ObjectAnimator imgAnim;
    private Context mContext;
    private RecyclerView.Adapter adapter;
    private SwipeRefreshLayout swipeLayout;//滑动的view
    private TextView footText, headText;
    private Object view;//自定义无数据的view
    private ProgressBar progressBar;//一开始加载数据的view
    private TextView textView;//默认展示无数据的view

    public TouchCallbackRecyclerView(Context context) {
        super(context);
        init(context);
    }

    public TouchCallbackRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public TouchCallbackRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    private void init(Context context) {
        this.mContext = context;
        footView = View.inflate(mContext, R.layout.ct_item_laod_more, null);
        footImg = footView.findViewById(R.id.iv_load_more);
        footText = footView.findViewById(R.id.tv_hint);
        headView = View.inflate(mContext, R.layout.ct_item_laod_more, null);
        headImg = headView.findViewById(R.id.iv_load_more);
        headText = headView.findViewById(R.id.tv_hint);
        initRecyclerView();
        initLoadingData();
    }

    private void initLoadingData() {
        progressBar = new ProgressBar(mContext);
        LayoutParams layoutParams = new LayoutParams(
                AndroidUtils.dp2px(mContext, 36), AndroidUtils.dp2px(mContext, 36));
        layoutParams.gravity = Gravity.CENTER_HORIZONTAL;
        layoutParams.setMargins(0,AndroidUtils.dp2px(mContext, 30), 0, 0);
        progressBar.setLayoutParams(layoutParams);
        showView(progressBar);
    }

    public void setErrorView(Object view) {
        if (this.view == null) {
            this.view = view;
        }
    }

    public void showNoDataView() {
        hideAllView();
        if (view != null && view instanceof View) {
            showView((View) view);
        } else {
            if (textView == null) {
                textView = new TextView(mContext);
                LayoutParams layoutParams = new LayoutParams(
                        LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                layoutParams.gravity = Gravity.CENTER_HORIZONTAL;
                textView.setLayoutParams(layoutParams);
                textView.setPadding(0, AndroidUtils.dp2px(getContext(), 16), 0, 0);
                textView.setGravity(Gravity.CENTER);
                if (view instanceof String) {
                    textView.setText((String) view);
                } else {
                    textView.setText("");
                }
            }
            showView(textView);
        }
    }

    private void showView(View view) {
        if (view != null && view.getParent() == null) {
            addView(view);
        }
        if (view != null && view.getVisibility() != VISIBLE) {
            view.setVisibility(VISIBLE);
        }
    }


    private BaseRecyclerView.ScrollCallback mScrollCallback;


    public void setScrollCallback(BaseRecyclerView.ScrollCallback callback) {
        this.mScrollCallback = callback;
    }

    private void initRecyclerView() {
        swipeLayout = new SwipeRefreshLayout(mContext);
        recyclerView = new BaseRecyclerView(mContext);
        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT);
        LayoutParams layoutParams1 = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        recyclerView.setLayoutParams(layoutParams1);
        recyclerView.setScrollCallback(new BaseRecyclerView.ScrollCallback() {
            @Override
            public void onTouchUp(float diffY) {
                mScrollCallback.onTouchUp(diffY);
            }
        });
        swipeLayout.setLayoutParams(layoutParams);
        swipeLayout.addView(recyclerView);
        swipeLayout.setFooterView(footView);
        swipeLayout.setHeaderView(headView);
        swipeLayout.setEnabled(false);
        swipeLayout.setOnPullRefreshListener(new SwipeRefreshLayout.OnPullRefreshListener() {
            @Override
            public void onRefresh() {
                if (listener != null) {
                    listener.onRefreshStart();
                }
                headImg.setImageResource(R.mipmap.ct_loading);
                showLoadMoreView(Style.HEAD);
                headText.setText(mContext.getString(R.string.ct_refreshing));
            }

            @Override
            public void onPullDistance(int distance) {

            }

            @Override
            public void onPullEnable(boolean enable) {
                if (enable) {
                    headText.setText(mContext.getString(R.string.ct_refresh_after_letting_go));
                    headImg.setImageResource(R.mipmap.ct_pull_refresh_arrow);
                } else {
                    headText.setText(mContext.getString(R.string.ct_pull_down_to_refresh));
                    headImg.setImageResource(R.mipmap.ct_pull_refresh_arrow);
                }
            }
        });
        swipeLayout.setOnPushLoadMoreListener(new SwipeRefreshLayout.OnPushLoadMoreListener() {
            @Override
            public void onLoadMore() {
                footImg.setImageResource(R.mipmap.ct_loading);
                footText.setText(mContext.getString(R.string.ct_is_loading));
                showLoadMoreView(Style.FOOT);
                if (listener != null) {
                    listener.onLoadMoreStart();
                }
            }

            @Override
            public void onPushDistance(int distance) {

            }

            @Override
            public void onPushEnable(boolean enable) {
                if (enable) {
                    footText.setText(mContext.getString(R.string.ct_load_after_letting_go));
                    footImg.setImageResource(R.mipmap.ct_pull_refresh_arrow);
                } else {
                    footText.setText(mContext.getString(R.string.ct_pull_up_loading));
                    footImg.setImageResource(R.mipmap.ct_pull_refresh_arrow);
                }
            }
        });
        showView(swipeLayout);
    }


    public void setLayoutManager(RecyclerView.LayoutManager layoutManager) {
        if (layoutManager != null) {
            recyclerView.setLayoutManager(layoutManager);
        }
    }

    public RecyclerView.LayoutManager getLayoutManager() {
        return recyclerView.getLayoutManager();
    }

    /**
     * 设置recycler的Adapter
     */
    public void setAdapter(RecyclerView.Adapter adapter) {
        hideAllView();
        if (adapter != null) {
            this.adapter = adapter;
            showRecyclerView();
        } else {
            showNoDataView();
        }
    }

    /**
     * 设置recycler的Adapter
     */
    public void setAdapterNormal(RecyclerView.Adapter adapter) {
        hideAllView();
        if (adapter == null) {
            showNoDataView();
            return;
        }
        this.adapter = adapter;
        recyclerView.setAdapter(adapter);
        if (adapter.getItemCount() > 0) {
            showRecyclerView();
        }
    }

    /**
     * 因为vlayout布局。需要手动启用
     */
    public void showVlayout() {
        showRecyclerView();
    }

    public RecyclerView.Adapter getAdapter() {
        return recyclerView.getAdapter();
    }

    private void showRecyclerView() {
        hideAllView();
        recyclerView.setAdapter(adapter);
    }

    private void hideAllView() {
        if (progressBar != null && progressBar.getParent() != null && progressBar.getVisibility() != GONE) {
            progressBar.setVisibility(GONE);
        }
        if (textView != null && textView.getParent() != null && textView.getVisibility() != GONE) {
            textView.setVisibility(GONE);
        }

        if (view != null && view instanceof View && ((View) view).getParent() != null && ((View) view).getVisibility() != GONE) {
            ((View) view).setVisibility(GONE);
        }
    }

    public void hideRlvView() {
        if (recyclerView != null && recyclerView.getParent() != null && recyclerView.getVisibility() != GONE) {
            recyclerView.setVisibility(GONE);
        }
    }

    public void showRlvView() {
        if (recyclerView != null && recyclerView.getParent() != null && recyclerView.getVisibility() != VISIBLE) {
            recyclerView.setVisibility(VISIBLE);
        }
    }

    public void notifyDataSetChanged() {
        if (adapter != null) {
            adapter.notifyDataSetChanged();
            if (adapter.getItemCount() <= 0) {
                showNoDataView();
            }
        } else {
            showNoDataView();
        }
    }

    public enum Style {
        HEAD,
        FOOT
    }

    private void showLoadMoreView(Style style) {
        RotateAnimation animation = new RotateAnimation(0, 360f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(1200);
        animation.setInterpolator(new LinearInterpolator());
        animation.setRepeatCount(Integer.MAX_VALUE);
        switch (style) {
            case HEAD:
                headImg.startAnimation(animation);
                ;
                break;
            case FOOT:
                footImg.startAnimation(animation);
                break;
        }


//        imgAnim = ObjectAnimator.ofFloat(imageView, ImageView.ROTATION,
//                imageView.getRotation(), imageView.getRotation() + 360);
//        imgAnim.setDuration(1200);
//        imgAnim.setRepeatCount(Integer.MAX_VALUE);
//        imgAnim.setInterpolator(new LinearInterpolator());
//        imgAnim.start();
    }


    public void setOnRefreshAndLoadListener(RefreshOrLoadMoreListener listener) {
        this.listener = listener;
    }

    @RequiresApi(api = Build.VERSION_CODES.FROYO)
    public void stopRefreshOrLoadMore() {
//        if (imgAnim != null && imgAnim.isRunning()) {
//            imgAnim.cancel();
//        }
        hideAllView();
        if (headImg.getAnimation() != null) {
            headImg.getAnimation().cancel();
        }
        if (footImg.getAnimation() != null) {
            footImg.getAnimation().cancel();
        }
        if (swipeLayout.isRefreshing()) {
            swipeLayout.setRefreshing(false);
        }
        swipeLayout.setLoadMore(false);
        if (recyclerView.getAdapter() == null) {
            showNoDataView();
        }
    }

    //设置是否可以上拉刷新，下拉加载 默认不可以
    public void setIsCanSwipe(boolean isEnable) {
        swipeLayout.setEnabled(isEnable);
    }

    //设置是否可以上拉加载  默认不可以
    public void setIsCanLoadMore(boolean isEnable) {
        swipeLayout.setIsCanLoadMore(isEnable);
    }

    public void showNoMoreView() {
        footText.setText(mContext.getString(R.string.ct_load_all));
        footImg.setVisibility(GONE);
        footView.setVisibility(VISIBLE);
        swipeLayout.setFooterView(footView);
    }

    //设置是否可以下拉刷新  默认可以
    public void setIsCanRefresh(boolean isEnable) {
        swipeLayout.setIsCanRefresh(isEnable);
    }

    public interface RefreshOrLoadMoreListener {
        void onRefreshStart();
        void onLoadMoreStart();
    }

    public void scrollToPosition(int pos) {
        recyclerView.scrollToPosition(pos);
    }


    public void addOnItemTouchListener(RecyclerView.OnItemTouchListener listener) {
        recyclerView.addOnItemTouchListener(listener);
    }
}

