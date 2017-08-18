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

package net.impacthub.members.usecase.features.messages;

import net.impacthub.members.model.features.messages.Id;
import net.impacthub.members.usecase.base.BaseUseCaseGenerator;

import java.util.concurrent.Callable;

import io.reactivex.Single;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/18/2017.
 */

public class SendMessageUseCase extends BaseUseCaseGenerator<Single<Id>, Id> {

    private final String mMessage;
    private final String mInReplyTo;

    public SendMessageUseCase(String message, String inReplyTo) {
        mMessage = message;
        mInReplyTo = inReplyTo;
    }

    @Override
    public Single<Id> getUseCase() {
        return Single.fromCallable(new Callable<Id>() {
            @Override
            public Id call() throws Exception {
                return getApiCall().getResponse(getSoqlRequestFactory().createSendMessageRequest(getUserAccount().getCommunityId(), mMessage, mInReplyTo), Id.class);
            }
        });
    }
}
