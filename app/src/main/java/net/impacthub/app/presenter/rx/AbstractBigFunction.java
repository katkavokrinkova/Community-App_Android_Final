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
import io.reactivex.functions.BiFunction;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/18/2017.
 */

public abstract class AbstractBigFunction<S, T1, T2, R> implements BiFunction<T1, T2, R> {

    private final S mSubject;

    public AbstractBigFunction(S subject) {
        mSubject = subject;
    }

    @Override
    public R apply(@NonNull T1 t1, @NonNull T2 t2) throws Exception {
        return apply(t1, t2, mSubject);
    }

    protected abstract R apply(T1 t1, T2 t2, S subject);
}
