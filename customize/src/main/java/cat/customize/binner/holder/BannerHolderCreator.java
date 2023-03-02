package cat.customize.binner.holder;

/**
 * Created by HSL
 * on 2023/3/2.
 */


public interface BannerHolderCreator<VH extends BannerViewHolder> {
    /**
     * 创建ViewHolder
     * @return
     */
    VH createViewHolder();
}
