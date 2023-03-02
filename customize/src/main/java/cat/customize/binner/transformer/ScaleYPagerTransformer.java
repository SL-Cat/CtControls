package cat.customize.binner.transformer;

import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * Created by HSL
 * on 2023/3/2.
 */


public class ScaleYPagerTransformer implements ViewPager.PageTransformer {
    private static final float MIN_SCALE = 0.85F;
    @Override
    public void transformPage(View page, float position) {

        if(position < -1){
            page.setScaleY(MIN_SCALE);
        }else if(position<= 1){
            //
            float scale = Math.max(MIN_SCALE,1 - Math.abs(position));
            page.setScaleY(scale);
            /*page.setScaleX(scale);

            if(position<0){
                page.setTranslationX(width * (1 - scale) /2);
            }else{
                page.setTranslationX(-width * (1 - scale) /2);
            }*/

        }else{
            page.setScaleY(MIN_SCALE);
        }
    }

}
