package cat.customize.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HSL
 * on 2022/11/9.
 */

public class CreateFragUlite {

    private static CreateFragUlite instance;
    private AppCompatActivity activity;
    private int layoutId = -1;
    public  List<Fragment> fragmentList = new ArrayList<>();

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

    public void addFragment(Fragment fragment) {
        if(layoutId == -1) return;
        addFragmentInfo(layoutId, fragment, true);
    }

    public void addFragment(int id, Fragment fragment) {
        addFragmentInfo(id,fragment, true);
    }

    public void addFragment(int id,Fragment fragment,boolean isStack) {
        addFragmentInfo(id, fragment, isStack);
    }

    public void addFragment(Fragment fragment,boolean isStack) {
        if (layoutId == -1) return;
        addFragmentInfo(layoutId, fragment, isStack);
    }

    public void replaceFragment(Fragment fragment) {
        if(layoutId == -1) return;
        replaceFragmentInfo(layoutId, fragment);
    }

    public void replaceFragment(int id, Fragment fragment) {
        replaceFragmentInfo(id,fragment);
    }

    private void replaceFragmentInfo(int id,Fragment fragment) {
        FragmentTransaction fragmentTransaction = activity.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.replace(id, fragment);
        fragmentTransaction.commit();
    }


    private void addFragmentInfo(int id, Fragment fragment, boolean isStack) {
        FragmentTransaction fragmentTransaction = activity.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        if(isStack) {
            fragmentTransaction.addToBackStack(fragment.getClass().getSimpleName());
            fragmentList.add(0,fragment);
        }
        fragmentTransaction.add(id, fragment);
        fragmentTransaction.commit();
    }


}
