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

    private CreateFragUlite(AppCompatActivity activity,int layoutId) {
        this.activity = activity;
        this.layoutId = layoutId;
    }

    public static CreateFragUlite getInstance(AppCompatActivity activity,int layoutId) {
        synchronized (CreateFragUlite.class) {
            if (instance == null) {
                instance = new CreateFragUlite(activity,layoutId);
            }
            return instance;
        }
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
        fragmentTransaction.addToBackStack(null);
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
