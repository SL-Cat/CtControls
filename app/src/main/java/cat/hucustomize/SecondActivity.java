package cat.hucustomize;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import cat.customize.radio.IRadiosButton;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        radios();
    }

    private void radios() {
        IRadiosButton radiosButton = (IRadiosButton) findViewById(R.id.ct_second_radios);
        radiosButton.setClickType(true);
        List<String> list = new ArrayList<>();
        list.add("新的1");
        list.add("新的2");
        list.add("新的3");
        list.add("新的4");
        radiosButton.setButtonLits(list);
        radiosButton.setOnIRadiosItemClick(new IRadiosButton.OnIRadiosListener() {
            @Override
            public void onRadiosItemClick(int position, boolean isClick) {
                ToastUlit.Toast(SecondActivity.this, position + "");
            }
        });
    }
}