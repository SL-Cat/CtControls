package cat.hucustomize.frg;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cat.hucustomize.R;

/**
 * Created by HSL
 * on 2023/3/13.
 */

public class ButtonFragment extends Fragment {


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View logView = inflater.inflate(R.layout.button_layout,container,false);
        initView(logView);
        return logView;
    }

    private void initView(View logView) {

    }
}
