package cat.customize.fragment;

import cat.customize.dialog.CtLoadDialog;
import cat.customize.dialog.CtToastDialog;

/**
 * Created by HSL
 * on 2023/4/17.
 */

public interface CtUiHelperListener {

    CtLoadDialog createDialog();

    CtToastDialog createToast();



}
