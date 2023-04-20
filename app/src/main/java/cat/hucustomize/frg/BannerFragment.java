package cat.hucustomize.frg;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import cat.customize.binner.CtBannerView;
import cat.customize.binner.holder.BannerHolderCreator;
import cat.customize.binner.holder.BannerPageClickListener;
import cat.customize.binner.holder.BannerViewHolder;
import cat.hucustomize.R;

/**
 * Created by HSL
 * on 2023/3/3.
 */

public class BannerFragment  extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.banner_layout,container,false);
        initView(view);
        return view;
    }
    public static final int []RES_ARRAY = new int[]{R.drawable.icon_1,R.drawable.icon_2,R.drawable.icon_1,R.drawable.icon_2,R.drawable.icon_1,R.drawable.icon_2};

    private void initView(View view) {
        CtBannerView bannerView = (CtBannerView) view.findViewById(R.id.banner_gallery_radius);
        bannerView.setBannerPageClickListener(new BannerPageClickListener() {
            @Override
            public void onPageClick(View view, int position) {
                Toast.makeText(getActivity(),"点击:"+position,Toast.LENGTH_LONG).show();
            }
        });
        bannerView.addPageChangeLisnter(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        List<Integer> bannerList = new ArrayList<>();
        for(int i=0;i<RES_ARRAY.length;i++){
            bannerList.add(RES_ARRAY[i]);
        }
        bannerView.setIndicatorVisible(true);
        bannerView.setPages(bannerList, new BannerHolderCreator<ViewHolderRadius>() {
            @Override
            public ViewHolderRadius createViewHolder() {
                return new ViewHolderRadius();
            }
        });
        bannerView.start();
    }

    /**
     * 圆角
     */
    public static class ViewHolderRadius implements BannerViewHolder<Integer> {
        private ImageView mImageView;
        @Override
        public View createView(Context context) {
            // 返回页面布局文件
            View view = LayoutInflater.from(context).inflate(R.layout.banner_item_radius,null);
            mImageView = (ImageView) view.findViewById(R.id.banner_image);
            return view;
        }

        @Override
        public void onBind(Context context, int position, Integer data) {
            // 数据绑定
//            mImageView.setImageResource(data);
            Glide.with(context).load(data).into(mImageView);

        }
    }

}
