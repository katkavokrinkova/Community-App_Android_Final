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
import io.reactivex.functions.Function;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/18/2017.
 */

public abstract class AbstractFunction<S, R, O> implements Function<R, O> {

    private final S mSubject;

    protected AbstractFunction(S subject) {
        mSubject = subject;
    }

    @Override
    public O apply(@NonNull R response) throws Exception {
        return apply(response, mSubject);
    }

    protected abstract O apply(R response, S subject);
}
