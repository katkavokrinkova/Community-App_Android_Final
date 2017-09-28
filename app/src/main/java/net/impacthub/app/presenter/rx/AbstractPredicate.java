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

package net.impacthub.app.presenter.rx;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Predicate;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/30/2017.
 */

public abstract class AbstractPredicate<S, T> implements Predicate<T> {

    private final S mSubject;

    public AbstractPredicate(S subject) {
        mSubject = subject;
    }

    @Override
    public boolean test(@NonNull T t) throws Exception {
        return test(t, mSubject);
    }

    protected abstract boolean test(T t, S subject);
}
