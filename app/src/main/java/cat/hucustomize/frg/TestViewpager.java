package cat.hucustomize.frg;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cat.customize.xlist.SmartScrollView;
import cat.hucustomize.R;

/**
 * Created by HSL
 * on 2023/5/9.
 */

public class TestViewpager extends Fragment {

    private SmartScrollView scrollView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.test_viewpager_layout, container, false);
        initView(view);
        return view;
    }

    int y = 0;

    private void initView(View view) {
        scrollView = (SmartScrollView) view.findViewById(R.id.smart_sv);
        scrollView.start();
        scrollView.setScanScrollChangedListener(new SmartScrollView.ISmartScrollChangedListener() {
            @Override
            public void onScrolledToButtom() {
            }

            @Override
            public void onScrolledToTop() {

            }

            @Override
            public void onScrolledToMedi() {

            }
        });


    }
}
