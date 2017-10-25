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

package net.impacthub.app.ui.binder;

import android.content.Context;
import android.widget.Toast;

import net.impacthub.app.presenter.base.UiContract;
import net.impacthub.app.presenter.base.UiPresenter;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/17/2017.
 */

public abstract class ViewBinderAdapter<P extends UiPresenter<? extends UiContract>> implements ViewBinder {

    private Context mContext;
    private P mPresenter;

    protected void onCreate(Context context) {
        mContext = context;
        mPresenter = onCreatePresenter();
        if (mPresenter != null) {
            mPresenter.registerUi();
        }
    }

    protected P onCreatePresenter() {
        return null;
    }

    public P getPresenter() {
        return mPresenter;
    }

    public Context getContext() {
        return mContext;
    }

    @Override
    public void bindView(Object model) {
        //Not needed
    }

    protected void showToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    public void onDestroy() {
        if (mPresenter != null) {
            mPresenter.unregisterUi();
        }
        mPresenter = null;
        mContext = null;
    }
}
