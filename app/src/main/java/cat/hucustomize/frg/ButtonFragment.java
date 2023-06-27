package cat.hucustomize.frg;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import cat.customize.radio.IRadiosButton;
import cat.customize.radio.ISwitchbutton;
import cat.customize.ui.ISelectButton;
import cat.hucustomize.R;
import cat.hucustomize.SecondActivity;
import cat.hucustomize.ToastUlit;

/**
 * Created by HSL
 * on 2023/3/13.
 */

public class ButtonFragment extends Fragment {


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View logView = inflater.inflate(R.layout.button_layout, container, false);
        initView(logView);
        return logView;
    }

    private void initView(final View logView) {
        final ISelectButton selectButton = (ISelectButton) logView.findViewById(R.id.ct_select_btn);
        selectButton.setChooesOnClickListener(new ISelectButton.IChooesOnClickListener() {
            @Override
            public void onClickChooesItemListerenr(int position) {
                selectButton.selectItem(position);
            }
        });
        final ISwitchbutton switchbutton = (ISwitchbutton) logView.findViewById(R.id.switch_btn);
        switchbutton.setmOnCheckedChangeListener(new ISwitchbutton.OnCheckedChangeListener() {
            @Override
            public void OnCheckedChanged(boolean isChecked) {
                if (isChecked) {
                    switchbutton.setText("2132132132131疯狂测试长度3333");
                } else {
                    switchbutton.setText(isChecked + "");
                }
            }
        });

        logView.findViewById(R.id.test_more_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IRadiosButton radiosButton = (IRadiosButton) logView.findViewById(R.id.ct_second_radios);
                radiosButton.setMaxLines(3);
                List<String> list = new ArrayList<>();
                for (int i = 0; i < 7; i++) {
                    list.add("Item"+i);
                }
                radiosButton.setButtonLits(list);
                radiosButton.setOnIRadiosItemClick(new IRadiosButton.OnIRadiosListener() {
                    @Override
                    public void onRadiosItemClick(int position, boolean isClick) {
                        ToastUlit.Toast(getActivity(), position + "");
                    }
                });
            }
        });
        logView.findViewById(R.id.test_more_btn_2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IRadiosButton radiosButton = (IRadiosButton) logView.findViewById(R.id.ct_second_radios);
                radiosButton.setMaxLines(2);
                List<String> list = new ArrayList<>();
                for (int i = 0; i < 3; i++) {
                    list.add("Item"+i);
                }
                radiosButton.setButtonLits(list);
                radiosButton.setOnIRadiosItemClick(new IRadiosButton.OnIRadiosListener() {
                    @Override
                    public void onRadiosItemClick(int position, boolean isClick) {
                        ToastUlit.Toast(getActivity(), position + "");
                    }
                });
            }
        });
    }
}
