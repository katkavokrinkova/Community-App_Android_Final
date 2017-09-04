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

package net.impacthub.members.presenter.rx;

import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableSingleObserver;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/31/2017.
 */

public abstract class DisposableSingleObserverAdapter<S, T> extends DisposableSingleObserver<T> {

    private final S mSubject;

    public DisposableSingleObserverAdapter(S subject) {
        mSubject = subject;
    }

    @Override
    public void onSuccess(@NonNull T t) {
        onSuccess(t, mSubject);
    }

    protected abstract void onSuccess(T t, S subject);
}
