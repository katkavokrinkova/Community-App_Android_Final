package net.impacthub.app.ui.base;

import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.ColorRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import net.impacthub.app.R;
import net.impacthub.app.presenter.base.UiContract;
import net.impacthub.app.presenter.base.UiPresenter;
import net.impacthub.app.ui.widgets.TypefaceToolbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/1/2017.
 */

public abstract class BaseActivity<P extends UiPresenter<? extends UiContract>> extends AppCompatActivity {

    @Nullable @BindView(R.id.toolbar) protected TypefaceToolbar mToolbar;
    private ProgressDialog mProgressDialog;

    private P mPresenter;
    private Unbinder mBinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentView());
        mBinder = ButterKnife.bind(this);
        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
        }
        mPresenter = onCreatePresenter();
        if (mPresenter != null) {
            mPresenter.registerUi();
        }
        onActivityCreated(savedInstanceState);
    }

    @CallSuper
    protected void onActivityCreated(Bundle savedInstanceState) {
    }

    protected P onCreatePresenter() {
        return null;
    }

    public P getPresenter() {
        return mPresenter;
    }

    protected void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    protected void setStatusBarColor(@ColorRes int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && color > 0) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(ContextCompat.getColor(this, color));
        }
    }

    protected void replaceFragment(int containerId, Fragment fragment, String tag) {
        getSupportFragmentManager().beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .replace(containerId, fragment)
                .commit();
    }

    public void showDialog(String message) {
        createDialog();
        mProgressDialog.setMessage(message);
        mProgressDialog.show();
    }

    public void dismissDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    private void createDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mProgressDialog.setCancelable(true);
        }
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
