package net.impacthub.members.ui.base;

import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.ColorRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import net.impacthub.members.R;
import net.impacthub.members.presenter.base.UiContract;
import net.impacthub.members.presenter.base.UiPresenter;
import net.impacthub.members.ui.widgets.TypefaceToolbar;
import net.impacthub.members.utilities.ViewUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/1/2017.
 */

public abstract class BaseFragment<P extends UiPresenter<? extends UiContract>> extends Fragment {

    @Nullable @BindView(R.id.toolbar) protected TypefaceToolbar mToolbar;
    @BindView(R.id.progress_bar) protected ProgressBar mProgressBar;

    private P mPresenter;
    private View mRootView;
    private Unbinder mBinder;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getParentFragment() == null) {
            setRetainInstance(true);
        }
        mPresenter = onCreatePresenter();
        if (mPresenter != null) {
            mPresenter.registerUi();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (container != null) {
            container.clearDisappearingChildren();
        }
        if (mRootView == null) {
            mRootView = inflater.inflate(R.layout.fragment_container, container, false);
            FrameLayout contentContainer = (FrameLayout) mRootView.findViewById(R.id.content_container);
            contentContainer.removeAllViews();
            inflater.inflate(getContentView(), contentContainer, true);
            mBinder = ButterKnife.bind(this, mRootView);
            bindView(mRootView);
        }
        return mRootView;
    }

    @CallSuper
    protected void bindView(View rootView) {
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

    public void onShowProgressBar(boolean showProgressBar) {
        if(showProgressBar && mProgressBar != null) ViewUtils.visible(mProgressBar);
        else if(mProgressBar != null) ViewUtils.gone(mProgressBar);
    }

    public void onError(Throwable throwable) {
        showToast(throwable.getMessage());
    }

    @Override
    public void onDestroy() {
        if (mPresenter != null) {
            mPresenter.unregisterUi();
        }
        if (mBinder != null) {
            mBinder.unbind();
        }
        mPresenter = null;
        mBinder = null;
        super.onDestroy();
    }

    protected abstract int getContentView();
}
