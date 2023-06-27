package cat.hucustomize.frg;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import cat.customize.datepicker.CustomDatePicker;
import cat.customize.datepicker.DatePickerView;
import cat.customize.ulite.DateUtils;
import cat.customize.ulite.system.CtLog;
import cat.hucustomize.MainActivity;
import cat.hucustomize.R;

/**
 * Created by HSL
 * on 2023/3/13.
 */

public class SelectFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View logView = inflater.inflate(R.layout.select_layout, container, false);
        initView(logView);
        return logView;
    }

    private void initView(View logView) {
        logView.findViewById(R.id.ct_select_day).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long timeGetTime = new Date().getTime();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
                String nowTime = sdf.format(timeGetTime);
                final CustomDatePicker customDatePicker = new CustomDatePicker(getActivity(), new CustomDatePicker.ResultHandler() {
                    @Override
                    public void handle(String time) {// 回调接口，获得选中的时间
                        CtLog.d(time);
                    }
                }, "2020-01-01 00:00:00", nowTime);// 初始化日期格式请用：yyyy-MM-dd HH:mm，否则不能正常运行

                customDatePicker.showSpecificTime(true); // 显示时和分
                customDatePicker.setIsLoop(false); // 不允许循环滚动
                customDatePicker.show(DateUtils.getPreTime(DateUtils.getStringDate(), "-4320"));
            }
        });


        DatePickerView loopView = (DatePickerView) logView.findViewById(R.id.ct_select_loop);
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add("ITEM " + i);
        }
        loopView.setData(list);
        loopView.setOnSelectListener(new DatePickerView.onSelectListener() {
            @Override
            public void onSelect(String text) {

            }
        });
    }
}
