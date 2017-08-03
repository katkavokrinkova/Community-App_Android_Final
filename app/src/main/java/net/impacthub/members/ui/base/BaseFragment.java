package net.impacthub.members.ui.base;

import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import net.impacthub.members.presenter.base.UiContract;
import net.impacthub.members.presenter.base.UiPresenter;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/1/2017.
 */

public abstract class BaseFragment<P extends UiPresenter<? extends UiContract>> extends Fragment {

    private P mPresenter;
    private Unbinder mBinder;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = onCreatePresenter();
        if (mPresenter != null) {
            mPresenter.registerUi();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getContentView(), container, false);
        mBinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        if (mBinder != null) {
            mBinder.unbind();
            mBinder = null;
        }
        super.onDestroyView();
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

    protected abstract int getContentView();
}
