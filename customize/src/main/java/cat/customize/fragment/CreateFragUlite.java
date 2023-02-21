package cat.customize.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by HSL
 * on 2022/11/9.
 */

public class CreateFragUlite {

    private static CreateFragUlite instance;
    private AppCompatActivity activity;
    private int layoutId = -1;

    private CreateFragUlite(AppCompatActivity activity) {
        this.activity = activity;
    }

    public static CreateFragUlite getInstance(AppCompatActivity activity) {
        synchronized (CreateFragUlite.class) {
            if (instance == null) {
                instance = new CreateFragUlite(activity);
            }
            return instance;
        }
    }

    public void setParentActivity(int id) {
        this.layoutId = id;
    }

    public void addFragment(int id, Fragment fragment) {
        FragmentTransaction fragmentTransaction = activity.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(id, fragment);
        fragmentTransaction.commit();
    }

    public void addFragment(Fragment fragment) {
        if (layoutId == -1) return;
        FragmentTransaction fragmentTransaction = activity.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.replace(layoutId, fragment);
        fragmentTransaction.commit();
    }

    public void addFragment(Fragment fragment,boolean isStack) {
        if (layoutId == -1) return;
        FragmentTransaction fragmentTransaction = activity.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        if(isStack) {
            fragmentTransaction.addToBackStack(null);
        }
        fragmentTransaction.add(layoutId, fragment);
        fragmentTransaction.commit();
    }


}
