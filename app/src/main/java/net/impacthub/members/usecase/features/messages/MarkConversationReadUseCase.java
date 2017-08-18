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

import net.impacthub.members.model.features.messages.ReadSet;
import net.impacthub.members.usecase.base.BaseUseCaseGenerator;

import java.util.concurrent.Callable;

import io.reactivex.Single;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/18/2017.
 */

public class MarkConversationReadUseCase extends BaseUseCaseGenerator<Single<ReadSet>, ReadSet> {

    private final String mConversationId;

    public MarkConversationReadUseCase(String conversationId) {
        mConversationId = conversationId;
    }

    @Override
    public Single<ReadSet> getUseCase() {
        return Single.fromCallable(new Callable<ReadSet>() {
            @Override
            public ReadSet call() throws Exception {
                return getApiCall().getResponse(getSoqlRequestFactory().createMarkConversationReadRequest(getUserAccount().getCommunityId(), mConversationId), ReadSet.class);
            }
        });
    }
}
