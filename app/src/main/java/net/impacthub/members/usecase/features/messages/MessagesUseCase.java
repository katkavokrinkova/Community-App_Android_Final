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

import net.impacthub.members.model.features.messages.MessageResponse;
import net.impacthub.members.usecase.base.BaseUseCaseGenerator;

import java.util.concurrent.Callable;

import io.reactivex.Single;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/9/2017.
 */

public class MessagesUseCase extends BaseUseCaseGenerator<Single<MessageResponse>, MessageResponse> {

    @Override
    public Single<MessageResponse> getUseCase() {
        return Single.fromCallable(new Callable<MessageResponse>() {
            @Override
            public MessageResponse call() throws Exception {
                return getApiCall().getResponse(getSoqlRequestFactory().createMessageRequest(getUserAccount().getCommunityId()), MessageResponse.class);
            }
        });
    }
}
