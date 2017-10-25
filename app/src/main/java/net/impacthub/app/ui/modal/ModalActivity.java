/*
 * Copyright (c) 2017 Lightful. All Rights Reserved.
 *
 * Save to the extent permitted by law, you may not use, copy, modify,
 * distribute or create derivative works of this material or any part
 * of it without the prior written consent of Lightful.
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 */

package net.impacthub.app.ui.modal;

import net.impacthub.app.R;
import net.impacthub.app.presenter.base.UiContract;
import net.impacthub.app.presenter.base.UiPresenter;
import net.impacthub.app.ui.base.BaseActivity;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/30/2017.
 */

public abstract class ModalActivity<P extends UiPresenter<? extends UiContract>> extends BaseActivity<P> {

    @Override
    protected void onResume() {
        overridePendingTransition(R.anim.slide_up, R.anim.stay);
        super.onResume();
    }

    @Override
    protected void onPause() {
        overridePendingTransition(R.anim.stay, R.anim.slide_down);
        super.onPause();
    }
}
