package cat.hucustomize;

import android.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import cat.customize.DateUtils;
import cat.customize.datepicker.CustomDatePicker;
import cat.customize.radio.IRadiosButton;
import cat.customize.radio.loop.ILoopView;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        radios();
        loopView();
    }


    private void loopView() {
        ILoopView loopView = (ILoopView) findViewById(R.id.ct_second_loop);
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add("ITEM " + i);
        }
        loopView.setItems(list);
    }

    private void radios() {
        IRadiosButton radiosButton = (IRadiosButton) findViewById(R.id.ct_second_radios);
        radiosButton.setClickType(true);
        List<String> list = new ArrayList<>();
        list.add("新的1");
        list.add("新的2");
        list.add("新的3");
        radiosButton.setButtonLits(list);
        radiosButton.setOnIRadiosItemClick(new IRadiosButton.OnIRadiosListener() {
            @Override
            public void onRadiosItemClick(int position, boolean isClick) {
                ToastUlit.Toast(SecondActivity.this, position + "");
            }
        });
    }
}