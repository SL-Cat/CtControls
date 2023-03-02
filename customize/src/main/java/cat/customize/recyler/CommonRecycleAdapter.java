package cat.customize.recyler;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;


/**
 * Created by HSL on 2020/4/2.
 */

public abstract class CommonRecycleAdapter<T> extends RecyclerView.Adapter<CommonViewHolder> {

    protected LayoutInflater layoutInflater;

    protected List<T> dataList;

    protected int layoutId;

    private int footLayoutId = -1;

    private int secondTime = 500;


    // 普通布局
    private final int TYPE_ITEM = 1;

    public CommonRecycleAdapter(Context context, List<T> dataList, int layoutId) {
        this.layoutInflater = LayoutInflater.from(context);
        this.dataList = dataList;
        this.layoutId = layoutId;
    }

    public interface OnItemClickListener {

        void onItemClickListener(int position);

        void onItemLongClickListener(View view, int position);

    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public int getItemViewType(int position) {
        return TYPE_ITEM;
    }

    @Override
    public CommonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(layoutId, parent, false);
        return new CommonViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CommonViewHolder holder, int position) {
        holder.setSecondTime(secondTime);
        holder.setCommonClickListener(new CommonViewHolder.onItemCommonClickListener() {
            @Override
            public void onItemClickListener(int position) {
                if (onItemClickListener != null)
                    onItemClickListener.onItemClickListener(position);
            }

            @Override
            public void onItemLongClickListener(View v, int position) {
                if (onItemClickListener != null)
                    onItemClickListener.onItemLongClickListener(v, position);
            }
        });
        bindData(holder, dataList, position);
    }

    @Override
    public int getItemCount() {
        if (footLayoutId >= 0) {
            return dataList.size() + 1;
        } else {
            return dataList.size();
        }
    }

    /**
     * 设置连续点击的时间
     *
     * @param secondTime
     */
    public void setSecondTime(int secondTime) {
        this.secondTime = secondTime;
    }


    protected abstract void bindData(CommonViewHolder holder, List<T> data, int position);

}
