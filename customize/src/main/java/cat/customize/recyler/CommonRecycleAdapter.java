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

    // 普通布局
    private final int TYPE_ITEM = 1;
    // 脚布局
    private final int TYPE_FOOTER = 2;


    public CommonRecycleAdapter(Context context, List<T> dataList, int layoutId) {
        this.layoutInflater = LayoutInflater.from(context);
        this.dataList = dataList;
        this.layoutId = layoutId;
    }

    public void addFootView(int footViewId) {
        this.footLayoutId = footViewId;
    }

    public void removeFoot() {
        footLayoutId = -1;
        notifyDataSetChanged();
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
        if (footLayoutId > 0) {
            if (position + 1 == getItemCount()) {
                return TYPE_FOOTER;
            } else {
                return TYPE_ITEM;
            }
        } else {
            return TYPE_ITEM;
        }
    }

    @Override
    public CommonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = null;
        switch (viewType) {
            case TYPE_ITEM:
                itemView = layoutInflater.inflate(layoutId, parent, false);
                break;
            case TYPE_FOOTER:
                itemView = layoutInflater.inflate(footLayoutId, parent, false);
                break;
            default:
                itemView = layoutInflater.inflate(layoutId, parent, false);
                break;
        }
        return new CommonViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CommonViewHolder holder, int position) {
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
        if (footLayoutId > 0) {
            if (position > dataList.size()) {
                bindData(holder, dataList, position);
            }
        } else {
            bindData(holder, dataList, position);
        }
    }

    @Override
    public int getItemCount() {
        if (footLayoutId >= 0) {
            return dataList.size() + 1;
        } else {
            return dataList.size();
        }
    }


    protected abstract void bindData(CommonViewHolder holder, List<T> data, int position);

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = ((GridLayoutManager) manager);
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    // 如果当前是footer的位置，那么该item占据2个单元格，正常情况下占据1个单元格
                    return getItemViewType(position) == TYPE_FOOTER ? gridManager.getSpanCount() : 1;
                }
            });
        }
    }
}
