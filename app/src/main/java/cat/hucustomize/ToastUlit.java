package cat.hucustomize;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by HSL on 2022/7/25.
 */

public class ToastUlit {

    public static void Toast(Context context,String str){
        android.widget.Toast.makeText(context,str, Toast.LENGTH_SHORT).show();
    }
}
