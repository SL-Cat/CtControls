package cat.customize.binner.holder;

import android.content.Context;
import android.view.View;

/**
 * Created by HSL
 * on 2023/3/2.
 */

public interface BannerViewHolder<T> {
    /**
     * 创建View
     * @param context
     * @return
     */
    View createView(Context context);

    /**
     * 绑定数据
     * @param context
     * @param position
     * @param data
     */
    void onBind(Context context, int position, T data);
}
