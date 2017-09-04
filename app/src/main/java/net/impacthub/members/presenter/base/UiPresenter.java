package net.impacthub.members.presenter.base;

import com.salesforce.androidsdk.accounts.UserAccount;

import java.lang.ref.WeakReference;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

import static net.impacthub.members.application.salesforce.SalesforceModuleDependency.userAccountProvider;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 7/26/2017.
 */

public abstract class UiPresenter<UI extends UiContract> {

    private final UserAccount mUserAccount = userAccountProvider();
    private WeakReference<UI> mUiReference;
    private CompositeDisposable mCompositeDisposable;
    private boolean mFetching;

    public UiPresenter(UI uiContract) {
        mUiReference = new WeakReference<>(uiContract);
    }

    protected UserAccount getUserAccount() {
        return mUserAccount;
    }

    public void registerUi() {
        mCompositeDisposable = new CompositeDisposable();
    }

    public void setFetching(boolean fetching) {
        mFetching |= fetching;
    }

    public UI getUi() {
        return mUiReference.get();
    }

    protected <D> void subscribeWith(Single<D> single, DisposableSingleObserver<D> singleObserver) {
        getCompositeDisposable().add(
                single.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.computation())
                .cache()
                .subscribeWith(singleObserver));
    }

    private CompositeDisposable getCompositeDisposable() {
        if (mCompositeDisposable == null || mCompositeDisposable.isDisposed()) {
            mCompositeDisposable = new CompositeDisposable();
        }
        return mCompositeDisposable;
    }

    public void unregisterUi() {
        if (mCompositeDisposable != null && !mCompositeDisposable.isDisposed()) {
            mCompositeDisposable.dispose();
        }
        mUiReference = null;
    }

    public boolean isFetching() {
        return mFetching;
    }
}
