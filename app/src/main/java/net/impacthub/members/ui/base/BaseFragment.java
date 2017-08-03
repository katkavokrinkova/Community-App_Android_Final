package net.impacthub.members.ui.base;

import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
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
        View parent = inflater.inflate(R.layout.fragment_container, container, false);
        FrameLayout contentContainer = (FrameLayout) parent.findViewById(R.id.content_container);
        contentContainer.removeAllViews();
        inflater.inflate(getContentView(), contentContainer, true);
        mBinder = ButterKnife.bind(this, parent);
        return parent;
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

    protected TextView createTabTitle(String label) {
        TextView tabTitleView = (TextView) getLayoutInflater(getArguments()).inflate(R.layout.tab_title_textview, new LinearLayout(getContext()), false);
        tabTitleView.setText(label);
        return tabTitleView;
    }

    protected P onCreatePresenter() {
        return null;
    }

    public P getPresenter() {
        return mPresenter;
    }

    public void onChangeStatus(boolean showProgressBar) {
        if(showProgressBar) ViewUtils.visible(mProgressBar);
        else ViewUtils.gone(mProgressBar);
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
