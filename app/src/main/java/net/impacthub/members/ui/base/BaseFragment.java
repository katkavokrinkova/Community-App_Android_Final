package net.impacthub.members.ui.base;

import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import net.impacthub.members.presenter.base.UiContract;
import net.impacthub.members.presenter.base.UiPresenter;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/1/2017.
 */

public class BaseFragment<P extends UiPresenter<? extends UiContract>> extends Fragment {

    private P mPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = onCreatePresenter();
        if (mPresenter != null) {
            mPresenter.registerUi();
        }
    }

    protected void setStatusBarColor(@ColorRes int color) {
        BaseActivity activity = (BaseActivity) getActivity();
        if (activity != null) {
            activity.setStatusBarColor(color);
        }
    }

    protected void showToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    protected P onCreatePresenter() {
        return null;
    }

    public P getPresenter() {
        return mPresenter;
    }

    public void onError(Throwable throwable) {
        showToast(throwable.getMessage());
    }

    @Override
    public void onDestroy() {
        if (mPresenter != null) {
            mPresenter.unregisterUi();
        }
        mPresenter = null;
        super.onDestroy();
    }
}
